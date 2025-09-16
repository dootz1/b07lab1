import java.lang.Math;

public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }
    }

    public Polynomial add(Polynomial other) {
        int length = Math.max(this.coefficients.length, other.coefficients.length);
        double[] temp = new double[length];

        int i = 0;
        while (i < this.coefficients.length && i < other.coefficients.length) {
            temp[i] = this.coefficients[i] + other.coefficients[i];
            i++;
        }

        while(i < this.coefficients.length) {
            temp[i] = this.coefficients[i];
            i++;
        }

        while(i < other.coefficients.length) {
            temp[i] = other.coefficients[i];
            i++;
        }

        return new Polynomial(temp);
    }

    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            sum += this.coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}