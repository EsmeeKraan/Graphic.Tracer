import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class OptimalizeDialoog extends JDialog {

    private JTextField JTbeschikbaarheid;
    private JTextField JTkosten;

    public OptimalizeDialoog(JFrame frame) {
        super(frame, true);
        setSize(350, 100);
        setLayout(new GridLayout(0,2));
        setTitle("Opgeslagen gegevens");

        add(new JLabel("Beschikbaarheid: "));
        JTbeschikbaarheid = new JTextField(6);
        add(JTbeschikbaarheid);

        add(new JLabel("Kosten: €"));
        JTkosten = new JTextField(6);
        add(JTkosten);

        setVisible(true);
    }
}
