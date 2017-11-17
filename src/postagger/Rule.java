package postagger;

import java.util.List;

/**
 * @author Jalal Sajadi and Sina Ahmadi
 * @since 1.0 (2015-06-16)
 */
public class Rule {
    private String key;
    private List<String> rightPart;

    /**
     * indicate that the rule is created by only basic parts (NN, JJ, VB, PP, PPS)
     */
    private boolean primitiveRule;

    public Rule() {
    }

    public Rule(String key, List<String> rightPart, boolean primitiveRule) {
        this.key = key;
        this.rightPart = rightPart;
        this.primitiveRule = primitiveRule;
    }

    public Rule(String key, List<String> rightPart) {
        this.key = key;
        this.rightPart = rightPart;
    }

    public boolean match(List<String> parts) {
        int index = 0;
        for (String part : parts) {
            if (rightPart.size() <= index) {
                return false;
            }
            if (!rightPart.get(index++).equals(part)) {
                return false;
            }
        }
        return true;
    }

    public boolean match(Node node) {
        int index = 0;
        if (node.hasPrefix()) {
            if (rightPart.size() <= index) {
                return false;
            }
            if (!node.prefix.equals(rightPart.get(index++))) {
                return false;
            }
        }
        if (rightPart.size() <= index) {
            return false;
        }
        if (!node.word.equals(rightPart.get(index++))) {
            return false;
        }
        if (node.hasSuffix()) {
            if (rightPart.size() <= index) {
                return false;
            }
            if (!node.suffix.equals(rightPart.get(index++))) {
                return false;
            }
        }
        return true;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getRightPart() {
        return rightPart;
    }

    public void setRightPart(List<String> rightPart) {
        this.rightPart = rightPart;
    }

    public boolean isPrimitiveRule() {
        return primitiveRule;
    }

    public void setPrimitiveRule(boolean primitiveRule) {
        this.primitiveRule = primitiveRule;
    }
}
