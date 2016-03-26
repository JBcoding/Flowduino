public class Case implements ICase {
    protected ICase leftSide;
    protected ICase rightSide;
    protected String operator;

    public Case(ICase leftSide, ICase rightSide, String operator) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.operator = operator;
    }

    public ICase getLeftSide() {
        return leftSide;
    }

    public ICase getRightSide() {
        return rightSide;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toCode() {
        return "(" + leftSide.toCode() + ") " + operator + " (" + rightSide.toCode() + ")";
    }

    public ICase clone() {
        return new Case(leftSide.clone(), rightSide.clone(), new String(operator));
    }
}