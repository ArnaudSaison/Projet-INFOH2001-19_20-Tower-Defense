package towerdefense.view.map;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import towerdefense.game.map.Map;
import towerdefense.game.map.PathTile;

import java.io.InputStream;

/**
 * GUI : Classe représentant la sortie d'un chemin
 */
public class ExitPathTileView extends PathTileView {
    // ==================== Attributs ====================
    private double arrowZoomFact = 1.0 / 2.0;
    private ImageView imageView; // conteneur élément rajouté
    private double aspectRatio = (1 - 14.0 / 22.0) * arrowZoomFact; // translation de correction
    private double translateX = 0; // si la translation doit être horizontale
    private double translateY = 0; // si la translation doit être verticale

    // ==================== Initialisation ====================

    /**
     * Constructeur de la classe
     */
    public ExitPathTileView(Map map, PathTile tile, PathTile.Connections connection) {
        super(map, tile); // Appel au constructeur de TileView
        initArrow(connection);
    }

    /**
     * Initilisation de la lfèche qui indique que des ennemis peuvent entrer par cette case
     */
    public void initArrow(PathTile.Connections connection) {
        // Chargement de l'image
        InputStream input = this.getClass().getResourceAsStream("../../../resources/graphics/arrow.png");
        Image image = new Image(input, 100, 100, true, false);
        imageView = new ImageView();

        // Réglages de l'image
        imageView.setImage(image);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(true);

        // Ajout de l'iamge au conteneur (cette classe)
        getChildren().add(imageView);

        // La flèche est tournée pour représenter la direction dans laquelle le NPC entre sur la case
        if (connection == PathTile.Connections.RIGHT) {
            imageView.setRotate(0);
            setAlignment(imageView, Pos.CENTER_RIGHT);
            translateX = 1;

        } else if (connection == PathTile.Connections.BOTTOM) {
            imageView.setRotate(90);
            setAlignment(imageView, Pos.BOTTOM_CENTER);
            translateY = 1;

        } else if (connection == PathTile.Connections.LEFT) {
            imageView.setRotate(180);
            setAlignment(imageView, Pos.CENTER_LEFT);
            translateX = -1;

        } else if (connection == PathTile.Connections.TOP) {
            imageView.setRotate(270);
            setAlignment(imageView, Pos.TOP_CENTER);
            translateY = -1;
        }

        fitImage();
    }

    // ==================== Interface Printable ====================

    /**
     * Redéfinition de la méthode update pour aussi mettre à jour l'élément rajouté
     */
    @Override
    public void update() {
        super.update();
        fitImage();
    }

    private void fitImage() {
        imageView.setFitWidth(map.getTileMetricWidth() * arrowZoomFact * map.getPixelsPerMeter());
        imageView.setFitHeight(map.getTileMetricWidth() * arrowZoomFact * map.getPixelsPerMeter());

        imageView.setTranslateX(translateX * aspectRatio * map.getTileMetricWidth() * map.getPixelsPerMeter());
        imageView.setTranslateY(translateY * aspectRatio * map.getTileMetricWidth() * map.getPixelsPerMeter());
    }
}
