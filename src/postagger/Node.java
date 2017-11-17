package postagger;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jalal
 */
public class Node implements TreeNode, Comparable<Node> {
    private final Logger LOG = Logger.getLogger(getClass().getName());

    public String prefix;
    public String word;
    public String suffix;
    public int index;
    public int level;
    public Node parent;
    public ArrayList<Node> children;
    public int childCount;
    public String label = "";
    public List<String> posTags;

    public Node(String prefix, String word, String suffix) {
        this.word = word;
        this.prefix = prefix;
        this.suffix = suffix;
        childCount = 0;
        parent = null;
        children = new ArrayList<Node>();
        posTags = new ArrayList<String>();
    }

    public Node(String prefix, String word, String suffix, int index) {
        this.word = word;
        this.prefix = prefix;
        this.suffix = suffix;
        this.index = index;
        childCount = 0;
        parent = null;
        children = new ArrayList<Node>();
        posTags = new ArrayList<String>();
    }

    public Node addChild(Node n) {
        n.parent = this;
        children.add(n);
        childCount++;
        return n;
    }

    public void removeChild(int index) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).index == index) {
                children.remove(i);
                childCount--;
                break;
            }
        }

    }

    public boolean hasPrefix() {
        return !prefix.equals(TreeGenerator.EMPTY_SIGN);
    }

    public boolean hasSuffix() {
        return !suffix.equals(TreeGenerator.EMPTY_SIGN);
    }

    public boolean hasChild() {
        return children.size() > 0;
    }

    @Override
    public int compareTo(Node o) {
        if (o.word.equals(word) && o.prefix.equals(prefix) && o.suffix.equals(suffix)) return 0;
        else return 1;
    }

    @Override
    public String toString() {
        return prefix + "+" + (word.startsWith("n_") || word.startsWith("p_") ? word.substring(2) : word) + "+" + suffix /*+ " [" + index + "]"*/;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if (children.size() == childIndex) return null;
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return childCount;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return ((Node) node).index;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return !hasChild();
    }

    @Override
    public Enumeration<Node> children() {
        return (Enumeration<Node>) children;
    }

}
