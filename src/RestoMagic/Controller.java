package RestoMagic;

import java.util.List;

public class Controller {
    private DatabaseManager databaseManager;
    private User currentUser;
    private List<Table> tableList;

    public Controller(){
        this.databaseManager = new DatabaseManager();
        this.retrieveDatabase();
    }

    private void retrieveDatabase(){
        this.tableList = this.databaseManager.getTables();
    }

    String[] getTableNames(){
        String[] tableNames = new String[this.tableList.size()];
        for(int i = 0; i < tableNames.length; i++){
            tableNames[i] = this.tableList.get(i).getName();
        }
        return tableNames;
    }
}
