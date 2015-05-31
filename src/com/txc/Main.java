package com.txc;

import org.apache.commons.math3.special.Gamma;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String help = "Error. Use --help for help";

        if (args.length == 6) {
            if(!args[0].equals("-q")) System.out.println(help);
            else if (!args[1].equals("-s")) System.out.println(help);
            else if (!args[4].equals("-p")) System.out.println(help);
            else { // Equation solving mode with custom parameters

                string equation = new string();

                equation.solveEquation(Double.valueOf(args[2]), Double.valueOf(args[3]), Long.valueOf(args[5]));
        }
    } else if (args.length == 4) { // Equation solving mode with custom starting values
            if (!args[0].equals("-q")) System.out.println(help);
            else if (!args[1].equals("-s")) System.out.println(help);
            else {

                string equation = new string();

                equation.solveEquation(Double.valueOf(args[2]), Double.valueOf(args[3]), 500000);
            }
        } else if (args.length == 3) { // Equation solving mode with custom number of passages
            if (!args[0].equals("-q")) System.out.println(help);
            else if (!args[1].equals("-p")) System.out.println(help);
            else {

                string equation = new string();

                equation.solveEquation(constants.random(1), constants.random(1), Long.valueOf(args[2]));
            }
        } else if (args.length == 1) { // Help
            if (args[0].equals("--help")) ; // TODO
            else if (args[0].equals("-h")) ; // TODO
            else if (args[0].equals("-e")) { // Expression solving mode
                System.out.println("Please type in your expression, without spaces");

                string expression = new string();
                vector eqVector = new vector();

                expression.setFromInput();
                expression.checkBrackets();
                expression.checkSpaces();
                expression.checkForm();
                eqVector.populate(expression);
                System.out.println("The solution is " + eqVector.solveAll());
                System.exit(0);
            } else if (args[0].equals("-q")) { // Equation solving mode with default parameters

                string equation = new string();

                equation.solveEquation(constants.random(1), constants.random(1), 500000);
            } else System.out.println(help);
        } else if (args.length == 0) { // Calculator mode

            double one, two, result = Double.NaN;
            char oper;
            Scanner input = new Scanner(System.in);

            System.out.println("\nPlease type in your operation");
            one = input.nextDouble();
            oper = input.next().charAt(0);

            switch (oper) {
                case '+':
                    two = input.nextDouble();
                    result = one + two;
                    break;
                case '-':
                    two = input.nextDouble();
                    result = one - two;
                    break;
                case '*':
                    two = input.nextDouble();
                    result = one * two;
                    break;
                case '/':
                    two = input.nextDouble();
                    result = one / two;
                    break;
                case '^':
                    two = input.nextDouble();
                    result = Math.pow(one, two);
                    break;
                case '%':
                    two = input.nextDouble();
                    result = Math.IEEEremainder(one, two);
                    break;
                case 'r':
                    two = input.nextDouble();
                    result = Math.pow(one, 1 / two);
                    break;
                case '!':
                    result = Gamma.gamma(one + 1);
                    break;
                case 's':
                    result = Math.sin(one);
                    break;
                case 'c':
                    result = Math.cos(one);
                    break;
                case 't':
                    result = Math.tan(one);
                    break;
                default:
                    result = Double.NaN;
                    break;
            }
            System.out.println(result);
            for (;;) {
                oper = input.next().charAt(0);
                switch (oper) {
                    case '+':
                        two = input.nextDouble();
                        result += two;
                        break;
                    case '-':
                        two = input.nextDouble();
                        result -= two;
                        break;
                    case '*':
                        two = input.nextDouble();
                        result *= two;
                        break;
                    case '/':
                        two = input.nextDouble();
                        result /= two;
                        break;
                    case '^':
                        two = input.nextDouble();
                        result = Math.pow(result, two);
                        break;
                    case '%':
                        two = input.nextDouble();
                        result = Math.IEEEremainder(result, two);
                        break;
                    case 'r':
                        two = input.nextDouble();
                        result = Math.pow(result, 1 / two);
                        break;
                    case '!':
                        result = Gamma.gamma(result + 1);
                        break;
                    case 's':
                        result = Math.sin(result);
                        break;
                    case 'c':
                        result = Math.cos(result);
                        break;
                    case 't':
                        result = Math.tan(result);
                        break;
                    default:
                        result = Double.NaN;
                        break;
                }
                System.out.println(result);
            }
        }
    }
}
