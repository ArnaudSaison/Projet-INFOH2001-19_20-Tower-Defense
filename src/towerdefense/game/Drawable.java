package towerdefense.game.interfaces;

import javafx.scene.layout.StackPane;

public interface Drawable {
    public void updateDrawing();
    public StackPane getDrawing();
    public void initializeDrawing();

    interface Lootable {
    }
}
