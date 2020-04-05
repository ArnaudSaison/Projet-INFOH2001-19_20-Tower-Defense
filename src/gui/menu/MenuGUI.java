package gui.menu;


import gui.menu.listeners.MenuButtonListener;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MenuGUI extends Application {
    private Stage mainWindow;
    private Scene mainMenuScene;
    private Scene gameScene;

    private BorderPane menuPane;
    private BorderPane gamePane;
    private int windowHeight = 1080;
    private int windowWidth = 1920;


    public void CreateMainMenuContent(){
        Label menuLabel = new Label("Menu du jeu");
        Button mainMenuButton = new Button("Nouvelle partie");
        MenuButtonListener mbl = new MenuButtonListener(mainWindow, gameScene);
        mainMenuButton.setOnMouseClicked(mbl);

        TilePane menuTopPane = new TilePane();
        menuTopPane.getChildren().addAll(menuLabel, mainMenuButton);

        menuPane.setTop(menuTopPane);
        menuPane.setCenter(new VBox(50));
    }

    public void CreateGameSceneContent(){
        Label gameLabel = new Label("Jeu");
        Button backToMainMenuButton = new Button("Retour au menu principal");
        MenuButtonListener mbl = new MenuButtonListener(mainWindow, mainMenuScene);
        backToMainMenuButton.setOnMouseClicked(mbl);

        HBox gameTopPane = new HBox();
        gameTopPane.getChildren().addAll(gameLabel, backToMainMenuButton);

        gamePane.setTop(gameTopPane);
        gamePane.setCenter(new VBox(50));
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;

        menuPane = new BorderPane();
        gamePane = new BorderPane();

        mainMenuScene = new Scene(menuPane, windowWidth, windowHeight);
        gameScene = new Scene(gamePane, windowWidth, windowHeight);
        mainWindow.setScene(mainMenuScene);

        CreateMainMenuContent();
        CreateGameSceneContent();

        mainWindow.setTitle("Tower Defense");
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
