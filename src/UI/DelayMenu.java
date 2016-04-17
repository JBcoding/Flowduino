import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.im.spi.InputMethodDescriptor;
import java.util.ArrayList;
import java.util.List;

public class DelayMenu extends Menu {
    private Scene scene;
    private AnchorPane root;
    private VBox vbox;
    private TextField delayText;
    private ComboBox<String> delayUnit;
    private Button okButton;
    private Button cancelButton;
    private String[] options = new String[]{"Microsecond(s)", "Millisecond(s)", "Second(s)"};

    public DelayMenu(DelayComponent delay) {
        running = true;
        root = new AnchorPane();
        scene = new Scene(root, 240, 80);
        this.setMaxWidth(240);
        this.setMaxHeight(80);
        this.setMinWidth(240);
        this.setMinHeight(80);
        setTitle("Delay");
        vbox = new VBox();

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                running = false;
                close();
            }
        });

        okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                running = false;
                if (delayUnit.getValue().equals(options[0])) {
                    delay.setDelayMicroseconds(Integer.parseInt(delayText.getText()));
                } else if (delayUnit.getValue().equals(options[1])) {
                    delay.setDelayMilliseconds(Integer.parseInt(delayText.getText()));
                } else if (delayUnit.getValue().equals(options[2])) {
                    delay.setDelaySeconds(Integer.parseInt(delayText.getText()));
                }
                close();
            }
        });

        delayUnit = new ComboBox<>();
        delayUnit.getItems().addAll(options);
        delayUnit.setValue(options[0]);
        long delayTime = delay.getDelayMicroseconds();
        if (delayTime % 1000 == 0) {
            delayTime /= 1000;
            delayUnit.setValue(options[1]);
        }
        if (delayTime % 1000 == 0) {
            delayTime /= 1000;
            delayUnit.setValue(options[2]);
        }
        delayText = new TextField("" + delayTime);
        delayText.setAlignment(Pos.BASELINE_RIGHT);
        delayText.setPrefWidth(100);

        root.getChildren().addAll(okButton, cancelButton, delayUnit, delayText);
        okButton.setPrefWidth(70);
        okButton.setPrefHeight(30);
        cancelButton.setPrefHeight(30);
        root.setBottomAnchor(okButton, 0.0);
        root.setLeftAnchor(okButton, 0.0);
        root.setBottomAnchor(cancelButton, 0.0);
        root.setRightAnchor(cancelButton, 0.0);
        root.setTopAnchor(delayUnit, 0.0);
        root.setRightAnchor(delayUnit, 0.0);
        root.setTopAnchor(delayText, 0.0);
        root.setLeftAnchor(delayText, 0.0);

        root.getStyleClass().add("delay-menu-root");
        scene.getStylesheets().add("style.css");

        setScene(scene);
        scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent ev) {
                running = false;
            }
        });

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.getCode() == KeyCode.ESCAPE)) {
                running = false;
                close();
            }
        });
    }
}
