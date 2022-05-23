import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class StartPagina extends JFrame {
    public StartPagina(){
        super("Graphic Tracer");
        setSize(600, 480);
        setLayout(new MigLayout("al center center"));
        JButton MonitorButton = new JButton("MonitoringsApplicatie");
        MonitorButton.setBackground(new Color(53, 80, 112));
        MonitorButton.setForeground(Color.white);
        MonitorButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        MonitorButton.setBorderPainted(false);


        JButton OntwerperButton = new JButton("Ontwerper");
        OntwerperButton.setBackground(new Color(53, 80, 112));
        OntwerperButton.setForeground(Color.white);
        OntwerperButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        OntwerperButton.setBorderPainted(false);

        MonitorButton.addActionListener(e->{
            try {
                MonitorGUI.main(null);
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ignored) {}
                    try {
                        MonitorClient.main(null);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dispose();
        });

        OntwerperButton.addActionListener(e -> {
            new main();
            new SluitFrame().setVisible(true);
            dispose();
        });

        add(MonitorButton);
        add(OntwerperButton);

        setVisible(true);
    }
}
