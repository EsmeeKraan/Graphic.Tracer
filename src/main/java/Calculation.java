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
        totalPrice =0;
    }

    // Berekening van de totale prijs per jaar
    // hier returnt hij de beschikbaarheid per toegevoegde component
    public double getAvailability (Components[]components){
        if (components == null) {
            return 0;
        }

        double availability = 1.0;

        for (Components component : components) {
            availability *= (1 - component.availability);
        }

        return (int) (1 - availability);

//        totalAvailability = ((1-dbAvailability)*(1-wAvailability)*pfSenseAvailability);
    }
}

