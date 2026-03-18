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
            Book b3 = new Book(
                    "Clean Code",
                    "Robert C. Martin",
                    720.0,
                    8,
                    "ISBN003",
                    "Software Engineering",
                    LocalDate.of(2008, 8, 1)
            );
            Book b4 = new Book(
                    "The Alchemist",
                    "Paulo Coelho",
                    399.0,
                    12,
                    "ISBN004",
                    "Fiction",
                    LocalDate.of(1988, 4, 15)
            );
            Book b5 = new Book(
                    "Atomic Habits",
                    "James Clear",
                    599.0,
                    7,
                    "ISBN005",
                    "Self-Help",
                    LocalDate.of(2018, 10, 16)
            );
            // Adding 5 books
            bookList.add(b1);
            bookList.add(b2);
            bookList.add(b3);
            bookList.add(b4);
            bookList.add(b5);
            System.out.println("\nBook Details:\n");
            for (Book b : bookList) {
                b.display();
            }
            // Book titles where genre is Fiction
            System.out.println("\nFiction Book Titles:");
            bookList.stream()
                    .filter(b -> b.getGenre().equalsIgnoreCase("Fiction"))
                    .forEach(b -> System.out.println(b.getTitle()));
            // Average price of all books
            double avgPrice = bookList.stream()
                    .mapToDouble(b -> b.getPrice())
                    .average()
                    .orElse(0.0);
            System.out.println(String.format("\nAverage Price of All Books: Rs.%.2f", avgPrice));
            // Exception Demo (Invalid price)
            Book invalidBook = new Book(
                    "Invalid Book",
                    "Test Author",
                    -200,
                    3,
                    "ISBN006",
                    "Test",
                    LocalDate.of(2022, 1, 1)
            );
            bookList.add(invalidBook);
        } catch (InvalidBookException e) {
            System.out.println("\nException Occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General Exception: " + e);
        }
    }
}