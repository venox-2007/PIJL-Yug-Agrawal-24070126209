import java.time.LocalDate;
// Custom Exception
class InvalidBookException extends Exception {
    public InvalidBookException(String message) {
        super(message);
    }
}
public class Book {
    private String title;
    private String author;
    private double price;
    private int stockCount;
    private String ISBN;
    private String genre;
    private LocalDate dateOfPub;
    // Default Constructor
    public Book() {
        this.title = "Unknown";
        this.author = "Unknown";
        this.price = 0.0;
        this.stockCount = 0;
        this.ISBN = "N/A";
        this.genre = "General";
        this.dateOfPub = LocalDate.now();
    }
    // Parameterized Constructor
    public Book(String title, String author, double price,
                int stockCount, String ISBN,
                String genre, LocalDate dateOfPub)
                throws InvalidBookException {
        if (title == null || title.isEmpty())
            throw new InvalidBookException("Title cannot be empty.");
        if (author == null || author.isEmpty())
            throw new InvalidBookException("Author cannot be empty.");
        if (price < 0)
            throw new InvalidBookException("Price cannot be negative.");
        if (stockCount < 0)
            throw new InvalidBookException("Stock count cannot be negative.");
        if (ISBN == null || ISBN.length() < 5)
            throw new InvalidBookException("Invalid ISBN.");
        if (genre == null || genre.isEmpty())
            throw new InvalidBookException("Genre cannot be empty.");
        if (dateOfPub == null || dateOfPub.isAfter(LocalDate.now()))
            throw new InvalidBookException("Invalid publication date.");
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockCount = stockCount;
        this.ISBN = ISBN;
        this.genre = genre;
        this.dateOfPub = dateOfPub;
    }
    // Copy Constructor
    public Book(Book b) {
        this.title = b.title;
        this.author = b.author;
        this.price = b.price;
        this.stockCount = b.stockCount;
        this.ISBN = b.ISBN;
        this.genre = b.genre;
        this.dateOfPub = b.dateOfPub;
    }
    // Display Method
    public void display() {
        System.out.println("Title      : " + title);
        System.out.println("Author     : " + author);
        System.out.println("Genre      : " + genre);
        System.out.println("Price      : Rs." + price);
        System.out.println("StockCount : " + stockCount);
        System.out.println("ISBN       : " + ISBN);
        System.out.println("Published  : " + dateOfPub);
        System.out.println("---------------------------");
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public double getPrice() {
        return price;
    }
}