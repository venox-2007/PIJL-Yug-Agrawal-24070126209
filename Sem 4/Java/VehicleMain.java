import java.time.Year;
public class VehicleMain {
    public static void main(String[] args) {
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Vehicle("Honda", "City", 1200000, "HON123");
        v2.color = "White";
        v2.fuelType = 'P';
        v2.mfgYear = Year.of(2021);
        v2.setRegNo("MH12AB2222");
        Vehicle v3 = new Vehicle("Maruti", "Swift", 800000, "MAR456");
        v3.color = "Red";
        v3.fuelType = 'P';
        v3.mfgYear = Year.of(2020);
        v3.setRegNo("MH14CD3333");
        Vehicle v4 = new Vehicle("Hyundai", "Creta", 1600000, "HYN789");
        v4.color = "Grey";
        v4.fuelType = 'D';
        v4.mfgYear = Year.of(2022);
        v4.setRegNo("KA01EF4444");
        Vehicle v5 = new Vehicle("Tata", "Nexon", 1400000, "TAT321");
        v5.color = "Blue";
        v5.fuelType = 'D';
        v5.mfgYear = Year.of(2021);
        v5.setRegNo("DL05GH5555");
        Vehicle v6 = new Vehicle("Mercedes", "S-Class", 4500000, "MER654");
        v6.color = "Black";
        v6.fuelType = 'P';
        v6.mfgYear = Year.of(2022);
        v6.setRegNo("DL01IJ6666");
        Vehicle v7 = new Vehicle("Tesla", "Model 3", 3500000, "TES999");
        v7.color = "White";
        v7.fuelType = 'E';
        v7.mfgYear = Year.of(2023);
        v7.setRegNo("MH01KL7777");
        Vehicle v8 = new Vehicle(v2);
        v8.setMfgCode("HON888");
        v8.setRegNo("MH12MN8888");
        Vehicle v9 = new Vehicle(v4);
        v9.setMfgCode("HYN999");
        v9.setRegNo("KA01OP9999");
        Vehicle v10 = new Vehicle(v7);
        v10.setMfgCode("TES111");
        v10.setRegNo("MH01QR1111");
        Vehicle[] vehicles = {
            v1, v2, v3, v4, v5,
            v6, v7, v8, v9, v10
        };
        double distance = 300;
        System.out.printf(
            "%-10s %-12s %-6s %-12s %-10s%n",
            "Brand", "Model", "Fuel", "Price", "Mileage"
        );
        System.out.println("--------------------------------------------------------------");
        for (Vehicle v : vehicles) {
            System.out.printf(
                "%-10s %-12s %-6c %-12.2f %-10.2f%n",
                v.brandName,
                v.modelName,
                v.fuelType,
                v.price,
                v.calcMileage(distance)
            );
        }
    }
}