
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;

public class file_dialog_funcs
{
    static StringBuilder category = new StringBuilder();

    public void initialize(JFrame window)
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuBestand = new JMenu("Bestand");
        menuBar.add(menuBestand);

        JMenu menuBewerken = new JMenu("Bewerken");
        menuBar.add(menuBewerken);

        JMenu menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);


        JMenuItem open = new JMenuItem("Open bestand");
        JMenuItem opslaan_bestand = new JMenuItem("Opslaan bestand");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open_file(window);
            }
        });

        opslaan_bestand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save_file(window);
            }
        });

        menuBestand.add(open);
        menuBestand.add(opslaan_bestand);

        window.setJMenuBar(menuBar);
    }


    public void open_file(JFrame window)
    {
        Dialog dg = new Dialog(window);
        FileDialog open_file_dialog = new FileDialog(dg,"",FileDialog.LOAD);

        open_file_dialog.setVisible(true);
    }

    public FileDialog save_file(JFrame window)
    {
        Dialog dg = new Dialog(window);
        FileDialog save_file_dialog = new FileDialog(dg,"",FileDialog.SAVE);

        save_file_dialog.setVisible(true);

        write_to_file(save_file_dialog.getFiles()[0], "texthier");

        return save_file_dialog;
    }

    public void write_to_file(File file, String txt)
    {
        try
        {
            FileWriter write_file = new FileWriter(file.getAbsolutePath());
            write_file.write(txt);
            write_file.close();
        }
        catch (IOException e)
        {
            System.out.print("");
        }
    }


}