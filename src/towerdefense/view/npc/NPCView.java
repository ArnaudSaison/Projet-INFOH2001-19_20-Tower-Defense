package towerdefense.view.npc;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private double scale;

    // JavaFX
    private Image rightTexture;
    private Image leftTexture;
    private Image upTexture;
    private Image downTexture;

    private ImageView imageView;
    private LoadBar healthBar;
    private Color hbBackgroundColor = new Color(0, 1.0, 0, 0.8);
    private Color hbBarColor = new Color(1.0, 0, 0, 1.0);

    // ==================== Initilisation ====================

    public NPCView(NPC npc, Map map, String graphicsFileName) {
        super();

        // Références
        this.npc = npc;
        this.map = map;

        // Construction de l'objet JavaFX
        String path = "./resources/graphics/enemy/" + graphicsFileName;
        rightTexture = new Image(path + " RIGHT.png", 300, 300, true, false);
        leftTexture = new Image(path + " LEFT.png", 300, 300, true, false);
        upTexture = new Image(path + " UP.png", 300, 300, true, false);
        downTexture = new Image(path + " DOWN.png", 300, 300, true, false);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);

        double hbWidth = map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() * widthProportion * 3 / 4;
        double hbHeight = map.getTileMetricWidth() * map.getSettingsPixelsPerMeter() / 30;
        healthBar = new LoadBar(hbWidth, hbHeight, npc.getMaxHealth(), hbBackgroundColor, hbBarColor);

        getChildren().add(healthBar);
        getChildren().add(imageView);
        setAlignment(Pos.CENTER);
        setSpacing(hbHeight);

        healthBar.updateTo(npc.getMaxHealth() / 2);
        update();
    }

    // ==================== Mises à jour ====================
    public void update() {
        HeadedDir dir = npc.getHeadedDir();
        if (viewDir != dir) {
            viewDir = dir;
            updateTexture(viewDir);
            updateScale(viewDir);
        }

        scale = map.getPixelsPerMeter() / map.getSettingsPixelsPerMeter();

        this.setLayoutX(npc.getPos().getPixelX() - this.getWidth() / 2);
        this.setLayoutY(npc.getPos().getPixelY() - this.getHeight() / 2);

        setScaleX(scale);
        setScaleY(scale);

        healthBar.updateTo(npc.getHealth());
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
        double fitMulti = map.getTileMetricWidth() * map.getSettingsPixelsPerMeter();

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
