package towerdefense.game.map;

import towerdefense.game.Drawable;
import towerdefense.view.map.MapView;
import towerdefense.view.Printable;
import towerdefense.view.map.ObstacleTileView;

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

    private int mapTileSizeX;
    private int mapTileSizeY;

    // Listes de cases
    private ArrayList<Tile> tiles;
    private ArrayList<ObstacleTile> obstacles;
    private ArrayList<GatePathTile> gates;

    // Liste des chemins
    private ArrayList<Path> availablePaths;

    // représentation graphique
    private MapView mapView;
    private ArrayList<Drawable> elementsOnMap; // éléments sur la map autres que des tiles

    // ==================== Initilisation ====================

    /**
     * Constructeur de la carte.
     * la création d'une carte requérant une lecture complexe de fichier et gestion d'erreur,
     * celle-ci ne peut être instancée qu'au travers de la MapFactory
     */
    public Map(ArrayList<String> lines, double pixelsPerMeter, double tileMetricWidth) {
        // Initialisation de tous les attributs
        this.tiles = new ArrayList<>();
        this.pixelsPerMeter = pixelsPerMeter;
        this.settingsPixelsPerMeter = pixelsPerMeter;
        this.tileMetricWidth = tileMetricWidth;
        this.mapTileSizeX = lines.get(0).length();
        this.mapTileSizeY = lines.size();

        // Lecture des lignes et construction de la carte
        // éléments qui constitueront la carte
        tiles = new ArrayList<>();

        // Lecture de chaque caractère du fichier et création des cases
        int rowCounter = 0;
        int columnCounter = 0;
        for (String line : lines) {
            columnCounter = 0;
            for (char c : line.toCharArray()) {
                switch (c) {
                    case 'X': // vide
                        tiles.add((Tile) new EmptyTile(columnCounter, rowCounter, this));
                        break;
                    case 'O': // obstacle : type par défaut (arbre)
                    case 'T': // obstacle : arbre
                        tiles.add((Tile) new ObstacleTile(columnCounter, rowCounter, this, ObstacleTileView.ObstacleType.TREE));
                        break;
                    case 'R': // obstacle : rock
                        tiles.add((Tile) new ObstacleTile(columnCounter, rowCounter, this, ObstacleTileView.ObstacleType.ROCK));
                        break;
                    case 'P': // chemin
                        tiles.add((Tile) new PathTile(columnCounter, rowCounter, this));
                        break;
                    case 'G': // entrée (gate)
                        tiles.add((Tile) new GatePathTile(columnCounter, rowCounter, this));
                        break;
                    case 'E': // sortie (exit)
                        tiles.add((Tile) new ExitPathTile(columnCounter, rowCounter, this));
                        break;
                    default:
                        tiles.add((Tile) new EmptyTile(columnCounter, rowCounter, this));
                        break;
                }
                columnCounter++;
            }
            rowCounter++;
        }

        // Initialisaiton des cases
        gates = new ArrayList<>();
        availablePaths = new ArrayList<>();
        obstacles = new ArrayList<>();

        // Création des chemins
        computePaths();

        // Liste des autres éléments qui se trouvent sur la carte (NPC, tour, mine d'or, ...)
        elementsOnMap = new ArrayList<>();
    }

    public void computePaths() {
        gates.clear();
        for (Tile t : tiles) {
            // création de liste des entrées de la carte
            if (t instanceof GatePathTile) {
                gates.add((GatePathTile) t);
            } else if (t instanceof ObstacleTile) {
                obstacles.add((ObstacleTile) t);
            }
        }

        // Calcul des chemins valides grâce au PathFactory
        PathFactory pathFactory = new PathFactory(this);
        for (GatePathTile gate : gates) {
            ArrayList<Path> computedPaths = pathFactory.getAllPaths(gate);
            availablePaths.clear();
            availablePaths.addAll(computedPaths);
            gate.clearPaths();
            gate.attachPaths(computedPaths);
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
    public void addElementOnMap(Drawable element) {
        elementsOnMap.add(element);
        element.initDrawing();
        mapView.addPrintable(element.getDrawing());
    }

    /**
     * Retrait d'un élément à ne plus afficher sur la carte
     */
    public void removeElementOnMap(Drawable element) {
        elementsOnMap.remove(element);
        mapView.removePrintable(element.getDrawing());
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
    public ArrayList<GatePathTile> getGates() {
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
     * Remplacer une case par une autre sur la carte.
     * Attention : opération déifnitive et gourmande en ressource (recalcul de tous les chemins)
     *
     * @param tile case que l'on veut insérer
     * @param x    abscisse de la position à laquelle on veut la mettre
     * @param y    ordonnée de la position à laquelle on veut la mettre
     */
    public void setTile(Tile tile, int x, int y) {
        tiles.set((y) * mapTileSizeX + (x), tile); // remplacement dans la liste des tiles
    }

    /**
     * Renvoie si on peut ou non construire sur toutes les cases d'une zone définie par
     * la position de son coin supérieur gauche et la largeur de la zone carrée considérée
     *
     * @param x    abscisse de la position du coin supérieur gauche
     * @param y    ordonnée  position du coin supérieur gauche
     * @param size largeur de la zone carrée (en nombre de cases)
     * @return true si toutes les cases dans la zone sont libres
     */
    public boolean canBeBuiltOn(int x, int y, int size) {
        boolean blocked = false;
        int i = 0;
        int j = 0;
        while (!blocked && i < size) {
            while (!blocked && j < size) {
                blocked = getTile(x + i, y + j).getBlockedState();
                j++;
            }
            i++;
        }
        return !blocked;
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

    public void setSettingsPixelsPerMeter(double pixelsPerMeter) {
        this.settingsPixelsPerMeter = pixelsPerMeter;
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
