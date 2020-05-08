package towerdefense.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import towerdefense.game.Placeable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;

public abstract class ElementView extends VBox implements Printable {
    // ==================== Attributs ====================
    // Références
    protected Position position;
    private Map map;

    // Fonctionnement
    private final String graphicsFileName;
    private final double widthProportion; // proportion de la largeur d'une case occupée
    private double scale;

    // JavaFX
    private Image texture;
    private ImageView imageView;

    // ==================== Initilisation ====================

    public ElementView(Position position, Map map, String graphicsFileName, int proportion) {
        super();

        // Références
        this.position = position;
        this.map = map;
        this.widthProportion = proportion;
        this.graphicsFileName = graphicsFileName;

        // Construction de l'objet JavaFX
        String path = "./resources/graphics/" + graphicsFileName;
        texture = new Image(path, 500, 500, true, false);

        imageView = new ImageView(texture);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() * widthProportion);
        imageView.setFitWidth(map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() * widthProportion);

        getChildren().add(imageView);
        setAlignment(Pos.CENTER);

        updatePos();
        update();

//        setStyle("-fx-border-color: magenta; -fx-border-width: 1;");
    }

    public void initListeners() {

    }


    // ==================== Mises à jour ====================
    public void update() {
        updateScale();
        updatePos();
    }

    private void updateScale() {
        scale = map.getPixelsPerMeter() / map.getSettingsPixelsPerMeter();

        setScaleX(scale);
        setScaleY(scale);
    }

    private void updatePos() {
        // placement de la représentation sur la carte sachant que la position de son modèle indique toujours son centre
        this.setLayoutX(position.getPixelX() - this.getWidth() / 2);
        this.setLayoutY(position.getPixelY() - this.getHeight() / 2);
    }
}
