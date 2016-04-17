/**
 * Created by mathias on 23/03/16.
 */
public class Variable implements IValues {

    protected String name;
    protected String type;
    protected String startValue;

    public Variable(String name) {
        this.name = name;
    }

    public Variable(String name, String type) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        this.name = name;
        this.type = type;
    }

    public Variable(String name, String type, String startValue) throws IllegalArgumentException {
        this(name, type);
        if (startValue == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (startValue.equals("")) {
            startValue = null;
        }
        this.startValue = startValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException { 
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        this.type = type;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }



    @Override
    public String toCode() {
        return "FV_" + name;
    }

    public String getInitCode() {
        if (startValue == null) {
            return type + " FV_" + name + ";";
        }
        if (type.equals("string")) {
            return type + " FV_" + name + " = \"" + startValue + "\";";
        } else if (type.equals("char")) {
            return type + " FV_" + name + " = '" + startValue + "';";
        }
        return type + " FV_" + name + " = " + startValue + ";";
    }

    @Override
    public ICase clone() {
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == String.class) {
            return name.equals(other);
        }
        return name.equals(((Variable)other).name);
    }
}
