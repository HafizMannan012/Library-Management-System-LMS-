import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

class Book {
    private static int nextId = 1;
    private int id;
    private String title;
    private String author;
    private int year;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void display() {
        System.out.println("ID: " + id + ", Title: " + title + " by " + author + " (" + year + ")");
    }

    public void update(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean editBook(int id, String title, String author, int year) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.update(title, author, year);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBook(int id) {
        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getId() == id) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            return true;
        }
        return false;
    }

    public void displayAllBooks() {
        for (Book book : books) {
            book.display();
        }
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}

public class LMS_Q2b {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        loadBooksFromFile(library, "data.txt");

        System.out.println("Loaded Books from File data.txt");
        while (true) {
            System.out.println("Library Management System menu:");
            System.out.println("1. Add book");
            System.out.println("2. Edit book");
            System.out.println("3. Delete book");
            System.out.println("4. View all books");
            System.out.println("5. View book by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter the title of the book: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter the author of the book: ");
                        String author = scanner.nextLine();
                        int year = 0;
                        boolean validYearInput = false;
                        while (!validYearInput) {
                            System.out.print("Enter the year of publication of the book: ");
                            try {
                                year = scanner.nextInt();
                                scanner.nextLine(); // Consume newline character
                                validYearInput = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input for year. Please enter a valid number.");
                                scanner.nextLine(); // Consume invalid input
                            }
                        }
                        // Generate a unique ID based on the current size of the library
                        int uniqueId = library.getBooks().size() + 1;
                        Book newBook = new Book(uniqueId, title, author, year);
                        library.addBook(newBook);
                        System.out.println("Book added successfully.");
                        saveBooksToFile(library, "data.txt");
                        break;

                    case 2:
                        System.out.print("Enter the ID of the book to edit: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Book editBook = library.findBookById(editId);
                        if (editBook != null) {
                            System.out.print("Enter the new title: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Enter the new author: ");
                            String newAuthor = scanner.nextLine();
                            System.out.print("Enter the new year: ");
                            int newYear = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character
                            editBook.update(newTitle, newAuthor, newYear);
                            System.out.println("Book updated successfully.");
                            saveBooksToFile(library, "data.txt");
                        } else {
                            System.out.println("Book not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter the ID of the book to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (library.deleteBook(deleteId)) {
                            System.out.println("Book deleted successfully.");
                            saveBooksToFile(library, "data.txt");
                        } else {
                            System.out.println("Book not found.");
                        }
                        break;

                    case 4:
                        System.out.println("All Books in the Library:");
                        library.displayAllBooks();
                        break;

                    case 5:
                        System.out.print("Enter the ID of the book to view: ");
                        int viewId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Book viewBook = library.findBookById(viewId);
                        if (viewBook != null) {
                            viewBook.display();
                        } else {
                            System.out.println("Book not found.");
                        }
                        break;

                    case 6:
                        saveBooksToFile(library, "data.txt");
                        System.out.println("Exiting Library Management System. Books saved to data.txt.");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private static void loadBooksFromFile(Library library, String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] bookData = line.split(",");
                if (bookData.length == 4) {
                    String title = bookData[0];
                    String author = bookData[1];
                    int year = Integer.parseInt(bookData[2]);
                    int id = Integer.parseInt(bookData[3]);
                    Book loadedBook = new Book(id, title, author, year);
                    library.addBook(loadedBook);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    private static void saveBooksToFile(Library library, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Book book : library.getBooks()) {
                writer.println(book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getId());
            }
            writer.flush(); // Flush the data to the file
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + filename);
        }
    }
}
