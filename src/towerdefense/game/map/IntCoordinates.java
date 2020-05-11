package towerdefense.game.map;

/**
 * Classe représentant des coordonnées entières sous forme de (colonne, ligne)
 * et ce afin de correspondre aux coordonnées des cases de la carte du jeu
 */
public class IntCoordinates {
    // ==================== Attributs ====================
    private int x;
    private int y;

    // ==================== Initialisation ====================

    /**
     * Réprésenter des coordonnées dans un quadrillage de cases sous forme de (colonne, ligne).
     *
     * @param x colonne
     * @param y ligne
     */
    public IntCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // ==================== Getters et Setters ====================

    /**
     * Récupérer la colonne
     *
     * @return colonne (abscisse entière)
     */
    public int getX() {
        return x;
    }

    /**
     * Définir la colonne
     *
     * @param x colonne (abscisse entière)
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Récupérer la ligne
     *
     * @return ligne (ordonnée entière)
     */
    public int getY() {
        return y;
    }

    /**
     * Définir la ligne
     *
     * @param y ligne (ordonnée entière)
     */
    public void setY(int y) {
        this.y = y;
    }

    // Représentation sous forme de texte
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // ==================== Fonctionnement ====================
    // redéfinition de l'équalité entre deux coordonnées
    @Override
    public boolean equals(Object obj) {
        return (x == ((IntCoordinates) obj).getX() && y == ((IntCoordinates) obj).getY());
    }
}
