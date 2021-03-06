public abstract class Loop implements IComponent {
    protected Node headOfContent;

    public void setHeadOfContent(Node headOfContent) {
        this.headOfContent = headOfContent;
    }

    public Node getHeadOfContent() {
        return headOfContent;
    }

    public boolean isNodeSubNode(Node n) {
        return headOfContent.isNodeSubNode(n);
    }

    public abstract IComponent clone();
}
