import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Calculation {

    // beschikbare componenten(servers)
    private List<Components> webComponents;
    private List<Components> databaseComponents;

    // gebruikt voor de berekening
    private transient int totalPrice;
    private transient double totalAvailability;
    private transient double dbAvailability;
    private transient double wAvailability;
    private transient double pfSenseAvailability;

    public Calculation() {
        webComponents = new ArrayList<>();
        databaseComponents = new ArrayList<>();
    }

    // Berekening van de totale prijs per jaar
    totalPrice =0;

    private int getPrice(Component[] components) {
        if (components == null) {
            return 999999999;
        }

        for (Component component : components) {
            totalPrice += Components.price;
        }

        return totalPrice;


        // hier returnt hij de beschikbaarheid per toegevoegde component
        public static double getAvailability (Components[]components){
            if (components == null) {
                return 0;
            }

            double availability = 1.0;

            for (Component component : components) {
                availability *= (1 - Components.availability);
            }

            return (int) (1 - availability);

            totalAvailability = ((1-dbAvailability)*(1-wAvailability)*pfSenseAvailability);
        }
    }
}

