import java.util.List;

/**
 * Created by mathias on 28/03/16
 */
public class PeripheralFunctionComponent implements IComponent {
    protected GenericPeripheral component;
    protected PeripheralFunction function;
    protected Variable returnValue;
    protected List<IValues> parameters;

    public PeripheralFunctionComponent(PeripheralFunction function) {
        this.function = function;
    }

    public void setComponent(GenericPeripheral component) {
        this.component = component;
    }

    public void setReturnValue(Variable returnValue) {
        this.returnValue = returnValue;
    }

    public void setParameters(List<IValues> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toCode(int tabDepth) {
        return null;
    }

    @Override
    public IComponent clone() {
        return null;
    }


}
