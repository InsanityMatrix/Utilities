package com.dvpie.utilities.Chemistry;

public class Element {

    private int group, period, atomic_number;
    private double atomic_mass;
    private String name, symbol;
    public int getGroup() {
        return group;
    }

    public int getPeriod() {
        return period;
    }

    public int getAtomic_number() {
        return atomic_number;
    }

    public void setAtomic_number(int atomic_number) {
        this.atomic_number = atomic_number;
    }

    public double getAtomic_mass() {
        return atomic_mass;
    }

    public void setAtomic_mass(double atomic_mass) {
        this.atomic_mass = atomic_mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public Element(int atomic_number) {
        this.atomic_number = atomic_number;
    }
    public Element(int atomic_number, String symbol, String name, double atomic_mass) {
        this.atomic_number = atomic_number;
        this.symbol = symbol;
        this.name = name;
        this.atomic_mass = atomic_mass;
    }
    public Element(int atomic_number, String symbol, String name, double atomic_mass,int group, int period) {
        this.atomic_number = atomic_number;
        this.symbol = symbol;
        this.name = name;
        this.atomic_mass = atomic_mass;
        setGroup(group);
        setPeriod(period);
    }
    public void setGroup(int num) {
        this.group = num;
    }
    public void setPeriod(int num) {
        this.period = num;
    }




    //Static Methods
    public static boolean isElementAMetal(Element element) {
        if(element.getAtomic_number() == 1) {
            return false;
        }
        if(element.getAtomic_number() >= 5 && element.getAtomic_number() <= 10)
            return false;
        int aNum = element.getAtomic_number();
        if(aNum >= 14 && aNum <= 18)
            return false;
        if(aNum >= 33 && aNum <= 36)
            return false;
        if(53 <= aNum && aNum <= 54) {
            return false;
        }
        return true;
    }
    public static int getElementCharge(Element element) {
        boolean Metal = isElementAMetal(element);
        if(!Metal) {
            switch(element.getGroup()) {
                case 13:
                    return -3;
                case 14:
                    return -4;
                case 15:
                    return -3;
                case 16:
                    return -2;
                case 17:
                    return -1;
                case 18:
                    return 0;
                default:
                    break;
            }
        }
        if(element.getGroup() == 1) {
            return 1;
        } else if (element.getGroup() == 2) {
            return 2;
        } else {
            //Metal Exception Cases here, then we go to other stuff
            if(element.getSymbol().equals("Zn"))
                return 2;
            //TODO:Finish getting charges
        }

        return 0;
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
