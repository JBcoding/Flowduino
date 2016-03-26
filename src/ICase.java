import java.io.Serializable;

public interface ICase extends Serializable {
    String toCode();

    ICase clone();
}