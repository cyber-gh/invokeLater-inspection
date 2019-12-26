import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;


public class SwingApp2 extends JFrame {

    public SwingApp2 () {
        super("Test");

        invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Imported call");
            }
        });

    }
}

