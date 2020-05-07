package towerdefense.view.goldmines;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.towers.Tower;
import towerdefense.view.Printable;

public class GoldMineView extends StackPane implements Printable {
    // ==================== Attributs ====================
    // Références
    private GoldMine goldMine;
    private Map map;

    // Fonctionnement
    private double widthProportion = 2.0; // proportion de la largeur d'une case occupée
    private double scale;

    // JavaFX
    private Image texture;
    private ImageView imageView;

    // ==================== Initilisation ====================

    public GoldMineView(GoldMine goldMine, Map map) {
        super();

        // Références
        this.goldMine = goldMine;
        this.map = map;

        // Construction de l'objet JavaFX
        String path = "./resources/graphics/gold_mine/" + "gold_mine.png";
        texture = new Image(path, 500, 500, true, false);

        imageView = new ImageView(texture);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() * widthProportion);

        getChildren().add(imageView);
        setAlignment(Pos.CENTER);

        update();
    }

    // ==================== Mises à jour ====================
    public void update() {
        scale = map.getPixelsPerMeter() / map.getSettingsPixelsPerMeter();

        this.setLayoutX(goldMine.getPos().getPixelX() - this.getWidth() / 2);
        this.setLayoutY(goldMine.getPos().getPixelY() - this.getHeight() / 2);

        setScaleX(scale);
        setScaleY(scale);
    }
}
