package RestoMagic;

import javax.swing.*;
import java.awt.event.*;

public class RemoveMenuItemPopup extends JDialog {
    private GUI gui;
    private Controller controller;
    private MenuItem menuItem;
    private JPanel contentPane;
    private JButton btCancel;
    private JButton btRemove;

    public RemoveMenuItemPopup(GUI pGui, Controller pController, MenuItem pMenuItem) {
        this.gui = pGui;
        this.controller = pController;
        this.menuItem = pMenuItem;
        this.setContentPane(contentPane);
        this.getRootPane().setDefaultButton(btCancel);
        this.pack();
        this.setSize(400,250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //this.setModal(true); Buttons werden ansonsten nicht funktionieren
        this.setVisible(true);

        btRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeMenuItem();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void removeMenuItem() {
        this.controller.adminRemoveMenuItem(this.menuItem);
        this.gui.setupTbAdminMenu();
        dispose();
    }

    private void cancel() {
        dispose();
    }
}
