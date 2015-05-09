package java8.chapter_01.lamdas;

public class Primer {

    public static int compareStrings(String first, String second) {
        return first.compareTo(second);
    }

    public static Primer CreateWith(String content){
        return new Primer(content);
    }

    private final String content;

    public Primer(String content) {
        this.content = content;
    }

    public int someCompareMethod(Primer primer) {
        return this.content.compareTo(primer.content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Primer primer = (Primer) o;

        return !(content != null ? !content.equals(primer.content) : primer.content != null);

    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}
