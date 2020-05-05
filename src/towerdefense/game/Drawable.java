package towerdefense.game;

import towerdefense.view.Printable;

/**
 * Permet l'intération avec tout élément possédant une représentation graphique dans le modèle
 * Voir interface Printable
 * => implémentaiton du MVC
 */
public interface Drawable {
    public void updateDrawing();

    public void initDrawing();

    public Printable getDrawing();
}
