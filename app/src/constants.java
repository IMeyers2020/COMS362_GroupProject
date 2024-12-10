package src;

public class constants {
    /**
     * This is a simple enum to assign a humanized value to a enum for times of day
     */
    public static enum TIMES {
        TwelveAM("12:00AM"),
        OneAM("1:00AM"),
        TwoAM("2:00AM"),
        ThreeAM("3:00AM"),
        FourAM("4:00AM"),
        FiveAM("5:00AM"),
        SixAM("6:00AM"),
        SevenAM("7:00AM"),
        EightAM("8:00AM"),
        NineAM("9:00AM"),
        TenAM("10:00AM"),
        ElevenAM("11:00AM"),
        TwelvePM("12:00PM"),
        OnePM("1:00PM"),
        TwoPM("2:00PM"),
        ThreePM("3:00PM"),
        FourPM("4:00PM"),
        FivePM("5:00PM"),
        SixPM("6:00PM"),
        SevenPM("7:00PM"),
        EightPM("8:00PM"),
        NinePM("9:00PM"),
        TenPM("10:00PM"),
        ElevenPM("11:00PM");
    
        public final String label;
    
        private TIMES(String label) {
            this.label = label;
        }
    }

    /**
     * This is a simple enum to assign a humanized value to a enum for days of week
     */
    public static enum DAYS {
        Monday("M"),
        Tuesday("T"),
        Wednesday("W"),
        Thursday("R"),
        Friday("F"),
        Saturday("S"),
        Sunday("S");
    
        public final String label;
    
        private DAYS(String label) {
            this.label = label;
        }
    }
}
