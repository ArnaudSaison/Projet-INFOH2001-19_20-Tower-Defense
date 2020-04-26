package towerdefense.gui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import towerdefense.MainApplication;
import towerdefense.gui.generic.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable, GUIController {
    private MainApplication mainApplication;

    // Initialisation du controller
    public MenuController(){

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
    public void handleNewGameButtonClicked(MouseEvent event) throws IOException {
        mainApplication.setNextScene(MainApplication.SceneType.GAME);
        mainApplication.setCurrentSceneTo(MainApplication.SceneType.SELECTOR);
    }

    @FXML
    public void handleMapEditorButtonClicked(MouseEvent event) throws IOException {
        mainApplication.setCurrentSceneTo(MainApplication.SceneType.EDITOR);
    }
}