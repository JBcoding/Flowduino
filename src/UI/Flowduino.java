import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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

import java.awt.*;
import java.nio.BufferUnderflowException;
import java.util.HashMap;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class Flowduino extends Application {

    protected Group root;
    protected VBox buttonBox;
    protected Document d = new Document();
    protected Button saveButton;
    protected Button openButton;
    protected IconMenu topBar;
    protected Pane programView;
    protected ScrollPane scrollPane;

    protected HashMap<Rectangle, Node> targetNodeMap = new HashMap<>();
    protected HashMap<Rectangle, Boolean> targetFirstMap = new HashMap<>();

    @Override 
    public void start(Stage stage) {

        TreeView<String> treeView = treeViewFromDocument(d);

        stage.setTitle("Flowduino");

        root = new Group();
        root.getChildren().add(treeView);

        buttonBox = createButtonBox();

        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("style.css");
        scene.setFill(Color.LIGHTGREEN);


        saveButton = new Button();
        saveButton.setId("save-button");

        openButton = new Button();
        openButton.setId("open-button");

        topBar = new IconMenu(saveButton, openButton);

        programView = new Pane();
        programView.setId("program-view");
        scrollPane = new ScrollPane();
        scrollPane.setContent(programView);

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                treeView.setPrefHeight(newSceneHeight.intValue() - buttonBox.getHeight() - topBar.getMenu().getHeight());
                treeView.setTranslateY(buttonBox.getHeight() + topBar.getMenu().getHeight());
                buttonBox.setTranslateY(topBar.getMenu().getHeight());
                scrollPane.setTranslateY(topBar.getMenu().getHeight());
                scrollPane.setMaxHeight(Math.max(1, newSceneHeight.doubleValue() - topBar.getMenu().getHeight()));
                scrollPane.setMinHeight(Math.max(1, newSceneHeight.doubleValue() - topBar.getMenu().getHeight()));
                scrollPane.setTranslateX(treeView.getWidth());
            }
        });
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHWidth, Number newSceneWidth) {
                topBar.getMenu().setMinWidth(newSceneWidth.doubleValue());
                scrollPane.setTranslateX(treeView.getWidth());
                scrollPane.setMaxWidth(Math.max(1, newSceneWidth.doubleValue() - treeView.getWidth()));
                scrollPane.setMinWidth(Math.max(1, newSceneWidth.doubleValue() - treeView.getWidth()));
            }
        });



        createProgramViewFromNode(d.getHead());

        stage.setScene(scene);
        stage.setHeight(601);
        stage.setWidth(601);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Rectangle insertDropTargetAtPosWithSize(int x, int y, int width, int height) {
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

                Node n = targetNodeMap.get(target);
                boolean b = targetFirstMap.get(target);
                IComponent newComponent = null;
                if (db.getString().equals("Commonly used - Delay")) {
                    newComponent = new DelayComponent();
                } else if (db.getString().equals("Commonly used - Break")) {
                    newComponent = new BreakComponent();
                } else if (db.getString().equals("Commonly used - Statement")) {
                    newComponent = new StatementComponent(d.getVariables());
                } else if (db.getString().equals("Commonly used - Loop")) {
                    ForLoop forLoop = new ForLoop(new Variable("i", "int"), new Constant("0"), new Constant("4"), new Constant("1"));
                    forLoop.setHeadOfContent(new Node());
                    newComponent = forLoop;
                }
                if (b) {
                    Node newNode = new Node(n.getComponent(), n.getNext());
                    n.setComponent(newComponent);
                    n.setNext(newNode);
                } else {
                    n.setNext(new Node(newComponent, n.getNext()));
                }

                createProgramViewFromNode(d.getHead());
            }
        });

        programView.getChildren().add(target);

        return target;
    }

    public void createProgramViewFromNode(Node n) {
        TreeView<String> treeView = null;
        for (Object o : root.getChildren()) {
            if (o.getClass() == TreeView.class) {
                treeView = (TreeView<String>)o;
            }
        }
        root.getChildren().clear();
        programView.getChildren().clear();
        targetNodeMap = new HashMap<>();
        targetFirstMap = new HashMap<>();
        root.getChildren().add(treeView);
        root.getChildren().add(buttonBox);
        root.getChildren().add(topBar.getMenu());
        root.getChildren().add(scrollPane);
        maxX = 0;
        maxY = 0;
        createProgramViewFromNodeRecursively(n, 25, 100, true);

        System.out.println("----------------------------");
        System.out.println(n.getProgramCode(0));
        System.out.println("----------------------------");
    }

    private int maxX;
    private int maxY;
    public Point createProgramViewFromNodeRecursively(Node n, int x, int y, boolean first) {
        if (first) {
            Rectangle r = insertDropTargetAtPosWithSize(x, y - 75, 50, 50);
            targetNodeMap.put(r, n);
            targetFirstMap.put(r, true);
        }
        if (n.getComponent() == null) {
            double newHeight = Math.max(maxY + 75, scrollPane.getHeight() - 2);
            programView.setMinHeight(newHeight);
            if (newHeight > scrollPane.getHeight()) {
                programView.setMinWidth(Math.max(maxX + 75, scrollPane.getWidth() - 12));
            } else {
                programView.setMinWidth(Math.max(maxX + 75, scrollPane.getWidth() - 2));
            }
            return new Point(x, y + 75);
        }
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
        if (n.getComponent().getClass() == DelayComponent.class) {
            // draw delay
        } else if (n.getComponent().getClass() == BreakComponent.class) {
            // draw break
        } else if (n.getComponent().getClass() == StatementComponent.class) {
            // draw statement
        } else if (n.getComponent().getClass() == ForLoop.class || n.getComponent().getClass() == WhileLoop.class) {
            // draw loop
            // draw extra targets
            Loop loop = (Loop)n.getComponent();

            Point loopSize = createProgramViewFromNodeRecursively(loop.getHeadOfContent(), x, y + 75, true);
        } else if (n.getComponent().getClass() == IfComponent.class) {
            // draw if
        }
        // make target for after
        Rectangle r = insertDropTargetAtPosWithSize(x, y, 50, 50);
        targetNodeMap.put(r, n);
        targetFirstMap.put(r, false);
        if (n.getNext() != null) {
            Point temp = createProgramViewFromNodeRecursively(n.getNext(), x, y + 75, false);
            x = temp.x;
            y = temp.y;
        }
        return new Point(x, y + 75);
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

                            /* allow any transfer mode */
                            Dragboard db = treeCell.startDragAndDrop(TransferMode.COPY);

                            /* put a string on dragboard */
                            ClipboardContent content = new ClipboardContent();
                            content.putString(treeCell.getTreeItem().getParent().getValue() + " - " + treeCell.getText());
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

    public VBox createButtonBox() {
        VBox buttonBox = new VBox();
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