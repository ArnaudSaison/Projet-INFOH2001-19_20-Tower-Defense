package towerdefense.game;

import towerdefense.view.Printable;

/**
 * Permet l'intération avec tout élément possédant une représentation graphique dans le modèle
 * Voir interface Printable
 * => implémentaiton du MVC
 */
public interface Drawable {
    public void updateDrawing();

    public Printable getDrawing();

    /**
     * Retirer la représentation graphique de l'élément de la vue
     * Cette méthode ne sert pas à faire disparaître la représentation en question,
     * mais bien à supprimer toute référence de cette raprésentation et ainsi pouvoir supprimer this
     */
    public void removeDrawing();
}
