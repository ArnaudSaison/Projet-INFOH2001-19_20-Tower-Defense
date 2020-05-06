package towerdefense.view.map;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import towerdefense.controller.listeners.TileClickedListener;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.view.Printable;

import java.io.InputStream;
import java.util.Random;

/**
 * GUI : Classe contenant la représentation graphique d'une case
 * Étant la classe StackPane de JavaFX
 */
public abstract class TileView extends StackPane implements Printable {
    // ==================== Attributs ====================
    // Références
    protected final Map map;
    protected final Tile tile;

    // JavFX
    private final Rectangle tileShape;
    private Rectangle hoverIndicator;
    private ImageView texture;
    private double zoomFact = 1.0;

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
        //getChildren().add(tileShape);

        // Assemblage
        tileShape.getStyleClass().addAll("tile-shape");
        setOnMousePressed(new TileClickedListener(tile));
    }

    /**
     * Initilisation de la texture
     */
    public void initTexture(String imageName, int rotation, double scaleX, double scaleY) {
        // Chargement de l'image
        InputStream input = getClass().getResourceAsStream("../../../resources/graphics/tiles/" + imageName);
        Image image = new Image(input, 100, 100, true, false);
        texture = new ImageView();

        // Réglages de l'image
        texture.setImage(image);
        texture.setFitWidth(map.getTileMetricWidth() * zoomFact * map.getPixelsPerMeter());
        texture.setPreserveRatio(true);
        texture.setSmooth(false);
        texture.setCache(true);

        texture.setRotate(rotation);
        texture.setScaleX(scaleX);
        texture.setScaleY(scaleY);

        // Ajout de l'iamge au conteneur (cette classe)
        this.getChildren().add(texture);
    }

    /**
     * Initilisation du sélecteur de case
     * */
    public void initHoverIndicator(boolean canBeBuiltOn) {
        String style;
        if (canBeBuiltOn) {
            style =  "can-be-built-on";
        } else {
            style =  "cannot-be-built-on";
        }
        // hoverIndicator
        hoverIndicator = new Rectangle();
        getChildren().add(hoverIndicator);
        hoverIndicator.getStyleClass().addAll("hover-indicator", style);
        getStyleClass().add("tile");
    }

    /**
     * Méthode qui renvoie une rotation aléatoire multiple de 90 degrés
     */
    public int getRandomRotation(int lowerBound, int upperBound, int multiplier) {
        Random random = new Random();
        return (random.nextInt(upperBound - lowerBound + 1) + lowerBound) * multiplier;
    }

    // ==================== Fonctionnement ====================

    /**
     * Méthode permettant de déplacer la réprésentation à l'endroit où l'objet se trouve dans le modèle
     */
    public void update() {
        double correctionAdd = 0.7; // permet de compenser l'erreur d'arrondi créant les fins liserets blancs

        double width = map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd;
        double height = map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd;

        this.setLayoutX(tile.getPosition().getPixelX());
        this.setLayoutY(tile.getPosition().getPixelY());

        tileShape.setHeight(height);
        tileShape.setWidth(width);
        hoverIndicator.setHeight(height);
        hoverIndicator.setWidth(width);
        texture.setFitWidth(width);
    }

    // ==================== Getters et Setters ====================

    /**
     * Récupération de l'objet sous forme d'une superclasse de JavaFX qui décrit un objet affichable en général
     */
    protected Node getTileShape() {
        return tileShape;
    }

    /**
     * Récupération de l'objet JavaFX qui permet d'afficher le contour de la case quand celle-ci est survolée
     * */
    protected Node getHoverIndicator() {
        return hoverIndicator;
    }
}
