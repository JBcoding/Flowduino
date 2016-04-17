import java.awt.datatransfer.Clipboard;

/**
 * Created by mathias on 23/03/16.
 */
public class Constant implements IValues {

    protected String value;

    public Constant(Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Argument must not be null");
        } else if (value.toString() == "") {
            throw new IllegalArgumentException("Argument must not be empty");
        }
        this.value = value.toString();
    }

    @Override
    public String toCode() {
        return value;
    }

    @Override
    public ICase clone() {
        return new Constant(new String(value));
    }

    @Override
    public String toString() {
        return value;
    }
}
