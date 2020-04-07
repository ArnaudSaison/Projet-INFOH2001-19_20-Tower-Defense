package towerdefense.gui.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import towerdefense.MainApplication;
import towerdefense.gui.generic.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, GUIController {
    private MainApplication mainApplication;
    @FXML private HBox gameInfoBar;
    @FXML private HBox gameHealthInfoBar;
    @FXML private HBox gameGoldInfoBar;
    @FXML private HBox gameRoundInfoBar;
    @FXML private StackPane mapPlaceHolder;

    // Initialisation du controller
    public GameController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //Getters et Setters
    public void setMainApplication(MainApplication main){
        this.mainApplication = main;
    }

    // Gestion des éléments FXML
    @FXML
    public void handleQuitGameButtonClicked(MouseEvent event) throws IOException {
        mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
    }
}
