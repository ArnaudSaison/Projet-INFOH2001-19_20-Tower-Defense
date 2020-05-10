package towerdefense.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import towerdefense.game.Placeable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.Shop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class ElementView extends StackPane implements Printable {
    // ==================== Attributs ====================
    // Références
    protected Position position;
    private Map map;

    // Fonctionnement
    private final String graphicsFileName;
    private final double widthProportion; // proportion de la largeur d'une case occupée
    private double scale;

    // JavaFX
    private String path;
    private Image texture;
    protected ImageView imageView;

    // ==================== Initilisation ====================

    public ElementView(Position position, Map map, String graphicsFileName, int proportion) {
        super();

        // Références
        this.position = position;
        this.map = map;
        this.widthProportion = proportion;
        this.graphicsFileName = graphicsFileName;

        buildJFX(500, 500); // construction de JFX avec grande résolution
    }

    public ElementView(Position position, Map map, String graphicsFileName, double proportion) {
        super();

        // Références
        this.position = position;
        this.map = map;
        this.widthProportion = proportion;
        this.graphicsFileName = graphicsFileName;


        buildJFX(50, 50); // construction de JFX avec grande résolution

        setStyle("-fx-border-color: magenta; -fx-border-width: 1;");
    }

    private void buildJFX(double sizeX, double sizeY) {
        path = "../../../resources/graphics/" + graphicsFileName;

        // Construction de l'objet JavaFX
        InputStream input = getClass().getResourceAsStream(path);
        texture = new Image(input, sizeX, sizeY, true, false);

        imageView = new ImageView(texture);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() * widthProportion);
        imageView.setFitWidth(map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() * widthProportion);

        getChildren().add(imageView);
        setAlignment(Pos.CENTER);

        updateScale();
        updatePos();
//        setStyle("-fx-border-color: magenta; -fx-border-width: 1;");
    }

    @Override
    public void initListeners() {

    }


    // ==================== Mises à jour ====================
    @Override
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
