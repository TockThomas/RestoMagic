package RestoMagic;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private DatabaseManager databaseManager;
    private User currentUser;
    private Order order;
    private List<Table> tableList;
    private List<MenuItem> menuItemList;
    private List<OrderedItem> orderedItemList;


    public Controller(){
        this.currentUser = null;
        this.databaseManager = new DatabaseManager();
        this.retrieveDatabase();
        this.order = new Order();
    }

    private void retrieveDatabase(){
        this.tableList = this.databaseManager.getTables();
        this.menuItemList = this.databaseManager.getMenuItems();
        this.orderedItemList = this.databaseManager.getOrderedItems();
    }

    public String[] getTableNames(){
        String[] tableNames = new String[this.tableList.size()];
        for(int i = 0; i < tableNames.length; i++){
            tableNames[i] = this.tableList.get(i).getName();
        }
        return tableNames;
    }

    public List<MenuItem> getMenuItems(){
        return this.menuItemList;
    }

    public MenuItem getMenuItem(int pMenuItemId){
        return this.menuItemList.get(pMenuItemId);
    }

    public void addMenuItem(int pMenuItemId){
        this.order.addOrderedItem(this.menuItemList.get(pMenuItemId));
    }

    public void removeMenuItem(int pOrderedItemId){
        this.order.removeOrderedItem(pOrderedItemId);
    }

    public double getTotalPrice(){
        return this.order.getTotal();
    }

    public void orderCancel(){
        this.order.orderCancel();
    }

    public List<OrderedItem> getOrderedItems(){
        return this.order.getOrderedItemList();
    }

    public List<OrderedItem> getOrderedItemsForAdmin(){
        List<OrderedItem> unfinishedOrderedItemList = databaseManager.getOrderedItems();
        List<OrderedItem> finishedOrderedItemList = new ArrayList<>(); //TODO: OrderedItem an sich überarbeiten
        for(int i = 0; i < unfinishedOrderedItemList.size(); i++){
            OrderedItem unfinishedItem = unfinishedOrderedItemList.get(i);
            int menuItemId = unfinishedItem.getMenuItemId();
            int quantity = unfinishedItem.getQuantity();
            finishedOrderedItemList.add(new OrderedItem(this.menuItemList.get(menuItemId), quantity));
        }
        return finishedOrderedItemList;
    }

    public void setOrderTable(int pTableId){
        this.order.setTable(this.tableList.get(pTableId));
    }

    public String getTableName(){
        return this.order.getTable().getName();
    }

    public int getOrderId(){
        return this.order.getOrderId();
    }

    public int placeOrder(String pEmail){
        return this.order.placeOrder(this, pEmail);
    }

    public boolean login(String pUsername, String pPassword){
        this.currentUser = this.databaseManager.getUser(pUsername, pPassword);
        return this.isUserLoggedIn();
    }

    public boolean isUserLoggedIn(){
        if(this.currentUser != null){
            return true;
        } else {
            return false;
        }
    }

    public void logOff(){
        this.currentUser = null;
    }

    public void adminAddMenuItem(String pName, double pPrice){
        if(currentUser != null){
            databaseManager.addMenuItem(pName, pPrice);
            this.retrieveDatabase();
        }
    }

    public void adminEditMenuItem(int pId, String pName, double pPrice){
        if(currentUser != null){
            databaseManager.editMenuItem(pId, pName, pPrice);
            this.retrieveDatabase();
        }
    }

    public void adminRemoveMenuItem(MenuItem pMenuItem){
        if(currentUser != null){
            databaseManager.removeMenuItem(pMenuItem);
            this.retrieveDatabase();
        }
    }

    public void addOrderedItemToDatabase(int pId, int pQuantity, int pTableId){
        databaseManager.addOrderedItem(pId, pQuantity, pTableId);
        this.retrieveDatabase();
    }
}
