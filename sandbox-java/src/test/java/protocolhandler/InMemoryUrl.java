package protocolhandler;

import com.github.signed.protocols.jvm.MemoryDictionary;
import com.github.signed.protocols.jvm.StyleStreamHandlerFactory;

import java.net.URL;

public class InMemoryUrl {

    public static void registerInMemoryUrlHandler(MemoryDictionary dictionary) {
        URL.setURLStreamHandlerFactory(new StyleStreamHandlerFactory(dictionary));
    }
}
