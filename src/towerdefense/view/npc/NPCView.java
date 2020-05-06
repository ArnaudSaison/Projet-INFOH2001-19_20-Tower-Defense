package towerdefense.view.npc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import towerdefense.game.map.Map;
import towerdefense.game.npcs.HeadedDir;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;

/**
 * Classe contenant la représentation d'un NPC sur la carte
 */
public class NPCView extends VBox implements Printable {
    // ==================== Attributs ====================
    // Références
    private NPC npc;
    private Map map;

    // Fonctionnement
    private HeadedDir viewDir;
    private double widthProportion = 1.0/2.0; // proportion de la largeur d'une case occupée

    // JavaFX
    private Image rightTexture;
    private Image leftTexture;
    private Image upTexture;
    private Image downTexture;

    private ImageView imageView;


    // ==================== Initilisation ====================

    public NPCView(NPC npc, Map map, String graphicsFileName) {
        super();

        // Références
        this.npc = npc;
        this.map = map;

        // Construction de l'objet JavaFX
        String path = "./resources/graphics/enemy/" + graphicsFileName;
        rightTexture = new Image(path + " RIGHT.png", 152, 218, true, false);
        leftTexture = new Image(path + " LEFT.png", 100, 100, true, false);
        upTexture = new Image(path + " UP.png", 100, 100, true, false);
        downTexture = new Image(path + " DOWN.png", 100, 100, true, false);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(USE_COMPUTED_SIZE);

//        healthBar = new LoadBar();

        getChildren().add(imageView);
        setMaxWidth(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setStyle("-fx-border-width: 2; -fx-border-color: magenta;");

        update();
    }

    // ==================== Mises à jour ====================
    public void update() {
        HeadedDir dir = npc.getHeadedDir();
        if (viewDir != dir) {
            updateTexture(dir);
            viewDir = dir;
        }

        this.setLayoutX(npc.getPos().getPixelX() - this.getWidth() / 2);
        this.setLayoutY(npc.getPos().getPixelY() - this.getHeight() / 2);

        updateScale(viewDir);
    }

    private void updateTexture(HeadedDir dir) {
        switch (dir) {
            case RIGHT:
                imageView.setImage(rightTexture);
                break;
            case LEFT:
                imageView.setImage(leftTexture);
                break;
            case DOWN:
                imageView.setImage(downTexture);
                break;
            case UP:
                imageView.setImage(upTexture);
                break;
        }
    }

    private void updateScale(HeadedDir dir) {
        double fitMulti = map.getTileMetricWidth() * map.getPixelsPerMeter();

        switch (dir) {
            case RIGHT:
                imageView.setFitWidth(widthProportion * fitMulti);
                break;
            case LEFT:
                imageView.setFitWidth(widthProportion * fitMulti);
                break;
            case DOWN:
                imageView.setFitHeight(widthProportion * fitMulti);
                break;
            case UP:
                imageView.setFitHeight(widthProportion * fitMulti);
                break;
        }

        setPrefWidth(widthProportion * fitMulti);
    }
}
