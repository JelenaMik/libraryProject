package controller;

import javax.swing.*;
import java.util.Scanner;

public class MenuController {
    Scanner scanner = new Scanner(System.in);
    LibraryController libraryController= new LibraryController();
    ReaderController readerController = new ReaderController();

    public void start(){
        this.showOptions();
        this.handleUserChoice();
        start();
    }

    private void showOptions() {
        System.out.println(
                """
                    
                    Select your option:
                    1. Add a book to library
                    2. View all available books
                    3. Create a new reader
                    4. View all readers
                    5. Borrow a book
                    6. Display reader's borrowed books 
                    7. Return book
                    8. Exit
                    """
        );
    }
    private void handleUserChoice(){
        System.out.println("Choose an option: ");
        String userChoice = scanner.nextLine();
        switch (userChoice){
            case "1":
                libraryController.addBook();
                break;
            case "2":
                libraryController.displayAllBooks();
                break;
            case "3":
                readerController.addReader();
                break;
            case "4":
                readerController.displayAllReaders();
                break;
            case "5":
                libraryController.displayAllBooks();
                System.out.println(libraryController.borrowBook());
                break;
            case "6":
                libraryController.displayReaderBooks();
                break;
            case "7":
                libraryController.returnBook();
                break;
            case "8":
                libraryController.closeConnection();
                System.exit(1);
                break;
            default:
                System.out.println("Please enter a valid option!");
                break;

        }
    }
}
