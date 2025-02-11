/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Others;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class image_attribution {
    private String name;
    private String url;
    private InputStream stream;

    public image_attribution(String name, String url, InputStream stream) throws IOException {
        this.name = name;
        this.url = url;
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public InputStream getStream() {
        return stream;
    }

    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public String getAttribution() {
        StringBuilder attribution = new StringBuilder();
        attribution.append("Icon made by ");
        attribution.append(this.attributes.get("author"));
        attribution.append(" from Freepik under the Freepik License & from Flaticon");
        return attribution.toString();
    }

    public Map<String, String> attributes = new HashMap<>();    
}
