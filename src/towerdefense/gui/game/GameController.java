package towerdefense.gui.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import towerdefense.MainApplication;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.gui.generic.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, GUIController {
    // Attributs nécessaires au fonctionnement de javafx
    private MainApplication mainApplication;
    private boolean isGameFinished;

    @FXML private HBox gameInfoBar;
    @FXML private HBox gameHealthInfoBar;
    @FXML private HBox gameGoldInfoBar;
    @FXML private HBox gameRoundInfoBar;
    @FXML private Pane mapPlaceHolder;
    @FXML private StackPane gameBox;
    @FXML private VBox sidebar;

    // Attributs nécessaires à la liaison avec le modèle
    private MapFactory mapFactory;
    private Map map;

    // Initialisation du controller
    public GameController() {
        isGameFinished = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation du jeu
        String path = "/Users/arnaudsaison/Library/Mobile Documents/com~apple~CloudDocs/Université/BA2/[INFOH2001] Programmation orientée objet/Projet-INFOH2001-19_20-Tower-Defense/resources/maps/map1";
        mapFactory = new MapFactory();
        try {
            map = mapFactory.getMap(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        mapPlaceHolder.getChildren().add(0, map);
    }

    //Getters et Setters
    public void setMainApplication(MainApplication main){
        this.mainApplication = main;
    }

    // Gestion des éléments FXML
    @FXML
    public void handleQuitGameButtonClicked(MouseEvent event) throws IOException {
        if (!isGameFinished) {
            boolean answer = mainApplication.confirmWindow("The Game is not finished yet. Do you want to quit anyway ?", "Quit Game", "Go back to Game", "Confirm");
            if (answer){
                mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
            }
        } else {
            mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
        }
    }

    @FXML
    public void handleZoomScroll(ScrollEvent event) throws IOException {
        map.updateZoomLevel(event.getDeltaY());
    }
}
