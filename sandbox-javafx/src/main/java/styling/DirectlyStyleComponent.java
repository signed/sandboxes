package styling;

public class DirectlyStyleComponent implements Stylist.PartToStyle {
    private Manican victim;

    public DirectlyStyleComponent(Manican victim) {
        this.victim = victim;
    }

    public void actOn(Manican manican){
        victim = manican;
    }

    @Override
    public void styleWith(String style) {
        if(null == this.victim) return;

        if (style.isEmpty()) {
            victim.clearStyle();
        } else {
            victim.setStyle(style);
        }

    }
}
