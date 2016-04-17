import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
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

public class IfMenu extends Menu {
    private Scene scene;
    private AnchorPane root;
    private VBox vbox;
    private List<ICase> cases;
    private ScrollPane scrollPane;
    private Button okButton;
    private Button cancelButton;
    private CheckBox elseCheckBox;

    private Variables v;

    private IfComponent ifff;

    public IfMenu(IfComponent iff, Variables v) {
        running = true;

        this.v = v;

        ifff = (IfComponent)iff.clone();

        root = new AnchorPane();
        scene = new Scene(root, 446, 200);
        this.setMinWidth(446);
        this.setMaxWidth(446);
        this.setMinHeight(200);
        setTitle("If case");
        vbox = new VBox();

        Button addButton = new Button("add else if");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (iff.getHeadOfContents().size() > iff.getCases().size()) {
                    ifff.getHeadOfContents().add(ifff.getHeadOfContents().size() - 1, new Node());
                } else {
                    ifff.getHeadOfContents().add(new Node());
                }
                addCase(true, new Case(new Constant(1), new Constant(2), "<="));
            }
        });

        scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setPrefHeight(100);

        elseCheckBox = new CheckBox("else?");
        HBox hBox = new HBox(30);
        hBox.getChildren().addAll(addButton, elseCheckBox);
        vbox.getChildren().add(hBox);

        cases = new ArrayList<>();

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
                for (int i = 0; i < vbox.getChildren().size() - 1; i ++) {
                    HBox currentBox = (HBox)vbox.getChildren().get(i);
                    IValues leftSide;
                    IValues rightSide;
                    String leftSideString = ((ComboBox<IValues>)currentBox.getChildren().get(1)).getEditor().getText();
                    String rightSideString = ((ComboBox<IValues>)currentBox.getChildren().get(3)).getEditor().getText();
                    int indexLeft = v.getVariables().indexOf(new Variable(leftSideString));
                    int indexRight = v.getVariables().indexOf(new Variable(rightSideString));
                    if (indexLeft != -1) {
                        leftSide = v.getVariables().get(indexLeft);
                    } else {
                        leftSide = new Constant(leftSideString);
                    }
                    if (indexRight != -1) {
                        rightSide = v.getVariables().get(indexRight);
                    } else {
                        rightSide = new Constant(rightSideString);
                    }
                    Case casee = new Case(leftSide, rightSide, ((ComboBox<String>)currentBox.getChildren().get(2)).getValue());
                    cases.add(casee);
                }
                int nodesNeeded = cases.size();
                if (elseCheckBox.isSelected()) {
                    nodesNeeded ++;
                }
                if (ifff.getHeadOfContents().size() > nodesNeeded) {
                    while (ifff.getHeadOfContents().size() != nodesNeeded) {
                        ifff.getHeadOfContents().remove(ifff.getHeadOfContents().size() - 1);
                    }
                } else if (ifff.getHeadOfContents().size() < nodesNeeded) {
                    while (ifff.getHeadOfContents().size() != nodesNeeded) {
                        ifff.getHeadOfContents().add(new Node());
                    }
                }
                ifff.setCases(cases);
                iff.setCases(ifff.getCases());
                iff.setHeadOfContents(ifff.getHeadOfContents());
                close();
            }
        });

        root.getChildren().addAll(okButton, cancelButton, scrollPane);
        okButton.setPrefWidth(70);
        okButton.setPrefHeight(30);
        cancelButton.setPrefHeight(30);
        root.setBottomAnchor(okButton, 0.0);
        root.setLeftAnchor(okButton, 0.0);
        root.setBottomAnchor(cancelButton, 0.0);
        root.setRightAnchor(cancelButton, 0.0);
        root.setRightAnchor(scrollPane, 0.0);
        root.setLeftAnchor(scrollPane, 0.0);
        root.setBottomAnchor(scrollPane, 35.0);
        root.setTopAnchor(scrollPane, 0.0);

        if (iff.getHeadOfContents().size() > iff.getCases().size()) {
            elseCheckBox.setSelected(true);
        }
        boolean notFirst = false;
        for (ICase casee : iff.getCases()) {
            addCase(notFirst, (Case)casee);
            notFirst = true;
        }


        root.getStyleClass().add("if-menu-root");
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

    public void addCase(boolean elseif, Case casee) {
        HBox hBox = new HBox(10);

        Label label = new Label(((elseif) ? " else if" : " if"));
        label.setPrefWidth(50);
        hBox.getChildren().add(label);

        ComboBox<IValues> leftSide = new ComboBox<>();
        leftSide.setEditable(true);
        leftSide.setPrefWidth(125);
        leftSide.getItems().addAll(v.getVariables());
        leftSide.setValue((IValues)casee.getLeftSide());
        hBox.getChildren().add(leftSide);

        ComboBox<String> operatorCombobox = new ComboBox<>();
        operatorCombobox.getItems().addAll("==", "!=", ">=", "<=", ">", "<");
        operatorCombobox.setValue(casee.getOperator());

        hBox.getChildren().add(operatorCombobox);

        ComboBox<IValues> rightSide = new ComboBox<>();
        rightSide.setEditable(true);
        rightSide.setPrefWidth(leftSide.getPrefWidth());
        rightSide.getItems().addAll(v.getVariables());
        rightSide.setValue((IValues)casee.getRightSide());
        hBox.getChildren().add(rightSide);

        if (elseif) {
            Button minusButton = new Button("X");
            minusButton.setPrefHeight(20);
            minusButton.setPrefWidth(20);
            hBox.getChildren().add(minusButton);
            minusButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int index = vbox.getChildren().indexOf(hBox);
                    ifff.getHeadOfContents().remove(index);
                    hBox.getChildren().clear();
                    vbox.getChildren().remove(hBox);
                }
            });
        }

        vbox.getChildren().add(vbox.getChildren().size() - 1, hBox);
    }
}
