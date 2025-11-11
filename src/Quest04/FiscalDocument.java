package Quest04;

import java.util.HashMap;
import java.util.Map;

public class FiscalDocument {
    private final String id;
    private String xml;
    private Map<String, Object> metadata = new HashMap<>();

    public FiscalDocument(String id, String xml) {
        this.id = id;
        this.xml = xml;
    }

    public String getId() { return id; }
    public String getXml() { return xml; }
    public void setXml(String xml) { this.xml = xml; }
    public Map<String, Object> getMetadata() { return metadata; }

    @Override
    public String toString() {
        return "FiscalDocument{id=" + id + ", xmlLen=" + (xml==null?0:xml.length()) + ", metadata=" + metadata + "}";
    }
}

