package src.jsonParser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * This is our JSON Utility class, used to help convert all our types into a JSON type that we can store in a DB file.
 *
 * For this JSON Utility, with both serialization and deserialization, we need to ensure that we implement a recursive solution.
 *   This is because we will be working with arrays, objects, objects in arrays, arrays in objects, and so on.
 *   Implementing a recursive solution will give us a way to parse these complex objects, even when there is a lot of depth.
 */
public class JsonUtil {

    /**
     * This is the serialize function to be called from a public scope. It calls our recursive implementation, and keeps track of which objects have been parsed
     *   to help prevent the possibility of an infinite loop (Shouldn't ever be the case, but added as a fallback)
     * @param <T> The type of the Object being serialized
     * @param obj The Object being serialized
     * @return The equivalent JSON string of the object that was serialized
     */
    public static <T> String serialize(T obj) {
        return serialize(obj, new HashSet<>());
    }
    
    /**
     * This is the recursive serialize method. It is used to handle the serialization of objects. If it runs into an object inside an object, it
     *   can recursively call itself. If it runs into an array, it can call serializeArray.
     * This serialize function uses the generic T value that was passed in, and gets a the declared fields associated with its class (They must be public and non-static)
     *   It then iterates through the fields on the obj instance that was passed in, and uses a StringBuilder to translate that to a JSON equivalent.
     * @param <T> The type of the object being serialized
     * @param obj The object instance to have its values serialized into a JSON object
     * @param seenObjects A list of objects that has already been seen in this recursive implementation, used as a fallback to prevent infinite loops
     * @return
     */
    private static <T> String serialize(T obj, Set<Object> seenObjects) {
        if (obj == null) {
            return "null";
        }
    
        if (seenObjects.contains(obj)) {
            return "\"CYCLIC_REFERENCE_DETECTED\"";
        }
        seenObjects.add(obj);
    
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
                    json.append(serializeArray((List<?>) value, seenObjects));
                } else {
                    json.append(serialize(value, seenObjects));
                }
                json.append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1); // Remove the trailing comma
        }
    
        json.append("}");
        return json.toString();
    }
    
    /**
     * This is the serialization helper method used to serialize Arrays. It is called by our recursive serialize method when it encounters an array
     *   and it can also call serialize to to continue the recursive implementation if needed if there are objects inside of arrays.
     * 
     * Similar to the serialize function, it iterates through the list of object, and uses a StringBuilder to translate them into JSON.
     * @param list The list of objects to be serialized.
     * @param seenObjects A list of objects that has already been seen in this recursive implementation, used as a fallback to prevent infinite loops
     * @return A JSON String of this List
     */
    private static String serializeArray(List<?> list, Set<Object> seenObjects) {
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
                jsonArray.append(serialize(item, seenObjects));
            }
            jsonArray.append(",");
        }
    
        if (jsonArray.length() > 1) {
            jsonArray.deleteCharAt(jsonArray.length() - 1); // Remove the trailing comma
        }
    
        jsonArray.append("]");
        return jsonArray.toString();
    }
    
    /**
     * This is the recursive deserialize method. It is used to handle the deserialization of objects. If it runs into an object inside an object, it
     *   can recursively call itself. If it runs into an array, it can call deserializeArray.
     * This deserialize function uses the generic T value that was passed in, and gets the default constructor associated with its class and creates an instance of it
     *   (This is why we have so many classes with public, empty constructors on them)
     *   It then iterates through the declared fields on the obj, and fits the values from the given jsonString into the equivalent fields on that obj.
     * @param <T> The type of the object being deserialized
     * @param jsonString The JSON string to be parsed into an object of type T
     * @param clazz The class associated with T, used to get default constructors / fields associated with that type.
     * @return
     */
    public static <T> T deserialize(String jsonString, Class<T> clazz) {
        try {
            jsonString = jsonString.trim();
            if (clazz.isArray()) {
                return handleArrayDeserialization(jsonString, clazz);
            }
    
            if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
                return handlePrimitiveTypes(jsonString, clazz);
            }
    
            T obj = clazz.getDeclaredConstructor().newInstance();
            jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove outer curly braces
            Map<String, String> map = parseJsonObject(jsonString);
    
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
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
                            ParameterizedType listType = (ParameterizedType) field.getGenericType();
                            Type[] actualTypeArguments = listType.getActualTypeArguments();
                            Class<?> itemType = (Class<?>) actualTypeArguments[0];
                            // Array has been found, use the deserialize
                            List<?> list = deserializeArray(value, itemType);
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
    
    /**
     * This is the serialization helper method used to serialize Arrays. It is called by our recursive serialize method when it encounters an array
     *   and it can also call serialize to to continue the recursive implementation if needed if there are objects inside of arrays.
     * 
     * Similar to the serialize function, it iterates through the list of object, and uses a StringBuilder to translate them into JSON.
     * @param <T> The type aggregated in the list object being deserialized
     * @param jsonArray The JSON string containing the list of objects to be deserialized.
     * @return An array of type List<T> containing all the objects from the 
     */
    public static <T> List<T> deserializeArray(String jsonArray, Class<T> itemType) {
        if (jsonArray.equals("null")) {
            return null;
        }
        List<T> list = new ArrayList<>();
        jsonArray = jsonArray.trim().substring(1, jsonArray.length() - 1); // Remove outer brackets
    
        int startIndex = 0;
        int depth = 0;
        boolean inQuotes = false;
        List<String> items = new ArrayList<>();
    
        for (int i = 0; i < jsonArray.length(); i++) {
            char c = jsonArray.charAt(i);
    
            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle inQuotes flag on each quote
            } else if (!inQuotes) {
                if (c == '[' || c == '{') {
                    depth++; // Increase depth for nested structures
                } else if (c == ']' || c == '}') {
                    depth--; // Decrease depth for nested structures
                } else if (c == ',' && depth == 0) {
                    items.add(jsonArray.substring(startIndex, i).trim());
                    startIndex = i + 1;
                }
            }
        }
    
        items.add(jsonArray.substring(startIndex).trim()); // Add the last item
    
        for (String item : items) {
            if (item.startsWith("{") && item.endsWith("}")) {
                list.add(deserialize(item, itemType));
            } else if (item.startsWith("\"") && item.endsWith("\"")) {
                list.add((T) item.substring(1, item.length() - 1)); // Remove quotes
            } else if (item.matches("-?\\d+(\\.\\d+)?")) {
                list.add((T) (Double) Double.parseDouble(item)); // Primitive number
            } else {
                list.add((T) item); // Default to string
            }
        }
    
        return list;
    }
    
    /**
     * This is slightly different than the arrayDeserialization method. This class isn't used in a recursive manner, it is used in cases where the original
     *  JSON (Ex. DB_Student content) contains an Array rather than an object. This shouldn't ever be the case with our current setup, but it's nice
     *  to have a fallback just in case.
     * @param <T> The type to be deserialized into
     * @param jsonString THe JSON String to be deserialized in to type T
     * @param clazz the Class of T, used to create an instance of that Array Type
     * @return Deserialized object of type T
     */
    private static <T> T handleArrayDeserialization(String jsonString, Class<T> clazz) {
        if (!jsonString.startsWith("[") || !jsonString.endsWith("]")) {
            throw new IllegalArgumentException("Invalid JSON array format.");
        }
    
        jsonString = jsonString.substring(1, jsonString.length() - 1).trim(); // Remove outer brackets
        String[] items = jsonString.split(", (?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by commas outside quotes
        Class<?> componentType = clazz.getComponentType();
        @SuppressWarnings("unchecked")
        T array = (T) Array.newInstance(componentType, items.length);
    
        for (int i = 0; i < items.length; i++) {
            String item = items[i].trim();
            if (componentType.isPrimitive() || componentType == String.class || Number.class.isAssignableFrom(componentType) || Boolean.class == componentType) {
                Array.set(array, i, handlePrimitiveTypes(item, componentType));
            } else {
                Array.set(array, i, deserialize(item, componentType));
            }
        }
    
        return array;
    }

    /**
     * Takes in a JSON string and maps out the key/value pairs. This is used in deserialization when we create an instance of a type T and 
     *   need to map its fields to the values in the JSON.
     * @param jsonString The json string to be parsed
     * @return A key/value pair mapping of each property in the JSON
     */
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
            int endValue = startValue;
            char startChar = jsonString.charAt(startValue);
    
            if (startChar == '{') {
                int bracesCount = 1;
                endValue++;
                while (bracesCount > 0 && endValue < jsonString.length()) {
                    char c = jsonString.charAt(endValue);
                    if (c == '{') bracesCount++;
                    else if (c == '}') bracesCount--;
                    endValue++;
                }
            } else if (startChar == '[') {
                int bracketsCount = 1;
                endValue++;
                while (bracketsCount > 0 && endValue < jsonString.length()) {
                    char c = jsonString.charAt(endValue);
                    if (c == '[') bracketsCount++;
                    else if (c == ']') bracketsCount--;
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
    
    
    /**
     * Used to help parse primatives from a string as best as possible. Starts with the more specific types
     *   (A boolean could always be a string, but a string isn't always a boolean), and then works its way down
     * @param <T>
     * @param value
     * @param clazz
     * @return
     */
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
