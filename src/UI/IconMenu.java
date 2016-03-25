import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mathias on 25/03/16.
 */
public class IconMenu {
    HBox menu;

    public IconMenu(Button ... buttons) {
        menu = new HBox();
        menu.setSpacing(5.0);
        menu.setId("icon-menu");
        for (Button b : buttons) {
            add(b);
        }
    }

    public void add(Button b) {
        if (!b.getStyleClass().contains("icon-menu-button")) {
            b.getStyleClass().add("icon-menu-button");
        }
        menu.getChildren().add(b);
    }

    public void remove(Button b) {
        b.getStyleClass().remove(b);
        menu.getChildren().remove(b);
    }

    public HBox getMenu() {
        return menu;
    }
}