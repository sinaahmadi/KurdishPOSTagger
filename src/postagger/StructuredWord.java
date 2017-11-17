package postagger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jalal Sajadi and Sina Ahmadi
 * @since 1.0 (2015-06-17)
 */
public class StructuredWord {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    public String word;
    public List<String> suffixes;
    public List<String> prefixes;

    public StructuredWord(String word) {
        this.word = word;
        suffixes = new ArrayList<String>();
        prefixes = new ArrayList<String>();
    }

    public StructuredWord(Node node) {
        this.word = node.word;
        suffixes = new ArrayList<String>();
        prefixes = new ArrayList<String>();
        while (node.parent != null) {
            addPrefix(node.prefix);
            addSuffix(node.suffix);
            node = node.parent;
        }
    }

    public StructuredWord(String word, List<String> suffixes, List<String> prefixes) {
        this.word = word;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
    }

    public void addPrefix(String prefix) {
        suffixes.add(prefix);
    }

    public void addSuffix(String suffix) {
        suffixes.add(suffix);
    }

}
