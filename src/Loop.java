/**
 * Created by mathias on 23/03/16.
 */
public abstract class Loop implements IComponent{
    protected Node headOfContent;

    public void setHeadOfContent(Node headOfContent) {

    }

    public Node getHeadOfContent() {
        return null;
    }

    @Override
    public String toCode(int tabDepth) {
        return getHeadOfContent().getProgramCode(tabDepth + Settings.getTabDepth());
    }
}
