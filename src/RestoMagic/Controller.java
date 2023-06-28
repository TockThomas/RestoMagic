package RestoMagic;

import java.util.List;

public class Controller {
    private DatabaseManager databaseManager;
    private User currentUser;
    private Order order;
    private List<Table> tableList;
    private List<MenuItem> menuItemList;


    public Controller(){
        this.databaseManager = new DatabaseManager();
        this.retrieveDatabase();
        this.order = new Order();
    }

    private void retrieveDatabase(){
        this.tableList = this.databaseManager.getTables();
        this.menuItemList = this.databaseManager.getMenuItems();
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
        return this.order.placeOrder(pEmail);
    }
}
