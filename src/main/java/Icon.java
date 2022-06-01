import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Icon extends MouseAdapter {
    ImageIcon icon;
    JLabel label;

    public Icon(ImageIcon icon, int w, int h, String txt)
    {
//        icon = new ImageIcon(getClass().getResource(path));
        this.icon = new ImageIcon(icon.getImage().getScaledInstance(w, h, 1));

        label = new JLabel(icon, SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.TOP);

        label.setText(txt);
        label.setFont(new Font("Inter", Font.PLAIN, 12));
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setVerticalAlignment(JLabel.TOP);


    }

    public JLabel get_label()
    {
        return label;
    }

    public ImageIcon get_icon()
    {
        return icon;
    }

}

////    public void drag() {
////        label.addMouseMotionListener(this);
////        label.addMouseListener(this);
////    }
//
//    @Override
////    public void mouseDragged(MouseEvent e) {
////        JOptionPane.showMessageDialog(null, "hoi");
////    }
//}
