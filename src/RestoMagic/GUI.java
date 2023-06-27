package RestoMagic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    private Controller controller;
    private CardLayout cardLayout = (CardLayout)this.pnlMain.getLayout();
    private JPanel pnlMain;
    private JPanel pnlTableSelection;
    private JButton btLogin;
    private JComboBox cbTableSelection;
    private JButton btTableSelectionNext;
    private JPanel pnlOrder;
    private JTable tbMenu;
    private JButton btOrderAdd;
    private JTable table2;
    private JButton btOrderRemove;
    private JButton btOrderNext;
    private JLabel lbOrderPrice;

    public GUI(){
        this.controller = new Controller();
        this.setTitle("RestoMagic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.displayTableSelectionScreen();
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        this.btLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLoginScreen();
            }
        });
        this.btTableSelectionNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMenuScreen();
            }
        });

        this.btOrderAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.btOrderRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.btOrderNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMenuScreen();
            }
        });
    }

    private void displayTableSelectionScreen(){
        String[] tableNames = this.controller.getTableNames();
        for(int i = 0; i < tableNames.length; i++){
            this.cbTableSelection.addItem(tableNames[i]);
        }
        this.setContentPane(this.pnlMain);
    }

    private void displayLoginScreen(){

    }

    private void displayMenuScreen(){
        this.cardLayout.show(pnlMain, "Order");
    }

    private void addMenuItem(){

    }

    private void removeMenuItem(){

    }

    private void displayOrderScreen(){

    }

    private void displayAdminScreen(){

    }
}
