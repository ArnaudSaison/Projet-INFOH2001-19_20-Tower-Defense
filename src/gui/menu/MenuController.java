package gui.menu;


import gui.menu.listeners.MenuButtonListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    // Méthodes d'initialisation
    public Scene getScene() throws IOException {
        Parent menuPane = FXMLLoader.load(getClass().getResource("MenuFXML.fxml"));
        return new Scene(menuPane);
    }

    // Méthodes pour le controller
    public void handleNewGameButtonClicked(){
        System.out.println("New Game");
    }

    public void handleMapEditorButtonClicked(){
        System.out.println("Map Editor");
    }
}
