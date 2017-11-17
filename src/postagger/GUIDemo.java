package postagger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

/**
 * @author Jalal
 */
public class GUIDemo extends JFrame implements ActionListener {
    private final Logger LOG = Logger.getLogger(getClass().getName());

    private JButton btnGenerate;
    private JTextField txtWord;
    private JTextArea txtRes;
    private JPanel panel;
    private JTabbedPane tab;

    public GUIDemo() {
    }

    public static void main(String[] argd) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUIDemo g = new GUIDemo();
                g.setFrame();
            }
        });

    }

    public void setFrame() {
        setTitle("Tree Generator");
        setLayout(new BorderLayout());
        btnGenerate = new JButton("Generate");
        btnGenerate.setBounds(470, 10, 100, 40);
        btnGenerate.addActionListener(this);
        txtWord = new JTextField();
        txtWord.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        txtWord.setFont(new Font("Unikurd Hejar", Font.LAYOUT_RIGHT_TO_LEFT, 18));
        txtWord.setBounds(600, 10, 180, 30);
        txtRes = new JTextArea();
        txtRes.setBounds(10, 60, 760, 490);
        txtRes.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        txtRes.setFont(new Font("Unikurd Hejar", Font.LAYOUT_RIGHT_TO_LEFT, 18));
        txtRes.setEditable(false);
        txtRes.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        JScrollPane scroll = new JScrollPane(txtRes);
        scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(0, 0, 760, 490);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(10, 60, 760, 490);
        tab = new JTabbedPane(JTabbedPane.TOP);
        tab.setBounds(10, 60, 760, 490);
        tab.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 || e.getButton() == MouseEvent.BUTTON2)
                    tab.remove(tab.getSelectedComponent());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        getContentPane().add(tab);

        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add(panel);
        add(txtWord, BorderLayout.NORTH);
        add(btnGenerate, BorderLayout.PAGE_END);
        panel.add(scroll, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerate) {
            TreeGenerator w = new TreeGenerator(txtWord.getText());
            JTree treeRes = new JTree(w.root);
            tab.addTab("Primitive: (" + txtWord.getText() + ")", new TreeView(treeRes));
            JTree treeResPruned = new JTree(w.prunedRoot);
            tab.addTab("Pruned: (" + txtWord.getText() + ")", new TreeView(treeResPruned));
            JTree treeTags = new JTree(w.tagsRoot);
            tab.addTab("Valid: (" + txtWord.getText() + ")", new TreeView(treeTags));
            JTree treeTags1 = new JTree(w.tagsRoot1);
            tab.addTab("Tagged: (" + txtWord.getText() + ")", new TreeView(treeTags1));
        }
    }

    class TreeView extends JPanel {

        public TreeView(JTree tr) {
            setLayout(new BorderLayout());
            setBounds(0, 0, 700, 600);
            tr.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            tr.setFont(new Font("Unikurd Hejar", Font.LAYOUT_RIGHT_TO_LEFT, 18));
            tr.setEditable(false);
            tr.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            for (int i = 0; i < tr.getRowCount(); i++) {
                tr.expandRow(i);
            }
            JScrollPane scroll = new JScrollPane(tr);
            scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            add(scroll);
        }

    }

}