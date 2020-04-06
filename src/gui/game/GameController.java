package gui.game;

import gui.MainController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController extends MainController {

    public GameController(){
        super();
    }

    @FXML
    public void handleQuitGameButtonClicked(MouseEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.setCurrentSceneTo(window, NextScene.MENU);
    }
}
