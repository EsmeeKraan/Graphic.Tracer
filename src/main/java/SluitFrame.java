//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//import javax.swing.*;
//import javax.swing.Icon;
//
//
//public class SluitFrame extends JFrame {
//
//    private static final JPanel panel = new JPanel();
//    JFrame newFrame = new JFrame();
//
//    public SluitFrame() throws HeadlessException {
//
////        super(newFrame);
//        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                handleClosing();
//            }
//        });
//
//        setSize(480, 320);
//        setLocationRelativeTo(null);
//    }
//
//    private void handleClosing() {
//        if (hasUnsaveData()) {
//            int answer = showWarningMessage();
//
//            switch (answer) {
//                case JOptionPane.YES_OPTION -> {
//                    System.out.println("Opslaan en afsluiten");
//                    dispose();
//                }
//                case JOptionPane.NO_OPTION -> {
//                    System.out.println("Aflsuiten zonder te opslaan");
//                    dispose();
//                }
//                case JOptionPane.CANCEL_OPTION -> System.out.println("Niet afsluiten");
//            }
//        } else {
//            dispose();
//        }
//    }
//
//    private int showWarningMessage() {
//        String[] buttonLabels = new String[] {"Ja", "Nee", "Annuleren"};
//        String defaultOption = buttonLabels[0];
//        Icon icon = null;
//
//        return JOptionPane.showOptionDialog(this,
//                "Er zijn ontwerpen die nog niet zijn opgeslagen\n" +
//                        "Wilt u het afsluiten?",
//                "Waarschuwing",
//                JOptionPane.YES_NO_CANCEL_OPTION,
//                JOptionPane.WARNING_MESSAGE,
//                icon,
//                buttonLabels,
//                defaultOption);
//    }
//
//    private boolean hasUnsaveData() {
//        return true;
//    }
//
//}