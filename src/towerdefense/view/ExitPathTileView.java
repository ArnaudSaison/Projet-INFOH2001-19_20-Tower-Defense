package towerdefense.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import towerdefense.game.map.Map;
import towerdefense.game.map.PathTile;
import towerdefense.game.map.Tile;

import java.io.InputStream;

/**
 * GUI : Classe représentant la sortie d'un chemin
 */
public class ExitPathTileView extends PathTileView {
    // ==================== Attributs ====================
    private double zoomFact = 1.0 / 2.0;
    private ImageView imageView; // conteneur élément rajouté

    // ==================== Initialisation ====================

    /**
     * Constructeur de la classe
     */
    public ExitPathTileView(Map map, Tile tile, PathTile.Connections connection) {
        super(map, tile); // Appel au constructeur de TileView
        this.getTileShape().getStyleClass().addAll("path-tile", "cannot-be-built-on");
        initArrow(connection);
    }

    /**
     * Initilisation de la lfèche qui indique que des ennemis peuvent entrer par cette case
     */
    public void initArrow(PathTile.Connections connection) {
        // Chargement de l'image
        InputStream input = this.getClass().getResourceAsStream("../../resources/graphics/arrow.png");
        Image image = new Image(input, 100, 100, true, false);
        imageView = new ImageView();

        // Réglages de l'image
        imageView.setImage(image);
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(true);

        // Ajout de l'iamge au conteneur (cette classe)
        this.getChildren().add(imageView);

        // La flèche est tournée pour représenter la direction dans laquelle le NPC entre sur la case
        if (connection == PathTile.Connections.RIGHT) {
            imageView.setRotate(0);
            setAlignment(imageView, Pos.CENTER_RIGHT);

        } else if (connection == PathTile.Connections.BOTTOM) {
            imageView.setRotate(90);
            setAlignment(imageView, Pos.BOTTOM_CENTER);

        } else if (connection == PathTile.Connections.LEFT) {
            imageView.setRotate(180);
            setAlignment(imageView, Pos.CENTER_LEFT);

        } else if (connection == PathTile.Connections.TOP) {
            imageView.setRotate(270);
            setAlignment(imageView, Pos.TOP_CENTER);
        }
    }

    // ==================== Interface Printable ====================

    /**
     * Redéfinition de la méthode update pour aussi mettre à jour l'élément rajouté
     */
    public void update() {
        super.update();
        imageView.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
    }
}
