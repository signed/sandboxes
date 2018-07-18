package sample;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Map;

import static ch.qos.logback.core.util.OptionHelper.extractDefaultReplacement;

public class MDCStructuralConverter extends ClassicConverter {
    private String key;
    private String name;

    @Override
    public void start() {
        String[] keyInfo = extractDefaultReplacement(getFirstOption());
        key = keyInfo[0];
        if (keyInfo[1] == null) {
            name = key;
        } else {
            name = keyInfo[1];
        }
        super.start();
    }

    @Override
    public void stop() {
        key = null;
        name = null;
        super.stop();
    }

    @Override
    public String convert(ILoggingEvent event) {
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();

        if (key == null || mdcPropertyMap == null || !mdcPropertyMap.containsKey(key)) {
            return "";
        }

        String value = mdcPropertyMap.get(key);
        if (value == null) {
            return "";
        }
        return " " + name + "=" + value;
    }
}
