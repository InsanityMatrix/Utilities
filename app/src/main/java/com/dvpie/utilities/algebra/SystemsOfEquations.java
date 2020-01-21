package com.dvpie.utilities.algebra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemsOfEquations {
    private final String BAD_FORMAT = "BAD FORMAT";
    public String Solve(String[] equations) {
        //First we are going to do our 2 & 3 equation systems, then I may learn how to do multiple eq systems
        int NUM_EQUATIONS = equations.length;

        if(NUM_EQUATIONS == 2) {
            //Check if Both Equations are in standard Form
            String equationOne = formatEquation(equations[0]);
            String equationTwo = formatEquation(equations[1]);
            if(equationOne.equals(BAD_FORMAT) || equationTwo.equals(BAD_FORMAT)) {
                return "An equation was formatted badly.";
            }
            String regex = "([+-]?\\d*[a-zA-Z])([+-]?\\s*\\d*\\s*[a-zA-Z])=(-?\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher match = pattern.matcher(equationOne);
            String E1_xString, E1_yString, E1_Equals;
            if(match.matches()){
                E1_xString = match.group(1);
                E1_yString = match.group(2);
                E1_Equals = match.group(3);
            }

            match = pattern.matcher(equationTwo);
            String E2_xString, E2_yString, E2_Equals;
            if(match.matches()) {
                E2_xString = match.group(1);
                E2_yString = match.group(2);
                E2_Equals = match.group(3);
            }
            {
                //Pull out xPrefix, yPrefix, and equals and turn them to values
                //Find out how they fit together, first try multiplying to fit together, but if not we can try making them equal each other
                //TODO: This shit
            }
            return "No Solution";
        } else if (NUM_EQUATIONS == 3) {

            return "No Solution";
        } else {
            return "Cannot solve yet";
        }



    }

    private String formatEquation(String equation) {
        String formatted;
        String formatBi = "([+-]?\\d*[a-zA-Z])\\s*([+-]?\\s*\\d*\\s*[a-zA-Z])\\s*=\\s*(-?\\d+)";
        Pattern format = Pattern.compile(formatBi);
        Matcher checker = format.matcher(equation);
        if(checker.matches()) {
            formatted = checker.group(1) + checker.group(2) + "=" + checker.group(3);
            return formatted;
        } else {
            //We Gonna Make it Match in the future;
        }

        return BAD_FORMAT;
    }
}
