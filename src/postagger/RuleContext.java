package postagger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Jalal Sajadi and Sina Ahmadi
 * @since 1.0 (2015-09-06)
 */
public class RuleContext {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private Set<Rule> rules = new HashSet<>();

    public RuleContext() {
        ItemList ruleList = new ItemList("resources/rules.txt");
        for (String ruleStr : ruleList) {
            String[] splitted = ruleStr.split(new String("\\="));
            String key = splitted[0];
            String[] parts = splitted[1].split(new String("\\+"));
            List<String> rightPart = new ArrayList<>();
            boolean primitive = true;
            for (String part : parts) {
                rightPart.add(part);
                if (part.startsWith("n_")) {
                    primitive = false;
                }
            }
            Rule rule = new Rule(key, rightPart, primitive);
            rules.add(rule);
        }
    }

    public Set<Rule> getRulesByRightPart(String criteria) {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.getRightPart().contains(criteria)) {
                results.add(rule);
            }
        }
        return results;
    }

    public Set<Rule> getRulesByRightPart(String criteria, int index) {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.getRightPart().size() <= index) {
                continue;
            }
            if (rule.getRightPart().get(index).equals(criteria)) {
                results.add(rule);
            }
        }
        return results;
    }

    public Set<Rule> getRuleByKey(String key) {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.getKey().equals(key)) {
                results.add(rule);
            }
        }
        return results;
    }

    public Set<Rule> getPrimitiveRules() {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.isPrimitiveRule()) {
                results.add(rule);
            }
        }
        return results;
    }

    public Set<Rule> getPrimitiveRules(String key) {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.isPrimitiveRule()) {
                if (rule.getKey().equals(key)) {
                    results.add(rule);
                }
            }
        }
        return results;
    }

    public Set<Rule> getNonePrimitiveRules() {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (!rule.isPrimitiveRule()) {
                results.add(rule);
            }
        }
        return results;
    }

    public Set<Rule> getNonePrimitiveRules(String key) {
        Set<Rule> results = new HashSet<>();
        for (Rule rule : rules) {
            if (!rule.isPrimitiveRule()) {
                if (rule.getKey().equals(key)) {
                    results.add(rule);
                }
            }
        }
        return results;
    }


}
