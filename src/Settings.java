/**
 * Created by mathias on 23/03/16.
 */
public final class Settings {
    public static int getTabDepth() {
        return 2;
    }

    public static String getTabString(int tabDepth) {
        String tab = "";
        for (int i = 0; i < tabDepth; i ++) {
            tab += " ";
        }
        return tab;
    }
}
