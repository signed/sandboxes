package styling;

public class DirectlyStyleComponent implements Stylist.PartToStyle {
    private final Manican victim;

    public DirectlyStyleComponent(Manican victim) {
        this.victim = victim;
    }

    @Override
    public void styleWith(String style) {
        if (style.isEmpty()) {
            victim.clearStyle();
        } else {
            victim.setStyle(style);
        }

    }
}
