import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    private static double EPSILON = 1e-9;
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            br.close();

            String[] terms = line.split("(?=[+-])");
            this.coefficients = new double[terms.length];
            this.exponents = new int[terms.length];
            for (int i = 0; i < terms.length; i++) {
                String[] s = terms[i].split("x");
                if (terms[i].contains("x")) {
                    this.coefficients[i] = Double.parseDouble(s[0]);
                    this.exponents[i] = Integer.parseInt(s[1]);
                } else {
                    this.coefficients[i] = Double.parseDouble(s[0]);
                    this.exponents[i] = 0;
                }
            }
        } catch (IOException e) {

        }
    }

    public Polynomial add(Polynomial other) {
        int maxExponent = 0;
        for (int exponent : this.exponents) {
            maxExponent = Math.max(maxExponent, exponent);
        }
        for (int exponent : other.exponents) {
            maxExponent = Math.max(maxExponent, exponent);
        }

        double[] exponentsToCoefficients = new double[maxExponent + 1]; // include maxExponent itself
        for (int i = 0; i < this.exponents.length; i++) {
            exponentsToCoefficients[this.exponents[i]] += this.coefficients[i];
        }
        for (int i = 0; i < other.exponents.length; i++) {
            exponentsToCoefficients[other.exponents[i]] += other.coefficients[i];
        }

        // count non-redundant terms to initialize size
        int terms = 0;
        for (double coefficient: exponentsToCoefficients) {
            if (Math.abs(coefficient) >= EPSILON) {
                terms++;
            }
        }

        // only add non-redundant terms
        double[] coefficients = new double[terms];
        int[] exponents = new int[terms];
        int j = 0;
        for (int i = 0; i < exponentsToCoefficients.length; i++) {
            if (Math.abs(exponentsToCoefficients[i]) >= EPSILON) {
                exponents[j] = i;
                coefficients[j] = exponentsToCoefficients[i];
                j++;
            }
        }
        return new Polynomial(coefficients, exponents);
    }

    public Polynomial multiply(Polynomial other) {
        int maxExponent1 = 0;
        for (int exponent : this.exponents) {
            maxExponent1 = Math.max(maxExponent1, exponent);
        }
        int maxExponent2 = 0;
        for (int exponent : other.exponents) {
            maxExponent2 = Math.max(maxExponent2, exponent);
        }

        double[] exponentsToCoefficients = new double[maxExponent1 + maxExponent2 + 1]; // deg(p * q) = deg(p) + deg(q)
        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < other.exponents.length; j++) {
                int exp1 = this.exponents[i];
                int exp2 = other.exponents[j];
                exponentsToCoefficients[exp1 + exp2] += this.coefficients[i] * other.coefficients[j];
            }
        }

        // count non-redundant terms to initialize size
        int terms = 0;
        for (double coefficient: exponentsToCoefficients) {
            if (Math.abs(coefficient) >= EPSILON) {
                terms++;
            }
        }
        
        // only add non-redundant terms
        double[] coefficients = new double[terms];
        int[] exponents = new int[terms];
        int j = 0;
        for (int i = 0; i < exponentsToCoefficients.length; i++) {
            if (Math.abs(exponentsToCoefficients[i]) >= EPSILON) {
                exponents[j] = i;
                coefficients[j] = exponentsToCoefficients[i];
                j++;
            }
        }
        return new Polynomial(coefficients, exponents);
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < this.exponents.length; i++) {
            sum += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < EPSILON;
    }

    public void saveToFile(String file) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < this.exponents.length; i++) {
                int exponent = this.exponents[i];
                double coefficient = this.coefficients[i];

                if (i == 0 && coefficient > 0) { // omit + sign when positive number first
                    bw.write("" + coefficient);
                } else {
                    bw.write(coefficient > 0 ? "+" : "");
                    bw.write("" + coefficient);
                }

                if (exponent != 0) {
                    bw.write("x" + exponent);
                }
            }

            bw.close();
        } catch (IOException e) {

        }
    }

    @Override
    public String toString() {
        if (this.coefficients.length == 0) return "0";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.exponents.length; i++) {
            int exponent = this.exponents[i];
            double coefficient = this.coefficients[i];

            if (i == 0 && coefficient > 0) {
                sb.append(coefficient);
            } else {
                sb.append(coefficient > 0 ? "+" : "");
                sb.append(coefficient);
            }

            if (exponent != 0) {
                sb.append("x^" + exponent);
            }
        }
        return sb.toString();
    }
}