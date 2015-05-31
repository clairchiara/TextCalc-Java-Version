package com.txc;

import java.util.Scanner;

public class string {
    private String exp;
    public string() {
    }
    public String getExp() {
        return exp;
    }
    public void setExp(String string) {
        exp = string;
    }
    public void setFromInput() {                // Sets from the console input

        String input;
        Scanner scanner = new Scanner(System.in);

        input = scanner.nextLine();
        exp = input;
    }
    public int findFirstNotOf(String chars) {
        for (int i = 0; i < exp.length(); i++) {
            if (!chars.contains(exp.substring(i,i+1))) return i;
        }
        return exp.length();
    }
    public void checkBrackets() {           // Checks for bracket errors

        long j = 0;

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') j++;
            else if (exp.charAt(i) == ')') j--;
            if (j < 0) {
                System.out.println("Bad brackets!");
                System.exit(1);
            }
        }
        if (j != 0) {
            System.out.println("Bad brackets!");
            System.exit(1);
        }
    }
    public void checkSpaces() {         // Checks that there are no spaces
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == ' ') {
                System.out.println("Spaces aren't allowed!");
                System.exit(1);
            }
        }
    }
    private void malformed(int place) {
        System.out.format("The expression is malformed at character %d", place);
        System.out.println();
        System.exit(1);
    }
    public boolean check(String constant, int index) {
        return constant.contains(String.valueOf(exp.charAt(index)));
    }
    public void checkForm() {           // Checks other well-formedness conditions
        for (int i = 0; i < exp.length()-1; i++) {
            if (constants.MSOPERATORS.contains(String.valueOf(exp.charAt(i)))) {
                if (check(constants.AFTSOPS, i+1)) continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == '!') {
                if (check(constants.AFTFACT, i+1) || exp.charAt(i+1) == '\n') continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == '(') {
                if (check(constants.CNUMBERS, i+1) || exp.charAt(i+1) == '(' || exp.charAt(i+1) == '-'
                    || check(constants.TRIG, i+1)) continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == ')') {
                if (check(constants.OPERATORS, i+1) || exp.charAt(i+1) == '\n' || exp.charAt(i+1) == ')') continue;
                else malformed(i+1);
            } else if (check(constants.CNUMBERS, i)) {
                if (check(constants.AFTERNUM, i+1)) continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == '-') {
                if (check(constants.AFTMINUS, i+1)) {
                    if (exp.charAt(i+1) == '-' && exp.charAt(i+2) == '-') malformed(i+1);
                    else continue;
                } else malformed(i+1);
            } else if (check(constants.TRIG, i)) {
                if (check(constants.AFTTRIG, i+1)) continue;
                else malformed(i+1);
            }
            if (!check(constants.ACCEPTED, i)) malformed(i+1);
        }
        if (exp.charAt(exp.length()-1) != ')' && !check(constants.CNUMBERS, exp.length()-1)
                && exp.charAt(exp.length()-1) != '!') malformed(exp.length());
    }
    private void xCheckForm() {           // Checks other well-formedness conditions
        for (int i = 0; i < exp.length()-1; i++) {
            if (constants.MSOPERATORS.contains(String.valueOf(exp.charAt(i)))) {
                if (check(constants.XAFTSOPS, i+1)) continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == '!') {
                if (check(constants.AFTFACT, i+1) || exp.charAt(i+1) == '\n') continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == '(') {
                if (check(constants.XCNUMBERS, i+1) || exp.charAt(i+1) == '(' || exp.charAt(i+1) == '-'
                        || check(constants.TRIG, i+1)) continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == ')') {
                if (check(constants.OPERATORS, i+1) || exp.charAt(i+1) == '\n' || exp.charAt(i+1) == ')') continue;
                else malformed(i+1);
            } else if (check(constants.CNUMBERS, i)) {
                if (check(constants.AFTERNUM, i+1)) continue;
                else malformed(i+1);
            } else if (exp.charAt(i) == '-') {
                if (check(constants.XAFTMINUS, i+1)) {
                    if (exp.charAt(i+1) == '-' && exp.charAt(i+2) == '-') malformed(i+1);
                    else continue;
                } else malformed(i+1);
            } else if (exp.charAt(i) == 'x') {
                if (check(constants.AFTX, i+1)) continue;
                else malformed(i+1);
            } else if (check(constants.TRIG, i)) {
                if (check(constants.XAFTTRIG, i+1)) continue;
                else malformed(i+1);
            }
            if (!check(constants.XACCEPTED, i)) malformed(i+1);
        }
        if (exp.charAt(exp.length()-1) != ')' && !check(constants.XCNUMBERS, exp.length()-1)
                && exp.charAt(exp.length()-1) != '!') malformed(exp.length());
    }
    public void solveEquation(double one, double two, final long passages) {

        string equation = new string();
        vector e_vector = new vector();
        double solOne, solTwo, solThree, three;
        string newEquation = new string();
        final double precision = 0.000001;

        System.out.println("Equations are solved with the bisection method");
        System.out.println("Please type in your equation, without spaces");
        equation.setFromInput();
        equation.checkSpaces();
        equation.checkBrackets();
        equation.xCheckForm();
        exp = equation.getExp();
        e_vector.populate(equation);
        e_vector.replaceX(one);
        solOne = e_vector.solveAll();
        if (Math.abs(solOne) < precision && !Double.isNaN(solOne) && !Double.isInfinite(solOne)) {
            System.out.println("\rThe approximate solution is x=" + one);
            System.exit(0);
        }
        newEquation.setExp(exp);
        e_vector.populate(newEquation);
        e_vector.replaceX(two);
        solTwo = e_vector.solveAll();
        if (Math.abs(solTwo) < precision && !Double.isInfinite(solTwo) && !Double.isNaN(solTwo)) {
            System.out.print("\rThe approximate solution is x=" + two);
            System.exit(0);
        }
        if (constants.samesign(solOne, solTwo)) {

            int i = 1;

            while (constants.samesign(solOne, solTwo)) {
                newEquation.setExp(exp);
                e_vector.populate(newEquation);
                one = constants.random(i);
                e_vector.replaceX(one);
                solOne = e_vector.solveAll();
                if (Math.abs(solOne) < precision && !Double.isInfinite(solOne) && !Double.isNaN(solOne)) {
                    System.out.println("\rThe solution is x=" + one);
                    System.exit(0);
                }
                newEquation.setExp(exp);
                e_vector.populate(newEquation);
                two = constants.random(i);
                e_vector.replaceX(two);
                solTwo = e_vector.solveAll();
                if (Math.abs(solTwo) < precision && !Double.isInfinite(solTwo) && !Double.isNaN(solTwo)) {
                    System.out.println("\rThe approximate solution is x=" + two);
                    System.exit(0);
                }
                constants.loadbar(i, passages);
                if (i > passages) {
                    System.out.println("\rSolution not found                 ");
                    System.exit(0);
                }
                i++;
            }
        }
        three = (one + two) / 2;
        newEquation.setExp(exp);
        e_vector.populate(newEquation);
        e_vector.replaceX(three);
        solThree = e_vector.solveAll();
        if (Math.abs(solThree) < precision && !Double.isInfinite(solThree) && !Double.isNaN(solThree)) {
            System.out.println("\rThe approximate solution is x=" + three);
            System.exit(0);
        }
        for (int i = 0; i < passages; i++) {
            three = (one + two) / 2;
            newEquation.setExp(exp);
            e_vector.populate(newEquation);
            e_vector.replaceX(three);
            solThree = e_vector.solveAll();
            if (Math.abs(solThree) < precision && !Double.isInfinite(solThree) && !Double.isNaN(solThree)) {
                System.out.println("\rThe approximate solution is x=" + three);
                System.exit(0);
            }
            if (constants.samesign(solTwo, solThree)) two = three;
            else one = three;
            constants.loadbar(i, passages);
        }
        if (Math.abs(solThree) < 0.1 && !Double.isInfinite(solThree) && !Double.isNaN(solThree)) {
            System.out.println("\rAfter " + passages + " passages the closest solution is x=" + three);
        } else System.out.println("\rSolution not found                 ");
        System.exit(0);
    }
}
