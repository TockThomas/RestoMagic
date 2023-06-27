package RestoMagic;

public class Table {
    private int tableId;
    private String name;
    private boolean is_available;

    public Table(int pTableId, String pName){
        this.tableId = pTableId;
        this.name = pName;
        this.is_available = true;
    }

    public String getName() {
        return this.name;
    }
}
