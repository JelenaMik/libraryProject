CREATE DATABASE IF NOT EXISTS library_Management;
USE  library_Management;

CREATE TABLE IF NOT EXISTS library (
    id int not null auto_increment,
    bookName text not null,
    author text not null,
    issn text not null,
    yearOfRelease int not null,
    genre text not null,
    quantity int,
    primary key(bookName)
     )

CREATE TABLE IF NOT EXISTS borrowedBooks (
    id int not null auto_increment,
    readerId int not null ,
    readerName text not null,
    libraryId int not null,
    bookName text not null,
    author text not null,
    issn text not null,
    yearOfRelease int not null,
    genre text not null,
    takenAt timestamp default current_timestamp,
    returnDue timestamp default (timestampadd(week,2,current_timestamp())),
    primary key(id),
    FOREIGN KEY(libraryId) REFERENCES library(id)
     )

     CREATE TABLE IF NOT EXISTS readers (
    readerId int not null ,
    readerName text not null,
    NumberOfBorrowedBooks int not null,
    primary key(readerId)
     )
