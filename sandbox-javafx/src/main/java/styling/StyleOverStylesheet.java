package styling;

import com.github.signed.protocols.jvm.MemoryDictionary;

public class StyleOverStylesheet implements Stylist.PartToStyle {

    private static final String WellKnownInMemoryStyleSheetUrl = "jvm://inmemory.css";
    private static final String StyleName = "inmemory.css";

    private final MemoryDictionary memoryDictionary;
    private Exhibit exhibit;

    public StyleOverStylesheet(Exhibit exhibit, MemoryDictionary memoryDictionary) {
        this.exhibit = exhibit;
        this.memoryDictionary = memoryDictionary;
    }

    public void actOn(Exhibit exhibit){
        this.exhibit = exhibit;
    }

    @Override
    public void styleWith(String style) {
        if(null == exhibit) return;
        StringBuilder builder = new StringBuilder().append(style);
        memoryDictionary.depose(StyleName, builder);
        exhibit.useInMemoryStyleSheetAt(WellKnownInMemoryStyleSheetUrl);
    }
}
