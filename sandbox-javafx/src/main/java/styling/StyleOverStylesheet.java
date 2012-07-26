package styling;

import com.github.signed.protocols.jvm.MemoryDictionary;

public class StyleOverStylesheet implements Stylist.PartToStyle {

    private static final String WellKnownInMemoryStyleSheetUrl = "jvm://inmemory.css";
    private static final String StyleName = "inmemory.css";
    private Exhibit exhibit;
    private MemoryDictionary memoryDictionary;

    public StyleOverStylesheet(Exhibit exhibit, MemoryDictionary memoryDictionary) {
        this.exhibit = exhibit;
        this.memoryDictionary = memoryDictionary;
    }

    @Override
    public void styleWith(String style) {
        StringBuilder builder = new StringBuilder().append(style);
        memoryDictionary.depose(StyleName, builder);
        exhibit.useInMemoryStyleSheetAt(WellKnownInMemoryStyleSheetUrl);
    }
}
