package com.dvpie.utilities.Chemistry;

public class PeriodicTable {
    //TODO: Finish implementing All Elements
    public static Element[] table =
            {
                    new Element(1,"H","Hydrogen",1.01,1,1),
                    new Element(2,"He","Helium",4.00,18,1),
                    new Element(3,"Li","Lithium",6.94,1,2),
                    new Element(4,"Be","Beryllium",9.01,2,2),
                    new Element(5,"B","Boron",10.81,13,2),
                    new Element(6,"C","Carbon",12.01,14,2),
                    new Element(7,"N","Nitrogen",14.01,15,2),
                    new Element(8,"O","Oxygen",16.00,16,2),
                    new Element(9,"F","Fluorine",19.00,17,2),
                    new Element(10,"Ne","Neon",20.18,18,2),
                    new Element(11,"Na","Sodium",22.99,1,3),
                    new Element(12,"Mg","Magnesium",24.31,2,3),
                    new Element(13,"Al","Aluminum",26.98,13,3),
                    new Element(14,"Si","Silicon",28.09,14,3),
                    new Element(15,"P","Phosphorus",30.97,15,3),
                    new Element(16,"S","Sulfur",32.06,16,3),
                    new Element(17,"Cl","Chlorine",35.45,17,3),
                    new Element(18,"Ar","Argon",39.95,18,3),
                    new Element(19, "K", "Potassium", 39.10,1,4),
                    new Element(20,"Ca","Calcium",40.08,2,4),
                    new Element(21, "Sc", "Scandium", 44.96,3,4),
                    new Element(22,"Ti","Titanium",47.88,4,4),
                    new Element(23,"V","Vanadium",50.94,5,4),
                    new Element(24, "Cr","Chromium",51.99,6,4),
                    new Element(25, "Mn", "Manganese",54.94,7,4),
                    new Element(26, "Fe", "Iron",55.85,8,4),
                    new Element(27,"Co","Cobalt",58.93,9,4),
                    new Element(28, "Ni", "Nickel", 58.69,10,4),
                    new Element(29,"Cu","Copper",63.55,11,4),
                    new Element(30, "Zn", "Zinc",65.38,12,4),
                    new Element(31,"Ga","Gallium",69.72,13,4),
                    new Element(32,"Ge","Germanium",72.63,14,4),
                    new Element(33,"As","Arsenic",74.92,15,4),
                    new Element(34,"Se","Selenium",78.97,16,4),
                    new Element(35,"Br","Bromine",79.90,17,4),
                    new Element(36,"Kr","Krypton",84.80,18,4),
                    new Element(37,"Rb","Rubidium",85.47,1,5),
                    new Element(38,"Sr","Strontium",87.62,2,5),
                    new Element(39,"Y","Yttrium",88.91,3,5),
                    new Element(40,"Zr","Zirconium",91.22,4,5),
                    new Element(41,"Nb","Niobium",92.91,5,5),
                    new Element(42,"Mo","Molybdenum",95.95,6,5),
                    new Element(43, "Tc", "Technetium",98.91,7,5),
                    new Element(44,"Ru","Ruthenium",101.07,8,5),
                    new Element(45,"Rh","Rhodium",102.91,9,5),
                    new Element(46,"Pd","Palladium",106.42,10,5),
                    new Element(47,"Ag","Silver",107.87,11,5),
                    new Element(48,"Cd","Cadmium",112.41,12,5),
                    new Element(49,"In","Indium",114.82,13,5),
                    new Element(50,"Sn","Tin",118.71,14,5),
                    new Element(51,"Sb","Antimony",121.76,15,5),
                    new Element(52, "Te","Tellurium",127.60,16,5)
            };

    public static Element getElementBySymbol(String sym) {
        for(Element element : table) {
            if(element.getSymbol().toLowerCase().equals(sym.toLowerCase())) {
                return element;
            }
        }
        return null;
    }
    public static Element getElementByName(String name) {
        for(Element element : table) {
            if(element.getName().toLowerCase().equals(name.toLowerCase())) {
                return element;
            }
        }
        return null;
    }
    public static Element getElementByANumber(int AN) {
        for(Element element : table) {
            if(element.getAtomic_number() == AN) {
                return element;
            }
        }
        return null;
    }
    public static boolean isElementDiatomic(Element element) {
        String name = element.getName().toLowerCase();
        if(name.equals("oxygen") || name.equals("hydrogen") || name.equals("nitrogen")
        || name.equals("fluorine") || name.equals("chlorine") || name.equals("bromine")
        || name.equals("iodine"))
            return true;
        return false;
    }

}
