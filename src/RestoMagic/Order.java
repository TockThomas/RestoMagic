package RestoMagic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private LocalDateTime timestamp;
    private Table table;
    private boolean isPaid;
    private double total;
    private List<OrderedItem> orderedItemList;


    public Order(){
        //TODO: OrderID durch Parameter
        this.initializeOrder();
    }

    private void initializeOrder(){
        //TODO: ORDERID durch Parameter
        this.timestamp = null;
        this.isPaid = false;
        this.total = 0.0;
        this.orderedItemList = new ArrayList<>();
    }

    public void addOrderedItem(MenuItem pMenuItem){
        int menuItemId = pMenuItem.getMenuItemId();
        if(!this.orderedItemList.isEmpty()){
            for(int i = 0; i < this.orderedItemList.size(); i++){
                if(this.orderedItemList.get(i).getId() == menuItemId){
                    this.orderedItemList.get(i).addItem();
                    this.calculateTotal();
                    return;
                }
            }
        }
        this.orderedItemList.add(new OrderedItem(pMenuItem));
        this.calculateTotal();
    }

    public void removeOrderedItem(int pOrderedItemId){
        if(this.orderedItemList.get(pOrderedItemId).getQuantity() != 1){
            this.orderedItemList.get(pOrderedItemId).removeItem();
        } else {
            this.orderedItemList.remove(pOrderedItemId);
        }
        this.calculateTotal();
    }

    private void calculateTotal(){
        double totalPrice = 0.0;
        if(!this.orderedItemList.isEmpty()){
            for(int i = 0; i < this.orderedItemList.size(); i++){
                totalPrice += this.orderedItemList.get(i).getPrice();
            }
        }
        this.total = totalPrice;
    }

    public double getTotal(){
        return this.total;
    }

    public int getOrderId(){
        return this.orderId;
    }

    public void setTable(Table pTable){
        this.table = pTable;
    }

    public Table getTable(){
        return this.table;
    }


    public List<OrderedItem> getOrderedItemList(){
        return this.orderedItemList;
    }

    public void orderCancel(){
        this.initializeOrder();
    }

    public int placeOrder(String pEmail){
        this.initializeOrder();
        return 0;
    }
}
