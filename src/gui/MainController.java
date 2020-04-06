package gui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Stage mainWindow;
    private Parent pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test2");
    }

    protected enum NextScene {MENU, GAME, EDITOR}

    public void setCurrentSceneTo(Stage window, NextScene s) throws IOException {
        this.mainWindow = window;
        switch (s) {
            case GAME:
                pane = FXMLLoader.load(getClass().getResource("/gui/game/GameFXML.fxml"));
                break;
            case MENU:
                pane = FXMLLoader.load(getClass().getResource("/gui/menu/MenuFXML.fxml"));
                break;
            case EDITOR:
                pane = FXMLLoader.load(getClass().getResource("/gui/map/editor/MapEditorFXML.fxml"));
                break;
        }
        Scene scene = new Scene(pane);
        mainWindow.setScene(scene);
    }
}
