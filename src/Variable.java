/**
 * Created by mathias on 23/03/16.
 */
public class Variable implements IValues {

    protected String name;
    protected String type;
    protected String startValue;

    public Variable(String name, String type) {}
    public Variable(String name, String type, String startValue) {}

    public String getName() {
        return null;
    }

    public void setName(String name) {

    }

    public String getType() {
        return null;
    }

    public void setType(String type) {

    }

    public String getStartValue() {
        return null;
    }

    public void setStartValue(String startValue) {

    }



    @Override
    public String toCode() {
        return null;
    }

    public String getInitCode() {
        return null;
    }
}
