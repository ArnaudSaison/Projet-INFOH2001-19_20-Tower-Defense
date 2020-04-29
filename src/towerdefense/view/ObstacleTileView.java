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
    private double zoomFact = 1; // zoom de l'élément rajouté sur la case
    private ImageView imageView; // conteneur élément rajouté

    // ==================== Initialisation ====================

    /**
     * Constructeur de la classe permettant de rajouter des éléments à la représentation
     */
    public ObstacleTileView(Map map, Tile tile) {
        super(map, tile); // Appel au constructeur de TileView
        this.getTileShape().getStyleClass().addAll("obstacle-tile", "cannot-be-built-on");
        initObstacle();
    }

    /**
     * Méthode permettant d'initialiser l'élément rajouté dans la case
     */
    public void initObstacle() {
        InputStream input = this.getClass().getResourceAsStream("../../resources/graphics/tree.png");
        Image image = new Image(input, 100, 100, true, false);
        imageView = new ImageView();

        imageView.setImage(image);
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
        imageView.setPreserveRatio(true); // Empêche la déformation
        imageView.setSmooth(false); // Diminue le lissage
        imageView.setCache(true);

        this.getChildren().add(imageView); // Ajout de l'élément à la représentation de case
        setAlignment(imageView, Pos.CENTER); // Aligement au centre
    }

    // ==================== Fonctionnement ====================

    /**
     * Redéfinition de la méthode update pour aussi mettre à jour l'élément rajouté
     */
    public void update() {
        super.update();
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
    }
}