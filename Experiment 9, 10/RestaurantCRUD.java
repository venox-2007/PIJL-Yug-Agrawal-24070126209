import java.sql.*;

public class RestaurantCRUD {

    static final String URL  = "jdbc:mysql://localhost:3306/restaurant_db";
    static final String USER = "root";
    static final String PASS = "Yuna1427";

    // ─────────────────────────────────────────────
    //  Utility: print MenuItem ResultSet as table
    // ─────────────────────────────────────────────
    static void printMenuItems(ResultSet rs) throws SQLException {
        System.out.printf("%-5s %-20s %-8s %-5s%n", "Id", "Name", "Price", "ResId");
        System.out.println("-".repeat(42));
        while (rs.next()) {
            System.out.printf("%-5d %-20s %-8.2f %-5d%n",
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getDouble("Price"),
                rs.getInt("ResId"));
        }
        System.out.println();
    }

    // ─────────────────────────────────────────────
    //  Utility: print Restaurant ResultSet as table
    // ─────────────────────────────────────────────
    static void printRestaurants(ResultSet rs) throws SQLException {
        System.out.printf("%-5s %-25s %-30s%n", "Id", "Name", "Address");
        System.out.println("-".repeat(63));
        while (rs.next()) {
            System.out.printf("%-5d %-25s %-30s%n",
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Address"));
        }
        System.out.println();
    }

    public static void main(String[] args) {

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement  st  = con.createStatement()) {

            // ══════════════════════════════════════
            //  1. INSERT — 10 Restaurants
            // ══════════════════════════════════════
            System.out.println("=== INSERT: 10 Restaurants ===");
            String[] restaurants = {
                "('Cafe Java',    'MG Road, Pune')",
                "('Pizza Palace', 'FC Road, Pune')",
                "('Spice Garden', 'Koregaon Park, Pune')",
                "('Burger Barn',  'Baner, Pune')",
                "('Pasta Point',  'Kothrud, Pune')",
                "('Sushi Stop',   'Viman Nagar, Pune')",
                "('Dosa Delight', 'Sadashiv Peth, Pune')",
                "('Noodle Nest',  'Hadapsar, Pune')",
                "('Waffle World', 'Aundh, Pune')",
                "('Taco Town',    'Wakad, Pune')"
            };
            for (String r : restaurants)
                st.executeUpdate("INSERT INTO Restaurant (Name, Address) VALUES " + r);

            // Print inserted restaurants
            ResultSet rs = st.executeQuery("SELECT * FROM Restaurant");
            printRestaurants(rs);

            // ══════════════════════════════════════
            //  2. INSERT — 10 MenuItems
            // ══════════════════════════════════════
            System.out.println("=== INSERT: 10 MenuItems ===");
            // ResId 1 = Cafe Java, 2 = Pizza Palace, etc.
            String[] menuItems = {
                "('Espresso',       60,  1)",   // Cafe Java  – price <= 100
                "('Cappuccino',     90,  1)",   // Cafe Java  – price <= 100
                "('Pasta Primavera',150, 1)",   // Cafe Java
                "('Margherita',     200, 2)",   // Pizza Palace
                "('Pepperoni Pizza',250, 2)",   // Pizza Palace – starts with P
                "('Paneer Tikka',   180, 3)",   // Spice Garden – starts with P
                "('Veg Burger',     80,  4)",   // Burger Barn  – price <= 100
                "('Prawn Fried Rice',220,6)",   // Sushi Stop   – starts with P
                "('Dosa Masala',    70,  7)",   // Dosa Delight – price <= 100
                "('Waffles',        120, 9)"    // Waffle World
            };
            for (String m : menuItems)
                st.executeUpdate("INSERT INTO MenuItem (Name, Price, ResId) VALUES " + m);

            // Print all MenuItems
            rs = st.executeQuery("SELECT * FROM MenuItem");
            printMenuItems(rs);

            // ══════════════════════════════════════
            //  3. SELECT — Price <= 100
            // ══════════════════════════════════════
            System.out.println("=== SELECT: MenuItems where Price <= 100 ===");
            rs = st.executeQuery("SELECT * FROM MenuItem WHERE Price <= 100");
            printMenuItems(rs);

            // ══════════════════════════════════════
            //  4. SELECT — Available in "Cafe Java"
            // ══════════════════════════════════════
            System.out.println("=== SELECT: MenuItems in 'Cafe Java' ===");
            rs = st.executeQuery(
                "SELECT m.Id, m.Name, m.Price, m.ResId " +
                "FROM MenuItem m " +
                "JOIN Restaurant r ON m.ResId = r.Id " +
                "WHERE r.Name = 'Cafe Java'"
            );
            printMenuItems(rs);

            // ══════════════════════════════════════
            //  5. UPDATE — Price <= 100 → set to 200
            // ══════════════════════════════════════
            System.out.println("=== UPDATE: Set Price = 200 where Price <= 100 ===");
            int updated = st.executeUpdate("UPDATE MenuItem SET Price = 200 WHERE Price <= 100");
            System.out.println("Rows updated: " + updated);
            rs = st.executeQuery("SELECT * FROM MenuItem");
            printMenuItems(rs);

            // ══════════════════════════════════════
            //  6. DELETE — Name starts with 'P'
            // ══════════════════════════════════════
            System.out.println("=== DELETE: MenuItems where Name starts with 'P' ===");
            int deleted = st.executeUpdate("DELETE FROM MenuItem WHERE Name LIKE 'P%'");
            System.out.println("Rows deleted: " + deleted);
            rs = st.executeQuery("SELECT * FROM MenuItem");
            printMenuItems(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}