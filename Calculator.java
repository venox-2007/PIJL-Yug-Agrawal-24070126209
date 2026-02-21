import java.util.Scanner;
public class Calculator {
    public double a, b;
    public double add(double a, double b) {
        return a + b;
    }
    public double subtract(double a, double b) {
        return a - b;
    }
    public double multiply(double a, double b) {
        return a * b;
    }
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by 0 is not allowed.");
        }
        return a / b;
    }
    public double mod(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Modulus by 0 is not allowed.");
        }
        return a % b;
    }
    public static void main(String[] args) {
        Calculator op = new Calculator();
        Scanner scan = new Scanner(System.in);
        int choice;
        do {
            System.out.print("\nEnter the first number: ");
            op.a = scan.nextDouble();
            System.out.print("Enter the second number: ");
            op.b = scan.nextDouble();
            System.out.println("\n0. Exit");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Modulus");
            System.out.print("Enter choice: ");
            choice = scan.nextInt();
            try {
                switch (choice) {
                    case 0:
                        System.out.println("Calculator exited.");
                        break;
                    case 1:
                        System.out.println("Result: " + op.add(op.a, op.b));
                        break;
                    case 2:
                        System.out.println("Result: " + op.subtract(op.a, op.b));
                        break;
                    case 3:
                        System.out.println("Result: " + op.multiply(op.a, op.b));
                        break;
                    case 4:
                        System.out.println("Result: " + op.divide(op.a, op.b));
                        break;
                    case 5:
                        System.out.println("Result: " + op.mod(op.a, op.b));
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 0);
        scan.close();
    }
}