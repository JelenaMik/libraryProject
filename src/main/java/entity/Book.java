package entity;

public class Book {
    public int id;
    public String bookName;
    public String author;
    public String issn;
    public int yearOfRelease;
    public Genre genre;
    public String takenAt;
    public String returnDue;

    public Book(String bookName, String author, String issn, int yearOfRelease, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.issn = issn;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
    }
    public Book(int id, String bookName, String author, String issn, int yearOfRelease, Genre genre, String takenAt, String returnDue) {
        this.id=id;
        this.bookName = bookName;
        this.author = author;
        this.issn = issn;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.takenAt = takenAt;
        this.returnDue = returnDue;
    }

    public Book(int id, String bookName, String author, String issn, int yearOfRelease, Genre genre) {
        this.id=id;
        this.bookName = bookName;
        this.author = author;
        this.issn = issn;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;

    }

    public Book(String bookName, String issn) {
        this.bookName = bookName;
        this.issn = issn;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        if(id!=0 && takenAt==null){
            return '\n' +"ID:" + id + '\t' +
                    "Title:" + bookName + '\t' +
                    "Author:" +author + '\t' +
                    "ISSN:" +issn + '\t' +
                    "Year:" +yearOfRelease + '\t' +
                    "Genre:" +genre;

        }
        if(takenAt!=null){
            return '\n' + "ID:" + id + '\t' +
                    "Title:" + bookName + '\t' +
                    "Author:" +author + '\t' +
                    "ISSN:" +issn + '\t' +
                    "Year:" +yearOfRelease + '\t' +
                    "Genre:" +genre + '\t' +
                    "taken at: " + takenAt + '\t' +
                    "return due: " + returnDue;
        }
        else

            return '\n' + "Title:" + bookName + '\t' +
                    "Author:" +author + '\t' +
                    "ISSN:" +issn + '\t' +
                    "Year:" +yearOfRelease + '\t' +
                    "Genre:" +genre
                    ;
    }
}
