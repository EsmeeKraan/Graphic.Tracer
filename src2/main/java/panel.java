import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

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

    DefaultListModel<GeplaatstComponent> totaleComponentenList = new DefaultListModel<>();

    public JList<GeplaatstComponent> totaleComponenten = new JList<>(totaleComponentenList);


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
        
        dialog_funcs.initialize(frame);
        frame.setResizable(true);
        frame.setTitle("Graphic Tracer");
//        frame.setIconImage("");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel main_paneel = new JPanel();
        JPanel geplaatsteComponenten = new JPanel();
        JPanel beschikbareOptiesPaneel = new JPanel();

        Box overzichtPaneel = Box.createVerticalBox();
        Font font = new Font("Courier",Font.BOLD,14);
        JLabel BLabel = new JLabel("Beschikbaarheidspercentage: " + totalAvailability);
        JLabel TLabel = new JLabel("Totale jaarlijkse kosten: " + totalPrice);
        Optimaliseren = new JButton("Optimaliseren");
        Optimaliseren.addActionListener((ActionListener) this);

        // servers
        JLabel WebLabel = new JLabel("Webserver(s):");
        JLabel DBLabel = new JLabel("Databaseserver(s):");
        JLabel FireLabel = new JLabel("Firewall:");
        JLabel DataLabel = new JLabel("Gegevens opzet:");

        //Weblabel
        overzichtPaneel.add(WebLabel);
        WebLabel.setFont(font);
        WebLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        WebLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // DBlabel
        overzichtPaneel.add(DBLabel);
        DBLabel.setFont(font);
        DBLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        DBLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //FireLabel
        overzichtPaneel.add(FireLabel);
        FireLabel.setFont(font);
        FireLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        FireLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

<<<<<<< HEAD
        //DataLabel
        second_panel.add(DataLabel);
        DataLabel.setFont(font);
        DataLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        DataLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //BLabel
        second_panel.add(BLabel);
=======
        // BLabel
        overzichtPaneel.add(BLabel);
>>>>>>> 9f0a29aeab979093c72bdb839dabbcfc09f2fe9a
        BLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        BLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //TLabel
        overzichtPaneel.add(TLabel);
        TLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        TLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

<<<<<<< HEAD

        // positie Geselecteerde componenten box
        second_panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        second_panel.setAlignmentY(Component.TOP_ALIGNMENT);
        second_panel.setMaximumSize(new Dimension(200, 200));
        second_panel.setBorder(BorderFactory.createTitledBorder("Geselecteerde componenten"));
=======
        //DLabel
        overzichtPaneel.add(DLabel);
        DLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        DLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


        // positie Geselecteerde componenten box
        overzichtPaneel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        overzichtPaneel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//        overzichtPaneel.setMaximumSize(new Dimension(800, 400));
        overzichtPaneel.setBorder(BorderFactory.createTitledBorder("Geselecteerde componenten"));
>>>>>>> 9f0a29aeab979093c72bdb839dabbcfc09f2fe9a


        // Optimaliseren button
        overzichtPaneel.add(Optimaliseren);

        JPanel DBserver = create_category("Databaseserver");
        JPanel Webserver = create_category("Webserver");
        JPanel Firewall = create_category("Firewall");
//        Firewall.setPreferredSize(new Dimension(200, 75));



        //Webserver
        for (int i = 1; i < 4; i++) {
            Icon icon1 = new Icon("icons/server.png", 30, 30, "HAL900" + i + "W");
            var event = new LabelClicked(icon1);
            icon1.label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    totaleComponentenList.addElement(new GeplaatstComponent(icon1.get_label().getText(), icon1.get_icon()));
                }
            });
            Webserver.add(icon1.get_label(), "wrap");
        }

        //DatabaseServer
        for (int i = 1; i < 4; i++) {
            Icon icon1 = new Icon("icons/server.png", 30, 30, "HAL900" + i + "DB");
            var event = new LabelClicked(icon1);
            icon1.label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    totaleComponentenList.addElement(new GeplaatstComponent(icon1.get_label().getText(), icon1.get_icon()));
                }
            });
            DBserver.add(icon1.get_label(), "wrap");
        }

        //Firewall
        for (int i = 0; i < 1; i++) {
            Icon icon1 = new Icon("icons/firewallserver.png", 30, 30, "JBHAL900" + i + "FW");
            var event = new LabelClicked(icon1);
            icon1.label.addMouseListener(event);
            Firewall.add(icon1.get_label(), "wrap");
        }

        beschikbareOptiesPaneel.add(Webserver, "wrap, growprioy 100");
        beschikbareOptiesPaneel.add(DBserver, "wrap, growprioy 100");
        beschikbareOptiesPaneel.add(Firewall, "wrap, growprioy 50");
        beschikbareOptiesPaneel.add(terugKnop, "wrap, growprioy 50");

        terugKnop.addActionListener((ef)->{
            Arrays.stream(StartPagina.getFrames()).forEach(frame1 -> frame1.dispose());
            new Thread(StartPagina::new).start();
            frame.dispose();
        });

        geplaatsteComponenten.add(new JScrollPane(totaleComponenten), "wrap, push");
        geplaatsteComponenten.setLayout(new MigLayout("al center center, wrap", "[grow,fill]", "[grow,fill]"));
        totaleComponenten.setCellRenderer(new GeplaatsteComponentRenderer());

        JButton verwijderButton = new JButton("Verwijder component");
        geplaatsteComponenten.add(verwijderButton, "growprioy 10");
        verwijderButton.addActionListener(e -> {
            if(totaleComponenten.isSelectionEmpty()){
                return;
            }
            final var list = totaleComponenten.getSelectedIndices();
            for (int i = list.length - 1; i >= 0; --i)
                totaleComponentenList.remove(list[i]);
        });


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
        frame.add(beschikbareOptiesPaneel, "pushy");
        frame.add(geplaatsteComponenten, "push");
        frame.add(overzichtPaneel, "pushy");

        frame.setLayout(new MigLayout("center center", "[grow 10, fill][grow,fill][grow 25, fill]", "[grow,fill]"));
        frame.setSize(800, 600);
        frame.setVisible(true);

        createClose();
    }

    public void createClose() {
        JFrame frame = new JFrame("Graphic Tracer");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Het huidige event wordt afgesloten");
                var a = JOptionPane.showConfirmDialog(frame, "Wilt u het huidige ontwerp afsluiten?");
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
