import javax.swing.*;

public class SwingApp extends JFrame {

    public SwingApp () {
        super("Test");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Normal call");
            }
        });
    }
}
