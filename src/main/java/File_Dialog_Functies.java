
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.net.URI;
import java.util.Collections;


public class File_Dialog_Functies
{
    static StringBuilder category = new StringBuilder();


    public void initialize(panel window)
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuBestand = new JMenu("Bestand");
        menuBar.add(menuBestand);

        JMenu menuBewerken = new JMenu("Bewerken");
        menuBar.add(menuBewerken);

        JMenu menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);


        JMenuItem open = new JMenuItem("Bestand openen");
        JMenuItem opslaan_bestand = new JMenuItem("Bestand opslaan");
        JMenuItem update = new JMenuItem("Check voor updates");
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

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updates(window);
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        menuBestand.add(open);
        menuBestand.add(opslaan_bestand);
        menuBestand.add(update);

        window.frame.setJMenuBar(menuBar);
    }


    public void open_file(panel window)
    {
        Dialog dg = new Dialog(window);
        FileDialog open_file_dialog = new FileDialog(dg,"",FileDialog.LOAD);

        open_file_dialog.setVisible(true);

        if(open_file_dialog.getFile() != null){
            window.totaleComponentenList.clear();
            Gson gson = new Gson();
            try {
                final var values = Files.readString(Path.of(open_file_dialog.getFile()));
                final var items = JsonParser.parseString(values).getAsJsonArray();
                for(final var item : items){
                    final var component = gson.fromJson(item, Components.class);
                    if(component.type == ComponentSpecies.PfSense){
                        component.icon = Components.firewall;
                    } else {
                        component.icon = Components.normaleIcon;
                    }
                    window.addComponent(component);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public FileDialog save_file(panel window)
    {
        Dialog dg = new Dialog(window);
        FileDialog save_file_dialog = new FileDialog(dg,"",FileDialog.SAVE);

        save_file_dialog.setVisible(true);

//        write_to_file(save_file_dialog.getFiles()[0], "texthier");

        if(save_file_dialog.getFile() != null){
            Gson gson = new Gson();
            final var elements = window.totaleComponentenList.elements();
            write_to_file(new File(save_file_dialog.getFile()), gson.toJson(Collections.list(elements)));
        }

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

    public void updates(JFrame frame) throws URISyntaxException, IOException {

       int response = JOptionPane.showConfirmDialog(null, "Er is een update beschikbaar, wilt u deze nu downloaden?", "Update!", JOptionPane.YES_NO_OPTION);

        if(response==JOptionPane.YES_OPTION){

            Desktop d = Desktop.getDesktop();
            d.browse(new URI("https://github.com/EsmeeKraan/Graphic.Tracer"));
        }
    }




}