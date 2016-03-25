import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mathias on 25/03/16.
 */
public class IconMenu {
    private List<Button> menuButtons;

    public IconMenu(Button ... buttons) {
        menuButtons = new ArrayList<Button>();
        for (Button b : buttons) {
            menuButtons.add(b);
        }
    }

    public void add(Button b) {
        menuButtons.add(b);
    }

    public void remove(Button b) {
        menuButtons.remove(b);
    }

    public HBox getMenu() {
        HBox menu = new HBox();
        for (Button b : menuButtons) {
            if (!b.getStyleClass().contains("icon-menu-button")) {
                b.getStyleClass().add("icon-menu-button");
            }
        }
        menu.getChildren().addAll(menuButtons);
        menu.setId("icon-menu");
        return menu;
    }
}
