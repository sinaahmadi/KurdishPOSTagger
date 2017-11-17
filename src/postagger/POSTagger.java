package postagger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jalal Sajadi and Sina Ahmadi
 * @since 1.0 (2015-06-17)
 */
public class POSTagger {
    private final Logger LOG = Logger.getLogger(getClass().getName());

    private List<Category> categories = new ArrayList<Category>();
    private List<Rule> rules = new ArrayList<Rule>();

    public POSTagger(String categoriesPath, String rulePath) {
        loadCategories(categoriesPath);
        loadRules(rulePath);

    }

    public void loadCategories(String categoriesPath) {

    }

    public void loadRules(String rulesPath) {

    }
}
