package gui.game;

import gui.menu.listeners.MenuButtonListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameController extends BorderPane{
    private BorderPane gamePane;

    public GameController() {
        this.gamePane = new BorderPane();
    }

    public void CreateGameSceneContent(Stage mainWindow, Scene mainMenuScene){
        Label gameLabel = new Label("Jeu");
        Button backToMainMenuButton = new Button("Retour au menu principal");
        MenuButtonListener mbl = new MenuButtonListener(mainWindow, mainMenuScene);
        backToMainMenuButton.setOnMouseClicked(mbl);

        HBox gameTopPane = new HBox();
        gameTopPane.getChildren().addAll(gameLabel, backToMainMenuButton);

        gamePane.setTop(gameTopPane);
        gamePane.setCenter(new VBox(50));
    }

}
