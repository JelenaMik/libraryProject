package controller;

import entity.Book;
import entity.Genre;
import entity.Reader;
import repository.LibraryRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;

public class LibraryController {

    Scanner scanner = new Scanner(System.in);
    LibraryRepository libraryRepository = new LibraryRepository();
    ReaderController readerController = new ReaderController();
    protected JFrame frame = new JFrame();

    public LibraryController() {
    }

    public void addBook(){
        try{
            Book book = this.collectBookInfo();
            libraryRepository.addbookToDB(book);
            System.out.println("book  was added to the list");
        } catch (Exception exception){
            System.out.println("Error occurred while adding a book: " + exception.getClass() + " - "+  exception.getMessage());
        }
    }

    private Book collectBookInfo() {

        Book book = new Book();

        System.out.println("Book title:");
        book.setBookName(scanner.nextLine());
        System.out.println("Book author:");
        book.setAuthor(scanner.nextLine());
        System.out.println("Book issn:");
        book.setIssn(scanner.nextLine());
        System.out.println("Book year of release:");
        book.setYearOfRelease(Integer.parseInt(scanner.nextLine()));
        book.setGenre(
                (Genre) JOptionPane.showInputDialog(
                        null,
                        "Select genre",
                        "Genre",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        Genre.stream().toArray(),
                        Genre.stream().toArray()[0]
                )
        );
        return book;
    }

    public void displayAllBooks() {
        try{
            ArrayList<Book> bookList = libraryRepository.getAllBooksFromDB();
            bookList.forEach(System.out::println);

            String[] col = {"id", "bookName", "author", "issn", "year", "genre"};

            DefaultTableModel tableModel = new DefaultTableModel(col, 0);

            bookList.forEach( book -> tableModel.addRow(
                            new String[]{
                                    String.valueOf(book.getId()),
                                    book.getBookName(),
                                    book.getAuthor(),
                                    book.getIssn(),
                                    String.valueOf(book.getYearOfRelease()),
                                    book.getGenre().toString()
                            }
                    )
            );
            displayTable(tableModel);

        }catch (SQLException exception){
            System.out.println("Error: " + exception.getClass() + exception.getMessage());
        }
    }

    private void displayTable(DefaultTableModel tableModel){
        JTable table = new JTable(tableModel);


        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public Book selectBookByID(int id) {
        try {
            return libraryRepository.findBookByID(id);
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return null;
    }

    public String borrowBook(){
        Reader reader = readerController.selectReader();
        int readerID = reader.getReaderID();
        int borrowedBooks = readerController.numberOfBorrowedBooks(readerID);

        if (borrowedBooks>=2) {
            return "You can borrow a new book when you return one";
        }
        System.out.println("Enter Book ID: ");
        int id = scanner.nextInt();

        Book book = this.selectBookByID(id);
        if (libraryRepository.checkIfNotSecondExemplar(id, readerID)!=0){
            return "You have already borrowed a copy of this book";
        }

        if(!libraryRepository.reduceQuantity(id)) {
            return "There is no available copy of selected book";
        }
        int readerId = reader.getReaderID();
        String readerName = reader.getReaderName();
        int libraryId = id;
        String bookName = book.getBookName();
        String author = book.getAuthor();
        String issn = book.getIssn();
        int yearOfRelease = book.getYearOfRelease();
        String genre = book.getGenre().toString();

        libraryRepository.putDataInBorrowedList(readerId, readerName, libraryId, bookName, author, issn, yearOfRelease, genre);
        libraryRepository.changeNumberOfBorrowedBooks(1, readerID);
        return ("You successfully borrowed book " + book.getBookName());
    }

    public void displayReaderBooks(){
        System.out.println(readerController.readerBooks());
    }

    public String returnBook() {
        Reader reader = readerController.selectReader();
        int readerID = reader.getReaderID();
        int borrowedBooks = readerController.numberOfBorrowedBooks(readerID);
        if (borrowedBooks<=0) {
            return reader.getReaderName() + " don't have borrowed books";
        }
        Book book = readerController.selectBorrowedBookToReturn(readerID);
        int bookId = book.getId();
        libraryRepository.increaseQuantity(bookId);

        if(libraryRepository.removeBookFromBorrowedList(bookId, readerID)){
            libraryRepository.changeNumberOfBorrowedBooks(-1, readerID);
            return "You have returned book successfully";
        }
        return null;
    }

    public void closeConnection(){
        libraryRepository.closeOpenConnections();
    }
}



