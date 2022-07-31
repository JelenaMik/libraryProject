package repository;

import entity.Book;
import entity.Genre;
import entity.Reader;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class LibraryRepository {

    Connection connection = new DBManager().getConnection();

    public void addbookToDB(Book book) throws SQLException{
        String query = "INSERT INTO library (bookName, author, issn, yearOfRelease, genre, quantity)" +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getBookName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getIssn() );
        preparedStatement.setInt(4, book.getYearOfRelease() );
        preparedStatement.setString(5, book.getGenre().toString());
        preparedStatement.setString(6, JOptionPane.showInputDialog(null, "Enter quantity"));
        preparedStatement.execute();
        System.out.println("Book was added successfully");
    }

    public ArrayList<Book> getAllBooksFromDB() throws SQLException{
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM library_management.library where quantity>0";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            books.add(this.createBookFromResultSet(result));
        }
        return books;
    }

    private Book createBookFromResultSet(ResultSet result) throws SQLException{
        return new Book(
                result.getInt("id"),
                result.getString("bookName"),
                result.getString("author"),
                result.getString("issn"),
                result.getInt("yearOfRelease"),
                Genre.valueOf(result.getString("genre"))
        );
    }

    public Book findBookByID(int id) throws SQLException{
        String query = "select  * from library_management.library where id="+id;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()){
            return new Book(
                    result.getString("bookName"),
                    result.getString("author"),
                    result.getString("issn"),
                    result.getInt("yearOfRelease"),
                    Genre.valueOf(result.getString("genre"))
            );
        }
        return null;
    }

    public Boolean reduceQuantity(int id) {
        try{
            String query = "update library_management.library set quantity=quantity-1 where id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }

        return false;
    }

    public void increaseQuantity(int bookID){
        try{
            String query = "update library_management.library set quantity=quantity+1 where id="+bookID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
    }

    public void changeNumberOfBorrowedBooks(int number, int readerId){
        try{
            String query = "update library_management.readers set NumberOfBorrowedBooks=NumberOfBorrowedBooks+"+number+ " where readerId="+readerId;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
    }

    public boolean removeBookFromBorrowedList(int bookID, int readerID){
        try{
            String query = "delete from library_management.borrowedbooks where libraryId="+bookID+ " and readerId="+readerID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return false;
    }

    public void putDataInBorrowedList(int readerId, String readerName, int libraryId, String bookName, String author, String issn, int yearOfRelease, String genre)
    {
        try{
            String query = "insert into library_management.borrowedbooks (readerId, readerName, libraryId, bookName, author, issn, " +
                    "yearOfRelease, genre) values (?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, readerId );
            preparedStatement.setString(2, readerName);
            preparedStatement.setInt(3, libraryId );
            preparedStatement.setString(4, bookName );
            preparedStatement.setString(5, author);
            preparedStatement.setString(6, issn);
            preparedStatement.setInt(7, yearOfRelease );
            preparedStatement.setString(8, genre);

            preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
    }

    public void addreaderToDB(Reader reader) throws SQLException{
        String query = "INSERT INTO readers (readerId, readerName, NumberOfBorrowedBooks)" +
                "VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reader.getReaderID() );
        preparedStatement.setString(2, reader.getReaderName());
        preparedStatement.setInt(3, reader.getBookList().size() );
        preparedStatement.execute();
        System.out.println("Reader was added successfully");
    }

    public int checkIdDublicates(int readerID){
        try{
            String query = "SELECT COUNT(*) FROM library_management.readers where readerId="+readerID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("COUNT(*)");
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return 1;
    }

    public ArrayList<Reader> getAllReadersFromDB() throws SQLException{
        ArrayList<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM library_management.readers";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            readers.add(this.createReaderFromResultSet(result));
        }
        return readers;
    }

    private Reader createReaderFromResultSet(ResultSet result) throws SQLException{
        return new Reader(
                result.getInt("readerID"),
                result.getString("readerName")
        );
    }

    public Reader findReaderByID(int id) throws SQLException{
        String query = "select  * from library_management.readers where readerId="+id;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()){
            return new Reader(
                    result.getInt("readerID"),
                    result.getString("readerName")
            );
        }
        return null;
    }

    public Reader selectReaderFromFrame() throws SQLException{
        ArrayList<Reader> readerList = this.getAllReadersFromDB();

        Reader reader = (Reader) JOptionPane.showInputDialog(
                null,
                "Select reader",
                "Reader",
                JOptionPane.QUESTION_MESSAGE,
                null,
                readerList.toArray(),
                readerList.toArray()[0]
        );
        return reader;
    }


    public ArrayList<Book> listBorrowedBooks(int id) {

        try{
            String query = "SELECT * FROM library_management.borrowedbooks where readerID="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            ArrayList<Book> readerBooks = new ArrayList<>();
            while (result.next()){
                readerBooks.add(new Book(
                        result.getInt("libraryId"),
                        result.getString("bookName"),
                        result.getString("author"),
                        result.getString("issn"),
                        result.getInt("yearOfRelease"),
                        Genre.valueOf(result.getString("genre")),
                        result.getString("takenAt"),
                        result.getString("returnDue")
                ));
            }
            return readerBooks;
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return null;
    }

    public int countBorrowedBooks(int readerId) throws SQLException{
        String query = "SELECT COUNT(*) FROM library_management.borrowedbooks where readerId="+readerId;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            return result.getInt("COUNT(*)");
        }
        return 0;
    }


    public int checkIfNotSecondExemplar(int id, int readerID) {
        try{
            String query = "SELECT COUNT(*) FROM library_management.borrowedbooks where libraryId="+ id + " and readerId="+readerID;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("COUNT(*)");
            }
        }catch (SQLException e){
            System.out.println("Error: " + e.getClass() + e.getMessage());
        }
        return 1;
    }


    public void closeOpenConnections() {
        try{
            if(connection != null) connection.close();
        }catch (Exception e) {
            System.out.println(e.getClass() + e.getMessage());
        }
    }


}
