package postagger;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jalal Sajadi and Sina Ahmadi
 * @since 1.0 (2015-06-18)
 */
public class Dictionary {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    List<String> dictionary;

    public Dictionary(String path) {
        dictionary = new ItemList(path);

    }

    public boolean contains(String word) {
        return dictionary.contains(word);
    }

}
