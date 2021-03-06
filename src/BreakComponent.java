/**
 * Created by mathias on 25/03/16.
 */
public class BreakComponent implements IComponent {
    @Override
    public String toCode(int tabDepth) {
        return Settings.getTabString(tabDepth) + "break;";
    }

    @Override
    public IComponent clone() {
        return new BreakComponent();
    }
}
