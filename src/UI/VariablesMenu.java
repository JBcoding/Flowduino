import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

/**
 * Created by mathias on 27/03/16
 */
public class VariablesMenu extends Menu {
    private Scene scene;
    private AnchorPane root;
    ScrollPane scrollPane;
    private VBox vbox;
    private List<ComboBox> comboBoxes;
    private List<TextField> varNames;
    private List<TextField> varValues;
    private List<Button> deleteButtons;
    Variables vars;

    public VariablesMenu(Variables vars) {
        running = true;
        this.vars = vars;
        root = new AnchorPane();
        scene = new Scene(root, 500, 200);
        scrollPane = new ScrollPane();
        setTitle("Variables");
        vbox = new VBox();

        comboBoxes = new ArrayList<ComboBox>();
        varNames = new ArrayList<TextField>();
        varValues = new ArrayList<TextField>();
        deleteButtons = new ArrayList<Button>();

        vars.getVariables().forEach(this::addVariableToMenu);

        Button addButton = new Button("Add new variable");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addVariableToMenu(new Variable("Variable", "String"));
            }
        });
        Button okButton = new Button("OK");

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Variable> newVars = new ArrayList<Variable>();
                for (int i = 0; i < comboBoxes.size(); i++) {
                    newVars.add(new Variable(varNames.get(i).getText(), comboBoxes.get(i).getValue().toString(), varValues.get(i).getText() == null ? "" : varValues.get(i).getText()));
                }
                vars.setVariables(newVars);
                running = false;
                close();
            }
        });

        root.getChildren().addAll(okButton, addButton);
        okButton.setPrefWidth(45);
        okButton.setPrefHeight(30);
        root.setBottomAnchor(okButton, 0.0);
        root.setRightAnchor(okButton, 0.0);
        root.setBottomAnchor(addButton, 0.0);
        root.setLeftAnchor(addButton, 0.0);

        scrollPane.setContent(vbox);
        scrollPane.setPrefHeight(100);
        root.setTopAnchor(scrollPane, 0.0);
        root.setBottomAnchor(scrollPane, 32.0);
        root.setLeftAnchor(scrollPane, 0.0);
        root.setRightAnchor(scrollPane, 0.0);
        root.getChildren().add(scrollPane);
        scrollPane.getStyleClass().add("variable-menu-pane");
        root.getStyleClass().add("variable-menu-root");
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

    protected void addVariableToMenu(Variable v) {
        HBox varBox = new HBox();
        ObservableList<String> variableTypes = FXCollections.observableArrayList(
                "String",
                "int",
                "long",
                "float",
                "double"
        );

        final ComboBox comboBox = new ComboBox(variableTypes);
        String varType = v.getType();
        switch (varType) {
            case "String":
                comboBox.setValue(variableTypes.get(0));
                break;
            case "int":
                comboBox.setValue(variableTypes.get(1));
                break;
            case "long":
                comboBox.setValue(variableTypes.get(2));

                break;
        }

        Button deleteButton = new Button("x");
        deleteButton.getStyleClass().add("variable-delete-button");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = deleteButtons.indexOf(deleteButton);
                deleteButtons.remove(index);
                varNames.remove(index);
                comboBoxes.remove(index);
                varValues.remove(index);
                vbox.getChildren().remove(index);
            }
        });

        TextField name = new TextField(v.getName());
        TextField value = new TextField(v.getStartValue());

        name.getStyleClass().add("variable-name-box");
        value.getStyleClass().add("variable-value-box");
        comboBox.getStyleClass().add("variable-type-combobox");

        comboBoxes.add(comboBox);
        varNames.add(name);
        varValues.add(value);
        deleteButtons.add(deleteButton);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(comboBox, name, value, deleteButton);
        vbox.getChildren().add(hBox);

    }
}
