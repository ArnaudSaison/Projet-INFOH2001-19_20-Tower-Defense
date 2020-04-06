package gui.menu;

import gui.MainController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends MainController {

    public MenuController() {
        super();
    }

    @FXML
    public void handleNewGameButtonClicked(MouseEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.setCurrentSceneTo(window, NextScene.GAME);
    }

    @FXML
    public void handleMapEditorButtonClicked(MouseEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.setCurrentSceneTo(window, NextScene.EDITOR);
    }
}