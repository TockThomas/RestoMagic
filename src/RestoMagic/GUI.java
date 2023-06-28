package RestoMagic;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable tbOrder;
    private JButton btOrderRemove;
    private JButton btOrderNext;
    private JLabel lbOrderPrice;
    private JButton btOrderCancel;
    private JPanel pnlOrderOverview;
    private JLabel lbOrderOverviewTable;
    private JLabel lbOrderOverviewOrderId;
    private JLabel lbOrderOverviewTotalPrice;
    private JTable tbOrderOverview;
    private JButton btOrderOverviewCancel;
    private JButton btOrderOverviewOrder;
    private JButton btOrderOverviewEdit;
    private JTextField tfOrderOverviewEmail;

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
                controller.setOrderTable(cbTableSelection.getSelectedIndex());
                displayMenuScreen();
            }
        });

        this.btOrderAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMenuItem();
            }
        });

        this.btOrderRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMenuItem();
            }
        });

        this.btOrderCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderCancel();
            }
        });

        this.btOrderNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.getOrderedItems().isEmpty()){
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Bitte wählen Sie mindestens ein Menüelement aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } else {
                    displayOrderOverviewScreen();
                }
            }
        });

        this.btOrderOverviewCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderCancel();
            }
        });

        this.btOrderOverviewEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMenuScreen();
            }
        });

        this.btOrderOverviewOrder.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        }));
    }

    private void displayTableSelectionScreen(){
        String[] tableNames = this.controller.getTableNames();
        this.cbTableSelection.removeAllItems();
        for(int i = 0; i < tableNames.length; i++){
            this.cbTableSelection.addItem(tableNames[i]);
        }
        this.cardLayout.show(pnlMain, "TableSelection");
        this.setContentPane(this.pnlMain);
    }

    private void displayLoginScreen(){

    }

    private void displayMenuScreen(){
        this.setupTbMenu();
        this.setupTbOrder();
        this.adjustPrice();
        this.cardLayout.show(pnlMain, "Order");
    }

    private void setupTbMenu(){
        String[] columnNames = {"Name", "Preis"};
        List<MenuItem> menuItems = this.controller.getMenuItems();
        Object[][] data = new Object[menuItems.size()][columnNames.length];

        for(int i = 0; i < menuItems.size(); i++){
            MenuItem item = menuItems.get(i);
            data[i][0] = item.getName();
            data[i][1] = String.format("%.2f", item.getPrice()) + " €";
        }

        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        for(int i = 0; i < columnNames.length; i++){
            model.addColumn(columnNames[i]);
        }

        for(int i = 0; i < data.length; i++){
            model.addRow(new Object[]{data[i][0], data[i][1]});
        }
        this.tbMenu.setModel(model);
        this.tbMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tbMenu.getColumnModel().getColumn(1).setMaxWidth(60);
    }

    private void setupTbOrder(){
        String[] columnNames = {"Name", "Preis"};
        List<OrderedItem> orderedItemList = this.controller.getOrderedItems();
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        for(int i = 0; i < columnNames.length; i++){
            model.addColumn(columnNames[i]);
        }
        if(orderedItemList.size() != 0){
            Object[][] data = new Object[orderedItemList.size()][columnNames.length];

            for(int i = 0; i < orderedItemList.size(); i++){
                OrderedItem item = orderedItemList.get(i);
                data[i][0] = item.getName();
                data[i][1] = String.format("%.2f", item.getPrice()) + " €";
            }


            for(int i = 0; i < data.length; i++){
                model.addRow(new Object[]{data[i][0], data[i][1]});
            }
        }
        this.tbOrder.setModel(model);
        this.tbOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tbOrder.getColumnModel().getColumn(1).setMaxWidth(60);
    }

    private void setupTbOrderOverview(){
        String[] columnNames = {"Name", "Anzahl", "Einzelpreis", "Summe"};
        List<OrderedItem> orderedItemList = this.controller.getOrderedItems();
        DefaultTableModel model = new DefaultTableModel();
        for(int i = 0; i < columnNames.length; i++){
            model.addColumn(columnNames[i]);
        }
        if(orderedItemList.size() != 0){
            Object[][] data = new Object[orderedItemList.size()][columnNames.length];

            for(int i = 0; i < orderedItemList.size(); i++){
                OrderedItem item = orderedItemList.get(i);
                data[i][0] = item.getName();
                data[i][1] = item.getQuantity();
                data[i][2] = String.format("%.2f", item.getUnitPrice()) + " €";
                data[i][3] = String.format("%.2f", item.getPrice()) + " €";
            }


            for(int i = 0; i < data.length; i++){
                model.addRow(new Object[]{data[i][0], data[i][1], data[i][2], data[i][3]});
            }
        }
        this.tbOrderOverview.setModel(model);
        this.tbOrderOverview.getColumnModel().getColumn(1).setMaxWidth(80);
        this.tbOrderOverview.getColumnModel().getColumn(2).setMaxWidth(120);
        this.tbOrderOverview.getColumnModel().getColumn(3).setMaxWidth(120);
    }

    private void addMenuItem(){
        if(this.tbMenu.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie mindestens ein Menüelement aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            int menuItemId = this.tbMenu.getSelectedRow();
            this.controller.addMenuItem(menuItemId);
            this.adjustPrice();
            this.setupTbOrder();
        }
    }

    private void removeMenuItem(){
        if(this.tbOrder.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie mindestens ein Menüelement aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            int orderedItemId = this.tbOrder.getSelectedRow();
            this.controller.removeMenuItem(orderedItemId);
            this.adjustPrice();
            this.setupTbOrder();
        }
    }

    private void adjustPrice(){
        double totalPrice = this.controller.getTotalPrice();
        this.lbOrderPrice.setText("Gesamt: " + String.format("%.2f", totalPrice) + " €");
    }

    private void orderCancel(){
        this.controller.orderCancel();
        this.adjustPrice();
        this.displayTableSelectionScreen();
    }

    private void displayOrderOverviewScreen(){
        this.lbOrderOverviewTable.setText(this.controller.getTableName());
        this.lbOrderOverviewOrderId.setText("Bestellnummer: #" + this.controller.getOrderId());
        this.lbOrderOverviewTotalPrice.setText("Gesamt: " + String.format("%.2f", this.controller.getTotalPrice()) + " €");
        this.setupTbOrderOverview();
        this.cardLayout.show(pnlMain, "OrderOverview");
    }

    private void placeOrder(){
        int orderId;
        String title;
        String message;
        int messageType;
        if(!this.tfOrderOverviewEmail.getText().contains("@")){
            title = "Email eingeben";
            message = "Bitte geben Sie Ihren Email richtig ein!";
            messageType = JOptionPane.ERROR_MESSAGE;
        } else {
            orderId = this.controller.placeOrder(this.tfOrderOverviewEmail.getText());
            title = "Bestellung Erfolgreich";
            message = "Vielen Dank für Ihre Bestellung\n" +
                    "Ihre Bestellnummer ist #" + orderId + ".\n" +
                    "Ein Kellner wird in Kürze bei Ihnen sein.";
            messageType = JOptionPane.INFORMATION_MESSAGE;
            this.displayTableSelectionScreen();
            this.tfOrderOverviewEmail.setText("");
        }
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void displayAdminScreen(){

    }
}
