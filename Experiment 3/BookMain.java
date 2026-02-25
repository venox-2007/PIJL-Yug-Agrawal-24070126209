import java.time.LocalDate;
import java.util.ArrayList;
public class BookMain {
    public static void main(String[] args) {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            Book b1 = new Book(
                    "Java Programming",
                    "Herbert Schildt",
                    550.0,
                    10,
                    "ISBN001",
                    "Programming",
                    LocalDate.of(2019, 5, 10)
            );
            Book b2 = new Book(
                    "Python for Data Science",
                    "Jake VanderPlas",
                    650.0,
                    5,
                    "ISBN002",
                    "Data Science",
                    LocalDate.of(2018, 3, 15)
            );
            Book b3 = new Book(b1); // Copy constructor
            bookList.add(b1);
            bookList.add(b2);
            bookList.add(b3);
            System.out.println("\nBook Details:\n");
            for (Book b : bookList) {
                b.display();
            }
            // Exception Demo
            Book b4 = new Book(
                    "Invalid Book",
                    "Test Author",
                    -200,
                    3,
                    "ISBN003",
                    "Test",
                    LocalDate.of(2025, 1, 1)
            );
            bookList.add(b4);
        } catch (InvalidBookException e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General Exception: " + e);
        }
    }
}