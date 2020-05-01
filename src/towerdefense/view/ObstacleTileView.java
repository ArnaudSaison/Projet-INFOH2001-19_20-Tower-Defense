package towerdefense.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;

import java.io.InputStream;

/**
 * GUI : Classe représentant une case avec un obstacle
 */
public class ObstacleTileView extends TileView {
    // ==================== Attributs ====================
    private double osbtacleZoomFact = 1; // zoom de l'élément rajouté sur la case
    private ImageView imageView; // conteneur élément rajouté

    public enum ObstacleType {TREE, ROCK}

    // ==================== Initialisation ====================

    /**
     * Constructeur de la classe permettant de rajouter des éléments à la représentation
     */
    public ObstacleTileView(Map map, Tile tile, ObstacleType type) {
        super(map, tile); // Appel au constructeur de TileView
        getTileShape().getStyleClass().addAll("obstacle-tile-shape");

        initTexture("grass.png", getRandomRotation(0, 3, 90), 1, 1);
        initObstacle(type);
        initHoverIndicator(false);
    }

    /**
     * Méthode permettant d'initialiser l'élément rajouté dans la case
     * L'utilisation d'un enum permet de très simplement rajouter des types d'obstacles
     * et de naturellement empêcher toute erreur qu'un argument de type String pourrait causer
     */
    public void initObstacle(ObstacleType type) {
        String fileName;
        switch (type) {
            case ROCK:
                fileName = "rock";
                osbtacleZoomFact = 3.0/4.0;
                break;
            default:
            case TREE:
                fileName = "tree";
                osbtacleZoomFact = 1.0;
                break;
        }

        InputStream input = this.getClass().getResourceAsStream("../../resources/graphics/obstacles/" + fileName + ".png");
        Image image = new Image(input, 100, 100, true, false);
        imageView = new ImageView();

        imageView.setImage(image);
        imageView.setFitWidth(map.getTileMetricWidth() * osbtacleZoomFact * map.getPixelsPerMeter());
        imageView.setPreserveRatio(true); // Empêche la déformation
        imageView.setSmooth(false); // Diminue le lissage
        imageView.setCache(true);

        getChildren().add(imageView); // Ajout de l'élément à la représentation de case
        setAlignment(imageView, Pos.CENTER); // Aligement au centre
    }

    // ==================== Fonctionnement ====================

    /**
     * Redéfinition de la méthode update pour aussi mettre à jour l'élément rajouté
     */
    public void update() {
        super.update();
        imageView.setFitWidth(map.getTileMetricWidth() * osbtacleZoomFact * map.getPixelsPerMeter());
    }
}