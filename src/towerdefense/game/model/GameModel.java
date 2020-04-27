package towerdefense.game.model;

import towerdefense.MainApplication;
import towerdefense.game.*;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.npcs.NPC;
import towerdefense.game.npcs.NPCFactory;
import towerdefense.game.towers.Tower;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel implements Runnable{
    //****** Attributs ******

    //Joueur
    private Player player;

    // Fonctionnement des threads
    private boolean isGameRunning;
    private Thread thread;

    // Attributs de la partie
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
    private ArrayList<Drawable.Lootable> lootables;
    private ArrayList<Placeable> placeables;
    private ArrayList<Drawable> drawables;
    private ArrayList<Upgradable> upgradables;
    private ArrayList<Buyable> buyables;
    private ArrayList<ProducesGold> producesGolds;
    private ArrayList<Movable> movables;

    //****** Initialisations ******
    public GameModel(MainApplication mainApplication) {
        // Initialisation joueur
        Player player = new Player();
        // Initialisation des attributs
        isGameRunning = false;
        score = 0;
        initHealth = 2000;
        health = initHealth;
        level = 1;
        round = 1;

        // Initialisation des listes
        ArrayList<NPC> NPCs = new ArrayList<NPC>();
        ArrayList<GoldMine> goldMines = new ArrayList<GoldMine>();

        // Initialisation des observeurs
        ArrayList<Tower> towers = new ArrayList<Tower>();
        ArrayList<Drawable.Lootable> lootables = new ArrayList<Drawable.Lootable>();
        ArrayList<Placeable> placeables = new ArrayList<Placeable>();
        ArrayList<Drawable> drawables = new ArrayList<Drawable>();
        ArrayList<Upgradable> upgradables = new ArrayList<Upgradable>();
        ArrayList<ProducesGold> producesGolds = new ArrayList<ProducesGold>();
        ArrayList<Movable> movables = new ArrayList<Movable>();

        // Initialisation d'un objet NPC factory
        NPCFactory npcFactory = new NPCFactory();

        // Initialisation de la map
        initializeMap();

        // initialisation d'un objet Shop
        Shop shop = new Shop();
        //shop.getInstance(Shop.Type.CANON_TOWER, );
    }

    public void initializeMap(){
        String workingDirectory = "resources/";
        String mapPath = workingDirectory + "maps/map1";
        String graphicsPath = "towerdefense/gui/game/graphics.css";

        MapFactory mapFactory = new MapFactory();
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
        for (Movable movable : movables){
            movable.move(); // TODO: séparer en plein de treads, car ennemis ne dépenent pas les uns des autres pour se déplacer
        }
    }


    public void killNPC(NPC NPCToKill){
        NPCs.remove(NPCToKill);
    }

    //****** Getteurs & setteurs ******

    public boolean getIsGameRunning() {
        return isGameRunning;
    }

    public void setIsGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getInitHealth() {
        return initHealth;
    }

    public void setInitHealth(int initHealth) {
        this.initHealth = initHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public NPCFactory getNpcFactory() {
        return npcFactory;
    }

    public void setNpcFactory(NPCFactory npcFactory) {
        this.npcFactory = npcFactory;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public ArrayList<GoldMine> getGoldMines() {
        return goldMines;
    }

    public ArrayList<Drawable.Lootable> getLootables() {
        return lootables;
    }

    public ArrayList<Placeable> getPlaceables() {
        return placeables;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public ArrayList<Upgradable> getUpgradables() {
        return upgradables;
    }

    public ArrayList<Buyable> getBuyables() {
        return buyables;
    }
}