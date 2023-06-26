package RestoMagic;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderID;
    private LocalDateTime timestamp;
    private int tableNumber;
    private boolean isPaid;
    private double total;
    private List<OrderedItem> orderedItems;

    private void addOrderedItem(MenuItem menuItem, int quantity){

    }

    private void removeOrderedItem(OrderedItem orderedItem){

    }

    private double calculateTotal(){
        return 0;
    }
}
