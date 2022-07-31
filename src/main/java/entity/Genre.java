package entity;

import java.util.stream.Stream;

public enum Genre {
    Thriller,
    Horror,
    Historical,
    Romance,
    Western,
    Science_fiction,
    Fantasy;

    Genre() {

    }

    public static Stream<Genre> stream() {
        return Stream.of(Genre.values());
    }
}
