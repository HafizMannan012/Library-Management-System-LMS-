// #include <iostream>
// #include <string>
// using namespace std;
// class Book {
// public:
    // static int nextId; // Static variable to auto-increment the ID
    // int id;
    // string title;
    // string author;
    // int year;
        // Book(string t, string a, int y) {
        // id = nextId++; // Assign the next available ID and increment it
        // title = t;
        // author = a;
        // year = y;
    // }
    // void display() {
    // cout << "ID: " << id << "Title: " << title << " by " << author << "
    // (" << year << ")" << endl;
//      }
// };
//      int Book::nextId = 1; // Initialize the static ID counter
// int main() {
    // string title, author;
    // int year;
    // cout << "Enter the title of the book: ";
    // getline(cin, title);
    // cout << "Enter the author of the book: ";
    // getline(cin, author);
    // cout << "Enter the year of publication of the book: ";
    // cin >> year;
    // cin.ignore(); // Ignore the newline character
    // Book book(title, author, year);
    // book.display();
    // return 0;
// }



import java.util.Scanner;

class Book {
    private static int nextId = 1; // Static variable to auto-increment the ID
    private int id;
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.id = nextId++; // Assign the next available ID and increment it
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
    



    public void display() {
        System.out.println("ID: " + id + ", Title: " + title + " by " + author + " (" + year + ")");
    }
}

public class LMS_Q2a {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();

        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();

        System.out.print("Enter the year of publication of the book: ");
        int year = scanner.nextInt();

        scanner.nextLine(); // Consume the newline character

        Book book = new Book(title, author, year);
        book.display();

        scanner.close();
    }
}
