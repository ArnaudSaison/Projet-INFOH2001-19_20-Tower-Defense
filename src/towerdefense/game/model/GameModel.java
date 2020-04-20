package towerdefense.game.model;

import towerdefense.MainApplication;
import towerdefense.game.*;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel implements runnable{
    // fonctionnement des threads
    private boolean isGameRunning;
    private Thread thread;

    // attributs de la partie
    private int score;
    private int initHealth;
    private int health;
    private int level;
    private int round;

    // Autres classes principales avec lequelles il faut comuniquer
    private Shop shop;
    private Map map;
    private NPCFactory npcFactory;

    // Liste des éléments du jeu
    private ArrayList<NPC> NPCs;
    private ArrayList<Tower> towers;
    private ArrayList<GoldMine> goldMines;

    // Liste des interfaces
    private ArrayList<Lootable> lootables;
    private ArrayList<Placeable> placeables;
    private ArrayList<Drawable> drawables;
    private ArrayList<Upgradable> upgradables;
    private ArrayList<Buyable> buyables;
    private ArrayList<ProducesGold> producesGolds;

    // Initialisations
    public GameModel(MainApplication mainApplication) {
        //========== Initilisation des attributs ==========
        isGameRunning = false;

        //========== Initialisation des listes ==========
        ArrayList<NPC> NPCs = new ArrayList<NPC>();

        NPCFactory npcFactory = new NPCFactory();

        //========== Map ==========
        initializeMap();

        //========== Shop ==========

    }

    public void initializeMap(){
        String workingDirectory = "resources/";
        String mapPath = workingDirectory + "maps/map1";
        String graphicsPath = "towerdefense/gui/game/graphics.css";

        mapFactory = new MapFactory();
        try {
            map = mapFactory.getMap(mapPath);
            map.getStylesheets().add(graphicsPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        mapPlaceHolder.getChildren().add(0, map);
    }

    /** Démarre le thread du jeu dans lequel vont être démarrés tous les autres threads
    * */
    public void initializeLevel(String t){
        //========== Autres initilisations ==========


        //========== Lancement du thread ==========

    }

    public void run(){
        // Lancement des threads des goldmines

        // Lancement des tours (pas des threads)

        //
    }

    public void killNPC(){}

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public NPCFactory getNpcFactory() {
        return npcFactory;
    }

    public void setNpcFactory(NPCFactory npcFactory) {
        this.npcFactory = npcFactory;
    }
}