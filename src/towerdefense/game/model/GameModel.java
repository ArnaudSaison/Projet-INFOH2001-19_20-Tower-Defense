package towerdefense.game.model;

import towerdefense.Config;
import towerdefense.game.Drawable;
import towerdefense.game.Hittable;
import towerdefense.game.Placeable;
import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.npcs.NPC;
import towerdefense.game.projectiles.Projectile;
import towerdefense.game.towers.Tower;
import towerdefense.game.waves.Wave;
import towerdefense.game.waves.WaveFactory;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel implements Runnable {
    private final Object syncKeyKillNPC = new Object();
    private final Object syncKeyKillTower = new Object();
    private final Object syncKeyKillGoldMine = new Object();
    private final Object syncKeyKillProjectile = new Object();
    private final Object syncKeyInitNPC = new Object();
    private final Object syncKeyInitTower = new Object();
    private final Object syncKeyInitGoldMine = new Object();
    private final Object syncKeyInitProjectile = new Object();
    private final Object syncKeyAccessNPCsOnMap = new Object();

    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    // Getion du thread:
    private boolean running;
    private boolean paused;
    private Thread gameThread;

    // Attributs du jeu:
    private Config config;
    private Wave wave;
    private WaveFactory waveFactory;
    private Map map;
    private Player player;
    private Shop shop;
    private int round;
    private boolean gameOver;

    // Eléments de la carte:
    private ArrayList<Hittable> hittables; //listeners des projectiles à effet de zone.
    private ArrayList<NPC> NPCsOnMap;
    private ArrayList<Tower> towers;
    private ArrayList<GoldMine> goldMines;
    private ArrayList<Placeable> placeables;

  
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
        placeables = new ArrayList<>();

        //Initialisation du joueur:
        player = new Player(this, config.getInitPlayerGold(), config.getInitPlayerHealth());

        //Initialisation de la carte:
        MapFactory mapFactory = new MapFactory();
        map = mapFactory.getMap(mapPath);

        // Initialisation du shop
        shop = new Shop(map, this, "resources/shops/shop.properties");

        //Initialisation de la première vague:
        waveFactory = new WaveFactory(map, this, mapPath);
        wave = waveFactory.getWave(config.getDifficulty());
        round = 0;

        //Initilisation du thread:
        this.gameThread = new Thread(this);
        running = false;
        paused = true;
    }

    /*==================================================================================================================
                                                   GESTION DES THREADS
    ==================================================================================================================*/

    /**
     * Routine du thread
     */
    @Override
    public void run() {
        int timer = 0; // une itération de timer dure (1 / (FPS * 1000)) milisecondes
        double sleepTime = 1.0 / config.getModelFrameRate() * 1000; // temps d'une itération (en ms)
        int timeBetweenNPCs = (int) Math.round(config.getTimeBetweenNPCs() / sleepTime * 1000); // nombre d'itérations de timer
        int timeBetweenRounds = (int) Math.round(config.getTimeBetweenRounds() / sleepTime * 1000);
        boolean endOfWave = false;

        try {
            while (running) { // si le jeu est en cours
                if (!paused) { // si le jeu n'est pas en pause
                    if (timer == 0) { // si le timer est temriné
                        if (!endOfWave) { // si la la vague en cours n'est pas terminée
                            NPC nextNPC = wave.getNextEnemy(); // récupération du prochain NPC
                            initializeElement(nextNPC); // ajout du NPC sur la carte

                            endOfWave = wave.isFinished(); // réévaluation de la fin de la vague
                            timer = timeBetweenNPCs; // ajout d'un temps avant le prochain NPC

                        } else { // si la vague est terminée
                            if (NPCsOnMap.isEmpty()) { // si la map est vide
                                wave = waveFactory.getNextWave(wave); // initilisation de la prochaine vague
                                round++; // variable de l'objet pour que le vue puisse la récupérer
                                endOfWave = false; // début de la prochaine vague enclenché
                                timer = timeBetweenRounds; // ajout d'un temps avant la prochaine vague une fois que la carte est vide
                            }
                        }
                    } else {
                        timer--;
                    }
                }

                Thread.sleep((long) sleepTime);

                // tests
                //                        System.out.println("============================Vague actuelle==============================");
                //                        wave.toPrint();
                //                        System.out.println("============================Le prochain NPC est : \n" + nextNPC.toString());
            }
        } catch (IOException | InterruptedException exception) { // gestion des possibles erreurs lors de l'exécution du thread
            exception.printStackTrace();
        }
    }


    public void initialize() {
        // Attributs permettant à tous les threads de connaître l'état du jeu
        running = true;
        paused = false;

        gameThread.start(); // démarrage des vagues

        player.initialize(); // Démarrage du joueur

        // Il faut démarrer tous les threads de toutes les tours et mines d'or déjà posées
        for (Placeable placeable : placeables) {
            placeable.initialize();
        }
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

    public void setGameOver() {
        gameOver = true;
        running = false;
    }

    /*==================================================================================================================
                                            GESTION ELEMENTS SUR LA CARTE
    ==================================================================================================================*/
    // ========== gestion dans le modèle ==========
    // NPC
    public void initializeElement(NPC npc) {
        synchronized (syncKeyInitNPC) {
            initializePlaceable(npc);
            NPCsOnMap.add(npc);
        }
    }

    public void killElement(NPC npc) {
        synchronized (syncKeyKillNPC) {
            if (NPCsOnMap.contains(npc)) { // on vérifie s'il n'a pas déjà été retiré (pour éviter de tuer deux fois un NPC
                if (!npc.getIsArrived()) {
                    player.increaseGold(npc.getGoldLoot());
                } else {
                    player.decreaseHealth(npc.getHealthLoot());
                }

                killPlaceable(npc);
                NPCsOnMap.remove(npc);
            }
        }
    }

    // Tower
    public void initializeElement(Tower tower) {
        synchronized (syncKeyInitTower) {
            initializePlaceable(tower);
            towers.add(tower);
        }
    }

    public void killElement(Tower tower) {
        synchronized (syncKeyKillTower) {
            if (towers.contains(tower)) {
                killPlaceable(tower);
                towers.remove(tower);
            }
        }
    }

    // GoldMine
    public void initializeElement(GoldMine goldMine) {
        synchronized (syncKeyInitGoldMine) {
            initializePlaceable(goldMine);
            goldMines.add(goldMine);
        }
    }

    public void killElement(GoldMine goldMine) {
        synchronized (syncKeyKillGoldMine) {
            if (goldMines.contains(goldMine)) {
                killPlaceable(goldMine);
                goldMines.remove(goldMine);
            }
        }
    }

    // Projectile
    public void initializeElement(Projectile projectile) {
        synchronized (syncKeyInitProjectile) {
            initializePlaceable(projectile);
        }
    }

    public void killElement(Projectile projectile) {
        synchronized (syncKeyKillProjectile) {
            killPlaceable(projectile);
        }
    }

    // ========== Représentation graphique ==========
    public void initializePlaceable(Placeable element) {
        map.addElementOnMap((Drawable) element); // Ajoute l'élément sur la carte TODO: mettre/remettre pour le test unitaire
        element.initialize(); // Démarre le thread
        placeables.add(element);
    }

    public void killPlaceable(Placeable element) {
        map.removeElementOnMap((Drawable) element); // suppression de l'élément de la carte //TODO: à mettre/remettre pour test pour le test unitaire
        placeables.remove(element);
    }


    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    public Config getConfig() {
        return config;
    }

    public ArrayList<NPC> getNPCsOnMap() {
        synchronized (syncKeyAccessNPCsOnMap) {
            return NPCsOnMap;
        }
    }

    public ArrayList<NPC> getNPCsOnMapClone() {
        return new ArrayList<>(NPCsOnMap);
    }

    public Wave getWave() {
        return wave;
    }

    public Player getPlayer() {
        return player;
    }

    public Boolean getPaused() {
        return paused;
    }

    public Boolean isRunning() {
        return running;
    }

    public ArrayList<Hittable> getHittables() {
        return hittables;
    }

    public Map getMap() {
        return map;
    }

    public Shop getShop() {
        return shop;
    }

    public ArrayList<GoldMine> getGoldMines() {
        return goldMines;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public int getRound() {
        return round;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}