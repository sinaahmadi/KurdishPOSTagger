package postagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * TreeGenerator contains an algorithm for generate tree
 *
 * @author Jalal Sajadi and Sina Ahmadi
 * @project KLPP
 * @since 22 Oct 2014
 */
public class TreeGenerator {
    //"‏"
    public static final String EMPTY_SIGN = "‏E‏"; // A symbol that used to show prefix/suffix when not exist
    private final Logger LOG = Logger.getLogger(getClass().getName());
    /**
     * A collection that used for collect result. (The result for each node is
     * create by toString() method in WordNode class).
     */
    public ArrayList<String> results;
    public Node root; // root node is used for tha main word (as the first node)
    public Node prunedRoot;
    public Node tagsRoot;
    public Node tagsRoot1;

    int index = 0; // index = Unique identifier for each node
    private String mainWord; // The main word

    private ArrayList<String> suffixesList;
    private ArrayList<String> prefixesList;
    private ArrayList<String> P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, lexicon, NN, JJ, VB, PP, PPS;

    /**
     * Constructor
     *
     * @param word the main word
     */
    public TreeGenerator(String word) {
        //Initialize----------
        mainWord = word;
        results = new ArrayList<String>();
        suffixesList = new ItemList("resources/suffixes.txt");
        prefixesList = new ItemList("resources/prefixes.txt");
        P1 = new ItemList("resources/verbalPrefixes.txt");
        P2 = new ItemList("resources/verbalContinuousPrefix.txt");
        P3 = new ItemList("resources/verbNegationSubjunctive.txt");
        P4 = new ItemList("resources/adjectiveMakingSuffixes.txt");
        P5 = new ItemList("resources/participle.txt");
        P6 = new ItemList("resources/predicateNominative.txt");
        P7 = new ItemList("resources/gerund.txt");
        P8 = new ItemList("resources/adverbMakingSuffixes.txt");
        P9 = new ItemList("resources/comparativeSuperlativeSuffixes.txt");
        P10 = new ItemList("resources/pluralSingularDefenitiveIndefinitiveSigns.txt");
        P11 = new ItemList("resources/possessivePronouns.txt");
        P12 = new ItemList("resources/cliticSuffixes.txt");
        lexicon = new ItemList("resources/lexicon.txt");
        NN = new ItemList("resources/NN.txt");
        JJ = new ItemList("resources/JJ.txt");
        VB = new ItemList("resources/VB.txt");
        PP = new ItemList("resources/PP.txt");
        PPS = new ItemList("resources/PPS.txt");

        root = new Node(EMPTY_SIGN, mainWord, EMPTY_SIGN);
        prunedRoot = new Node(EMPTY_SIGN, mainWord, EMPTY_SIGN);
        tagsRoot = new Node(EMPTY_SIGN, mainWord, EMPTY_SIGN);
        tagsRoot1 = new Node(EMPTY_SIGN, mainWord, EMPTY_SIGN);
        //--------------------
        setChildren(mainWord, root, 0);
        pruning(root, prunedRoot);
        findValidNodes(prunedRoot);
        createValidTree(prunedRoot, tagsRoot);
        createValidTree(prunedRoot, tagsRoot1);
        List<String> list = checkRules(tagsRoot1);

    }

    /**
     * Initialize collection, for change String[] into ArrayList<String>
     *
     * @param collection a collection to change into ArrayList
     * @return converted ArrayList<String>
     */
    private ArrayList<String> setArray(String[] collection) {
        ArrayList<String> res = new ArrayList<String>();
        for (String str : collection) {
            res.add(str);
        }
        return res;
    }

    /**
     * A recursive method to generate tree. This method will be finish if both
     * prefix and suffix of the word are equal to <code>EMPTY_SIGN</code>
     *
     * @param word  completed word (with prefix and suffix) in each iteration.
     *              Start with the main word.
     * @param root  WordNode root, parent for add derivations in each level
     * @param level a tree level for each node
     */
    private void setChildren(String word, Node root, int level) {
        ArrayList<String> prefixes = getPrefixes(word);
        ArrayList<String> suffixes = getSuffixes(word);

        if (suffixes.isEmpty()) {
            suffixes.add(EMPTY_SIGN);
        }
        if (prefixes.isEmpty()) {
            prefixes.add(EMPTY_SIGN);
        }

        for (String prefix : prefixes) {
            for (String suffix : suffixes) {
                Node wn = new Node(prefix, getRoot(word, prefix, suffix), suffix);
                results.add(wn.toString());
                wn.index = ++index;
                wn.level = level + 1;
                if (!wn.word.equals(""))
                    root.addChild(wn);
                if (prefix.equals(EMPTY_SIGN) && suffix.equals(EMPTY_SIGN)) {
                    return;
                }
                setChildren(getRoot(word, prefix, suffix), wn, level + 1);
            }
        }
    }

    /**
     * This method divides word to into prefix+root+suffix and return root
     *
     * @param word   a completed word that contains prefix+root+postfix
     * @param prefix prefix part of the word
     * @param suffix suffix part of the word
     * @return root part of the word as String
     */
    private String getRoot(String word, String prefix, String suffix) {
        String temp = word;
        if (!prefix.equals(EMPTY_SIGN) && prefix.length() <= temp.length()) {
            temp = temp.substring(prefix.length());
        }
        if (!suffix.equals(EMPTY_SIGN) && suffix.length() <= temp.length()) {
            temp = temp.substring(0, temp.length() - suffix.length());
        }
        return temp;
    }

    /**
     * This method used to find any possible prefixes according to prefixes list
     * (prefixesList)
     *
     * @param word a word to get prefixes from it
     * @return an ArrayList that includes any possible prefixes
     */
    private ArrayList<String> getPrefixes(String word) {
        ArrayList<String> p = new ArrayList<String>();
        for (String Prefix : prefixesList) {
            if (word.startsWith(Prefix)) {
                p.add(Prefix);
            }
        }
        p.add(EMPTY_SIGN);
        return p;
    }

    /**
     * This method used to find any possible suffixes according to suffixes list
     * (suffixesList)
     *
     * @param word a word to get prefixes from it
     * @return an ArrayList that includes any possible suffixes
     */
    private ArrayList<String> getSuffixes(String word) {
        ArrayList<String> s = new ArrayList<String>();
        for (String Suffix : suffixesList) {
            if (word.endsWith(Suffix)) {
                s.add(Suffix);
            }
        }
        s.add(EMPTY_SIGN);
        return s;
    }

    /**
     * To get prefixes sequence from node to parent ... until the root is gained
     *
     * @param node start point to get sequence of prefixes
     * @return an ArrayList<String> that includes sequence of prefixes
     */
    private ArrayList<String> getPrefixSequence(Node node) {
        ArrayList<String> pfxSeq = new ArrayList<String>();
        while (node.getParent() != null) {
            pfxSeq.add(node.prefix);
            node = node.parent;
        }
        return pfxSeq;
    }

    /**
     * To get suffixes sequence from node to parent ... until root
     *
     * @param node start point to get sequence of suffixes
     * @return an ArrayList<String> that includes sequence of suffixes
     */
    private ArrayList<String> getSuffixSequence(Node node) {
        ArrayList<String> sfxSeq = new ArrayList<String>();
        while (node.getParent() != null) {
            sfxSeq.add(node.suffix);
            node = node.parent;
        }
        return sfxSeq;
    }

    /**
     * Get priority of prefix based on Priority List (?)
     *
     * @param affix a prefix
     * @return an int value that shows the priority of prefix
     */
    private int getPriorityForPrefix(String affix) {
        if (affix.equals(EMPTY_SIGN)) {
            return 0;
        }
        if (P1.contains(affix)) {
            return 1;
        }
        if (P2.contains(affix)) {
            return 2;
        }
        if (P3.contains(affix)) {
            return 3;
        }

        //System.out.println(affix);
        return -1;
    }

    /**
     * Get priority of suffix based on Priority List (?)
     *
     * @param affix a suffix
     * @return an int value that shows the priority of suffix
     */
    private int getPriorityForSuffix(String affix) {
        if (affix.equals(EMPTY_SIGN)) {
            return 0;
        }
        if (P4.contains(affix)) {
            return 4;
        }
        if (P5.contains(affix)) {
            return 5;
        }
        if (P6.contains(affix)) {
            return 6;
        }
        if (P7.contains(affix)) {
            return 7;
        }
        if (P8.contains(affix)) {
            return 8;
        }
        if (P9.contains(affix)) {
            return 9;
        }
        if (P10.contains(affix)) {
            return 10;
        }
        if (P11.contains(affix)) {
            return 11;
        }
        if (P12.contains(affix)) {
            return 12;
        }

        return -1;
    }

    /**
     * Indicate true sequence or not, based on priority
     *
     * @param node start point
     * @return true if has true sequence, false if has false sequence
     */
    private boolean hasTruePriority(Node node) {

        ArrayList<String> tempSeq = getPrefixSequence(node);

        // for prefixes
        int MaximumPriority = getPriorityForPrefix(tempSeq.get(0));
        for (int i = 1; i < tempSeq.size(); i++) {
            if (MaximumPriority > getPriorityForPrefix(tempSeq.get(i))) {
                return false;
            }
            MaximumPriority = getPriorityForPrefix(tempSeq.get(i));
        }

        // for suffixes
        tempSeq = getSuffixSequence(node);
        MaximumPriority = getPriorityForSuffix(tempSeq.get(0));
        for (int i = 1; i < tempSeq.size(); i++) {
            if (MaximumPriority > getPriorityForSuffix(tempSeq.get(i))) {
                return false;
            }
            MaximumPriority = getPriorityForSuffix(tempSeq.get(i));
        }

        // all priorities are true then:
        return true;
    }

    /**
     * A recursive method to pruning the tree
     *
     * @param node start point
     * @prarm prunedRoot a node that points to root of pruned tree after pruning execution
     */
    private void pruning(Node node, Node prunedRoot) {
        for (Node child : node.children) {
            if (hasTruePriority(child)) {
                if (!child.word.equals(""))
                    pruning(child, prunedRoot.addChild(new Node(child.prefix, child.word, child.suffix, child.index)));
            } else {
                child.label = "pruned";
            }
        }
    }

    /**
     * A recursive method to find the meaningful node based on a dictionary
     */
    private void findValidNodes(Node node) {
        for (Node child : node.children) {
            if (!child.isLeaf()) {
                findValidNodes(child);
            } else {
                if (isInDictionary(child.word)) {
                    child.label = "valid";
                    Node parent = child.parent;
                    while (parent != null) {
                        parent.label = "valid";
                        parent = parent.parent;
                    }
                }
            }
        }
    }

    private void createValidTree(Node prunedNode, Node validTreeNode) {
        for (Node child : prunedNode.children) {
            if (child.label.equals("valid")) {
                createValidTree(child, validTreeNode.addChild(new Node(child.prefix, child.word, child.suffix, child.index)));
            }
        }
    }

    private List<String> checkRules(Node validatedRoot) {
        List<String> result = new ArrayList<>();
        RuleContext ruleContext = new RuleContext();
        List<Node> leaves = getLeaves(validatedRoot);
        for (Node leaf : leaves) {
            leaf.word = getPrimitiveCategory(leaf.word);
            while (leaf != null) {
                Set<Rule> primitiveRules = ruleContext.getPrimitiveRules();
                for (Rule rule : primitiveRules) {
                    if (rule.match(leaf)) {
                        leaf.parent.word = rule.getKey().substring(2);
                    } else {
                        Set<Rule> nonePrimitiveRules = ruleContext.getNonePrimitiveRules();
                        for (Rule npRule : nonePrimitiveRules) {
                            if (npRule.match(leaf)) {
                                leaf.parent.word = npRule.getKey();
                            } else {
                                result.add(leaf.word);
                            }
                        }
                    }
                }
                leaf = leaf.parent;
            }
        }
        return result;
    }

    private List<Node> getLeaves(Node root) {
        List<Node> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            for (Node child : node.children) {
                if (child.isLeaf()) {
                    result.add(child);
                } else {
                    stack.push(child);
                }
            }
        }
        return result;
    }

    /**
     *
     */
    private boolean isInDictionary(String _word) {
        if (lexicon.contains(_word)) {
            return true;
        }
        return false;
    }

    private String getPrimitiveCategory(String word) {
        if (NN.contains(word)) {
            return "p_NN";
        }
        if (JJ.contains(word)) {
            return "p_JJ";
        }
        if (VB.contains(word)) {
            return "p_VB";
        }
        if (PP.contains(word)) {
            return "p_PP";
        }
        if (PPS.contains(word)) {
            return "p_PPS";
        }
        // Default:
        if (word.startsWith("n_") || word.startsWith("p_")) {
            return word;
        } else {
            return "p_NN";
        }

    }
}
