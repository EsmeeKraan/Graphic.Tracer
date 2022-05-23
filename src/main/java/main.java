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
        // prepare files
        JsonFileWriter<String> jsonFileWriter = new JsonFileWriter<>();
        ArrayList<String> webservers = new ArrayList<>();
        jsonFileWriter.WriteToJsonFile(webservers, "webservers.json");
//        new main();
        new StartPagina();
//        new Drag();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
//                new SluitFrame().setVisible(true);
            }
        });
    }
}