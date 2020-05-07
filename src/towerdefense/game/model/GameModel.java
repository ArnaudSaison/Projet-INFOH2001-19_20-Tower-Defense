package towerdefense.game.model;

import towerdefense.Config;
import towerdefense.game.Drawable;
import towerdefense.game.Hittable;
import towerdefense.game.Placeable;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.npcs.NPC;
import towerdefense.game.towers.Tower;
import towerdefense.game.waves.Wave;
import towerdefense.game.waves.WaveFactory;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel implements Runnable {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    // Getion du thread:
    private boolean running;
    private boolean paused;
    private Thread gameThread;

    // Attributs du jeu:
    private int timeBeforeBeginning;

    private Config config;
    private Wave wave;
    private WaveFactory waveFactory;
    private Map map;
    private Player player;
    private Shop shop;

    // Eléments de la carte:
    private ArrayList<Hittable> hittables; //listeners des projectiles à effet de zone.
    private ArrayList<NPC> NPCsOnMap;
    private ArrayList<Tower> towers;
    private ArrayList<GoldMine> goldMines;

  
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    /**
     * Constructeur du jeu
     */
    public GameModel(Config config, String mapPath) throws IOException {
        // Configuration
        this.config = config;

        //Initialisation des éléments de la carte:
        NPCsOnMap = new ArrayList<>();
        hittables = new ArrayList<>();
        towers = new ArrayList<>();
        goldMines = new ArrayList<>();

        //Initialisation du joueur:
        player = new Player(config.getInitPlayerGold(), config.getInitPlayerHealth());

        //Initialisation de la carte:
        MapFactory mapFactory = new MapFactory();
        map = mapFactory.getMap(mapPath);

        // Initialisation du shop
        shop = new Shop(map, this, "resources/shops/shop.properties");

        //Initialisation de la première vague:
        waveFactory = new WaveFactory(map, this, mapPath);
        wave = waveFactory.getWave("easy");

        //Initilisation du thread:
        this.gameThread = new Thread(this);
        running = false;
        paused = true;

        //Initialisation du joueur:
        player = new Player(100, 100);
    }

    /*==================================================================================================================
                                                   GESTION DES THREADS
    ==================================================================================================================*/

    /**
     * Routine du thread
     */
    public void run() {
        while (running) {
            try {
                while (!paused) {
                    int max = wave.getLength() ;
                    for (int i = 0; i < max; i++){
                        NPC nextNPC = wave.getNextEnemy();
                        initializeElement(nextNPC);
                        Thread.sleep(2000); // place et démarre un NPC toutes les secondes.
                        player.increaseScore(); //augmentation du score du joueur.

                        // tests
                        System.out.println("============================Vague actuelle==============================");
                        wave.toPrint();
                        System.out.println("============================Le prochain NPC est : \n" + nextNPC.toString());

                    }if(!NPCsOnMap.isEmpty()) {
                        Thread.sleep(5000);//Attend qu'il n'y ait plus de NPC sur la carte avant de relancer une nouvelle vague.
                        pauseGame();
                    }
                    wave = waveFactory.getNextWave(wave);
                }
                while (paused) {
                Thread.sleep(1000);
                System.out.println("Le jeu est en pause");
                }

                Thread.sleep(1 / config.getModelFrameRate());
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void initialize() {
        gameThread.start();
        running = true;
        paused = false;
    }

    /**
     * Mettre le jeu en pause
     * appelle tous les threads de tous les objets pour les mettre en pause
     */
    public void pauseGame() {
        paused = true;
    }

    /**
     * Relancer le jeu après une pause
     * appelle tous les threads de tous les objets pour arrêter la pause
     */
    public void resumeGame() {
        paused = false;
    }

    /**
     * Arrête le jeu
     * appelle tous les threads de tous les objets pour les mettre en pause
     */
    public void stopGame() {
        paused = true;
        running = false;
    }

    /*==================================================================================================================
                                            GESTION ELEMENTS SUR LA CARTE
    ==================================================================================================================*/
    public void killNPC(NPC npc) {
        if (!npc.getIsArrived()) {
            player.increaseGold(npc.getGoldLoot());
        } else {
            player.decreaseHealth(npc.getHealthLoot());
        }
        map.removeElementOnMap(npc); //TODO: à mettre/remettre pour test
        NPCsOnMap.remove(npc);
    }

    public void initializeElement(NPC npc) {
        initializePlaceable(npc);
        NPCsOnMap.add(npc);
    }

    public void initializeElement(Tower tower) {
        initializePlaceable(tower);
        towers.add(tower);
    }

    public void initializeElement(GoldMine goldMine) {
        initializePlaceable(goldMine);
        goldMines.add(goldMine);
    }

    public void initializePlaceable(Placeable element) {
        map.addElementOnMap((Drawable) element); // Ajoute l'élément sur la carte TODO: mettre/remettre
        element.initialize(); // Démarre le thread
    }


    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    public Config getConfig() {
        return config;
    }

    public ArrayList<NPC> getNPCsOnMap() {
        return NPCsOnMap;
    }

    public Wave getWave(){ return wave;}

    public Player getPlayer() {
        return player;
    }

    public Boolean getPaused() {
        return paused;
    }

    public Boolean getRunning() {
        return running;
    }

    public ArrayList<Hittable> getHittables(){
        return hittables;
    }

    public Map getMap() {
        return map;
    }

    public Shop getShop() {
        return shop;
    }

    public ArrayList<GoldMine> getGoldMines(){
        return goldMines;
    }

    public ArrayList<Tower> getTowers(){
        return towers;
    }
}