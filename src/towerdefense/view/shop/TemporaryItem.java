package towerdefense.view.shop;

import towerdefense.game.map.Position;

public interface TemporaryItem {
    /**
     * Méthode spéciale des TemporaryItem permettant à une ebhet de JavaFX d'avoir une position en mètres réglable directement
     *
     * @param pos position à laquelle on souhaite déplacer l'objet
     */
    public void setPosition(Position pos);
    public void update();
}
