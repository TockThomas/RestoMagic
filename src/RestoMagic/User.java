package RestoMagic;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role;

    public User(int pUserId, String pUsername, String pPassword, String pRole){
        this.userId = pUserId;
        this.username = pUsername;
        this.password = pPassword;
        this.role = pRole;
    }
}
