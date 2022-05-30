import java.util.ArrayList;

public class Components {

    public static int price;
    public static double availability;
    public static String name;


    public Components(String name, double availability, int price, ComponentSpecies serverType) {
        this.name = name;
        this.availability = availability;
        this.price = price;
    }

    private static final ArrayList<Components> Components = new ArrayList<>();

    static {
        //toevoegen van database servers
        Components.add(new Components("HAL9001DB", 90.000, 5100, ComponentSpecies.DbServer));
        Components.add(new Components("HAL9002DB", 95.000, 7700, ComponentSpecies.DbServer));
        Components.add(new Components("HAL9003DB", 99.000, 9800, ComponentSpecies.DbServer));
        //toevoegen van webservers
        Components.add(new Components("HAL9001W", 80.000, 2200, ComponentSpecies.WServer));
        Components.add(new Components("HAL9002W", 90.000, 3200, ComponentSpecies.WServer));
        Components.add(new Components("HAL9003W", 95.000, 5100, ComponentSpecies.WServer));
        //toevoegen van pfsense
        Components.add(new Components("pfSense", 99.998, 4000, ComponentSpecies.PfSense));
    }
}


