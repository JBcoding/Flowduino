public class Case implements ICase {
    protected ICase leftSide;
    protected ICase rightSide;
    protected String operator;

    public Case(ICase leftSide, ICase rightSide, String operator) {

    }

    public ICase getLeftSide() {
        return null;
    }

    public ICase getRightSide() {
        return null;
    }

    public ICase getOperator() {
        return null;
    }

    @Override
    public String toCode() {
        return null;
    }
}