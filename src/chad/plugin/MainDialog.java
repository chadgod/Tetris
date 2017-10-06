package chad.plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainDialog extends JDialog {
    private JPanel contentPane;
    private JPanel screenPanel;
    TetrisBlock tetrisblock = new TetrisBlock();
    public MainDialog() {
        setContentPane(contentPane);
        setModal(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setFocusable(true);
        screenPanel.setLayout(new BorderLayout());
        addKeyListener(new KeyGetter());
        screenPanel.add(tetrisblock,BorderLayout.CENTER);
        setResizable(false);
    }

    private void onCancel() {
        dispose();
    }
    class KeyGetter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            tetrisblock.keyPressed(e);
        }
    }
}
