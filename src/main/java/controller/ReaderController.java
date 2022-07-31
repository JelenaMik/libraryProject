package controller;

import entity.Book;
import entity.Genre;
import entity.Reader;
import repository.LibraryRepository;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReaderController {
    Scanner scanner = new Scanner(System.in);

    LibraryRepository libraryRepository = new LibraryRepository();

    public ReaderController() {
    }

    public void addReader(){
        try{
            Reader reader = this.collectReaderInfo();
            libraryRepository.addreaderToDB(reader);
        } catch (Exception exception){
            System.out.println("Error occurred while adding a reader: " + exception.getClass() + " - "+  exception.getMessage());
        }
    }

    public Reader collectReaderInfo() {
        int dublicates=1;
        while (dublicates!=0){
            Reader reader = new Reader();
            dublicates = libraryRepository.checkIdDublicates(reader.getReaderID());
            System.out.println("Reader id is set to " + reader.getReaderID() );
            System.out.println("Reader name:");
            reader.setReaderName(scanner.nextLine());
            ArrayList<Book> bookList=new ArrayList<Book>();
            reader.setBookList(bookList);

            return reader;
        }
        return null;
    }

    public void displayAllReaders() {
        try{
            ArrayList<Reader> readerList = libraryRepository.getAllReadersFromDB();
            readerList.forEach(System.out::println);
        }catch (SQLException exception){
            System.out.println("Error: " + exception.getClass() + exception.getMessage());
        }
    }

    public Reader selectReaderByID() {
        System.out.println("Enter Reader ID: ");
        int id = scanner.nextInt();
        try {
            return libraryRepository.findReaderByID(id);
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return null;
    }

    public Reader selectReader() {
        try{
            return libraryRepository.selectReaderFromFrame();
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return null;

    }

    public Reader readerBooks(){
        Reader reader = this.selectReader();
        int readerId = reader.getReaderID();
        reader.setBookList( libraryRepository.listBorrowedBooks(readerId));
        return reader;
    }

    public int numberOfBorrowedBooks(int readerID){
        try{
            return libraryRepository.countBorrowedBooks(readerID);
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return 0;
    }

    public Book selectBorrowedBookToReturn(int id){
        ArrayList<Book> bookList = libraryRepository.listBorrowedBooks(id);

        Book book = (Book) JOptionPane.showInputDialog(
                null,
                "Select book you want to return",
                "Book",
                JOptionPane.QUESTION_MESSAGE,
                null,
                bookList.toArray(),
                bookList.toArray()[0]
        );
        return book;
    }

}
