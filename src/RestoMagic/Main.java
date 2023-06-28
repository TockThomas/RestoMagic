package RestoMagic;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Erstellen und Anzeigen des GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
