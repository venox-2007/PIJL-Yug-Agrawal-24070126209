public class VectorMain {
    public static void main(String[] args) {
        // Test 1: Valid 3D vectors
        try {
            VectorClass v1 = new VectorClass(new double[]{1, 2, 3});
            VectorClass v2 = new VectorClass(new double[]{4, 5, 6});
            VectorClass sum = v1.add(v2);
            VectorClass diff = v1.subtract(v2);
            double dot = v1.dotProduct(v2);
            System.out.println("\nTest 1");
            System.out.print("Vector 1: "); v1.print();
            System.out.print("Vector 2: "); v2.print();
            System.out.print("Addition: "); sum.print();
            System.out.print("Subtraction: "); diff.print();
            System.out.println("Dot Product: " + dot);
        } catch (InvalidVectorException e) {
            System.out.println(e.getMessage());
        }
        // Test 2: Valid 2D vectors
        try {
            VectorClass v1 = new VectorClass(new double[]{2, 3});
            VectorClass v2 = new VectorClass(new double[]{5, 7});
            VectorClass sum = v1.add(v2);
            VectorClass diff = v1.subtract(v2);
            double dot = v1.dotProduct(v2);
            System.out.println("\nTest 2");
            System.out.print("Vector 1: "); v1.print();
            System.out.print("Vector 2: "); v2.print();
            System.out.print("Addition: "); sum.print();
            System.out.print("Subtraction: "); diff.print();
            System.out.println("Dot Product: " + dot);
        } catch (InvalidVectorException e) {
            System.out.println(e.getMessage());
        }
        // Test 3: Dimension mismatch error
        try {
            VectorClass v1 = new VectorClass(new double[]{1, 2});
            VectorClass v2 = new VectorClass(new double[]{3, 4, 5});
            VectorClass sum = v1.add(v2);
            System.out.println("\nTest 3");
            sum.print();
        } catch (InvalidVectorException e) {
            System.out.println("\nTest 3 Error: " + e.getMessage());
        }
        // Test 4: Invalid vector size
        try {
            VectorClass v1 = new VectorClass(new double[]{1});
            System.out.println("\nTest 4");
            v1.print();
        } catch (InvalidVectorException e) {
            System.out.println("\nTest 4 Error: " + e.getMessage());
        }
    }
}