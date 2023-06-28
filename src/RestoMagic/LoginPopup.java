package RestoMagic;

import javax.swing.*;
import java.awt.event.*;

public class LoginPopup extends JDialog {
    private GUI gui;
    private Controller controller;
    private JPanel pnlLogin;
    private JButton btLoginCancel;
    private JButton btLoginLogin;
    private JTextField tfLoginUsername;
    private JPasswordField tfLoginPassword;

    public LoginPopup(GUI pGui, Controller pController) {
        this.gui = pGui;
        this.controller = pController;
        this.setContentPane(pnlLogin);
        this.setTitle("Mitarbeiter-Anmeldung");
        this.getRootPane().setDefaultButton(btLoginCancel);
        this.pack();
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //this.setModal(true); Buttons werden ansonsten nicht funktionieren
        this.setVisible(true);

        this.btLoginCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginCancel();
            }
        });

        this.btLoginLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                loginCancel();
            }
        });
    }

    private void login() {
        String username = tfLoginUsername.getText();
        String password = tfLoginPassword.getText();
        boolean isUserLoggedIn = this.controller.login(username, password);
        if(!isUserLoggedIn){
            String message = "Der eingegebene Benutzername und/oder das Passwort stimmen nicht mit unseren Aufzeichnungen überein. Bitte versuchen Sie es erneut.";
            String title = "Anmeldungsfehler";
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
        } else {
            this.gui.displayAdminScreen();
            this.dispose();
        }
    }

    private void loginCancel() {
        this.dispose();
    }
}
