package towerdefense.game;

import towerdefense.game.map.Position;

/**
 * Tout placeable doit également être un drawable (noter que l'inverse n'est pas vrai, ce qui justifie la création de cette interface)
 */
public interface Placeable {
    public void setPosition(Position position);
    public Position getPosition();
    public void initialize();
}
