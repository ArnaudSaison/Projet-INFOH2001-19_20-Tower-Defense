package towerdefense.game.map;

import java.util.ArrayList;
import java.util.Random;

/**
 * Réprésente un chemin sur la carte que peut emprunter tout NPC qui s'y déplace.
 * Les chemins sont calculés par le PathFactory
 */
public class Path {
    // ==================== Attributs ====================
    private Map map;
    private ArrayList<IntCoordinates> coordinates;
    private ArrayList<Position> positions;

    // distance maximale autour du centre de la case
    private final Random random;
    private double maxRandomRange;

    // ==================== Initilisation ====================

    /**
     * La classe Path décrit un chemin qui peut être parcourru sous forme d'une liste de positions.
     *
     * @param intCoordinates liste de IntCoordinates qui représentent les coordonnées des cases sur la carte
     */
    public Path(ArrayList<IntCoordinates> intCoordinates, Map map) {
        this.map = map;
        this.coordinates = intCoordinates;
        positions = new ArrayList<>();
        random = new Random();

        // Initilisation de la portée aléatoire maximale autour du centre d'une case
        maxRandomRange = map.getTileMetricWidth() * 1.0 / 4.0;

        // Construction de toutes les positions métriques sur la carte
        for (IntCoordinates coords : coordinates) {
            positions.add(map.getTile(coords.getX(), coords.getY()).getCenterPosition());
        }
    }

    // ==================== Getters et Setters ====================

    /**
     * Renvoie la liste des positions du chemin
     *
     * @return liste de IntCoordinates
     */
    public ArrayList<IntCoordinates> getCoords() {
        return coordinates;
    }

    /**
     * Méthode générant une liste de positions auquelles le NPC doit de rendre pour se déplacer sur le chemin.
     * Celles-ci sont randomisées autour du centre des cases composant les changements de direction du chemin.
     *
     * @return listes de Position (en mètres autour du centre d'une case du chemin)
     */
    public ArrayList<Position> getRandomPositions() {
        ArrayList<Position> res = new ArrayList<>();

        double len = maxRandomRange * random.nextDouble();
        double angle = 2 * Math.PI * random.nextDouble();

        for (Position pos : positions) {
            res.add(pos.getAdded(new Position(len, angle, new Position(map), map)));
        }

        return res;
    }

    // ==================== Représentation ====================

    /**
     * @return String représentant la liste des cases où s'opèrent les changements de direction du chemin
     */
    @Override
    public String toString() {
        String res = "Path = ";
        for (IntCoordinates pos : coordinates) {
            res += pos.toString() + " ";
        }
        return res;
    }
}
