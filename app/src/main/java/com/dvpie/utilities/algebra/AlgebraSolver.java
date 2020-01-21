package com.dvpie.utilities.algebra;

import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * TODO: Implement Solving Problems By Plugging in Values
 *
 */
public class AlgebraSolver {
    //All Regex Patterns to Identify Algebraic Problems
    private String TrinomialVertexForm = "(\\d*)?y\\s*=\\s*(-?\\d*)?\\s*\\*?\\(?(\\d*)?\\s*x\\s*([+-]\\s*\\d+)?\\)?\\^2\\s*([+-]\\s*\\d+)?";
    private String TrinomialStandardForm = "y\\s*=\\s*(-?\\d*)?\\s*([a-z])\\s*\\^\\s*2\\s*([+-]\\s*\\d*)\\s*([a-z])\\s*([+-]\\s*\\d*\\.?\\d*)";
    private String LinearSlopeInterceptForm = "(-?\\d*)?y\\s*=\\s*(-?\\d*)?\\*?x\\s*([+-]\\s*\\d+)?";
    private String LinearStandardForm = "\\s*(\\d*)?x\\s*([+-]\\s*(\\d*)?)y\\s*=\\s*([+-]?\\s*\\d+)";



    //Polynomials
    private String CubedBinomial1 = "(-?\\d*)?([a-zA-Z])\\s*\\^\\s*3\\s*([+-])\\s*(\\d*)";
    private String SecondDegTrinomial = "(-?\\d*)?([a-zA-Z])\\s*\\^\\s*2\\s*([+-]\\s*\\d*)([a-zA-Z])\\s*([+-]\\s*\\d+)";
    private String SecondDegBinomial1 = "(-?\\d*)?([a-zA-Z])\\s*\\^\\s*2\\s*([+-]\\s*\\d+)";
    private String SecondDegBinomial2 = "^(-?\\d*)?([a-zA-Z])\\s*\\^\\s*2\\s*([+-]\\s*\\d*)\\s*([a-zA-Z])$";
    private String FirstDegBinomial = "(-?\\d*)\\s*([a-zA-Z])\\s*([+-])\\s*(\\d*.?\\d*)";


    private DecimalFormat df;
    public AlgebraSolver(){
        df = new DecimalFormat("#.##");
    }

    /**
     *
     * @param problem Problem to Solve
     * @return Uses RegEx to Identify equations & Expressions, then returns answers in a String Array
     */
    public String[] solve(@NonNull String problem) {
        String[] result;
        if(!problem.contains("=")) {

            Pattern checker = Pattern.compile(SecondDegTrinomial);
            Matcher format = checker.matcher(problem);
            if(format.matches()) {
                result = new String[]{FactorSecondDegTrinomial(problem)};
                return result;
            }
            checker = Pattern.compile(CubedBinomial1);
            format = checker.matcher(problem);
            if(format.matches()) {
                result = new String[]{FactorCubedBinomial1(problem)};
                return result;
            }
            checker = Pattern.compile(SecondDegBinomial2);
            format = checker.matcher(problem);
            if(format.matches()) {
                return new String[] {FactorSecondDegBinomial2(problem)};
            }
            checker = Pattern.compile(SecondDegBinomial1);
            format = checker.matcher(problem);
            if(format.matches()) {
                return (new String[] {FactorSecondDegBinomial1(problem)});
            }
            checker = Pattern.compile(FirstDegBinomial);
            format = checker.matcher(problem);
            if(format.matches()) {
                return (new String[] {FactorFirstDegBinomial(problem)});
            }
        }
        //Lets first check if it is a binomial in vertex form!
        Pattern checker = Pattern.compile(TrinomialVertexForm);
        Matcher matcher = checker.matcher(problem);
        if(matcher.matches()) {
            //It is a Binomial in Vertex form
            result = SolveBinomialVertexForm(problem);
            return result;
        }
        checker = Pattern.compile(TrinomialStandardForm);
        matcher = checker.matcher(problem);
        if(matcher.matches()){
            result = SolveTrinomialStandardForm(problem);
            return result;
        }
        //Check if it is Linear Slope Intercept Equation
        checker = Pattern.compile(LinearSlopeInterceptForm);
        matcher = checker.matcher(problem);
        if(matcher.matches()) {
            //It is a Linear Slope Intercept Equation
            result = SolveLinearSlopeInterceptForm(problem);
            return result;
        }
        //Check for Linear in Standard Form
        checker = Pattern.compile(LinearStandardForm);
        matcher = checker.matcher(problem);
        if(matcher.matches()) {
            result = SolveLinearStandardForm(problem);
            return result;
        }



        result = new String[]{"I could not solve this problem."};
        return result;
    }
    public String FactorCubedBinomial1(String problem) {
        String response = "Could not Solve.";
        Pattern checker = Pattern.compile(CubedBinomial1);
        Matcher format = checker.matcher(problem);

        String aVal, aVar, midSign, bVal;
        if(format.matches()) {
            response = format.group(0) + " is a cubic binomial.";
            aVal = format.group(1);
            aVar = format.group(2);
            midSign = format.group(3);
            bVal = format.group(4);
        } else {
            return "ERROR: Try again?";
        }
        return response;
    }

    /**
     *
     * @param problem = Problem to Solve
     * @return String Array of 3 Values: Slope, Y-Intercept, X-Intercept, respectively
     */
    public String[] SolveLinearStandardForm(String problem) {
        String[] Solutions = new String[3];
        Pattern checker = Pattern.compile(LinearStandardForm);
        Matcher matcher = checker.matcher(problem);

        String xPrefix, yPrefix, result;
        if(matcher.matches()) {
            xPrefix = matcher.group(1);
            yPrefix = matcher.group(2);
            result = matcher.group(4);
        } else {
            return new String[] {"There was an error matching your equation to Standard Linear Form"};
        }
        //Get our Values by formatting Strings
        double xVal, yVal, res;
        if(xPrefix.equals("") || xPrefix.equals("+")) {
            xVal = 1;
        } else {
            xPrefix = xPrefix.replaceAll("", "");
            if(xPrefix.contains("+"))
                xPrefix = xPrefix.replaceAll("\\+", "");
            xVal = Double.parseDouble(xPrefix);
        }

        yPrefix = yPrefix.replaceAll("\\s","");
        if (yPrefix.equals("") || yPrefix.equals("+")) {
            yVal = 1;
        } else if(yPrefix.equals("-")) {
            yVal = -1;
        } else {
            if(yPrefix.contains("+"))
                yPrefix = yPrefix.replaceAll("\\+", "");
            yVal = Double.parseDouble(yPrefix);
        }

        result = result.replaceAll("\\s", "");
        if(result.contains("+"))
            result.replaceAll("\\+", "");
        res = Double.parseDouble(result);

        String xInt = df.format( (res/xVal) );
        String yInt = df.format( (res/yVal) );
        Solutions[1] = "Y-Intercept = (0," + yInt + ")";
        Solutions[2] = "X-Intercept = (" + xInt + ",0)";
        double slope = xVal * -1;
        slope = slope / yVal;
        Solutions[0] = "Slope: m =" + df.format(slope);

        return Solutions;
    }

    public String[] SolveTrinomialStandardForm(String problem) {
        Pattern pattern = Pattern.compile(TrinomialStandardForm);
        Matcher match = pattern.matcher(problem);
        //Strings
        String aString, aVar, bString, bVar,cString;
        if(match.matches()) {
            aString = match.group(1);
            aVar = match.group(2);
            bString = match.group(3);
            bVar = match.group(4);
            cString = match.group(5);
        } else {
            return solve(problem);
        }

        double aVal = Double.parseDouble(aString);


        return new String[] {"Not implemented yet"};
    }
    /**
     *
     * @param problem = Problem to Solve
     * @return String Array of 3 Values: Slope, Y-Intercept, X-Intercept, respectively
     */
    public String[] SolveLinearSlopeInterceptForm(String problem) {
        String[] Solutions = new String[3];
        Pattern checker = Pattern.compile(LinearSlopeInterceptForm);
        Matcher matcher = checker.matcher(problem);

        String yPrefix, mString, bString;
        if(matcher.matches()) {
            yPrefix = matcher.group(1);
            mString = matcher.group(2);
            bString = matcher.group(3);
        } else {
            return new String[] {"There was an error processing your Linear Equation."};
        }

        //Format the Strings
        double yPref, mVal, bVal;
        //Processing y String
        if(yPrefix.equals("") || yPrefix == null) {
            yPref = 1;
        }else if(yPrefix.equals("-")) {
            yPref = -1;
        } else {
            yPref = Double.parseDouble(yPrefix);
        }
        //Time to get M
        if(mString.equals("") || mString == null) {
            mVal = 1;
        } else if(mString.equals("-")) {
            mVal = -1;
        } else {
            mVal = Double.parseDouble(mString);
        }
        //Time to get b
        bString = bString.replaceAll("\\s", "");
        if(bString.equals("")) {
            bVal = 0;
        } else {
            bVal = Double.parseDouble(bString);
        }
        Solutions[0] = "Slope: m = " + mVal;
        Solutions[1] = "Y-Intercept: (0," + df.format(bVal / yPref) + ")";

        double xint;
        double left = bVal * -1;
        xint = left/mVal;
        Solutions[2] = "X-Intercept: (" + df.format(xint) + ",0)";


        return Solutions;
    }
    public String[] SolveBinomialVertexForm(String problem) {
        String[] solutions = new String[3];
        String pattern = TrinomialVertexForm;
        Pattern checker = Pattern.compile(pattern);
        Matcher matcher = checker.matcher(problem);

        String yPrefix, aPrefix,xPrefix, h, k;
        if(matcher.matches()) {
            //Get all of our RegEx Groups for solving the equation
            yPrefix = matcher.group(1);
            aPrefix = matcher.group(2);
            xPrefix = matcher.group(3);
            h = matcher.group(4);
            k = matcher.group(5);
        }else {
            return null;
        }

        //Get Vertex first
        int[] Vertex = new int[2];
        boolean hNeg = false, kNeg = false;
        //Mostly String Formatting:
        if(h == null)
            h = "";
        if(k == null)
            k = "";
        if(!h.equals(""))
             h = h.replaceAll("\\s", "");
        else {
            h = "0";
        }
        if(!k.equals(""))
            k = k.replaceAll("\\s", "");
        else
            k = "0";

        if(h.contains("+")) {
            hNeg = true;
            h = h.replaceAll("\\+","");
        } else if(h.contains("-")) {
            hNeg = false;
            h = h.replaceAll("-","");
        } else {
            h = "0";
        }
        //We got our hVal now!
        int hVal = Integer.parseInt(h);
        if(h.equals("0"))
            hVal = 0;
        if(hNeg)
            hVal *= -1;
        Vertex[0] = hVal;

        //More String Formatting...
        if(k.contains("+")) {
            kNeg = false;
            k = k.replaceAll("\\+","");
        } else if(k.contains("-")) {
            kNeg = true;
            k = k.replaceAll("-","");
        } else {
            k = "0";
        }
        //Got K Value
        int kVal = Integer.parseInt(k);
        if(k.equals("0"))
            kVal = 0;
        if(kNeg)
            kVal *= -1;
        Vertex[1] = kVal;

        //Make Vertex Response
        String vertex = "(";
        vertex += hVal + ", ";
        vertex += kVal + ")";

        //Check A and shit now
        int aVal;
        if (aPrefix.equals("") || aPrefix == null) {
            aVal = 1;
        } else {
            if(aPrefix.contains("+")) {
                aPrefix = aPrefix.replaceAll("\\+","");
            }
            try {
                aVal = Integer.parseInt(aPrefix);
            } catch(NumberFormatException e) {
                aVal = 1;
            }
        }
        //Check B
        int bVal;
        if(xPrefix.equals("") || xPrefix == null) {
            bVal = 1;
        } else {
            if(xPrefix.contains("+")) {
                xPrefix = xPrefix.replaceAll("\\+","");
            }
            try {
                bVal = Integer.parseInt(xPrefix);
            } catch(NumberFormatException e) {
                bVal = 1;
            }
        }
        int yVal;
        solutions[0] = "Vertex: " + vertex;
        double yIntercept;
        //Next let's solve for the y-Intercept;
        solutions[1] = "Y-Intercept: (0,";
        if(yPrefix.equals("") || yPrefix == null) {
            //Aight plug in 0 for x right?
            yVal = 1;
            double result = (double)hVal * -1;

            result = Math.pow(result, 2);
            if(hVal == 0) {
                result = 0;
            }
            result = (double)aVal * result;
            result = result + (double)kVal;
            result = result / (double)yVal;
            yIntercept = result;
            solutions[1] += df.format(result) +")";
        } else {
            if(yPrefix.contains("+"))
                yPrefix = yPrefix.replaceAll("\\+","");
            try {
                yVal = Integer.parseInt(yPrefix);
            } catch(NumberFormatException e) {
                yVal = 1;
            }
            double result = (double)hVal * -1;

            result = Math.pow(result, 2);
            if(hVal == 0) {
                result = 0;
            }
            result = (double)aVal * result;
            result = result + (double)kVal;
            result = result/(double)yVal;
            yIntercept = result/bVal;
            solutions[1] += df.format(yIntercept) +")";
        }

        //Lets Go ahead and get our quadB
        double roots[] = new double[2];
        {
            double left = 0;
            left = (double)kVal * -1;
            left = left / (double)aVal;
            if(left < 0.0) {
                left *= -1;
                Math.sqrt(left);
                String eq = df.format(left) + "i";
                String sol1 = (hVal * -1) + "+" + eq;
                String sol2 = (hVal * -1) + "-" + eq;
                if(hVal == 0){
                    sol1 = sol1.replaceAll("0","");
                    sol2 = sol2.replaceAll("0", "");
                }
                solutions[2] = "Roots: " + sol1 + ", " + sol2;
                return solutions;
            } else {
                double ol = left;
                left = Math.sqrt(left);

                double pos, neg;
                pos = left;
                neg = left * -1;
                if(ol == 0)
                    pos = 0;
                if(ol == 0)
                    neg = 0;
                pos += hVal;
                neg += hVal;
                roots[0] = pos;
                roots[1] = neg;

            }

        }



        solutions[2] = "Roots: (" + df.format(roots[0]) + ",0) & (" + df.format(roots[1]) + ",0)";
        return solutions;
    }

    /**
     *
     * @param problem -> Possible Second Degree Trinomial (ax^2 +- bx +- c)
     * @return Factors The second degree trinomial
     */
    @SuppressWarnings("all")
    public String FactorSecondDegTrinomial(String problem) {
        String result;
        Pattern check = Pattern.compile(SecondDegTrinomial);
        Matcher matcher = check.matcher(problem);

        String aString, bString, var1, var2, cString;
        if(matcher.matches()) {
            result = matcher.group(0);
            aString = matcher.group(1);
            bString = matcher.group(3).replaceAll("\\s", "");
            cString = matcher.group(5).replaceAll("\\s", "");


            var1 = matcher.group(2);
            var2 = matcher.group(4);
            if(!var1.equals(var2))
                return "Variables in Second Degree Trinomial Did not Match";
        } else {
            return null;
        }
        //Format b and c
        int bVal, cVal;

        if(bString.equals("+"))
            bVal = 1;
        else if(bString.equals("-"))
            bVal = -1;
        else {
            if(bString.contains("+")) {
                bString = bString.replaceAll("\\+","");
            }
            bVal = Integer.parseInt(bString);
        }
        if(cString.contains("+"))
            cString = cString.replaceAll("\\+", "");
        cVal = Integer.parseInt(cString);
        if(aString.equals("") || aString == null) {
            //B must be the factors of C added up.
            int factors[] = {0,0};
            int newC;
            if(cVal < 0)
                newC = cVal * -1;
            else
                newC = cVal;
            for(int i = 1; i <= newC; ++i){
                if(cVal % i == 0) {
                    int fac2 = cVal / i;
                    if(i + fac2 == bVal){
                        factors[0] = i;
                        factors[1] = fac2;
                        break;
                    } else {
                        int newFac = i * -1;
                        fac2 *= -1;
                        if(newFac + fac2 == bVal) {
                            factors[0] = newFac;
                            factors[1] = fac2;
                            break;
                        }
                    }
                }
            }
            //Now we Have our factors... maybe
            if(factors[0] == 0 && factors[1] == 0) {
                //Not factorable
                return problem;
            } else {
                String firstFactor ="(" + var1;
                if(factors[0] >= 0){
                    firstFactor += "+";
                }
                firstFactor += factors[0] + ")";

                String secondFactor = "(" + var1;
                if(factors[1] >= 0) {
                    secondFactor += "+";
                }
                secondFactor += factors[1] + ")";
                result = firstFactor + secondFactor;
                return result;
            }

        } else {
            int aVal = Integer.parseInt(aString);
            //Get a new Value to work with from this, then we are going to factor and split the middle
            int combinedVal = aVal * cVal;
            int[] factors = {0,0};
            int newC;
            if(combinedVal < 0)
                newC = combinedVal * -1;
            else
                newC = combinedVal;

            for(int i = 1; i <= newC; ++i) {
                if (combinedVal % i == 0) {
                    int fac2 = combinedVal / i;
                    if (i + fac2 == bVal) {
                        factors[0] = i;
                        factors[1] = fac2;
                        break;
                    } else {
                        int newFac = i * -1;
                        fac2 *= -1;
                        if (newFac + fac2 == bVal) {
                            factors[0] = newFac;
                            factors[1] = fac2;
                            break;
                        }
                    }
                }
            }
                String reformat = aVal + var1 + "^2";
                if(factors[1] > 0)
                    reformat += "+";
                reformat += factors[1] + var1;
                if(factors[0] > 0)
                    reformat += "+";
                reformat += factors[0] + var1 + "+" + cVal;

                //Pair up factor 1 with the aVal because it is usually the bigger value
                //Then factors0 with cVal

                //Get greatest common divisor
                int oPar1,oPar2;
                int aPar1, aPar2;
                int iPar1,iPar2;
                {
                 //First Part
                    BigInteger b1 = BigInteger.valueOf(aVal);
                    BigInteger b2 = BigInteger.valueOf(factors[1]);

                    BigInteger gcd = b1.gcd(b2);
                    int GCF = gcd.intValue();
                    //If first Number is negative we make gcf negative now.
                    if(aVal < 0)
                        GCF *= -1;
                    oPar1 = GCF;
                    aPar1 = aVal/GCF;
                    iPar1 = factors[1] / GCF;
                }
                {
                    BigInteger b1 = BigInteger.valueOf(factors[0]);
                    BigInteger b2 = BigInteger.valueOf(cVal);

                    BigInteger gcd = b1.gcd(b2);
                    int GCF = gcd.intValue();
                    if(factors[0] < 0)
                        GCF *= -1;
                    oPar2 = GCF;
                    aPar2 = factors[0]/GCF;
                    iPar2 = cVal/GCF;
                }
                if(aPar1 == aPar2 && iPar1 == iPar2) {
                    String res = "(";
                    if(oPar1 != 1){
                        res += oPar1;
                    }
                    res += var1;
                    if(oPar2 >= 0) {
                        res += "+";
                    }
                    res += oPar2 +")(";
                    if(aPar1 != 1)
                        res += aPar1;
                    res += var1;
                    if(iPar1 >= 0)
                        res += "+";
                    res += iPar1 + ")";
                    return res;

                } else {
                    String res = oPar1 + "x" + "(" + aPar1 + var1;
                    if(iPar1 >= 0)
                        res +="+";
                    res+= iPar1 + ")";
                    if(oPar2 >= 0)
                        res+="+";
                    res+= oPar2 + "(" + aPar2 + var1;
                    if(iPar2 >= 0)
                        res += "+";
                    res+=iPar2+")";
                    return res;
                }



            }

    }

    /**
     *
     * @param problem -> Possible Second Degree Binomial (x^2 +- c)
     * @return Fully Factored version of the problem, or error message.
     */
    @NonNull
    @SuppressWarnings("all")
    public String FactorSecondDegBinomial1(String problem){
        String result = problem;
        Pattern checker = Pattern.compile(SecondDegBinomial1);
        Matcher format = checker.matcher(problem);

        String aString, cString, var;
        if(format.matches()) {
            result = format.group(0).replaceAll("\\s","");
            aString = format.group(1);
            var = format.group(2);
            cString = format.group(3).replaceAll("\\s","");

        } else {
            return "I thought that getting to this point in the program was impossible\nError Code: 0x31";
        }
        if(aString.equals("") || aString == null) {
            //Woooo
            if(cString.contains("-")) {
                //Could be Perfect Square?
                int cVal = Integer.parseInt(cString);
                int posC = cVal;
                if(cVal < 0)
                    posC = cVal * -1;
                //Check if Perfect Square
                if(checkPerfectSquare((double)posC)) {
                    double sqr = Math.sqrt((double)posC);
                    String res = "(" + var + "+" + df.format(sqr) + ")(" + var + "-" + df.format(sqr) + ")";
                    return res;
                } else {
                    //It is not a Perfect Square
                    return format.group(0).replaceAll("\\s", "");
                }

            } else {
                return format.group(0).replaceAll("\\s","");
            }
        }


        return result;
    }

    public String FactorSecondDegBinomial2(String problem) {
        Pattern checker = Pattern.compile(SecondDegBinomial2);
        Matcher format = checker.matcher(problem);
        String result;
        String aString, var1, bString, var2;
        if(format.matches()) {
            result = format.group(0);

            aString = format.group(1);
            var1 = format.group(2);
            bString = format.group(3).replaceAll("\\s", "");
            var2 = format.group(4);
        } else {
            String[] possibleRes = solve(problem);
            String res = "";
            for(String line : possibleRes) {
                res += line + "\n";
            }
            return res;
        }
        int aVal, bVal;
        if(!var1.equals(var2)) {
            return format.group(0);
        }
        if(!aString.equals("")) {
            aString = aString.replaceAll("\\+", "");
            aVal = Integer.parseInt(aString);
        } else
            aVal = 1;
        if(bString.equals("+")) {
            bVal = 1;
        } else if(bString.equals("-")) {
            bVal = -1;
        } else {
            bString = bString.replaceAll("\\+","");
            bVal = Integer.parseInt(bString);
        }
        int oPar, aPar, iPar;
        {
            BigInteger b1 = BigInteger.valueOf(aVal);
            BigInteger b2 = BigInteger.valueOf(bVal);

            BigInteger gcd = b1.gcd(b2);
            int GCF = gcd.intValue();
            if(aVal < 0) {
                GCF *= -1;
            }
            oPar = GCF;
            aPar = aVal /GCF;
            iPar = bVal / GCF;
        }

        result = "";
        if(oPar == 1)
            result += "";
        else if(oPar==-1) {
            result +="-";
        } else {
            result +=oPar;
        }
        result += var1 + "(";
        if(aPar == 1) {
            result += "";
        }else if(aPar == -1) {
            result += "-";
        } else {
            result += aPar;
        }
        result += var1;
        if(iPar >= 0) {
            result += "+";
        }
        result += iPar + ")";
        return result;
    }

    public String FactorFirstDegBinomial(String problem) {
        Pattern pattern = Pattern.compile(FirstDegBinomial);
        Matcher matcher = pattern.matcher(problem);

        String xPref, xVar, cPref, cNum;
        if(matcher.matches()) {
            xPref = matcher.group(1);
            xVar = matcher.group(2);
            cPref = matcher.group(3);
            cNum = matcher.group(4);
        } else {
            String[] workaround = solve(problem);
            String ret = "";
            for(String string : workaround) {
                ret += string + "\n";
            }
            return ret;
        }
        double xVal;
        if(xPref == null || xPref.equals("")) {
            xVal = 1;
        } else {
            if (xPref.equals("-"))
                xVal = -1;
            else
                xVal = Double.parseDouble(xPref);
        }
        double cVal = Double.parseDouble(cNum);
        if(cPref.equals("-"))
            cVal *= -1;
        String factored;
        if(xVal < 0) {
            xVal *= -1;
            cVal *= -1;
            factored = "-(" + df.format(xVal) + xVar;
            if(cVal >= 0)
                factored += "+";
            factored += df.format(cVal) + ")";
            return factored;
        } else {
            factored = "(" + df.format(xVal) + xVar;
            if(cVal >= 0)
                factored += "+";
            factored += df.format(cVal) + ")";
            return factored;
        }
    }
    /**
     * @param a
     * @param b
     * @param c
     * @return first root, second root, complex or not.
     * <pre>
     *     {@code 0} is returned as third value if not complex\n
     *     {@code 1} is returned as third value if complex\n
     * </pre>
     */
    public double[] SolveWithQuadraticFormula(double a, double b, double c) {
        double result[] = new double[3];
        double divisor = 2 * a;
        double numerator1;
        double numerator2;
        double negb = b * -1;
        double discriminant = (Math.pow(b,2) + (-4 * a * c));
        if(discriminant < 0.0) {
            result[0] = 0;
            result[1] = 0;
            result[2] = 1;
        } if (discriminant == 0.0) {
            result[1] = negb/divisor;
            result[0] = negb/divisor;
            result[2] = 0;
        } else {
            numerator1 = negb + Math.sqrt(discriminant);
            result[0] = numerator1 / divisor;
            numerator2 = negb - Math.sqrt(discriminant);
            result[1] = numerator2 / divisor;
            result[2] = 0;
        }

        return result;
    }
    public boolean checkPerfectSquare(double x) {
        double sq = Math.sqrt(x);

        return((sq - Math.floor(sq)) == 0);
    }

}
