/**
 * Created by mathias on 23/03/16.
 */
public class DelayComponent implements IComponent {
    protected long delayMicroseconds = 1000;

    public long getDelayMicroseconds() {
        return delayMicroseconds;
    }

    public void setDelayMicroseconds(long delayMicroseconds) throws IllegalArgumentException {
        if (delayMicroseconds < 0) {
            throw new IllegalArgumentException("Delay must be greater than zero");
        }
        this.delayMicroseconds = delayMicroseconds;
    }

    public void setDelayMilliseconds(long delayMilliseconds) throws IllegalArgumentException {
        if (delayMilliseconds < 0) {
            throw new IllegalArgumentException("Delay must be greater than zero");
        }
        this.delayMicroseconds = delayMilliseconds * 1000;
    }

    public void setDelaySeconds(long seconds) throws IllegalArgumentException {
        if (seconds < 0) {
            throw new IllegalArgumentException("Delay must be greater than zero");
        }
        this.delayMicroseconds = seconds * 1000000;
    }

    @Override
    public String toCode(int tabDepth) {
        String code = "";
        String tab = Settings.getTabString(tabDepth);
        long delayMicrosecondsCopy = delayMicroseconds;
        while (delayMicrosecondsCopy >= 4294967295000L) {
            code += tab + "delay(4294967295);\n";
            delayMicrosecondsCopy -= 4294967295000L;
        }
        if (delayMicrosecondsCopy >= 1000) {
            code += tab + "delay(" + (delayMicrosecondsCopy / 1000) + ");\n";
            delayMicrosecondsCopy -= (delayMicrosecondsCopy / 1000) * 1000;
        }
        if (delayMicrosecondsCopy > 0) {
            code += tab + "delayMicroseconds(" + delayMicrosecondsCopy + ");\n";
        }
        return code.substring(0, code.length()-1);
    }
}