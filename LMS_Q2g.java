import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Define the Configuration interface
interface Configuration {
    void displayInfo();
    double calculateBorrowingCost();
}

// interface Item {
//     double calculateBorrowingCost();
// }

class Borrower {
    private static int nextId = 1;
    private int id;
    private String name;
    private ArrayList<Item> borrowedItems;

    public Borrower(String name) {
        this.id = nextId++;
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public void borrowItem(Item item) {
        borrowedItems.add(item);
    }

    public ArrayList<Integer> getBorrowedItemIds() {
        ArrayList<Integer> itemIds = new ArrayList<>();
        for (Item item : borrowedItems) {
            itemIds.add(item.getId());
        }
        return itemIds;
    }
}

class Item implements Configuration {
    private int popularityCount;
    private static int nextId = 1;
    private int id;
    private String title;
    private int type;
    private boolean isAvailable; // Add a field to track availability

    public Item(String title, int type) {
        this.id = nextId++;
        this.title = title;
        this.type = type;
        this.isAvailable = true; // Initialize as available
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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

    public int getPopularityCount() {
        return popularityCount;
    }

    public void incrementPopularity() {
        popularityCount++;
    }

    @Override
    public void displayInfo() {
        System.out.print("Item ID: " + getId() + ", ");
        System.out.print("Title: " + getTitle() + ", ");
        System.out.print("Type: " + getTypeAsString());
        System.out.print(", Popularity Count: " + getPopularityCount());
        System.out.print(", Available: " + isAvailable());
        //System.out.println(); // Add a newline at the end
    
        // System.out.println();
        // if (this instanceof Book) {
        //     Book book = (Book) this;
        //     System.out.print(", Author: " + book.getAuthor() + ", ");
        //     System.out.print("Year: " + book.getYear());
        // } else if (this instanceof Magazine) {
        //     Magazine magazine = (Magazine) this;
        //     System.out.print(", Authors: " + String.join(", ", magazine.getAuthors()) + ", ");
        //     System.out.print("Publisher: " + magazine.getPublisher());
        // } else if (this instanceof Newspaper) {
        //     Newspaper newspaper = (Newspaper) this;
        //     System.out.print(", Publisher: " + newspaper.getPublisher() + ", ");
        //     System.out.print("Publication Date: " + newspaper.getPublicationDate());
        // }
        // System.out.println(); // Add a newline at the end
    }

    @Override
        public double calculateBorrowingCost() {
        // Calculate borrowing cost for a book
        double bookCost = 280.2;
        double gst = 200.0; // GST for books
        return bookCost + (0.20 * bookCost) + gst;
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

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.print(", Author: " + getAuthor() + ", ");
        System.out.print("Year: " + getYear());
        System.out.println(); // Add a newline at the end
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

    @Override
    public double calculateBorrowingCost() {
        // Calculate borrowing cost for a magazine
        // double magazineCost = 239.2;
        double baseCost = 10.0;
        double publisherCharges = 5.0;
        return baseCost + publisherCharges;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.print(", Authors: " + String.join(", ", getAuthors()) + ", ");
        System.out.print("Publisher: " + getPublisher());
        System.out.println(); // Add a newline at the end
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

     @Override
    public double calculateBorrowingCost() {
        // Calculate borrowing cost for a newspaper
        double magazineCost = 221.1;
        int popularityCount = getPopularityCount(); // Get the popularity count
        return magazineCost * popularityCount;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.print(", Publisher: " + getPublisher() + ", ");
        System.out.print("Publication Date: " + getPublicationDate());
        System.out.println(); // Add a newline at the end
    }
}
class Library {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Borrower> borrowers = new ArrayList<>();

    public void borrowItem(int borrowerId, String borrowerName, String bookName) {
    Borrower borrower = findBorrowerById(borrowerId);
    if (borrower == null) {
        borrower = createBorrower(borrowerName);
    }

    Item itemToBorrow = null;

    for (Item item : items) {
        if (item.getTitle().equalsIgnoreCase(bookName) && item.isAvailable()) {
            itemToBorrow = item;
            break;
        }
    }

    if (itemToBorrow != null && borrower != null) {
        if (!borrower.getBorrowedItems().contains(itemToBorrow)) {
            double borrowingCost = itemToBorrow.calculateBorrowingCost();
            borrower.borrowItem(itemToBorrow);

            // Set the availability of the item to false
            itemToBorrow.setAvailable(false);

            System.out.println("Item borrowed successfully.");
            System.out.println("Borrowing Cost: Rs. " + borrowingCost); // Display the borrowing cost

            itemToBorrow.incrementPopularity();

            // // Remove the borrowed item from the librarys
            // items.remove(itemToBorrow);

            // Save the borrower details and borrowed item to files
            saveBorrowerDetails(borrower, itemToBorrow);
            saveBorrowedItems();
        } else {
            System.out.println("Item is already borrowed by this borrower.");
        }
    } else {
        if (itemToBorrow == null) {
            System.out.println("Item not found.");
        } else {
            System.out.println("Item is not available for borrowing.");
        }
    }
}


    private void saveBorrowerDetails(Borrower borrower, Item item) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("BorrowerDetails.txt", true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());
            writer.println("Borrower: " + borrower.getName() + ", Book: " + item.getTitle() + ", Borrowed At: " + timestamp);
        } catch (IOException e) {
            System.out.println("Error saving borrower details.");
        }
    }

    private void saveBorrowedItems() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("borrowers.txt"))) {
            for (Borrower borrower : borrowers) {
                ArrayList<Integer> borrowedItemIds = borrower.getBorrowedItemIds();
                for (Integer itemId : borrowedItemIds) {
                    writer.println("Borrower ID: " + borrower.getId() + ", Borrowed Item ID: " + itemId);
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving borrowed items.");
        }
    }




    public void displayBorrowers(ArrayList<Borrower> borrowers) {
    for (Borrower borrower : borrowers) {
        System.out.println("Borrower ID: " + borrower.getId() + ", Name: " + borrower.getName());

        ArrayList<Item> borrowedItems = borrower.getBorrowedItems();
        ArrayList<Integer> borrowedItemIds = borrower.getBorrowedItemIds();

        if (!borrowedItems.isEmpty()) {
            System.out.println("Borrowed Items:");
            for (int i = 0; i < borrowedItems.size(); i++) {
                Item item = borrowedItems.get(i);
                int itemId = borrowedItemIds.get(i);
                System.out.println("- Borrowed Item ID: " + itemId);

                // Format the borrowed item information
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestamp = sdf.format(new Date());
                System.out.println("Book: " + item.getTitle() + ", Borrowed At: " + timestamp);
            }
        } else {
            System.out.println("No items currently borrowed.");
        }
        System.out.println();
    }
}


    public void addItem(Item item) {
        items.add(item);
    }

    public Borrower createBorrower(String name) {
    Borrower borrower = new Borrower(name);
    borrowers.add(borrower);
    return borrower;
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
            item.displayInfo();
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

    public ArrayList<Borrower> getBorrowers() {
        return borrowers;
    }

    public Borrower findBorrowerById(int borrowerId) {
        for (Borrower borrower : borrowers) {
            if (borrower.getId() == borrowerId) {
                return borrower;
            }
        }
        return null;
    }
}

public class LMS_Q2g {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        loadItemsFromFile(library, "data4.txt");

        System.out.println("Loaded Items from File data4.txt");
        while (true) {
            System.out.println("Library Management System menu:");
            System.out.println("1. Hot Picks");
            System.out.println("2. Borrow an item");
            System.out.println("3. Add item");
            System.out.println("4. Edit item");
            System.out.println("5. Delete item");
            System.out.println("6. View all items");
            System.out.println("7. View item by ID");
            System.out.println("8. Display borrowers List"); // Added Hot Picks option
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                    // Hot Picks
                        System.out.println("Hot Picks (Sorted by Popularity):");
                        ArrayList<Item> hotPicks = new ArrayList<>(library.getItems());
                        hotPicks.sort(Comparator.comparingInt(Item::getPopularityCount).reversed());
                        for (Item item : hotPicks) {
                            System.out.println(item.getTitle() + " - Popularity: " + item.getPopularityCount());
                        }
                        break;

                    case 2:
                        // Borrow item
                        System.out.print("Enter your borrower ID: ");
                        int borrowerId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                    
                        System.out.print("Enter your borrower name: ");
                        String borrowerName = scanner.nextLine();
                    
                        System.out.print("Enter the title of the item to borrow: ");
                        String bookName = scanner.nextLine();
                    
                        // Borrow the item
                        library.borrowItem(borrowerId, borrowerName, bookName);
                        break;
                    

                    case 3:
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
                        saveItemsToFile(library, "data4.txt");
                        break;
                        

                    case 4:
                        System.out.print("Enter the ID of the item to view: ");
                        int viewId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Item viewItem = library.findItemById(viewId);
                        if (viewItem != null) {
                            viewItem.displayInfo();
                        } else {
                            System.out.println("Item not found.");
                        }
                        
                        break;
                         

                    case 5:
                        System.out.print("Enter the ID of the item to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        if (library.deleteItem(deleteId)) {
                            System.out.println("Item deleted successfully.");
                            saveItemsToFile(library, "data4.txt");
                        } else {
                            System.out.println("Item not found.");
                        }
                        break;
                      

                    case 6: 
                         System.out.println("All Items in the Library:");
                        library.displayAllItems();
                        break;
                        
                    case 7: 
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
                            saveItemsToFile(library, "data4.txt");
                        } else {
                            System.out.println("Item not found.");
                        }
                        break;

                    case 8: 
                        // Display borrowers
                        System.out.println("Borrowers and Borrowed Items:");
                        library.displayBorrowers(library.getBorrowers());
                        break;
                        
                    case 9:
                        saveItemsToFile(library, "data4.txt");
                        System.out.println("Exiting Library Management System. Items saved to data4.txt.");
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


