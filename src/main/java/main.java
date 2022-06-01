import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class main {
    static Gson gson = new Gson();
    public void initialize()
    {
        panel ah = new panel();
        ah.initialize();
    }

    public main()
    {
        initialize();
    }

    public static void main(String[] argc)
    {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (info.getClassName().equals("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        new StartPagina();
    }
}