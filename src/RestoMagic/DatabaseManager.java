package RestoMagic;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:restomagic.db";
    private Connection connection;

    public DatabaseManager(){
        connect();
        this.initializeDatabase();
    }

    private void connect(){
        try{
            this.connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void initializeDatabase(){
        this.executeUpdate("CREATE TABLE IF NOT EXISTS Tische (\n" +
                "    TischId INTEGER PRIMARY KEY,\n" +
                "    Name TEXT NOT NULL,\n" +
                "    verfügbar BOOLEAN DEFAULT TRUE\n" +
                ");");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS MenueElemente (\n" +
                "    MenueElementId INTEGER PRIMARY KEY,\n" +
                "    Name TEXT NOT NULL,\n" +
                "    Beschreibung TEXT,\n" +
                "    Preis REAL NOT NULL,\n" +
                "    Kategorie TEXT\n" +
                ");");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS Bestellungen (\n" +
                "    BestellId INTEGER PRIMARY KEY,\n" +
                "    TischNr INTEGER,\n" +
                "    Zeitstempel DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    abgeschlossen BOOLEAN DEFAULT FALSE,\n" +
                "    TotalPreis REAL,\n" +
                "    FOREIGN KEY (TischNr) REFERENCES Tische(TischId)\n" +
                ");\n");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS BestellteElemente (\n" +
                "    BestellteElementId INTEGER PRIMARY KEY,\n" +
                "    BestellId INTEGER,\n" +
                "    MenueId INTEGER,\n" +
                "    Anz INTEGER NOT NULL,\n" +
                "    FOREIGN KEY (BestellId) REFERENCES Bestellungen(BestellId),\n" +
                "    FOREIGN KEY (MenueId) REFERENCES MenueElemente(MenueElementId)\n" +
                ");");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS Rechnungen (\n" +
                "    RechnungId INTEGER PRIMARY KEY,\n" +
                "    BestellId INTEGER,\n" +
                "    Zeitstempel DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    GesamtPreis INTEGER,\n" +
                "    FOREIGN KEY (BestellId) REFERENCES Bestellungen(BestellId)\n" +
                ");");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS Benutzer (\n" +
                "    BenutzerId INTEGER PRIMARY KEY,\n" +
                "    Benutzername TEXT NOT NULL UNIQUE,\n" +
                "    Passwort TEXT NOT NULL,\n" +
                "    Rolle TEXT NOT NULL\n" +
                ");");
    }

    private void executeUpdate(String query) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet executeQuery(String query) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Table> getTables(){
        List<Table> tables = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TischId, Name FROM Tische");

            while(resultSet.next()){
                int tableId = resultSet.getInt("TischId");
                String tableName = resultSet.getString("Name");
                tables.add(new Table(tableId, tableName));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return tables;
    }

    public List<MenuItem> getMenuItems(){
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MenueElementId, Name, Beschreibung, Preis, Kategorie FROM MenueElemente");

            while(resultSet.next()){
                int menuItemId = resultSet.getInt("MenueElementId");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Beschreibung");
                double price = resultSet.getDouble("Preis");
                String category = resultSet.getString("Kategorie");
                menuItems.add(new MenuItem(menuItemId, name, description, price, category));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return menuItems;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveOrder(Order order){

    }

    private void saveMenuItem(MenuItem menuItem){

    }

    private List<Order> getOrders(){
        return null;
    }

    public User getUser(String pUsername, String pPassword){
        String sql = "SELECT * FROM Benutzer WHERE Benutzername = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("Benutzername");
                String password = resultSet.getString("Passwort");
                if(username.equals(pUsername) && password.equals(pPassword)){
                    int userId = resultSet.getInt("BenutzerId");
                    String role = resultSet.getString("Rolle");
                    return new User(userId, username, password, role);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void addMenuItem(String pName, double pPrice){
        String sql = "INSERT INTO MenueElemente (Name, Preis)" +
                "VALUES (?, ?);";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pName);
            preparedStatement.setDouble(2, pPrice);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void editMenuItem(int pId, String pName, double pPrice){
        String sql = "UPDATE MenueElemente " +
                "SET Name = ?, Preis = ?" +
                "WHERE MenueElementId = ?;";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pName);
            preparedStatement.setDouble(2, pPrice);
            preparedStatement.setInt(3, pId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeMenuItem(MenuItem pMenuItem){
        int id = pMenuItem.getMenuItemId();
        String sql = "DELETE FROM MenueElemente " +
                "WHERE MenueElementId = ?";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<OrderedItem> getOrderedItems(){
        List<OrderedItem> orderedItem = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BestellteElementId, MenueId, Anz FROM BestellteElemente");

            while(resultSet.next()){
                int orderedItemId = resultSet.getInt("BestellteElementId");
                int menuItemId = resultSet.getInt("MenueId");
                int quantity = resultSet.getInt("Anz");
                orderedItem.add(new OrderedItem(orderedItemId, quantity, menuItemId));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return orderedItem;
    }

    public void addOrderedItem(int pId, int pQuantity, int pTableId){
        String sql = "INSERT INTO BestellteElemente (MenueId, Anz)" +
                "VALUES (?, ?);";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, pId);
            preparedStatement.setInt(2, pQuantity);
            //preparedStatement.setInt(3, pTableId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
