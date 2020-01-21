package com.dvpie.utilities.Chemistry;

public class EffusionDefusionCalculator {

    /**
     * @param AMass is the Atomic Mass of Element A
     * @param BMass is the Atomic Mass of Element B
     * @return Effusion Ratio of the elements
     **/
    public double calculateRatio(double AMass, double BMass) {
        double solution;
        solution = BMass/AMass;
        solution = Math.sqrt(solution);
        return solution;
    }

    /**
     *
     * @param elementA is Element A for the Ratio
     * @param elementB is Element B for the Ratio
     * @return Effusion Ratio of the Elements
     */
    public double calculateRatio(Element elementA, Element elementB) {
        double AMass = elementA.getAtomic_mass();
        if(Element.isElementDiatomic(elementA)) {
            AMass *= 2;
        }
        double BMass = elementB.getAtomic_mass();
        if(Element.isElementDiatomic(elementB)){
            BMass *= 2;
        }
        return calculateRatio(AMass, BMass);
    }


    public double findXWithRateOfB(double BRate, double AMass, double BMass) {
        double Ratio = calculateRatio(AMass, BMass);
        double solution = Ratio * BRate;
        return solution;
    }

    public double findXWithRateOfB(double BRate, Element elementA, Element elementB) {
        double Ratio = calculateRatio(elementA, elementB);
        double solution = Ratio * BRate;
        return solution;
    }

    public double findXWithRateOfA(double ARate, Element elementA, Element elementB) {
        double Ratio = calculateRatio(elementA, elementB);
        double solution = ARate / Ratio;
        return solution;
    }
    public double findXWithRateOfA(double ARate, double AMass, double BMass) {
        double Ration = calculateRatio(AMass, BMass);
        double solution = ARate / Ration;
        return solution;
    }
}
