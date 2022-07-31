package entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

public class Reader {
    private int readerID;
    private String readerName;
    private ArrayList<Book> bookList;

    Random random = new Random();

    public Reader(int readerID, String readerName, ArrayList<Book> bookList) {
        this.readerID = random.nextInt(20000);
        this.readerName = readerName;
        this.bookList = bookList;
    }

    public Reader(int readerID, String readerName) {
        this.readerID = readerID;
        this.readerName = readerName;
    }

    public Reader() {
        this.readerID = random.nextInt(2000);
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }


    @Override
    public String toString() {
        if(bookList != null){
            return "Reader ID: " + readerID + '\t' +
                    readerName + '\t' +
                    " Borrowed books: " + bookList;
        }else{
            return "Reader ID: " + readerID + '\t' +
                    readerName + '\t';
        }

    }
}
