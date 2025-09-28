import java.io.File;

public class Driver {
    public static void main(String[] args) {
        // Original examples
        double[] a1 = {6, -2, 5};
        int[] b1 = {0, 1, 3};

        double[] a2 = {-3, 2, 10, -5};
        int[] b2 = {3, 1, 5, 2};

        double[] a3 = {2, -3, -2};
        int[] b3 = {2, 1, 0};

        Polynomial p1 = new Polynomial(a1, b1); // 6 - 2x + 5x^3
        Polynomial p2 = new Polynomial(a2, b2); // -3x^3 + 2x + 10x^5 - 5x^2
        Polynomial p3 = new Polynomial(a3, b3); // 2x^2 - 3x - 2

        System.out.println("=== Original Examples ===");
        System.out.println("p1(x) = " + p1);
        System.out.println("p2(x) = " + p2);
        System.out.println("p3(x) = " + p3);

        System.out.println("p1(1) = " + p1.evaluate(1));
        System.out.println("p3(-0.5) has root: " + p3.hasRoot(-0.5));
        System.out.println("p3(2) has root: " + p3.hasRoot(2));
        System.out.println("p3(0) has root: " + p3.hasRoot(0));

        System.out.println("p1 + p2 = " + p1.add(p2));
        System.out.println("p2 + p1 = " + p2.add(p1));

        // Edge cases
        System.out.println("\n=== Extra Tests (Edge Cases) ===");

        // Zero polynomial
        Polynomial zero = new Polynomial();
        System.out.println("Zero polynomial: " + zero);
        System.out.println("Zero(5) = " + zero.evaluate(5));

        // Polynomial with gaps (2 - x^3)
        Polynomial gap = new Polynomial(new double[]{2, -1}, new int[]{0, 3});
        System.out.println("Gap polynomial: " + gap);
        System.out.println("Gap(2) = " + gap.evaluate(2));

        // Addition that cancels terms (5x^3 + -5x^3)
        Polynomial c1 = new Polynomial(new double[]{5}, new int[]{3});
        Polynomial c2 = new Polynomial(new double[]{-5}, new int[]{3});
        Polynomial cancel = c1.add(c2);
        System.out.println("5x^3 + (-5x^3) = " + cancel);

        // Overlap addition (2 + 3x) + (-2 + 4x) = 7x
        Polynomial o1 = new Polynomial(new double[]{2, 3}, new int[]{0, 1});
        Polynomial o2 = new Polynomial(new double[]{-2, 4}, new int[]{0, 1});
        Polynomial overlap = o1.add(o2);
        System.out.println("(2 + 3x) + (-2 + 4x) = " + overlap);

        // Evaluate a bigger polynomial
        System.out.println("p2(2) = " + p2.evaluate(2));

        System.out.println("\n=== Multiplication Tests ===");

        // (x - 1)(x^2 + x + 1) = x^3 - 1
        Polynomial f1 = new Polynomial(new double[]{-1, 1}, new int[]{0, 1});   // x - 1
        Polynomial f2 = new Polynomial(new double[]{1, 1, 1}, new int[]{0, 1, 2}); // x^2 + x + 1
        Polynomial product = f1.multiply(f2);
        System.out.println("(x - 1)(x^2 + x + 1) = " + product);

        // Check evaluation: (x^3 - 1)
        System.out.println("product(2) = " + product.evaluate(2)); // 7
        System.out.println("product(1) = " + product.evaluate(1)); // 0

        // Simple multiplication: (2 + 3x)(1 - x)
        Polynomial m1 = new Polynomial(new double[]{2, 3}, new int[]{0, 1}); // 2 + 3x
        Polynomial m2 = new Polynomial(new double[]{1, -1}, new int[]{0, 1}); // 1 - x
        Polynomial mProd = m1.multiply(m2);
        System.out.println("(2 + 3x)(1 - x) = " + mProd);

        // Multiply with zero polynomial
        Polynomial zeroPoly = new Polynomial();
        Polynomial zeroProd = m1.multiply(zeroPoly);
        System.out.println("(2 + 3x)(0) = " + zeroProd);

        // Multiply with a gap polynomial (2 - x^3)(x^2)
        Polynomial g1 = new Polynomial(new double[]{2, -1}, new int[]{0, 3}); // 2 - x^3
        Polynomial g2 = new Polynomial(new double[]{1}, new int[]{2});        // x^2
        Polynomial gProd = g1.multiply(g2);
        System.out.println("(2 - x^3)(x^2) = " + gProd);

        // File
        File file = new File("polynomial.txt");
        Polynomial h1 = new Polynomial(file);
        System.out.println("file = " + h1);
        h1.saveToFile("pout.txt");

        /*
            === Original Examples ===
            p1(x) = 6 - 2x^1 + 5x^3
            p2(x) = 2x^1 - 5x^2 - 3x^3 + 10x^5
            p3(x) = -2 - 3x^1 + 2x^2
            p1(1) = 9.0
            p3(-0.5) has root: true
            p3(2) has root: true
            p3(0) has root: false
            p1 + p2 = 6 - 5x^2 + 2x^3 + 10x^5
            p2 + p1 = (same as above)

            === Extra Tests (Edge Cases) ===
            Zero polynomial: 0
            Zero(5) = 0.0
            Gap polynomial: 2 - x^3
            Gap(2) = -6.0
            5x^3 + (-5x^3) = 0
            (2 + 3x) + (-2 + 4x) = 7x
            p2(2) = 280.0

            === Multiplication Tests ===
            (x - 1)(x^2 + x + 1) = -1 + x^3
            product(2) = 7.0
            product(1) = 0.0
            (2 + 3x)(1 - x) = 2 + x - 3x^2
            (2 + 3x)(0) = 0
            (2 - x^3)(x^2) = 2x^2 - x^5

            === File Tests ===
            file = 5.0-3.0x^2+7.0x^8
            File contents: 5.0-3.0x2+7.0x8
         */
    }
}
