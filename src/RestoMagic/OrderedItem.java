package RestoMagic;

public class OrderedItem {
    private int orderedItemId;
    private int menuItemId;
    private String name;
    private MenuItem menuItem;
    private int quantity;
    private double price;
    private double unitPrice;

    public OrderedItem(MenuItem pMenuItem){
        this.menuItemId = pMenuItem.getMenuItemId();
        this.menuItem = pMenuItem;
        this.name = pMenuItem.getName() + " x 1";
        this.unitPrice = pMenuItem.getPrice();
        this.price = this.unitPrice;
        this.quantity = 1;
    }

    public OrderedItem(MenuItem pMenuItem, int pQuantity){
        this.menuItemId = pMenuItem.getMenuItemId();
        this.menuItem = pMenuItem;
        this.name = pMenuItem.getName();
        this.unitPrice = pMenuItem.getPrice();
        this.price = this.unitPrice;
        this.quantity = pQuantity;
    }

    public OrderedItem(int pId, int pQuantity, int pMenuItemId){
        this.orderedItemId = pId;
        this.quantity = pQuantity;
        this.menuItemId = pMenuItemId;
    }

    public int getOrderedItemId(){
        return this.orderedItemId;
    }

    public int getMenuItemId(){
        return this.menuItemId;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public double getUnitPrice(){
        return this.unitPrice;
    }

    public int getQuantity(){
        return this.quantity;
    }

    private void setQuantity(){
        this.name = this.name.substring(0, this.name.length() - 1);
        this.name += Integer.toString(this.quantity);
    }

    private void calculatePrice(){
        this.price = this.menuItem.getPrice() * this.quantity;
    }

    public void addItem(){
        this.quantity += 1;
        this.setQuantity();
        this.calculatePrice();
    }

    public void removeItem(){
        this.quantity -= 1;
        this.setQuantity();
        this.calculatePrice();
    }
}
