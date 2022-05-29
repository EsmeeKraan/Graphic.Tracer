import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.zip.ZipEntry;

class t {
    String name = "";
    int id;

    t(String v1, int v3) {
        name = v1;
        id = v3;
    }
}

class array {
    int item_count = 0;
    int max = 0;
    t[] a;

    public array(int max_elements) {
        max = max_elements;

        a = new t[max];
    }

    public void insert(t ts) {
        if (item_count >= max) return;

        a[item_count] = ts;
    }

    public t get_element(int id) {
        return a[id];
    }

    public void set_element(int id, t elem) {
        a[id] = elem;
    }
}

public class panel extends JFrame implements ActionListener {

    JButton terugKnop = new JButton("Terug");

    array array_category = new array(5);
    JPanel[] panel_array = new JPanel[array_category.max];
    Drag dragicon = new Drag();
    private JButton Optimaliseren;
    public JList<String> Webservers = new JList<>(new DefaultListModel<>());
    public JList<String> DBservers = new JList<>(new DefaultListModel<>());

    JPanel create_category(String name) {
        t xd = new t(name, array_category.item_count);
        array_category.insert(xd);

        JPanel panel = new JPanel();
//        panel.setPreferredSize(new Dimension(200, 200));
//        panel.setMaximumSize(new Dimension(200, 200));
        panel.setBorder(BorderFactory.createTitledBorder(array_category.get_element(array_category.item_count).name));
        panel.setLayout(new MigLayout("al center center, wrap", "[grow,fill]", "[grow,fill]"));
        panel_array[array_category.item_count] = panel;

        array_category.item_count++;
        return panel;
    }

    void initialize() {
        file_dialog_funcs dialog_funcs = new file_dialog_funcs();

        JFrame frame = new JFrame();
        SluitFrame sluitFrame = new SluitFrame();
        
        dialog_funcs.initialize(frame);
        frame.setResizable(true);
        frame.setTitle("Graphic Tracer");
//        frame.setIconImage("");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel main_paneel = new JPanel();
        JPanel beschikbareOptiesPaneel = new JPanel();

        Box second_panel = Box.createVerticalBox();
        Font font = new Font("Courier",Font.BOLD,14);
        JLabel BLabel = new JLabel("Beschikbaarheidspercentage:");
        JLabel TLabel = new JLabel("Totale jaarlijkse kosten");
        JLabel DLabel = new JLabel("Downtime");
        Optimaliseren = new JButton("Optimaliseren");
        Optimaliseren.addActionListener((ActionListener) this);

        // servers
        JLabel WebLabel = new JLabel("Webserver");
        JLabel DBLabel = new JLabel("Databaseserver");
        JLabel FireLabel = new JLabel("Firewall");

        //Weblabel
        second_panel.add(WebLabel);
        WebLabel.setFont(font);
        WebLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        WebLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // DBlabel
        second_panel.add(DBLabel);
        DBLabel.setFont(font);
        DBLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        DBLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //FireLabel
        second_panel.add(FireLabel);
        FireLabel.setFont(font);
        FireLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        FireLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // BLabel
        second_panel.add(BLabel);
        BLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        BLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //TLabel
        second_panel.add(TLabel);
        TLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        TLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //DLabel
        second_panel.add(DLabel);
        DLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        DLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


        // positie Geselecteerde componenten box
        second_panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        second_panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        second_panel.setMaximumSize(new Dimension(800, 400));
        second_panel.setBorder(BorderFactory.createTitledBorder("Geselecteerde componenten"));

        // Optimaliseren button
        second_panel.add(Optimaliseren);

        JPanel DBserver = create_category("Databaseserver");
        JPanel Webserver = create_category("Webserver");
        JPanel Firewall = create_category("Firewall");
//        Firewall.setPreferredSize(new Dimension(200, 75));



        //Webserver
        for (int i = 0; i < 4; i++) {
            Icon icon1 = new Icon("icons/server.png", 30, 30, "JBHAL900" + i + "W");
            var event = new LabelClicked(icon1);
            icon1.label.addMouseListener(event);
            Webserver.add(icon1.get_label(), "wrap");
        }

        //DatabaseServer
        for (int i = 0; i < 4; i++) {
            Icon icon1 = new Icon("icons/server.png", 30, 30, "JBHAL900" + i + "DB");
            var event = new LabelClicked(icon1);
            icon1.label.addMouseListener(event);
            DBserver.add(icon1.get_label(), "wrap");
        }

        //Firewall
        for (int i = 0; i < 1; i++) {
            Icon icon1 = new Icon("icons/firewallserver.png", 30, 30, "JBHAL900" + i + "FW");
            var event = new LabelClicked(icon1);
            icon1.label.addMouseListener(event);
            Firewall.add(icon1.get_label(), "wrap");
        }

        beschikbareOptiesPaneel.add(DBserver, "wrap, growprioy 100");
        beschikbareOptiesPaneel.add(Webserver, "wrap, growprioy 100");
        beschikbareOptiesPaneel.add(Firewall, "wrap, growprioy 50");
        beschikbareOptiesPaneel.add(terugKnop, "wrap, growprioy 50");

        terugKnop.addActionListener((ef)->{
            new Thread(StartPagina::new).start();
            frame.dispose();
            Arrays.stream(SluitFrame.getFrames()).forEach(frame1 -> frame1.dispose());
        });

        this.Webservers.setDragEnabled(true);
        this.DBservers.setDragEnabled(true);
        this.Webservers.setDropMode(DropMode.INSERT);
        this.DBservers.setDropMode(DropMode.INSERT);

        Webservers.setTransferHandler(new ListTransferHandler());
        DBservers.setTransferHandler(new ListTransferHandler());

        //beschikbareOptiesPaneel.setBackground(new Color(255,255,255));
//        beschikbareOptiesPaneel.setLayout(new BoxLayout(beschikbareOptiesPaneel, BoxLayout.X_AXIS));
//        beschikbareOptiesPaneel.setLayout(new GridLayout(3, 1));
        beschikbareOptiesPaneel.setLayout(new MigLayout("al center center, wrap", "[grow,fill]", "[grow,fill]"));
        beschikbareOptiesPaneel.setVisible(true);

//        JPanel pnlr = new JPanel();
//        pnlr.setVisible(true);
//        pnlr.setLayout(new BoxLayout(pnlr, BoxLayout.X_AXIS));
//        pnlr.setBackground(new Color(255, 255, 255));
//        beschikbareOptiesPaneel.add(pnlr);
        frame.add(beschikbareOptiesPaneel);

        frame.add(second_panel);

        frame.setLayout(new GridLayout(1, 2));
        frame.setSize(800, 600);
        frame.setVisible(true);

        createClose();
    }
    public void createClose() {
        JFrame frame = new JFrame("Graphic Tracer");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("De huidige event wordt afgesloten");
                var a = JOptionPane.showConfirmDialog(frame, "Wilt u de huidige ontwerp afsluiten?");
                if (a == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Optimaliseren) {
            OptimalizeDialoog dialog = new OptimalizeDialoog(this);
        }
//        if(answer == JOptionPane.YES_OPTION){
//
//        }
    }
}
