import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Node;
import javafx.scene.control.*;
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

public class StatementMenu extends Menu {
    private Scene scene;
    private AnchorPane root;
    private VBox vbox;
    private TextArea statementText;
    private Button okButton;
    private Button cancelButton;

    public StatementMenu(StatementComponent statement) {
        running = true;
        root = new AnchorPane();
        scene = new Scene(root, 240, 200);
        this.setMinWidth(240);
        this.setMinHeight(200);
        setTitle("Statement");
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
                try {
                    statement.setValue(statementText.getText());
                    close();
                } catch (MalformedExpressionException e) {
                    AlertBox.info("Invalid statement", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });

        statementText = new TextArea(statement.getValue());

        root.getChildren().addAll(okButton, cancelButton, statementText);
        okButton.setPrefWidth(70);
        okButton.setPrefHeight(30);
        cancelButton.setPrefHeight(30);
        root.setBottomAnchor(okButton, 0.0);
        root.setLeftAnchor(okButton, 0.0);
        root.setBottomAnchor(cancelButton, 0.0);
        root.setRightAnchor(cancelButton, 0.0);
        root.setTopAnchor(statementText, 0.0);
        root.setLeftAnchor(statementText, 0.0);
        root.setRightAnchor(statementText, 0.0);
        root.setBottomAnchor(statementText, 35.0);

        root.getStyleClass().add("statement-menu-root");
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
