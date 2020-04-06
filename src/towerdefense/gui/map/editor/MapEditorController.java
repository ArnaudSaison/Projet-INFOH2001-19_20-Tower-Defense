package towerdefense.gui.map.editor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import towerdefense.MainApplication;
import towerdefense.gui.gui.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditorController implements Initializable, GUIController {
    private MainApplication mainApplication;

    // Initialisation du controller
    public MapEditorController(){

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
    public void handleBackToMenuButtonClicked(MouseEvent event) throws IOException {
        mainApplication.setCurrentSceneTo("MENU");
    }
}