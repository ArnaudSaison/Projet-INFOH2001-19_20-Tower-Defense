package towerdefense.controller.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import towerdefense.MainApplication;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.controller.generic.GUIController;
import towerdefense.view.MapView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Le GameCOntroller est instancié par JavaFX lors de la lecture du fichier FXML
 */
public class GameController implements Initializable, GUIController {
    // Attributs nécessaires au fonctionnement de javafx
    private MainApplication mainApplication;
    private boolean isGameFinished;

    @FXML
    private HBox gameInfoBar;
    @FXML
    private HBox gameHealthInfoBar;
    @FXML
    private Text gameHealthInfoBarText;
    @FXML
    private HBox gameGoldInfoBar;
    @FXML
    private Text gameGoldInfoBarText;
    @FXML
    private HBox gameRoundInfoBar;
    @FXML
    private Text gameRoundInfoBarText;
    @FXML
    private Pane mapPlaceHolder;
    @FXML
    private StackPane gameBox;
    @FXML
    private VBox sidebar;
    @FXML
    private Button ResetViewButton;

    // Attributs nécessaires à la liaison avec le modèle
    private MapFactory mapFactory;
    private Map map;
    private MapView mapPane;

    private double deltaXDrag;
    private double deltaYDrag;

    // Initialisation du controller
    public GameController() {
        isGameFinished = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //Getters et Setters
    public void setMainApplication(MainApplication main) {
        this.mainApplication = main;

        // Initialisation de la map
        String mapPath = mainApplication.getSelectedMapPath();
        String graphicsPath = "towerdefense/controller/game/graphics.css";

        mapFactory = new MapFactory();
        try {
            map = mapFactory.getMap(mapPath);
            map.initDrawing(); // initialisation de toutes les raprésentations graphiques
            mapPane = (MapView) map.getDrawing(); // Ce casting est parmis par déifnition du MCV parttern
            mapPane.getStylesheets().add(graphicsPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        mapPlaceHolder.getChildren().add(0, mapPane);
    }

    //***** Gestion des éléments FXML *****
    @FXML
    public void handleQuitGameButtonClicked(MouseEvent event) throws IOException {
        if (!isGameFinished) {
            boolean answer = mainApplication.confirmWindow("The Game is not finished yet. Do you want to quit anyway ?", "Quit Game", "Go back to Game", "Confirm");
            if (answer) {
                mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
            }
        } else {
            mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
        }
    }

    @FXML
    public void handleZoomScroll(ScrollEvent event) {
        mapPane.updateZoomLevel(event);
    }

    // Gestion du déplacement de la carte
    @FXML
    public void handleMousePressedDelta(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            deltaXDrag = mapPane.getLayoutX() - event.getX();
            deltaYDrag = mapPane.getLayoutY() - event.getY();
        }
    }

    @FXML
    public void handleMouseDraggedMap(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            mapPane.setLayoutX(event.getX() + deltaXDrag);
            mapPane.setLayoutY(event.getY() + deltaYDrag);
        }
    }

    @FXML
    public void handleResetViewButton(MouseEvent event) {
        mapPane.setLayoutX(0);
        mapPane.setLayoutY(0);
        map.resetPixelsPerMeter();
        map.updateDrawing();
    }
}
