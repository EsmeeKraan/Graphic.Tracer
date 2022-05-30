import  javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Drag extends MouseInputAdapter {
    private MouseEvent event;

    public void mousePressed(MouseEvent e){
        event = e;
    }

    public void mouseDragged(MouseEvent e){
        Component component = e.getComponent();
        Point point = component.getLocation();
        int x = point.x -  event.getX() + e.getX();

        int y = point.y -  event.getY() + e.getY();
        component.setLocation(x,y);
    }
}
