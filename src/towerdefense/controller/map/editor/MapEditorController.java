package towerdefense.controller.map.editor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import towerdefense.MainApplication;
import towerdefense.controller.generic.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditorController implements Initializable, GUIController {
    private MainApplication mainApplication;
    private boolean isSaved;

    @FXML private StackPane mapPlaceHolder;

    // Initialisation du controller
    public MapEditorController(){
        isSaved = false;
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
        if (!isSaved) {
            boolean answer = mainApplication.confirmWindow("Unsaved changes will be lost. Are you sure you want to exit the editor ?", "Yes", "No", "Confirm");
            if (answer){
                mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
            }
        } else {
            mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
        }
    }

    @FXML
    public void handleSaveButtonClicked(MouseEvent event) throws IOException {
        isSaved = true;
    }



    // Logique de controller

}