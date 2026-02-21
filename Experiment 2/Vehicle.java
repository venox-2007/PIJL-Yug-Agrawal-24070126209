import java.time.Year;
public class Vehicle {
    // Public data members
    public String brandName;
    public String modelName;
    public double price;
    public String color;
    public Year mfgYear;
    public char fuelType; // P-Petrol, D-Diesel, E-Electric, H-Hybrid, C-CNG
    // Private data members
    private String mfgCode;
    private String regNo;
    // Getters and setters
    public void setMfgCode(String mCode) {
        mfgCode = mCode;
    }
    public String getMfgCode() {
        return mfgCode;
    }
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
    public String getRegNo() {
        return regNo;
    }
    // Default constructor
    public Vehicle() {
        brandName = "Hyundai";
        modelName = "i10";
        price = 500000;
        color = "Silver";
        mfgYear = Year.of(2020);
        fuelType = 'P';
        mfgCode = "HYN12345";
        regNo = "MH12AA1111";
    }
    // Parameterized constructor
    public Vehicle(String brandName, String modelName, double price, String mfgCode) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.price = price;
        this.mfgCode = mfgCode;
    }
    // Copy constructor
    public Vehicle(Vehicle v) {
        brandName = v.brandName;
        modelName = v.modelName;
        price = v.price;
        color = v.color;
        mfgYear = v.mfgYear;
        fuelType = v.fuelType;
        mfgCode = v.mfgCode;
        regNo = v.regNo;
    }
    // Required methods
    public void start() {
        System.out.println("Vehicle started");
    }
    public void stop() {
        System.out.println("Vehicle stopped");
    }
    public void drive() {
        System.out.println("Vehicle is driving");
    }
    public void changeSpeed(int newSpeed) {
        System.out.println("Speed changed to " + newSpeed);
    }
    // Mileage depends on fuel type and distance
    public double calcMileage(double distance) {
        double efficiency;
        switch (fuelType) {
            case 'P': efficiency = 15; break;   // Petrol
            case 'D': efficiency = 20; break;   // Diesel
            case 'E': efficiency = 120; break;  // Electric
            case 'H': efficiency = 25; break;   // Hybrid
            case 'C': efficiency = 22; break;   // CNG
            default: efficiency = 0;
        }
        return distance / efficiency;
    }
}