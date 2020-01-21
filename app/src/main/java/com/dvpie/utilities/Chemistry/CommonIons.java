package com.dvpie.utilities.Chemistry;

public class CommonIons {
    public static final Ion SULFATE = new Ion(new String[]{"S","O"},new int[]{1,4}, -2);
    public static final Ion SULFITE = new Ion(new String[]{"S","O"},new int[]{1,3},-2);
    public static final Ion NITRATE = new Ion(new String[]{"N","O"},new int[]{1,3}, -1);
    public static final Ion NITRITE = new Ion(new String[]{"N","O"},new int[]{1,2}, -1);
    public static final Ion HYDROXIDE = new Ion(new String[]{"O","H"},new int[]{1,1}, -1);
    public static final Ion CYANIDE = new Ion(new String[]{"C","N"},new int[]{1,1}, -1);
    public static final Ion CYANATE = new Ion(new String[]{"O","C","N"},new int[]{1,1,1}, -1);
    public static final Ion THIOCYANATE = new Ion(new String[]{"S","C","N"},new int[]{1,1,1}, -1);
    public static final Ion PERCHLORATE = new Ion(new String[]{"Cl","O"},new int[]{1,4}, -1);
    public static final Ion CHLORATE = new Ion(new String[]{"Cl","O"},new int[]{1,3}, -1);
    public static final Ion CHLORITE = new Ion(new String[]{"Cl","O"},new int[]{1,2}, -1);
    public static final Ion HYPOCHLORITE = new Ion(new String[]{"Cl","O"},new int[]{1,1}, -1);
    public static final Ion PERBROMATE = new Ion(new String[]{"Br","O"},new int[]{1,4}, -1);
    public static final Ion BROMATE = new Ion(new String[]{"Br","O"},new int[]{1,3}, -1);
    public static final Ion BROMITE = new Ion(new String[]{"Br","O"},new int[]{1,2}, -1);
    public static final Ion HYPOBROMITE = new Ion(new String[]{"Br","O"},new int[]{1,1}, -1);
    //TODO: Finish Adding All Ions
    // -- Left off at Periodate

    public static Object[][] IONS =
            {
                    {"Sulfate",SULFATE},
                    {"Sulfite",SULFITE},
                    {"Nitrate",NITRATE},
                    {"Nitrite",NITRITE},
                    {"Hydroxide",HYDROXIDE},
                    {"Cyanide",CYANIDE},
                    {"Cyanate",CYANATE},
                    {"Thiocyanate",THIOCYANATE},
                    {"Perchlorate",PERCHLORATE},
                    {"Chlorate", CHLORATE},
                    {"Chlorite",CHLORITE},
                    {"Hypochlorite",HYPOCHLORITE},
                    {"Perbromate",PERBROMATE},
                    {"Bromate",BROMATE},
                    {"Bromite",BROMITE},
                    {"Hypobromite",HYPOBROMITE}
            };

    public static Ion getIonByName(String name) {
        for(Object[] ion : IONS) {
            if(((String)ion[0]).toLowerCase().equals(name.toLowerCase()))
                return ((Ion)ion[1]);
        }
        return null;
    }
    public static String getNameByName(String name) {
        for(Object[] ion : IONS) {
            if(((String)ion[0]).toLowerCase().equals(name.toLowerCase()))
                return ((String)ion[0]);
        }
        return null;
    }
}
