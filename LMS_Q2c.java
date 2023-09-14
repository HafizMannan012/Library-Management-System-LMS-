import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

class Item {
    private static int nextId = 1;
    private int id;
    private String title;
    private int type;

    public Item(String title, int type) {
        this.id = nextId++;
        this.title = title;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getTypeAsString() {
        switch (type) {
            case 1:
                return "Book";
            case 2:
                return "Magazine";
            case 3:
                return "Newspaper";
            default:
                return "Unknown";
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void display() {
        System.out.println("ID: " + id + ", Title: " + title + ", Type: " + getTypeAsString());
    }
}

class Book extends Item {
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        super(title, 1); // Type 1 represents a book
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void update(String title, String author, int year) {
        setTitle(title);
        this.author = author;
        this.year = year;
    }
}

class Magazine extends Item {
    private ArrayList<String> authors;
    private String publisher;

    public Magazine(String title, ArrayList<String> authors, String publisher) {
        super(title, 2); // Type 2 represents a magazine
        this.authors = authors;
        this.publisher = publisher;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void update(String title, ArrayList<String> authors, String publisher) {
        setTitle(title);
        this.authors = authors;
        this.publisher = publisher;
    }
}

class Newspaper extends Item {
    private String publisher;
    private String publicationDate;

    public Newspaper(String title, String publisher, String publicationDate) {
        super(title, 3); // Type 3 represents a newspaper
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void update(String title, String publisher, String publicationDate) {
        setTitle(title);
        this.publisher = publisher;
        this.publicationDate = publicationDate;
    }
}

class Library {
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean editItem(int id, String title, int type) {
        for (Item item : items) {
            if (item.getId() == id) {
                item.setTitle(title);
                if (item instanceof Book && type == 2) {
                    items.remove(item);
                    items.add(new Magazine(title, new ArrayList<>(), ""));
                } else if (item instanceof Magazine && type == 3) {
                    items.remove(item);
                    items.add(new Newspaper(title, "", ""));
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteItem(int id) {
        Item itemToRemove = null;
        for (Item item : items) {
            if (item.getId() == id) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            items.remove(itemToRemove);
            return true;
        }
        return false;
    }

    public void displayAllItems() {
        for (Item item : items) {
            item.display();
        }
    }

    public Item findItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}

public class LMS_Q2c {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        loadItemsFromFile(library, "data1.txt");

        System.out.println("Loaded Items from File data1.txt");
        while (true) {
            System.out.println("Library Management System menu:");
            System.out.println("1. Add item");
            System.out.println("2. Edit item");
            System.out.println("3. Delete item");
            System.out.println("4. View all items");
            System.out.println("5. View item by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter the title of the item: ");
                        String title = scanner.nextLine();
                        System.out.print("Select the type of item (1. Book, 2. Magazine, 3. Newspaper): ");
                        int type = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (type == 1) {
                            System.out.print("Enter the author of the book: ");
                            String author = scanner.nextLine();
                            System.out.print("Enter the year of publication of the book: ");
                            int year = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character
                            library.addItem(new Book(title, author, year));
                        } else if (type == 2) {
                            System.out.print("Enter the list of authors (comma-separated) of the magazine: ");
                            String authorsInput = scanner.nextLine();
                            ArrayList<String> authors = new ArrayList<>(List.of(authorsInput.split(",")));
                            System.out.print("Enter the publisher of the magazine: ");
                            String publisher = scanner.nextLine();
                            library.addItem(new Magazine(title, authors, publisher));
                        } else if (type == 3) {
                            System.out.print("Enter the publisher of the newspaper: ");
                            String publisher = scanner.nextLine();
                            System.out.print("Enter the publication date of the newspaper (DD-MM-YYYY): ");
                            String publicationDate = scanner.nextLine();
                            library.addItem(new Newspaper(title, publisher, publicationDate));
                        } else {
                            System.out.println("Invalid item type.");
                        }
                        System.out.println("Item added successfully.");
                        saveItemsToFile(library, "data1.txt");
                        break;

                    case 2:
                        System.out.print("Enter the ID of the item to edit: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Item editItem = library.findItemById(editId);
                        if (editItem != null) {
                            System.out.print("Enter the new title: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Select the new type of item (1. Book, 2. Magazine, 3. Newspaper): ");
                            int newType = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character
                            library.editItem(editId, newTitle, newType);
                            System.out.println("Item updated successfully.");
                            saveItemsToFile(library, "data1.txt");
                        } else {
                            System.out.println("Item not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter the ID of the item to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (library.deleteItem(deleteId)) {
                            System.out.println("Item deleted successfully.");
                            saveItemsToFile(library, "data1.txt");
                        } else {
                            System.out.println("Item not found.");
                        }
                        break;

                    case 4:
                        System.out.println("All Items in the Library:");
                        library.displayAllItems();
                        break;

                    case 5:
                        System.out.print("Enter the ID of the item to view: ");
                        int viewId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Item viewItem = library.findItemById(viewId);
                        if (viewItem != null) {
                            viewItem.display();
                        } else {
                            System.out.println("Item not found.");
                        }
                        break;

                    case 6:
                        saveItemsToFile(library, "data1.txt");
                        System.out.println("Exiting Library Management System. Items saved to data1.txt.");
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

    private static void loadItemsFromFile(Library library, String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] itemData = line.split(",");
                if (itemData.length >= 3) {
                    String title = itemData[0];
                    int type = Integer.parseInt(itemData[1]);
                    if (type == 1 && itemData.length == 4) {
                        String author = itemData[2];
                        int year = Integer.parseInt(itemData[3]);
                        library.addItem(new Book(title, author, year));
                    } else if (type == 2 && itemData.length == 4) {
                        String authorsInput = itemData[2];
                        ArrayList<String> authors = new ArrayList<>(List.of(authorsInput.split(",")));
                        String publisher = itemData[3];
                        library.addItem(new Magazine(title, authors, publisher));
                    } else if (type == 3 && itemData.length == 4) {
                        String publisher = itemData[2];
                        String publicationDate = itemData[3];
                        library.addItem(new Newspaper(title, publisher, publicationDate));
                    } else {
                        System.out.println("Ignoring invalid item data: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    private static void saveItemsToFile(Library library, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Item item : library.getItems()) {
                if (item instanceof Book) {
                    Book book = (Book) item;
                    writer.println(book.getTitle() + "," + 1 + "," + book.getAuthor() + "," + book.getYear());
                } else if (item instanceof Magazine) {
                    Magazine magazine = (Magazine) item;
                    writer.println(magazine.getTitle() + "," + 2 + "," + String.join(",", magazine.getAuthors()) + "," + magazine.getPublisher());
                } else if (item instanceof Newspaper) {
                    Newspaper newspaper = (Newspaper) item;
                    writer.println(newspaper.getTitle() + "," + 3 + "," + newspaper.getPublisher() + "," + newspaper.getPublicationDate());
                }
            }
            writer.flush(); // Flush the data to the file
        } catch (IOException e) {
            System.out.println("Error saving items to file: " + filename);
        }
    }
}
