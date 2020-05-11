package towerdefense.game.map;

import towerdefense.game.npcs.HeadedDir;

/**
 * Les positions du jeu sont toutes représentées en mètres. Ce système permet de créer un lien flexible avec la carte
 * et la représentation graphique.
 */
public class Position {
    private final Object syncKeySetPos = new Object();

    // ==================== Attributs ====================
    // x et y sont en coordonnées métriques, cela permet de changer l'échelle à volonté par la suite
    private double x;
    private double y;

    // Cette classe indique la postion sur la map :
    private Map map;

    // taille d'une case
    private double tileMetricWidth;

    // ==================== Construteurs ====================

    /**
     * Positon en 0,0
     *
     * @param map carte sur laquelle on place la position
     */
    public Position(Map map) {
        this.map = map;
        x = 0;
        y = 0;
    }

    /**
     * Position à partir de coordonnées métriques
     *
     * @param x   abscisse en mètres
     * @param y   ordonnée en mètres
     * @param map carte sur laquelle on représente la position
     */
    public Position(double x, double y, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
    }

    /**
     * Réservé aux tests
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Position en mètres à partir de coordonnées d'une case
     *
     * @param x   abscisse en cases
     * @param y   ordonnée en cases
     * @param map carte sur laquelle on représente la position
     */
    public Position(int x, int y, Map map) {
        this.x = x * map.getTileMetricWidth();
        this.y = y * map.getTileMetricWidth();
        this.map = map;
    }

    /**
     * Position à partir de coordonnées en pixel
     *
     * @param x               abscisse en pixels
     * @param y               ordonnée en pixels
     * @param map             carte sur laquelle on représente la position
     * @param convertToMeters boolean indiquant si l'on souhaite convertir en mètres la position en pixels
     */
    public Position(double x, double y, Map map, boolean convertToMeters) {
        this.map = map;
        if (convertToMeters) {
            this.x = x / map.getPixelsPerMeter();
            this.y = y / map.getPixelsPerMeter();
        } else {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Constructeur de Position permettant de créer une position
     * autour de l'origine fournie à partir d'un angle et d'une distance
     *
     * @param distance (en mètres) distance autour de l'orgine
     * @param angle    (en radians) angle formé par la droite passant par l'origine et la position que l'on souhaite créer
     * @param origin   origine autour de laquelle on veut créer la position
     * @param map      carte sur laquelle on place la position
     */
    public Position(double distance, double angle, Position origin, Map map) {
        this.x = distance * Math.cos(angle) + origin.getX();
        this.y = distance * Math.sin(angle) + origin.getY();
        this.map = map;
    }

    // Créer une position à partir de coordonnées de cases

    /**
     * Constructeur de position dans le cas où on ne dispose pas de carte sur laquelle mettre la position.
     * Il est possible d'en attacher une par après grâce à attachMap().
     * Il faut cependant toujours au moins passer la taille des cases en mètres si l'on veut pouvoir convertir
     * les coordonnées de cases en coordonnées métriques
     *
     * @param x               abscisse de la case
     * @param y               ordonnée de la case
     * @param tileMetricWidth largeur d'une case (en mètres)
     */
    public Position(int x, int y, double tileMetricWidth) {
        this.tileMetricWidth = tileMetricWidth;
        this.x = x * tileMetricWidth;
        this.y = y * tileMetricWidth;
    }

    /**
     * Méthode permettant d'attacher une carte à la position
     * afin de pouvoir travailler avec des distances en pixels
     * (si celà n'a pas pu être fait dans le constructeur)
     *
     * @param map carte sur laquelle la position est représentée
     */
    public void attachMap(Map map) {
        this.map = map;
        this.tileMetricWidth = map.getTileMetricWidth();
    }

    // ==================== Setters et Getters ====================
    //***** Setters *******

    /**
     * Changer l'abscisse en mètres
     *
     * @param x abscisse en mètres
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Changer l'ordonnée en mètres
     *
     * @param y ordonnée en mètres
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Changer abscisse et ordonnée en même temps
     *
     * @param x
     * @param y
     */
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setToPosition(Position pos) {
        synchronized (syncKeySetPos) {
            this.setXY(pos.getX(), pos.getY());
        }
    }

    //***** Getters *******

    /**
     * Récupérer l'abscisse en mètres
     *
     * @return abscisse en mètres
     */
    public double getX() {
        return x;
    }

    /**
     * Changer l'ordonnée en mètres
     *
     * @return ordonnée en mètres
     */
    public double getY() {
        return y;
    }

    /**
     * Récupérer l'abscisse en pixels
     * (pour celà la position doit obligatoirement appartenir à une carte)
     *
     * @return abscisse en pixels
     */
    public double getPixelX() {
        assertMapAttached();
        return (x * map.getPixelsPerMeter());
    }

    /**
     * Récupérer l'ordonnée en pixels
     * (pour celà la position doit obligatoirement appartenir à une carte)
     *
     * @return ordonnée en pixels
     */
    public double getPixelY() {
        assertMapAttached();
        return (y * map.getPixelsPerMeter());
    }

    /**
     * Méthode permettant de récupérer l'indice des abscisses de la case dans laquelle la position se trouve
     *
     * @return abscisse des cordonnées de case
     */
    public int getTileX() {
        assertMapAttached();
        return (int) Math.round(x / map.getTileMetricWidth());
    }

    /**
     * Méthode permettant de récupérer l'indice des ordonnées de la case dans laquelle la position se trouve
     *
     * @return ordonnée des cordonnées de case
     */
    public int getTileY() {
        assertMapAttached();
        return (int) Math.round(y / map.getTileMetricWidth());
    }

    /**
     * Méthode permettant de récupérer le coin inférieur droit de la case dans laquelle on se trouve
     *
     * @return
     */
    public Position getSouthEastTileCorner() {
        assertMapAttached();
        return new Position(getX() + tileMetricWidth, getTileY() + tileMetricWidth, map);
    }

    /**
     * Méthode permettant de récupérer les coordonnées de la case dans la quelle la position se trouve
     *
     * @return cordonnées de la case dans laquelle la position se trouve
     */
    public IntCoordinates getTileCoords() {
        return new IntCoordinates(getTileX(), getTileY());
    }

    /**
     * Récupérer la norme d'une position entre celle-ci et l'origine
     *
     * @return norme
     */
    public double getNorm() {
        return Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2));
    }

    /**
     * Méthode statique permettant de récupérer la norme entre l'origine d'un repère et le point (x,y)
     *
     * @param X abscisses
     * @param Y ordonées
     * @return norme avec l'origine
     */
    public static double getNorm(double X, double Y) {
        return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
    }

    /**
     * Récupérer la distance par rapport à un autre point
     *
     * @param p point par rapport auquel on veut récupérer la distance
     * @return norme entre les points
     */

    public double getDistance(Position p) {
        double varX = x - p.getX();
        double varY = y - p.getY();
        return getNorm(varX, varY);
    }

    /**
     * (en place) Normaliser la distane entre l'origine et le point
     */
    public void normalize() {
        double norm = getNorm();
        if (norm != 0) {
            x /= norm;
            y /= norm;
        }
    }

    /**
     * Normaliser la distane entre l'origine et le point
     */
    public Position getNormalized() {
        double norm = getNorm();
        x /= norm;
        y /= norm;
        return new Position(x, y, map);
    }

    // ==================== Fonctionnement de la classe ====================
    // Opérateurs

    /**
     * (en place) Multiplier un vecteur par une constante
     *
     * @param fact facteur de multiplication
     */
    public void multiply(double fact) {
        x *= fact;
        y *= fact;
    }

    /**
     * Multiplier un vecteur par une constante
     *
     * @param fact facteur de multiplication
     * @return position
     */
    public Position getMultiplied(double fact) {
        assertMapAttached();
        return new Position(x * fact, y * fact, map);
    }

    /**
     * (en place) Additionner un vecteur à la position
     *
     * @param pos2 positon à additionner
     */
    public void add(Position pos2) {
        x += pos2.getX();
        y += pos2.getY();
    }

    /**
     * Additionner un vecteur à la position
     *
     * @param pos2 positon à additionner
     * @return position
     */
    public Position getAdded(Position pos2) {
        assertMapAttached();
        return new Position(x + pos2.getX(), y + pos2.getY(), map);
    }

    /**
     * (en place) Soustraction d'un vecteur à la position
     *
     * @param pos2 positon à soustraire
     */
    public void substract(Position pos2) {
        x -= pos2.getX();
        y -= pos2.getY();
    }

    /**
     * Soustraction d'un vecteur à la position
     *
     * @param pos2 positon à soustraire
     * @return position
     */
    public Position getSubstracted(Position pos2) {
        assertMapAttached();
        return new Position(x - pos2.getX(), y - pos2.getY(), map);
    }

    /**
     * Renvoie l'angle trigonométrique (en degrés) formé par le vecteur position
     *
     * @return angle en degrés
     */
    public double getAngle() {

        double res;

        if (x != 0 && y != 0) {
            double alpha = Math.atan(Math.abs(y / x)) * 180 / Math.PI; // on ajoute ∏/2 car y est orienté vers le bas
            int quadrant = getQuadrant();
            res = (quadrant + 1) * 90 - alpha;
//        System.out.println("Pour la position " + this + " Angle= " + alpha + " Quadrant= " + quadrant);

        } else if (x == 0 && y == 0) { // centre
            res = 0;

        } else if (x > 0 && y == 0) { // à droite
            res = 0;

        } else if (x == 0 && y < 0) { // au dessus
            res = 90;

        } else if (x < 0 && y == 0) { // à gauche
            res = 180;

        } else if (x == 0 && y > 0) { // en bas
            res = 270;

        } else {
            res = 0;
        }

        synchronized (syncKeySetPos) {
            return res;
        }
    }

    /**
     * Renvoie la quadrant dans lequel pointe le vecteur
     *
     * @return quadrant (0, 1, 2 ou 3)
     */
    public int getQuadrant() {
        int res = 0;

        if (x > 0 && y <= 0) {
            res = 0;

        } else if (x < 0 && y <= 0) {
            res = 1;

        } else if (x < 0 && y > 0) {
            res = 2;

        } else if (x > 0 && y > 0) {
            res = 3;

        }

        return res;
    }

    /**
     * Renvoie le côté vers lequel la direction pointe le plus
     *
     * @return direction
     */
    public HeadedDir getMainDirection() {
        double angle = getAngle();
        HeadedDir res = HeadedDir.RIGHT;

        if (angle <= 45 || angle > 315) {
            res = HeadedDir.RIGHT;

        } else if (angle > 45 && angle <= 135) {
            res = HeadedDir.UP;

        } else if (angle > 135 && angle <= 255) {
            res = HeadedDir.LEFT;

        } else if (angle > 225 && angle <= 315) {
            res = HeadedDir.DOWN;
        }

        return res;
    }

    // Représentation dans la console
    @Override
    public String toString() {
        return "(" + x + " ; " + y + ")";
    }

    public String toString(String txt) {
        return txt + " (" + x + " ; " + y + ")";
    }

    // ==================== Fonctionnement interne ====================

    /**
     * Gestion d'erreur dans le cas où une méthode a besoin qu'une map soit attachée à la position, mais qu'il n'y en a pas.
     */
    private void assertMapAttached() {
        try {
            if (map == null) {
                throw new Exception("L'objet Position ne contient pas de référence à la carte. Utilisez la méthode 'attachMap()'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
