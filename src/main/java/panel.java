import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Locale;

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

    DefaultListModel<Components> totaleComponentenList = new DefaultListModel<>();

    public JList<Components> totaleComponenten = new JList<>(totaleComponentenList);


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

    // Berekening van beschikbaarheid
    JLabel BLabel = new JLabel("Verwachtte beschikbaarheid: 0%");


    // Berekening van de jaarlijkse kosten
    JLabel TLabel = new JLabel("Totale jaarlijkse kosten: \u20AC 0");
    float totalCost = 0;

    // servers
    private final JLabel WebLabel = new JLabel("Webserver(s): 0");
    private final JLabel DBLabel = new JLabel("Databaseserver(s): 0");
    private final JLabel FireLabel = new JLabel("Firewall: 0");

    private void updateAvailability() {
        // 1. Per categorie (ComponentSpecies) uitrekenen
        final var subavailabilities = new double[3];
        for (int i = 0; i < 3; ++i)
            subavailabilities[i] = 1;

        for (int i = 0; i < totaleComponentenList.size(); ++i) {
            final var component = totaleComponentenList.getElementAt(i);
            switch (component.type) {
                case DbServer -> subavailabilities[0] *= 1 - (component.availability / 100);
                case WServer -> subavailabilities[1] *= 1 - (component.availability / 100);
                case PfSense -> subavailabilities[2] *= 1 - (component.availability / 100);
            }
        }

        // 2. Categorieën samenvoegen
        float totalAvailability = 1;
        for (double subAvailability : subavailabilities) {
            if (subAvailability == 1)
                continue;
            totalAvailability *= 1 - subAvailability;
        }

        BLabel.setText("Verwachtte beschikbaarheid: " + String.format("%.4f", 100*totalAvailability) + "%");
    }

    private static String formatCount(int count) {
        if (count == 1)
            return ": 1";
        return "s: " + count;
    }

    private void updateComponentCounts() {
        // 1. Per categorie (ComponentSpecies) uitrekenen
        final var counts = new int[3];

        for (int i = 0; i < totaleComponentenList.size(); ++i) {
            final var component = totaleComponentenList.getElementAt(i);
            switch (component.type) {
                case DbServer -> counts[0] += 1;
                case WServer -> counts[1] += 1;
                case PfSense -> counts[2] += 2;
            }
        }

        DBLabel.setText("Databaseserver" + formatCount(counts[0]));
        WebLabel.setText("Webserver" + formatCount(counts[1]));
        FireLabel.setText("Firewall" + formatCount(counts[2]));
    }

    private void addComponent(Components components) {
        totalCost = 0;
        totaleComponentenList.addElement(components);
        updateAvailability();
        updateComponentCounts();
        for (int i = 0; i < totaleComponentenList.size(); ++i) {
            components = totaleComponentenList.getElementAt(i);
            totalCost += components.price;
        }
        TLabel.setText(String.format(Locale.ITALIAN, "Totale jaarlijkse kosten: \u20AC %.2f", totalCost));
    }
    private void removeComponent(Components components) {
        for (int i = 0; i < totaleComponentenList.size(); ++i) {
            components = totaleComponentenList.getElementAt(i);
            totalCost = 0;

            for (int k = 0; k < totaleComponentenList.size(); k++){
                components = totaleComponentenList.getElementAt(k);
                totalCost += components.price;
            }}
        updateAvailability();
        updateComponentCounts();
        TLabel.setText(String.format(Locale.ITALIAN, "Totale jaarlijkse kosten: \u20AC %.2f", totalCost));
    }
    void initialize() {
        File_Dialog_Functies dialog_funcs = new File_Dialog_Functies();

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
        Optimaliseren = new JButton("Optimaliseren");
        Optimaliseren.addActionListener((ActionListener) this);

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

        // BLabel
        overzichtPaneel.add(BLabel);
        BLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        BLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //TLabel
        overzichtPaneel.add(TLabel);
        TLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        TLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


        // positie Geselecteerde componenten box
        overzichtPaneel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        overzichtPaneel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//        overzichtPaneel.setMaximumSize(new Dimension(800, 400));
        overzichtPaneel.setBorder(BorderFactory.createTitledBorder("Geselecteerde componenten"));

        // Optimaliseren button
        overzichtPaneel.add(Optimaliseren);

        JPanel DBserver = create_category("Databaseserver");
        JPanel Webserver = create_category("Webserver");
        JPanel Firewall = create_category("Firewall");
//        Firewall.setPreferredSize(new Dimension(200, 75));


        // Hier worden componenten toegevoegd
        for (Components components : Components.ALL_COMPONENTS) {
            final var icon = new Icon("icons/server.png", 30, 30, components.name);
            icon.label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    addComponent(components);
                }
            });
            switch (components.type) {
                case WServer -> Webserver.add(icon.get_label(), "wrap");
                case DbServer -> DBserver.add(icon.get_label(), "wrap");
                case PfSense -> Firewall.add(icon.get_label(), "wrap");
            }
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
                totalCost = 0;
                return;
            }
            final var list = totaleComponenten.getSelectedIndices();
            for (int i = list.length - 1; i >= 0; --i)
                totaleComponentenList.remove(list[i]);
            totalCost = 0;
            for (Components components : Components.ALL_COMPONENTS) {
                removeComponent(components);
            }
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

    public DefaultListModel<Components> getTotaleComponentenList() {
        return totaleComponentenList;
    }
}