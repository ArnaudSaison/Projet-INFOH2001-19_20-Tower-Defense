package towerdefense.game.map;

import towerdefense.game.Drawable;
import towerdefense.view.MapView;
import towerdefense.view.Printable;

import java.util.ArrayList;

/**
 * Classe représentant une carte dans le modèle du jeu
 */
public class Map implements Drawable {
    //==================== Attributs ====================
    // Attributs prorpes au fonctionnement de la carte
    private double pixelsPerMeter;
    private double settingsPixelsPerMeter;
    private double tileMetricWidth;

    private String mapName;
    private int mapTileSizeX;
    private int mapTileSizeY;

    // Listes de cases
    private ArrayList<Tile> tiles;
    private ArrayList<ObstacleTile> obstacles;
    private ArrayList<Tile> gates;

    // Liste des chemins
    private ArrayList<Path> availablePaths;

    // représentation graphique
    private MapView mapView;
    private ArrayList<Drawable> elementsOnMap; // éléments sur la map autres que des tiles

    // ==================== Constructeur ====================

    /**
     * Constructeur de la carte.
     * la création d'une carte requérant une lecture complexe de fichier et gestion d'erreur,
     * celle-ci ne peut être instancée qu'au travers de la MapFactory
     */
    public Map(ArrayList<Tile> tiles, double pixelsPerMeter, double tileMetricWidth, int mapTileSizeX, int mapTileSizeY, String mapName) {
        // Initialisation de tous les attributs
        this.tiles = tiles;
        this.pixelsPerMeter = pixelsPerMeter;
        this.settingsPixelsPerMeter = pixelsPerMeter;
        this.tileMetricWidth = tileMetricWidth;
        this.mapTileSizeX = mapTileSizeX;
        this.mapTileSizeY = mapTileSizeY;
        this.mapName = mapName;

        // Initialisaiton des cases
        gates = new ArrayList<>();
        availablePaths = new ArrayList<>();
        obstacles = new ArrayList<>();

        for (Tile t : tiles) {
            // Initialisation de la forme
            t.attachMap(this);

            // création de liste des entrées de la carte
            if (t instanceof GatePathTile) {
                gates.add(t);
            } else if (t instanceof ObstacleTile) {
                obstacles.add((ObstacleTile) t);
            }
        }

        // Calcul des chemins valides grpace au PathFactory
        PathFactory pathFactory = new PathFactory(this);
        for (Tile gate : gates) {
            ArrayList<Path> computedPaths = pathFactory.getAllPaths(gate);
            availablePaths.addAll(computedPaths);
        }

        // Liste des autres éléments qui se trouvent sur la carte (NPC, tour, mine d'or, ...)
        elementsOnMap = new ArrayList<>();

        // test
        for (Path path : availablePaths) {
            System.out.println(path);
        }
    }

    //==================== Interface Drawable ====================

    /**
     * Initilisation de la vue correpsondant à la carte
     */
    public void initDrawing() {
        mapView = new MapView(this);
    }

    /**
     * Mise à jour de la vue correpsondant à la carte
     * Requiert d'avoir d'abord initialisé la vue avec initDrawing()
     */
    public void updateDrawing() {
        mapView.update();
    }

    /**
     * Récupération de la vue correpsondant à la carte
     * Requiert d'avoir d'abord initialisé la vue avec initDrawing()
     */
    public Printable getDrawing() {
        return mapView;
    }

    /**
     * Retirer la représentation graphique de l'élément de la vue
     * Cette méthode ne sert pas à faire disparaître la représentation en question,
     * mais bien à supprimer toute référence de cette raprésentation et ainsi pouvoir supprimer this
     */
    public void removeDrawing() {
    }

    // ======== Gestion des éléments présents sur la carte n'étant pas des cases ========

    /**
     * Ajout d'un élément à afficher sur la carte
     */
    public void addElementsOnMap(Drawable element) {
        elementsOnMap.add(element);
        mapView.addDrawable(element.getDrawing());
    }

    /**
     * Retrait d'un élément à ne plus afficher sur la carte
     */
    public void removeElementsOnMap(Drawable element) {
        elementsOnMap.remove(element);
        mapView.removeDrawable(element.getDrawing());
    }

    // ==================== Getters et Setters ====================

    /**
     * Récupérer la taille de la carte en nombre de cases selon X
     */
    public int getMapTileSizeX() {
        return mapTileSizeX;
    }

    /**
     * Récupérer la taille de la carte en nombre de cases selon Y
     */
    public int getMapTileSizeY() {
        return mapTileSizeY;
    }

    /**
     * Récupérer toutes les tiles sous forme d'une ArrayList de Tile
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Récupérer toutes les entrées de la carte
     */
    public ArrayList<Tile> getGates() {
        return gates;
    }

    /**
     * Récupérer la case se trouvant à la position (x, y) de la grille
     */
    public Tile getTile(int x, int y) {
        Tile res = null;
        if (x >= 0 && x < mapTileSizeX && y >= 0 && y < mapTileSizeY) {
            // permet de retrouver une case particulière dans la liste des cases selon la positon qu'elle y occupe
            res = tiles.get((y) * mapTileSizeX + (x));
        }
        return res;
    }

    /**
     * Récupére le nom de la carte lu dans le fichier map.properties
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Récupérer l'échelle en pixels par mètres
     * le mètre est l'unité de mesure universelle utilisée par le jeux pour représenter tout objet sur la carte
     */
    public double getPixelsPerMeter() {
        return pixelsPerMeter;
    }

    /**
     * Modifier l'échelle en pixels par mètres
     * le mètre est l'unité de mesure universelle utilisée par le jeux pour représenter tout objet sur la carte
     */
    public void setPixelsPerMeter(double pixelsPerMeter) {
        this.pixelsPerMeter = pixelsPerMeter;
    }

    /**
     * Réinitialiser l'échelle en pixels par mètres à sa valeur par défaut
     * celle-ci est trouvée dans le fichier map.properties
     * le mètre est l'unité de mesure universelle utilisée par le jeux pour représenter tout objet sur la carte
     */
    public void resetPixelsPerMeter() {
        this.pixelsPerMeter = settingsPixelsPerMeter;
    }

    /**
     * Récupérer l'échelle en pixels par mètres à sa valeur par défaut
     * celle-ci est trouvée dans le fichier map.properties
     */
    public double getSettingsPixelsPerMeter() {
        return settingsPixelsPerMeter;
    }

    /**
     * Récupérer la taille d'une case (côté) en mètres
     */
    public double getTileMetricWidth() {
        return tileMetricWidth;
    }

    /**
     * Modifier la taille d'une case (côté) en mètres
     */
    public void setTileMetricWidth(double width) {
        tileMetricWidth = width;
    }

    /**
     * Récupérer la liste des tous les éléments présents sur la carte qui ne sont pas des cases
     * Cette méthode ne doit JAMAIS petre utilisée pour supprimer ou ajouter des éléments sur la carte,
     * autrmentsn ceux-ci n'appraitront pas dans la représentation graphique
     */
    public ArrayList<Drawable> getElementsOnMap() {
        return elementsOnMap;
    }
}
