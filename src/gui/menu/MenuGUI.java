package gui.menu;


import gui.menu.listeners.MenuButtonListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuGUI extends Application {
    Stage mainWindow;
    Scene menu, game;

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;

        Label menuLabel = new Label("Menu du jeu");
        Button mainMenuButton = new Button("Nouvelle partie");
        mainMenuButton.setOnMouseClicked(new MenuButtonListener(mainWindow, game));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
