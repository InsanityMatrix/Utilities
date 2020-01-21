package com.dvpie.utilities.Chemistry;

public class GasLaws {

    /**
     *
     * @param Pressure1 = Pressure of Gas at state 1
     * @param Volume1 = Volume of Gas at state 1
     * @param Pressure2 = Pressure of Gas afterwards
     * @return The new Volume
     */
    public static double BoylesLawForVolume(double Pressure1, double Volume1, double Pressure2) {
        double Vol2;
        Vol2 = (Pressure1 * Volume1) / Pressure2;
        return Vol2;
    }

    /**
     *
     * @param Pressure1 = Pressure of Gas at state 1
     * @param Volume1 = Volume of Gas at state 1
     * @param Volume2 = Volume of Gas afterwards
     * @return The new gas pressure
     */
    public static double BoylesLawForPressure(double Pressure1, double Volume1, double Volume2) {
        double P2;
        P2 = (Pressure1 * Volume1) / Volume2;
        return P2;
    }

    /**
     *
     * @param Volume1 = Volume of Gas at state 1
     * @param Temp1 = Temperature of Gas at State 1
     * @param Temp2 = Temperature of Gas afterwards
     * @return The new Volume
     */
    public static double CharlesLawForVolume(double Volume1, double Temp1, double Temp2) {
        double Vol2;
        Vol2 = (Volume1/Temp1) * Temp2;
        return Vol2;
    }

    /**
     *
     * @param Volume1 = Volume of Gas at state 1
     * @param Temp1 = Temperature of Gas at state 1
     * @param Volume2 = Volume of Gas afterwards
     * @return
     */
    public static double CharlesLawForTemp(double Volume1, double Temp1, double Volume2) {
        double Temp2;
        Temp2 = (Volume1/Temp1) * Volume2;
        return Temp2;
    }

    //TODO: Gay-Lussae's Law & The Combined Gas Law
}
