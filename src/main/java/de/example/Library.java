package de.example;

import java.util.*;
import java.util.stream.Collectors;

class Library {
    private List<Book> books = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void loanBook(Book book, Date startDate, Date endDate) {
        loans.add(new Loan(book, startDate, endDate));
    }

    public List<Loan> getLoans() {
        return loans;
    }

    // 1. Übung
    public List<Book> getBooksSortedByPublicationYear() {
        return books.stream()
                .sorted(Comparator.comparing(Book::getPublicationYear))
                .toList();
    }

    // 2. Übung
    public List<Author> getAuthorsSortedByBirthYear() {
        return books.stream()
                .flatMap(book -> book.getAuthors().stream()
                        .sorted(Comparator.comparingInt(Author::getBirthYear)))
                .toList();
    }

    // 3. Übung
    public List<Book> filterBooksByPageCount(int minPages, int maxPages) {
        return books.stream()
                .filter(book -> book.getPages() > minPages && book.getPages() < maxPages)
                .toList();
    }

    // 4. Übung
    public Map<Author, List<Book>> groupBooksByAuthor() {
        return books.stream()
                .flatMap(book -> book.getAuthors().stream().map(
                        author -> new AbstractMap.SimpleEntry<>(author, book)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    // 5. Übung
    public Optional<Author> getOldestAuthor() {
        return books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .min(Comparator.comparing(Author::getBirthYear));
    }

    // 6. Übung
    public List<Book> getBooksByTitleKeyword(String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().contains(keyword))
                .toList();
    }

    // 7. Übung
    public List<Book> getMostLoanedBooks() {
        return loans.stream()
                .map(Loan::getBook)
                .collect(Collectors.groupingBy(book -> book, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .limit(3)
                .toList();
    }

    // 8. Übung
    public Map<Book, Long> getLoanCountPerBook() {
        return loans.stream()
                .map(Loan::getBook)
                .collect(Collectors.groupingBy(book -> book, Collectors.counting()));
    }

    // 9. Übung
    public List<Book> getBooksLoanedByAuthor(Author author) {
        return loans.stream()
                .flatMap(loan -> loan.getBook().getAuthors().stream()
                        .map(auth -> new AbstractMap.SimpleEntry<>(loan.getBook(), auth)))
                .filter(book -> book.getValue().equals(author))
                .map(Map.Entry::getKey)
                .toList();
    }

    // 10. Übung
    public OptionalDouble getAverageLoanDuration() {
        return loans.stream()
                .mapToInt(loan -> Math.toIntExact(loan.getDurationBetweenDates()))
                .average();
    }


    // Bonus: 11. Übung
    public String getMostPopularAuthorByBorrowedBooksCount() {
        return loans.stream()
                .flatMap(book -> book.getBook().getAuthors().stream()
                        .map(author -> new AbstractMap.SimpleEntry<>(
                                author, book.getBook())))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toString();

    }

}
