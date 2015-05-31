package com.txc;

import org.apache.commons.math3.special.Gamma;

import java.util.ArrayList;
import java.util.Scanner;

class element {
    protected double val;               // Contains numbers
    protected char notval;              // Contains operators
    public element() {
        val = Double.NaN;
        notval = '\0';
    }
    public element(double a, char b) {
        val = a;
        notval = b;
    }
    public element(double a) {
        val = a;
        notval = '\0';
    }
    public element(char b) {
        val = Double.NaN;
        notval = b;
    }
    public double getVal() {
        return val;
    }
    public char getNotval() {
        return notval;
    }
    public void setVal(double number) {
        val = number;
    }
    public void setNotval(char oper) {
        notval = oper;
    }
}

public class vector {
    private ArrayList<element> exp;
    public vector() {
        exp = new ArrayList<element>();
    }
    public ArrayList<element> getExp() {
        return exp;
    }
    public void setExp(ArrayList<element> input) {
        exp = input;
    }
    public void populate(string expression) {

        double number;
        char character;
        int remainingLength = expression.getExp().length();

        exp.clear();
        while (remainingLength != 0) {
            if (expression.check(constants.NUMBERSWITHPOINT, 0)) {
                int cut = expression.findFirstNotOf(constants.NUMBERSWITHPOINT);
                number = Double.valueOf(expression.getExp().substring(0, cut));
                expression.setExp(expression.getExp().substring(cut, expression.getExp().length()));
                exp.add(new element(number));
                remainingLength = expression.getExp().length();
            } else {
                character = expression.getExp().charAt(0);
                exp.add(new element(character));
                expression.setExp(expression.getExp().substring(1));
                remainingLength--;
            }
        }
    }
    private void negatives() {
        for (int i = 0; i <= exp.size()-1; i++) {
            if (exp.get(i).notval == '-') {
                if (i == 0) {

                    element n = new element(0 - exp.get(i+1).val);

                    exp.remove(i+1);
                    exp.add(i+1, n);
                    exp.remove(i);
                } else if (exp.get(i-1).notval != ')' && exp.get(i-1).notval != '!' && exp.get(i-1).notval != '\0') {

                    element n = new element(0 - exp.get(i+1).val);

                    exp.remove(i+1);
                    exp.add(i+1, n);
                    exp.remove(i);
                }
            }
        }
    }
    public void replaceX(double value) {

        element evalue = new element(value);

        for (int i = 0; i<= exp.size()-1; i++) {
            if (exp.get(i).notval == 'x') {
                exp.remove(i);
                exp.add(i, evalue);
            }
        }
    }
    private void replaceConst() {

        element pi = new element(Math.PI);
        element e = new element(Math.E);

        for (int i = 0; i <= exp.size()-1; i++) {
            if (exp.get(i).notval == 'p') {
                exp.remove(i);
                exp.add(i, pi);
            } else if (exp.get(i).notval == 'e') {
                exp.remove(i);
                exp.add(i, e);
            }
        }
    }
    private int countParentheses() {

        int parentheses = 0;

        for (int i = 0; i < exp.size(); i++) if (exp.get(i).notval == '(' || exp.get(i).notval == ')') parentheses++;
        return parentheses;
    }
    private void singleArgOp(int index, double result) {

        element eresult = new element(result);

        exp.remove(index-1);
        exp.add(index-1, eresult);
        exp.remove(index);
    }
    private void doubleArgOp(int index, double result) {

        element eresult = new element(result);

        exp.remove(index-1);
        exp.add(index-1, eresult);
        exp.remove(index);
        exp.remove(index);
    }
    private void trig(int index, double result) {

        element eresult = new element(result);

        exp.remove(index);
        exp.remove(index);
        exp.add(index, eresult);
    }
    private void solveBrackets() {

        int i, j = 0, k;

        for (i = exp.size()-1; i >= 0; i--) {
            if (exp.get(i).notval == '(') {
                j = i;
                break;
            }
        }
        for (k = 0; k < 11; k++) {
            for (i = j; ;) { // Breaks when it reaches a closing bracket
                if (exp.get(i).notval == constants.ORDEROFOPS.charAt(k)) {
                    switch (constants.ORDEROFOPS.charAt(k)) {
                        case '+':
                            doubleArgOp(i, exp.get(i-1).val + exp.get(i+1).val);
                            break;
                        case '-':
                            doubleArgOp(i, exp.get(i-1).val - exp.get(i+1).val);
                            break;
                        case '*':
                            doubleArgOp(i, exp.get(i-1).val * exp.get(i+1).val);
                            break;
                        case '/':
                            doubleArgOp(i, exp.get(i-1).val / exp.get(i+1).val);
                            break;
                        case '^':
                            doubleArgOp(i, Math.pow(exp.get(i-1).val, exp.get(i+1).val));
                            break;
                        case '%':
                            doubleArgOp(i, Math.IEEEremainder(exp.get(i-1).val, exp.get(i+1).val));
                            break;
                        case 'r':
                            doubleArgOp(i, Math.pow(exp.get(i-1).val, 1 / exp.get(i+1).val));
                            break;
                        case '!':
                            singleArgOp(i, Gamma.gamma(exp.get(i-1).val+1));
                            break;
                        case 's':
                            trig(i, Math.sin(exp.get(i+1).val));
                            break;
                        case 'c':
                            trig(i, Math.cos(exp.get(i+1).val));
                            break;
                        case 't':
                            trig(i, Math.tan(exp.get(i+1).val));
                            break;
                    }
                    i--;
                } else if (exp.get(i).notval == ')') {
                    i = j;
                    break;
                } else i++;
            }
        }
        exp.remove(i);
        exp.remove(i+1);
    }
    private double solveExpression() {
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i <= exp.size()-1; i++) {
                if (exp.get(i).notval == constants.ORDEROFOPS.charAt(k)) {
                    switch (constants.ORDEROFOPS.charAt(k)) {
                        case '+':
                            doubleArgOp(i, exp.get(i-1).val + exp.get(i+1).val);
                            break;
                        case '-':
                            doubleArgOp(i, exp.get(i-1).val - exp.get(i+1).val);
                            break;
                        case '*':
                            doubleArgOp(i, exp.get(i-1).val * exp.get(i+1).val);
                            break;
                        case '/':
                            doubleArgOp(i, exp.get(i-1).val / exp.get(i+1).val);
                            break;
                        case '^':
                            doubleArgOp(i, Math.pow(exp.get(i-1).val, exp.get(i+1).val));
                            break;
                        case '%':
                            doubleArgOp(i, Math.IEEEremainder(exp.get(i-1).val, exp.get(i+1).val));
                            break;
                        case 'r':
                            doubleArgOp(i, Math.pow(exp.get(i-1).val, 1 / exp.get(i+1).val));
                            break;
                        case '!':
                            singleArgOp(i, Gamma.gamma(exp.get(i-1).val+1));
                            break;
                        case 's':
                            trig(i, Math.sin(exp.get(i+1).val));
                            break;
                        case 'c':
                            trig(i, Math.cos(exp.get(i+1).val));
                            break;
                        case 't':
                            trig(i, Math.tan(exp.get(i+1).val));
                            break;
                    }
                    i--;
                }
            }
        }
        return exp.get(0).val;
    }
    public double solveAll() {

        int parentheses = countParentheses();

        replaceConst();
        negatives();
        while (parentheses > 0) {
            solveBrackets();
            parentheses -= 2;
        }
        return solveExpression();
    }
}
