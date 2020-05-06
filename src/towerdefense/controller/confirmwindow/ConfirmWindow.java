package towerdefense.controller.confirmwindow;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Tous les attributs et méthodes de cette classe sont static car elle ne pourra jamais être instanciée
// En effet, son exécition bloque le contrôle des autres fenêtres
// De plus, cela permet de facilement communiquer entre fenêtres

public class ConfirmWindow {
    // Stockage de la variable
    private static boolean answer;

    public static boolean askUser(String msg, String msgYES, String msgNO, String windowTitle, Stage primaryStage) {
        answer = false;

        // Création d'une fenêtre spéciale
        Stage confirmWindow = new Stage();
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.initStyle(StageStyle.UTILITY);
        confirmWindow.initOwner(primaryStage);
        confirmWindow.setTitle(windowTitle);
        confirmWindow.setAlwaysOnTop(true);

        // Affichage du message qui pose la question à l'utilisateur
        Label message = new Label(msg);

        // Création des boutons
        Button yesButton = new Button(msgYES);
        Button noButton = new Button(msgNO);

        // On initialise les listeners pour les deux boutons
        yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                answer = true;
                confirmWindow.close();
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                answer = false;
                confirmWindow.close();
            }
        });

        VBox mainPane = new VBox();
        HBox buttonPane = new HBox();

        mainPane.getChildren().addAll(message, buttonPane);
        buttonPane.getChildren().addAll(yesButton, noButton);

        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(20);

        mainPane.setSpacing(20);
        mainPane.setPadding(new Insets(50));
        mainPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("towerdefense/controller/confirmwindow/ConfirmWindowStylesheet.css");

        message.getStyleClass().add("message");
        yesButton.getStyleClass().add("yes");
        noButton.getStyleClass().add("no");

        confirmWindow.setScene(scene);
        confirmWindow.showAndWait();

        return answer;
    }

}
