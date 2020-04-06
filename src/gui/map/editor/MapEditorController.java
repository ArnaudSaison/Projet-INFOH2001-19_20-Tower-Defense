package gui.map.editor;

import gui.MainController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MapEditorController extends MainController {

    @FXML
    public void handleBackToMenuButtonClicked(MouseEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.setCurrentSceneTo(window, NextScene.MENU);
    }
}