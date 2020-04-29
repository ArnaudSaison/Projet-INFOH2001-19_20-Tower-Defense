package towerdefense.view;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import towerdefense.controller.listeners.TileClickedListener;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;

/**
 * GUI : Classe contenant la représentation graphique d'une case
 * Étant la classe StackPane de JavaFX
 */
public class TileView extends StackPane implements Printable {
    // ==================== Attributs ====================
    // Références
    protected final Map map;
    protected final Tile tile;

    // JavFX
    private final Rectangle tileShape;

    // ==================== Initilisation ====================

    /**
     * Constructeur de l'objet TileView qui contient tout le code JavaFX relatif à l'affichage d'une case
     */
    public TileView(Map map, Tile tile) {
        super(); // Appel du constructeur JavaFX

        // Construction
        this.tile = tile;
        this.map = map;

        // Contenu
        tileShape = new Rectangle();
        this.getChildren().add(tileShape);

        // Assemblage
        tileShape.getStyleClass().addAll("tile");
        tileShape.setOnMouseClicked(new TileClickedListener(tile));
    }

    // ==================== Fonctionnement ====================

    /**
     * Méthode permettant de déplacer la réprésentation à l'endroit où l'objet se trouve dans le modèle
     */
    public void update() {
        double correctionAdd = 0; // permet de compenser l'erreur d'arrondi créant les fins liserets blancs

        this.setLayoutX(tile.getPosition().getPixelX());
        this.setLayoutY(tile.getPosition().getPixelY());
        tileShape.setHeight(map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd);
        tileShape.setWidth(map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd);
    }

    // ==================== Getters et Setters ====================

    /**
     * Récupération de l'objet sous forme d'une superclasse de JavaFX qui décrit un objet affichable en général
     */
    protected Node getTileShape() {
        return tileShape;
    }
}
