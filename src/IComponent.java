import java.io.Serializable;

/**
 * Created by mathias on 23/03/16.
 */
public interface IComponent extends Serializable {
    String toCode(int tabDepth);
}
