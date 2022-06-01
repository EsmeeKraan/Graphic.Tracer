import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class Components {

    public transient ImageIcon icon;
    public int price;
    public double availability;
    public String name;
    public ComponentSpecies type;
    public static ImageIcon normaleIcon;
    public static ImageIcon firewall;


    public Components(ImageIcon imageIcon, String name, double availability, int price, ComponentSpecies serverType) {
        this.icon = imageIcon;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.type = serverType;
    }

    public static final ArrayList<Components> ALL_COMPONENTS = new ArrayList<>();

    static {
        normaleIcon = new ImageIcon(Objects.requireNonNull(Components.class.getResource("icons/server.png")));
        normaleIcon.setImage(normaleIcon.getImage().getScaledInstance(30, 30, 1));

        firewall = new ImageIcon(Objects.requireNonNull(Components.class.getResource("icons/firewallserver.png")));
        firewall.setImage(firewall.getImage().getScaledInstance(30, 30, 1));


        //toevoegen van database servers
        ALL_COMPONENTS.add(new Components(normaleIcon, "HAL9001DB", 90.000, 5100, ComponentSpecies.DbServer));
        ALL_COMPONENTS.add(new Components(normaleIcon, "HAL9002DB", 95.000, 7700, ComponentSpecies.DbServer));
        ALL_COMPONENTS.add(new Components(normaleIcon, "HAL9003DB", 99.000, 9800, ComponentSpecies.DbServer));
        //toevoegen van webservers
        ALL_COMPONENTS.add(new Components(normaleIcon, "HAL9001W", 80.000, 2200, ComponentSpecies.WServer));
        ALL_COMPONENTS.add(new Components(normaleIcon, "HAL9002W", 90.000, 3200, ComponentSpecies.WServer));
        ALL_COMPONENTS.add(new Components(normaleIcon, "HAL9003W", 95.000, 5100, ComponentSpecies.WServer));
        //toevoegen van pfsense
        ALL_COMPONENTS.add(new Components(firewall, "pfSense", 99.998, 4000, ComponentSpecies.PfSense));
    }
}


