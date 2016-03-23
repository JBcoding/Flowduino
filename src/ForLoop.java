/**
 * Created by mathias on 23/03/16.
 */
public class ForLoop extends Loop {
    protected IValues startValue;
    protected IValues endValue;
    protected IValues step;
    protected Variable counter;

    public ForLoop(Variable counter, IValues endValue) {

    }

    public ForLoop(Variable counter, IValues endValue, IValues step) {

    }

    public ForLoop(Variable counter, IValues startValue,IValues endValue, IValues step) {

    }

    public IValues getStartValue() {
        return null;
    }

    public void setStartValue(IValues startValue) {

    }

    public IValues getEndValue() {
        return null;
    }

    public void setEndValue(IValues endValue) {

    }

    public IValues getStep() {
        return null;
    }

    public void setStep(IValues step) {

    }

    public Variable getCounter() {
        return null;
    }

    public void setCounter(Variable counter) {

    }

    @Override
    public String toCode(int tabDepth) {
        return null;
    }
}
