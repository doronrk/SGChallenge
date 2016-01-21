import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Doron on 1/20/16.
 */
@Data
public class NameURLPayload implements Validable{
    private String name;
    private String url;

    public static final String nameRegex = "[A-Za-z0-9]+";

    public NameURLPayload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    @JsonIgnore
    public boolean isValid() {
        return validName() && validUrl();
    }

    private boolean validName() {
        return name != null &&
                name.matches(nameRegex);
    }

    private boolean validUrl() {
        if (url == null) {
            return false;
        }
        try {
            new URL(url);
        }
        catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
