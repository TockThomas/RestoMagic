package RestoMagic;

import javax.swing.*;
import java.awt.event.*;

public class EditMenuItemPopup extends JDialog {
    private GUI gui;
    private Controller controller;
    private MenuItem menuItem;
    private JPanel contentPane;
    private JButton btCancel;
    private JButton btSave;
    private JTextField tfName;
    private JTextField tfPrice;

    public EditMenuItemPopup(GUI pGui, Controller pController, MenuItem pMenuItem) {
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
        this.fillTextField();
        this.setVisible(true);

        this.btSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editMenuItem();
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
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void editMenuItem() {
        int id = this.menuItem.getMenuItemId();
        String name = tfName.getText();
        double price = Double.parseDouble(tfPrice.getText());
        this.controller.adminEditMenuItem(id, name, price);
        this.gui.setupTbAdminMenu();
        dispose();
    }

    private void fillTextField(){
        this.tfName.setText(this.menuItem.getName());
        this.tfPrice.setText(Double.toString(this.menuItem.getPrice()));
    }

    private void cancel() {
        dispose();
    }
}
