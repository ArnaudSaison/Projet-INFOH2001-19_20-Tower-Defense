package towerdefense.view.towers;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import towerdefense.game.map.Map;
import towerdefense.game.npcs.HeadedDir;
import towerdefense.game.npcs.NPC;
import towerdefense.game.towers.Tower;
import towerdefense.view.Printable;
import towerdefense.view.npc.LoadBar;

public class TowerView extends StackPane implements Printable {
    // ==================== Attributs ====================
    // Références
    private Tower tower;
    private Map map;

    // Fonctionnement
    private double widthProportion = 2.0; // proportion de la largeur d'une case occupée
    private double scale;

    // JavaFX
    private Image texture;
    private ImageView imageView;

    // ==================== Initilisation ====================

    public TowerView(Tower tower, Map map, String graphicsFileName) {
        super();

        // Références
        this.tower = tower;
        this.map = map;

        // Construction de l'objet JavaFX
        String path = "./resources/graphics/towers/" + graphicsFileName + "tower.png";
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

        this.setLayoutX(tower.getPos().getPixelX() - this.getWidth() / 2);
        this.setLayoutY(tower.getPos().getPixelY() - this.getHeight() / 2);

        setScaleX(scale);
        setScaleY(scale);
    }
}
