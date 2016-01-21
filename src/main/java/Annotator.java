/**
 * Created by Doron on 1/20/16.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
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
        Document doc = Jsoup.parse(html);
        // TODO: wont pretty much every element match this regex if it has any text at all?
        Elements matches = doc.getElementsMatchingOwnText(NameURLPayload.nameRegex);
        for (Element e: matches) {
            String tagName = e.tagName();
            if (tagName != "a") {
                String text = e.ownText();
                Matcher m = Pattern.compile(NameURLPayload.nameRegex).matcher(text);
                while(m.find()) {
                    String possibleName = m.group();
                    if (possibleName != null) {
                        String url = urls.get(possibleName);
                        if (url != null) {
                            Element linkEl = e.appendElement("a");
                            linkEl.attr("href", url);
                            linkEl.text(possibleName);
                        }
                    }
                }
            }
        }
        return doc.toString();
    }
}
