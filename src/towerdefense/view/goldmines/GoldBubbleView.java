package towerdefense.view.goldmines;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import towerdefense.game.goldmine.GoldMine;

public class GoldBubbleView extends StackPane {
    public GoldBubbleView(GoldMine goldMine, double size) {
        super();

        ImageView goldImage = new ImageView(new Image("./resources/graphics/UI/ingot.png", 50, 50, true, false));
        goldImage.setFitWidth(size);
        goldImage.setFitHeight(size);

        this.getStyleClass().add("retrieve-gold");
        this.getChildren().add(goldImage);
        setAlignment(Pos.CENTER);

        this.setMaxSize(size, size);
        this.setPadding(new Insets((size / 4.0)));
        CornerRadii cr = new CornerRadii((size / 4.0));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, cr, new Insets(0))));

        // Récupération de l'or
        this.setOnMouseClicked(mouseEvent -> {
            goldMine.retrieveGold();
        });
    }

    public void setFull() {
        this.getStyleClass().add("retrieve-gold-full");
    }

    public void setNotFull() {
        this.getStyleClass().remove("retrieve-gold-full");
    }
}
