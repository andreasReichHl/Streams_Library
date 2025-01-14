package de.example;
class Author {
    private String name;
    private int birthYear;

    public Author(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() { return name; }
    public int getBirthYear() { return birthYear; }

    @Override
    public String toString() {
        return name + " (geb. " + birthYear + ")";
    }
}