/**
 * Created by mathias on 28/03/16
 */
public class Arduino {
    private int numberOfDigitalPins;
    private int numberOfAnalogPins;

    private String name;

    public Arduino(int numberOfAnalogPins, int numberOfDigitalPins, String name) {
        this.numberOfAnalogPins = numberOfAnalogPins;
        this.numberOfDigitalPins = numberOfDigitalPins;
        this.name = name;
    }

    public int getNumberOfAnalogPins() {
        return numberOfAnalogPins;
    }

    public void setNumberOfAnalogPins(int numberOfAnalogPins) {
        this.numberOfAnalogPins = numberOfAnalogPins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfDigitalPins() {
        return numberOfDigitalPins;
    }

    public void setNumberOfDigitalPins(int numberOfDigitalPins) {
        this.numberOfDigitalPins = numberOfDigitalPins;
    }
}
