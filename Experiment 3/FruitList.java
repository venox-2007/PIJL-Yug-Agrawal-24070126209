import java.util.ArrayList;
public class FruitList {
    public static void main(String[] args) {
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Mango");
        fruits.add("Orange");
        fruits.add("Grapes");
        fruits.add("Guava");
        fruits.forEach(fruit -> {
            if (fruit.startsWith("G")) {
                System.out.println(fruit);
            }
        });
    }
}