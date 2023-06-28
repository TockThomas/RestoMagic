package RestoMagic;

import javax.swing.*;
import java.awt.event.*;

public class AddMenuItemPopup extends JDialog {
    private GUI gui;
    private Controller controller;
    private JPanel pnlAddMenuItem;
    private JButton btCancel;
    private JButton btAdd;
    private JTextField tfName;
    private JTextField tfPrice;

    public AddMenuItemPopup(GUI pGui, Controller pController) {
        this.gui = pGui;
        this.controller = pController;
        this.setContentPane(pnlAddMenuItem);
        this.setTitle("Neues Menü Element erstellen");
        this.getRootPane().setDefaultButton(btCancel);
        this.pack();
        this.setSize(400,250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //this.setModal(true); Buttons werden ansonsten nicht funktionieren
        this.setVisible(true);

        this.btAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMenuItem();
            }
        });

        this.btCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        // call onCancel() when cross is clicked
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });

        // call onCancel() on ESCAPE
        this.pnlAddMenuItem.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void addMenuItem() {
        String name = tfName.getText();
        double price = Double.parseDouble(tfPrice.getText());
        this.controller.adminAddMenuItem(name, price);
        this.gui.setupTbAdminMenu();
        dispose();
    }

    private void cancel() {
        dispose();
    }
}
