import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// ─────────────────────────────────────────────
// Product Interface (used by both Adapter and NewProduct)
// ─────────────────────────────────────────────
interface Product {
    void displayDetails();
}

// ─────────────────────────────────────────────
// LegacyItem – old/incompatible class
// ─────────────────────────────────────────────
class LegacyItem {
    private int itemId;
    private String description;

    public LegacyItem(int itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }

    public void print() {
        System.out.println("Legacy Item [ID: " + itemId + ", Description: " + description + "]");
    }
}

// ─────────────────────────────────────────────
// ProductAdapter – Adapter Pattern
// Wraps LegacyItem so it can be used as a Product
// ─────────────────────────────────────────────
class ProductAdapter implements Product {
    private LegacyItem legacyItem;

    public ProductAdapter(LegacyItem legacyItem) {
        this.legacyItem = legacyItem;
    }

    @Override
    public void displayDetails() {
        legacyItem.print();  // delegates to LegacyItem's print()
    }
}

// ─────────────────────────────────────────────
// NewProduct – modern product implementing Product directly
// ─────────────────────────────────────────────
class NewProduct implements Product {
    private String name;

    public NewProduct(String name) {
        this.name = name;
    }

    @Override
    public void displayDetails() {
        System.out.println("New Product: " + name);
    }
}

// ─────────────────────────────────────────────
// InventoryManager – Singleton Pattern
// Holds a single shared list of all products
// ─────────────────────────────────────────────
class InventoryManager {
    private static InventoryManager instance;
    private List<Product> inventory;

    // Private constructor prevents direct instantiation
    private InventoryManager() {
        inventory = new ArrayList<>();
    }

    // Global access point – lazy initialization
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    // Returns an Iterator for the product list – Iterator Pattern
    public Iterator<Product> returnInventory() {
        return inventory.iterator();
    }
}

// ─────────────────────────────────────────────
// Main Class
// ─────────────────────────────────────────────
public class InventorySystem {
    public static void main(String[] args) {

        // Singleton: only one instance of InventoryManager
        InventoryManager manager = InventoryManager.getInstance();

        // Add NewProduct objects
        manager.addProduct(new NewProduct("Wireless Keyboard"));
        manager.addProduct(new NewProduct("USB-C Hub"));

        // Add LegacyItem objects via Adapter
        manager.addProduct(new ProductAdapter(new LegacyItem(101, "Old CRT Monitor")));
        manager.addProduct(new ProductAdapter(new LegacyItem(102, "Vintage Mechanical Mouse")));

        // Iterator: traverse inventory using Iterator pattern
        Iterator<Product> iterator = manager.returnInventory();

        System.out.println("===== Inventory Details =====");
        while (iterator.hasNext()) {
            Product product = iterator.next();
            product.displayDetails();
        }
    }
}