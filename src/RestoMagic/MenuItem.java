package RestoMagic;

public class MenuItem {
    private int menuId;
    private String name;
    private String description;
    private double price;
    private String category;

    public MenuItem(int pMenuId, String pName, String pDescription, double pPrice, String pCategory){
        this.menuId = pMenuId;
        this.name = pName;
        this.description = pDescription;
        this.price = pPrice;
        this.category = pCategory;
    }

    public int getMenuItemId(){
        return this.menuId;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }
}
