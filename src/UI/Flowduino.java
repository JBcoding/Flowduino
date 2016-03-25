import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.*;
import javafx.beans.property.*;
import javafx.beans.*;
import javafx.beans.value.*;

import java.nio.BufferUnderflowException;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class Flowduino extends Application {

    protected Group root;
    protected HBox buttonBox;

    @Override 
    public void start(Stage stage) {
        Document d = new Document();

        TreeView<String> treeView = treeViewFromDocument(d);

        stage.setTitle("Flowduino");

        root = new Group();
        root.getChildren().add(treeView);

        buttonBox = createButtonBox();

        treeView.setPrefHeight(600);
        Scene scene = new Scene(root, 600, 600);
        scene.setFill(Color.LIGHTGREEN);

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                treeView.setPrefHeight(newSceneHeight.intValue() - buttonBox.getHeight());
                treeView.setTranslateY(buttonBox.getHeight());
            }
        });

        createProgramViewFromNode(d.getHead());

        stage.setScene(scene);
        stage.setHeight(600);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void insertDropTargetAtPosWithSize(int x, int y, int width, int height) {
        final Rectangle target = new Rectangle(x, y, width, height);

        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    target.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.setFill(Color.BLACK);

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    System.out.println(db.getString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
                createProgramViewFromNode(null);
            }
        });


        root.getChildren().add(target);
    }

    public void createProgramViewFromNode(Node n) {
        TreeView<String> treeView = null;
        for (Object o : root.getChildren()) {
            if (o.getClass() == TreeView.class) {
                treeView = (TreeView<String>)o;
            }
        }
        root.getChildren().clear();
        root.getChildren().add(treeView);
        root.getChildren().add(buttonBox);
        insertDropTargetAtPosWithSize((int)(Math.random() * 200 + 200), (int)(Math.random() * 400), 100, 100);
    }

    public TreeView<String> treeViewFromDocument(Document d) {
        TreeItem<String> blocks = new TreeItem<String> ("Blocks");
        blocks.setExpanded(true);

        TreeItem<String> commonlyUsed = new TreeItem<String> ("Commonly used");
        commonlyUsed.setExpanded(true);
        String[] commonlyUsedBlocks = new String[] {"If", "Loop", "Delay", "Break", "Statement"};
        for (String s : commonlyUsedBlocks) {
            TreeItem<String> item = new TreeItem<String> (s);
            commonlyUsed.getChildren().add(item);
        }

        TreeItem<String> peripheralsItems = new TreeItem<String> ("Peripherals");
        peripheralsItems.setExpanded(true);
        for (Peripheral p : d.getPeripherals()) {
            TreeItem<String> peripheral = new TreeItem<String> (p.getName());
            peripheral.setExpanded(false);
            for (PeripheralFunction pf : p.getFunctions()) {
                TreeItem<String> item = new TreeItem<String> (pf.getName());
                peripheral.getChildren().add(item);
            }
            peripheralsItems.getChildren().add(peripheral);
        }

        blocks.getChildren().add(commonlyUsed);
        blocks.getChildren().add(peripheralsItems);

        TreeView<String> treeView = new TreeView<String>(blocks);
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                TreeCell<String> treeCell = new TreeCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else if (item != null) {
                            setText(item);
                        }
                    }
                };

                treeCell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (treeCell.getTreeItem().isLeaf()) {
                            /* drag was detected, start drag-and-drop gesture*/
                            System.out.println("onDragDetected");

                            /* allow any transfer mode */
                            Dragboard db = treeCell.startDragAndDrop(TransferMode.COPY);

                            /* put a string on dragboard */
                            ClipboardContent content = new ClipboardContent();
                            content.putString(treeCell.getText());
                            db.setContent(content);

                            mouseEvent.consume();
                        }
                    }
                });

                return treeCell;
            }
        });
        return treeView;
    }

    public HBox createButtonBox() {
        HBox buttonBox = new HBox();
        buttonBox.setId("button-box");
        Button buttonSettings = new Button("Settings");
        Button buttonVariabels = new Button("Variabels");
        Button buttonObjects = new Button("Connections");
        buttonSettings.getStyleClass().add("buttons");
        buttonVariabels.getStyleClass().add("buttons");
        buttonObjects.getStyleClass().add("buttons");
        buttonBox.getChildren().addAll(buttonSettings, buttonVariabels, buttonObjects);
        return buttonBox;
    }
}