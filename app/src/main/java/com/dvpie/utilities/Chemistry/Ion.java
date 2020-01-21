package com.dvpie.utilities.Chemistry;

public class Ion {
    private String formula;
    private String[] Elements;
    private int[] ElementCounts;

    public String getFormula() {
        return formula;
    }
    public String[] getElements() {
        return Elements;
    }

    public void setElements(String[] elements) {
        Elements = elements;
    }

    public int[] getElementCounts() {
        return ElementCounts;
    }

    public void setElementCounts(int[] elementCounts) {
        ElementCounts = elementCounts;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    private int charge;
    public Ion(String[] Elements, int[] eCounts, int charge) {
        this.Elements = Elements;
        this.ElementCounts = eCounts;
        this.charge = charge;
        createFormula();
    }

    private void createFormula() {
        if(this.Elements.length != this.ElementCounts.length) {
            this.formula = "Error";
            return;
        }
        formula = "";
        for(int i = 0; i < this.Elements.length; i++) {
            int amt = this.ElementCounts[i];
            String element = this.Elements[i];
            this.formula += element;
            if(amt > 9) {
                //Ugh, time to combine subscripts
            } else {
                switch(amt) {
                    case 1:
                        break;
                    case 2:
                        char sub = '\u2082';
                        this.formula += sub;
                        break;
                    case 3:
                        char sub1 = '\u2083';
                        this.formula += sub1;
                        break;
                    case 4:
                        sub = '\u2084';
                        this.formula += sub;
                        break;
                    case 5:
                        sub = '\u2085';
                        this.formula += sub;
                        break;
                    case 6:
                        sub = '\u2086';
                        this.formula += sub;
                        break;
                    case 7:
                        sub = '\u2087';
                        this.formula += sub;
                        break;
                    case 8:
                        sub = '\u2088';
                        this.formula += sub;
                        break;
                    case 9:
                        sub = '\u2089';
                        this.formula += sub;
                        break;
                    default:
                        this.formula += ("\\u208" + amt + " ");
                }
            }
        }
    }
}
