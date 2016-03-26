public class WhileLoop extends Loop {

    protected ICase condition;

    public WhileLoop(ICase condition) {
        this.condition = condition;
    }

    public ICase getCondition() {
        return condition;
    }

    public void setCondition(ICase condition) {
        this.condition = condition;
    }

    @Override
    public String toCode(int tabDepth) {
        String code = "";
        String tab = Settings.getTabString(tabDepth);
        code = tab + "while (" + condition.toCode() + ") {\n";
        code += headOfContent.getProgramCode(tabDepth + Settings.getTabDepth());
        code += "\n" + tab + "}";
        return code;
    }

    @Override
    public IComponent clone() {
        WhileLoop wl = new WhileLoop(condition.clone());
        wl.setHeadOfContent(headOfContent.clone());
        return wl;
    }
}