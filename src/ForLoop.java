/**
 * Created by mathias on 23/03/16.
 */
public class ForLoop extends Loop {
    protected IValues startValue;
    protected IValues endValue;
    protected IValues step;
    protected Variable counter;

    public ForLoop(Variable counter, IValues endValue) throws IllegalArgumentException {
        this(counter, endValue, new Constant(1));
    }

    public ForLoop(Variable counter, IValues endValue, IValues step) throws IllegalArgumentException {
        this(counter, new Constant(0), endValue, step);
    }

    public ForLoop(Variable counter, IValues startValue, IValues endValue, IValues step) throws IllegalArgumentException {
        if (counter == null) {
            throw new IllegalArgumentException("Counter cannot be null");
        }
        if (startValue == null) {
            throw new IllegalArgumentException("StartValue cannot be null");
        }
        if (endValue == null) {
            throw new IllegalArgumentException("EndValue cannot be null");
        }
        if (step == null) {
            throw new IllegalArgumentException("StepValue cannot be null");
        }
        this.counter = counter;
        this.startValue = startValue;
        this.endValue = endValue;
        this.step = step;
    }

    public IValues getStartValue() {
        return startValue;
    }

    public void setStartValue(IValues startValue) {
        this.startValue = startValue;
    }

    public IValues getEndValue() {
        return endValue;
    }

    public void setEndValue(IValues endValue) {
        this.endValue = endValue;
    }

    public IValues getStep() {
        return step;
    }

    public void setStep(IValues step) {
        this.step = step;
    }

    public Variable getCounter() {
        return counter;
    }

    public void setCounter(Variable counter) {
        this.counter = counter;
    }

    @Override
    public String toCode(int tabDepth) {
        String code = "";
        String tab = Settings.getTabString(tabDepth);
        code = tab + "for (" + counter.getName() + " = " + startValue.toCode() + "; " + counter.getName() + " < " + endValue.toCode() + "; " + counter.getName() + " += " + step.toCode() + ") {\n";
        code += headOfContent.getProgramCode(tabDepth + Settings.getTabDepth());
        code += "\n" + tab + "}";
        return code;
    }
}
