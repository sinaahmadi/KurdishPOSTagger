package postagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jalal Sajadi - Sina Ahmadi
 * @since 1.0 (2015-06-17)
 */
public class Category extends ItemList {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private int priority;
    private List<String> affixes;
    private String key;
    private String description;

    public Category(String key, int priority, String filePath) {
        super(filePath);
        this.key = key;
        this.priority = priority;
    }

    private void loadCategory(String filePath) {
        affixes = new ItemList(filePath);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getAffixes() {
        return affixes;
    }

    public void setAffixes(List<String> affixes) {
        this.affixes = affixes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
