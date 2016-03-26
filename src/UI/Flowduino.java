import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.WindowEvent;
import javafx.util.*;
import javafx.beans.value.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class Flowduino extends Application {

    protected Group root;
    protected VBox buttonBox;
    protected Document d = new Document();
    protected Button saveButton;
    protected Button saveAsButton;
    protected Button openButton;
    protected Button newDocumentButton;
    protected Button runButton;
    protected IconMenu topBar;
    protected Pane programView;
    protected ScrollPane scrollPane;
    protected Stage stage;

    protected HashMap<Rectangle, Node> targetNodeMap = new HashMap<>();
    protected HashMap<Rectangle, Boolean> targetFirstMap = new HashMap<>();

    @Override 
    public void start(Stage stage) {
        this.stage = stage;
        TreeView<String> treeView = treeViewFromDocument(d);

        stage.setTitle("Flowduino");

        root = new Group();
        root.getChildren().add(treeView);

        buttonBox = createButtonBox();

        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("style.css");
        scene.setFill(Color.valueOf("#3c3f41"));



        final ContextMenu contextMenu = new ContextMenu();
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        contextMenu.getItems().addAll(cut, copy, paste);
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Cut...");
            }
        });

        saveButton = new Button();
        saveButton.setId("save-button");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveFile();
            }
        });
        saveButton.setTooltip(new Tooltip("Save file"));
        saveButton.setContextMenu(contextMenu);

        saveAsButton = new Button();
        saveAsButton.setId("save-as-button");
        saveAsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveAs();
            }
        });
        saveAsButton.setTooltip(new Tooltip("Save As"));


        newDocumentButton = new Button();
        newDocumentButton.setId("new-document-button");
        newDocumentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newFile();
            }
        });
        newDocumentButton.setTooltip(new Tooltip("New Document"));


        openButton = new Button();
        openButton.setId("open-button");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openFile();
            }
        });
        openButton.setTooltip(new Tooltip("Open Project"));

        runButton = new Button();
        runButton.setId("run-button");
        runButton.setTooltip(new Tooltip("Compile to Arduino"));

        topBar = new IconMenu(saveButton, saveAsButton,newDocumentButton , openButton, runButton);

        programView = new Pane();
        programView.setId("program-view");
        scrollPane = new ScrollPane();
        scrollPane.setContent(programView);

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                treeView.setPrefHeight(newSceneHeight.intValue() - buttonBox.getHeight() - topBar.getMenu().getHeight());
                treeView.setTranslateY(buttonBox.getHeight() + topBar.getMenu().getHeight());
                buttonBox.setTranslateY(topBar.getMenu().getHeight());
                scrollPane.setTranslateY(topBar.getMenu().getHeight() + 2);
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

        scrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHWidth, Number newSceneWidth) {
                createProgramViewFromNode(d.getHead());
            }
        });

        scrollPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHWidth, Number newSceneWidth) {
                createProgramViewFromNode(d.getHead());
            }
        });


        createProgramViewFromNode(d.getHead());



        stage.setScene(scene);
        stage.setHeight(601);
        stage.setWidth(601);
        stage.setMaximized(true);
        stage.getIcons().add(new Image("file:images/save.png"));
        stage.show();

        scene.setOnKeyPressed((KeyEvent evt)->{
            if ((evt.isControlDown() || evt.isMetaDown())//meta for Mac
                    && evt.getCode() == KeyCode.O){
                openFile();
            } else if ((evt.isControlDown() || evt.isMetaDown())//meta for Mac
                    && evt.getCode() == KeyCode.S && evt.isShiftDown()) {
                saveAs();
            } else if ((evt.isControlDown() || evt.isMetaDown())//meta for Mac
                    && evt.getCode() == KeyCode.S){
                saveFile();
            }

        });

        scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent ev) {
                if (d.getHead().getComponent() != null && !d.getSavedSinceLastChange()) {
                    switch (AlertBox.show("Confirm quitting", "Save before exiting?", "")) {
                        case BUTTON_OK:
                            saveFile();
                            break;
                        case BUTTON_NO:
                            break;
                        case BUTTON_CANCEL:
                            ev.consume();
                            break;
                    }
                }
            }
        });


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Rectangle insertDropTargetAtPosWithSize(int x, int y, int width, int height) {
        final Rectangle target = new Rectangle(x, y, width, height);
        target.toFront();
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
        target.getStyleClass().add("target-no-block");

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
                    target.getStyleClass().add("target-block");
                    target.getStyleClass().remove("target-no-block");
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.getStyleClass().add("target-no-block");
                target.getStyleClass().remove("target-block");

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                d.setSavedSinceLastChange(false);
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
                } else if (db.getString().equals("Commonly used - If")) {
                    List<Node> nodes = new ArrayList<>();
                    nodes.add(new Node());
                    nodes.add(new Node());
                    nodes.add(new Node());
                    List<ICase> cases = new ArrayList<>();
                    cases.add(new Case(new Variable("i", "int"), new Constant(4), ">="));
                    cases.add(new Case(new Variable("i", "int"), new Constant(4), ">="));
                    IfComponent ifComponent = new IfComponent(nodes, cases);
                    newComponent = ifComponent;
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

    public void drawLine(int startX, int startY, int endX, int endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.toBack();
        line.setDisable(true);
        line.getStyleClass().add("line");
        programView.getChildren().add(line);
    }

    public void drawLineBetweenTargets(int startX, int startY, int endX, int endY) {
        drawLine(startX + targetWidth / 2, startY + targetHeight / 2, endX + targetWidth / 2, endY + targetHeight / 2);
    }

    private int targetWidth = 75;
    private int targetHeight = 25;
    private int blockHeight = 50;
    private int xSpaceBetweenTargets = 50;
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
        createProgramViewFromNodeRecursively(n, xSpaceBetweenTargets, blockHeight, true);
        updateProgramViewSize();

        System.out.println("----------------------------");
        System.out.println(n.getProgramCode(0));
        System.out.println("----------------------------");
    }

    private int maxX;
    private int maxY;
    public BranchData createProgramViewFromNodeRecursively(Node n, int x, int y, boolean first) {
        BranchData thisBranch = new BranchData(x, y, 0, blockHeight + targetHeight);
        if (first) {
            Rectangle r = insertDropTargetAtPosWithSize(x, y, targetWidth, targetHeight);
            targetNodeMap.put(r, n);
            targetFirstMap.put(r, true);
        }
        if (n.getComponent() == null) {
            return new BranchData(x, y, 0, blockHeight + targetHeight);
        }
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
            BranchData loopSize = createProgramViewFromNodeRecursively(loop.getHeadOfContent(), thisBranch.x, thisBranch.nextY(), true);
            thisBranch.width = loopSize.width;
            thisBranch.height = loopSize.height + blockHeight + targetHeight;
        } else if (n.getComponent().getClass() == IfComponent.class) {
            // draw if
            IfComponent ifComponent = (IfComponent)n.getComponent();
            List<BranchData> ifNodeSize = new ArrayList<>();
            for (Node ifNode : ifComponent.getHeadOfContents()) {
                ifNodeSize.add(createProgramViewFromNodeRecursively(ifNode, thisBranch.nextX(), thisBranch.y + blockHeight + targetHeight, true));
                BranchData last = ifNodeSize.get(ifNodeSize.size() - 1);
                thisBranch.width += last.width + xSpaceBetweenTargets + targetWidth;
                thisBranch.height = Math.max(last.height + blockHeight + targetHeight, thisBranch.height);
            }
            int offset = blockHeight / 2 + targetHeight / 2;
            for (BranchData bd : ifNodeSize) {
                drawLineBetweenTargets(bd.x, bd.y - offset, bd.x, bd.y + thisBranch.height - offset * 3 - 2);
            }
            thisBranch.width -= xSpaceBetweenTargets + targetWidth;
            int height1 = thisBranch.y + blockHeight / 2 + targetHeight / 2;
            int height2 = thisBranch.y + thisBranch.height - (blockHeight / 2 + targetHeight / 2);
            drawLineBetweenTargets(thisBranch.x, height1, thisBranch.nextX() - ifNodeSize.get(ifNodeSize.size() - 1).width, height1);
            drawLineBetweenTargets(thisBranch.x, height2, thisBranch.nextX() - ifNodeSize.get(ifNodeSize.size() - 1).width, height2);
        }
        // make target for after
        Rectangle r = insertDropTargetAtPosWithSize(thisBranch.x, thisBranch.nextY(), targetWidth, targetHeight);
        targetNodeMap.put(r, n);
        targetFirstMap.put(r, false);
        if (n.getNext() != null) {
            drawLineBetweenTargets(thisBranch.x, thisBranch.y, thisBranch.x, thisBranch.nextY());
            BranchData temp = createProgramViewFromNodeRecursively(n.getNext(), thisBranch.x, thisBranch.nextY(), false);
            thisBranch.height += temp.height;
        }
        return thisBranch;
    }

    public void updateProgramViewSize() {
        double newHeight = Math.max(maxY + blockHeight + targetHeight, scrollPane.getHeight() - 2);
        programView.setMinHeight(newHeight);
        if (newHeight > scrollPane.getHeight() - 2) {
            programView.setMinWidth(Math.max(maxX + xSpaceBetweenTargets + targetWidth, scrollPane.getWidth() - 14));
        } else {
            programView.setMinWidth(Math.max(maxX + xSpaceBetweenTargets + targetWidth, scrollPane.getWidth() - 2));
        }
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
        Button buttonVariables = new Button("Variables");
        Button buttonObjects = new Button("Connections");
        buttonSettings.getStyleClass().add("buttons");
        buttonVariables.getStyleClass().add("buttons");
        buttonObjects.getStyleClass().add("buttons");
        buttonBox.getChildren().addAll(buttonSettings, buttonVariables, buttonObjects);
        return buttonBox;
    }
    public boolean saveAs() {
        String oldName = d.getName();
        d.setName(null);
        if (!saveFile()) {
            d.setName(oldName);
            return false;
        }
        return true;
    }

    public boolean saveFile() {
        if (d.getName() == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Flowduino file");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                String fileName = file.getAbsoluteFile().toString();
                if (!fileName.toLowerCase().endsWith(".fdi")) {
                    fileName += ".fdi";
                }
                d.setName(fileName);

            } else {
                return false;
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(d.getName());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(d);
            out.close();
            fileOut.close();
            d.setSavedSinceLastChange(true);

        } catch (Exception e) {
            System.out.println("Save failed");
            e.printStackTrace();
        }
        return true;
    }

    public void openFile() {
        if (!checkSaved("Confirm opening file", "Do you want to save your document before opening another?")) {
            return;
        }
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String fileName = file.getAbsoluteFile().toString();
            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                d = (Document)in.readObject();
                in.close();
                fileIn.close();
            } catch(Exception e) {

            }
            d.reloadPeripherals();
            d.setSavedSinceLastChange(true);
            createProgramViewFromNode(d.getHead());
        }
    }

    public void newFile() {
        if (!checkSaved("Confirm creating new file", "Do you want to save your document before creating a new one?")) {
            return;
        }
        d = new Document();
        d.reloadPeripherals();
        createProgramViewFromNode(d.getHead());
    }

    public boolean checkSaved(String title, String message) {
        if (!d.getSavedSinceLastChange()) {
            switch (AlertBox.show(title, message, "")) {
                case BUTTON_OK:
                    if (!saveFile()) {
                        return false;
                    }
                    return true;
                case BUTTON_NO:
                    return true;
                case BUTTON_CANCEL:
                    return false;
            }
        }
        return false;
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Flowduino");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Flowduino file (*.fdi)", "*.fdi")
        );
    }
}