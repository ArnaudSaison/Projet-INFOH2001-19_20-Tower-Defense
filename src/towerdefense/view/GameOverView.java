package towerdefense.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameOverView extends VBox {
    public GameOverView(int score) {
        super();

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("game-over-main-label");

        Label scoreLabel = new Label("With score: " + score);
        gameOverLabel.getStyleClass().add("game-over-score-label");

        this.getStyleClass().add("game-over");

        this.setMinWidth(300);
        this.setMinHeight(150);
        this.setMaxWidth(300);
        this.setMaxHeight(150);
        this.setPrefWidth(300);
        this.setPrefHeight(150);
        this.setAlignment(Pos.CENTER);

        this.getChildren().add(gameOverLabel);
        this.getChildren().add(scoreLabel);
    }
}
