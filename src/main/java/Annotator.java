/**
 * Created by Doron on 1/20/16.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Annotator {
    private Map<String, String> urls;

    public Annotator() {
        urls = new ConcurrentHashMap<String, String>();
    }

    public void setNameURL(NameURLPayload data) {
        if (!data.isValid()) {
            return;
        }
        urls.put(data.getName(), data.getUrl());
    }

    public String getUrl(String name) {
        return urls.get(name);
    }

    public void deleteAll() {
        urls = new ConcurrentHashMap<String, String>();
    }

    public String annotate(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        Elements matches = doc.getAllElements();
        StringBuilder sb = new StringBuilder();
        for (Element e: matches) {
            String tagName = e.tagName();
            String text = e.ownText();
            if (tagName != "a") {
                Matcher m = Pattern.compile(NameURLPayload.nameRegex).matcher(text);
                int end = -1;
                while(m.find()) {
                    int start = m.start();
                    if (end > -1) {
                        sb.append(text.substring(end, start));
                    }
                    end = m.end();
                    String possibleName = m.group();
                    String url = urls.get(possibleName);
                    if (url != null) {
                        Element linkEl = e.appendElement("a");
                        linkEl.attr("href", url);
                        linkEl.text(possibleName);
                        sb.append(linkEl.toString());
                    } else {
                        sb.append(possibleName);
                    }
                }
                if (end > -1) {
                    sb.append(text.substring(end));
                }
            } else {
                sb.append(e.toString());
            }
        }
        return sb.toString();
    }
}
