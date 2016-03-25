import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mathias on 25/03/16.
 */
public class IconMenu {
    HBox menu;

    public IconMenu() {
        menu = new HBox();
        menu.setId("icon-menu");
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
        System.out.println(menu.getChildren().get(0).getId());
        return menu;
    }
}
