package com.txc;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.lang.reflect.Array;

public class constants {
    public static final String MOPERATORS = "+*/^%r!";                      // Right-side operators without minus
    public static final String MSOPERATORS = "+*/^%r";                      // Right-side, two arguments operators w/o minus
    public static final String SOPERATORS = "+*-/^%r";                      // Right-side, two arguments operators
    public static final String OPERATORS = "+*-/^%r!";                      // All right-side operators
    public static final String NUMBERS = "0123456789";
    public static final String CNUMBERS = "0123456789pe";                   // Numbers with constants
    public static final String XNUMBERS = "0123456789x";                    // Numbers with X
    public static final String XCNUMBERS = "0123456789xpe";                 // Numbers with constants and x

    public static final String AFTSOPS = "0123456789(-pesct";
    // Can come after a right-side, two-arguments operator

    public static final String XAFTSOPS = "0123456789(-x.pesct";
    // Can come after a right-side, two-arguments operator in an equation

    public static final String AFTFACT = "+*-/^%r!)";                       // Can come after a factorial
    public static final String AFTERNUM = "+*-/^%r!)0123456789.";           // Can come after a number
    public static final String AFTMINUS = "0123456789(-.pesct";             // Can come after a minus
    public static final String XAFTMINUS = "0123456789(-x.pesct";           // Can come after a minus in an equation
    public static final String ACCEPTED = "+*-/^%r!()0123456789.pesct";     // Accepted characters (in an expression)
    public static final String XACCEPTED = "+*-/^%r!()0123456789.xpesct";   // Accepted characters (in an equation)
    public static final String ORDEROFOPS = "!sct%^r/*-+";                  // Order of operators
    public static final String AFTX = "+*-/^%r!)";                          // Can come after x
    public static final String NUMBERSWITHPOINT = "0123456789.";
    public static final String TRIG = "sct";                                // Trigonometric operators
    public static final String AFTTRIG = "0123456789-pe";                   // Can come after a trigonometric operator
    public static final String XAFTTRIG = "0123456789-xpe";
    // Can come after a trigonometric operator in an equation

    public static void help(String argument) {                                     // Shows the help
        System.out.print("Usage: ");
        System.out.print(argument);
        System.out.println(" [--help | -e | -q (-s # #) (-p #)]");
        System.out.println();
        System.out.println("-e\t\texpression solving mode");
        System.out.print("-q\t\t");
        System.out.println("equation solving mode (default passages = 500,000");
        System.out.println("-s # #\t\t(After -q) custom starting values");
        System.out.println("-p #\t\t(After -q or -s) custom number of passages");
        System.out.println("-h\t\thelp");
        System.out.println();
        System.out.println("Default mode requires a space between each number, operator and bracket.");
        System.out.println("Expression and equation solving modes do not accept spaces");
        System.out.println();
        System.out.println("Operators:");
        System.out.println("# + #\taddition");
        System.out.println("# - #\tsubtraction");
        System.out.println("# * #\tmultiplication");
        System.out.println("# / #\tdivision");
        System.out.println("# ^ #\tpower:");
        // TODO
    }
    public static boolean samesign(double a, double b) {              // Returns TRUE if same sign and FALSE if different sign
        return (a >= 0 && b >= 0) || (a < 0 && b < 0);
    }
    public static double random(int m) {                // Generates a random number that tends to be larger for larger inputs

        double a = Math.round(Math.random()) * 10;

        return Math.round(Math.random()) == 0 ? a * (0.0012 * m * m) : -a * (0.0012 * m * m);
    }
    public static void loadbar(long x, long n) {                                    // Generates the loading bar
        if (x != n && x % (n / 100 + 1) != 0 && x != 1) return;
        System.out.print('\r');
        System.out.flush();

        float ratio = x / n;
        int c = Math.round(ratio * 20);

        System.out.print(Math.round(ratio * 100));
        System.out.print("% [");
        for (int i = 0; i < c; i++) System.out.print("=");
        for (int i = c; i < 20; i++) System.out.print(" ");
        System.out.print("]");
    }
}