package src.jsonParser;

import java.lang.reflect.Field;
import java.util.*;

public class JsonUtil {

    public static <T> String serialize(T obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder json = new StringBuilder("{");

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object value = field.get(obj);

                json.append("\"").append(key).append("\":");

                if (value == null) {
                    json.append("null");
                } else if (value instanceof String) {
                    json.append("\"").append(value).append("\"");
                } else if (value instanceof Integer || value instanceof Boolean || value instanceof Double || value instanceof Float || value instanceof Long) {
                    json.append(value);
                } else if (value instanceof List) {
                    json.append(serializeArray((List<?>) value));
                } else {
                    json.append(serialize(value));
                }
                json.append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

    public static String serializeArray(List<?> list) {
        if (list == null) {
            return "null";
        }
        StringBuilder jsonArray = new StringBuilder("[");

        for (Object item : list) {
            if (item == null) {
                jsonArray.append("null");
            } else if (item instanceof String) {
                jsonArray.append("\"").append(item).append("\"");
            } else if (item instanceof Integer || item instanceof Boolean || item instanceof Double || item instanceof Float || item instanceof Long) {
                jsonArray.append(item);
            } else {
                jsonArray.append(serialize(item));
            }
            jsonArray.append(",");
        }

        if (jsonArray.length() > 1) {
            jsonArray.deleteCharAt(jsonArray.length() - 1); // Remove the trailing comma
        }

        jsonArray.append("]");
        return jsonArray.toString();
    }

    public static <T> T deserialize(String jsonString, Class<T> clazz) {
        try {
            jsonString = jsonString.trim();
            if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
                return handlePrimitiveTypes(jsonString, clazz);
            }

            T obj = clazz.getDeclaredConstructor().newInstance();
            jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove outer curly braces
            Map<String, String> map = parseJsonObject(jsonString);

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                // Don't get static properties (I was running into some accessibility issues)
                if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) && map.containsKey(field.getName())) {
                    String value = map.get(field.getName());
                    if (value.equals("null")) {
                        field.set(obj, null);
                    } else {
                        Class<?> fieldType = field.getType();

                        if (fieldType == int.class) {
                            field.set(obj, Integer.parseInt(value));
                        } else if (fieldType == boolean.class) {
                            field.set(obj, Boolean.parseBoolean(value));
                        } else if (fieldType == double.class) {
                            field.set(obj, Double.parseDouble(value));
                        } else if (fieldType == float.class) {
                            field.set(obj, Float.parseFloat(value));
                        } else if (fieldType == long.class) {
                            field.set(obj, Long.parseLong(value));
                        } else if (List.class.isAssignableFrom(fieldType)) {
                            List<Object> list = deserializeArray(value, Object.class); // Assuming List<Object>
                            field.set(obj, list);
                        } else {
                            field.set(obj, deserialize(value, fieldType));
                        }
                    }
                }
            }

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Function to be called to try and parse a list from a stringified JSON array.
    //  Pulled out so we can easily handle nested objects/arrays.
    public static List<Object> deserializeArray(String jsonArray, Class<?> itemType) {
        // If the value that should be an array is instead null, return the null type (This probably shouldn't get hit)
        if (jsonArray.equals("null")) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        // Remove outer brackets so that I can parse the array contents
        jsonArray = jsonArray.trim().substring(1, jsonArray.length() - 1);
        // Use regex to get the array items. Split by commas that are outside of quotes so that it doesn't try to split on the items inside the array
        String[] items = jsonArray.split(", (?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        for (String item : items) {
            item = item.trim();
            // If the item inside the array is an object, deserialize it using the deserialize function I made earlier
            if (item.startsWith("{") && item.endsWith("}")) {
                list.add(deserialize(item, itemType));
            // If the item is a string that contains quotes, extract the string from the quotes.
            } else if (item.startsWith("\"") && item.endsWith("\"")) {
                list.add(item.substring(1, item.length() - 1));
            // Use regex to check if the string is a number, if it is, try to parse it to a double (Possibly need to seperate to int/double in the future)
            } else if (item.matches("-?\\d+(\\.\\d+)?")) {
                list.add(Double.parseDouble(item));
            // Just add the stringified object as a default (Not ideal, but shouldn't get hit)
            } else {
                list.add(item);
            }
        }

        return list;
    }

    private static Map<String, String> parseJsonObject(String jsonString) {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        while (i < jsonString.length()) {
            int startKey = jsonString.indexOf("\"", i);
            if (startKey == -1) break;
            startKey++;
            int endKey = jsonString.indexOf("\"", startKey);
            if (endKey == -1) break;
            String key = jsonString.substring(startKey, endKey);
            i = endKey + 1;

            int startValue = jsonString.indexOf(":", i);
            if (startValue == -1) break;
            startValue++;
            int endValue;

            if (jsonString.charAt(startValue) == '{') {
                int bracesCount = 1;
                endValue = startValue + 1;
                while (bracesCount > 0 && endValue < jsonString.length()) {
                    if (jsonString.charAt(endValue) == '{') bracesCount++;
                    if (jsonString.charAt(endValue) == '}') bracesCount--;
                    endValue++;
                }
            } else if (jsonString.charAt(startValue) == '[') {
                int bracketsCount = 1;
                endValue = startValue + 1;
                while (bracketsCount > 0 && endValue < jsonString.length()) {
                    if (jsonString.charAt(endValue) == '[') bracketsCount++;
                    if (jsonString.charAt(endValue) == ']') bracketsCount--;
                    endValue++;
                }
            } else {
                endValue = jsonString.indexOf(",", startValue);
                if (endValue == -1) {
                    endValue = jsonString.length();
                }
            }

            String value = jsonString.substring(startValue, endValue).trim();
            map.put(key, value);
            i = endValue + 1;
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    private static <T> T handlePrimitiveTypes(String value, Class<T> clazz) {
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) Boolean.valueOf(value);
        } else if (clazz == double.class || clazz == Double.class) {
            return (T) Double.valueOf(value);
        } else if (clazz == float.class || clazz == Float.class) {
            return (T) Float.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(value);
        } else if (clazz == String.class) {
            return (T) value.substring(1, value.length() - 1); // Remove quotes
        } else {
            throw new IllegalArgumentException("Unsupported primitive type: " + clazz.getName());
        }
    }
}
