package towerdefense.game.model;

import towerdefense.game.Hittable;
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
    private Shop shop;
    private WaveFactory waveFactory;
    private Wave wave;
    private Player player;

    //Eléments de la carte:
    private Map map;

    // Objets sur la carte:
    private ArrayList<Hittable> hittables; //listeners des projectiles à effet de zone.
    private ArrayList<NPC> NPCsOnMap;
    private ArrayList<Tower> towers;
    private ArrayList<GoldMine> goldMines;

  
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public GameModel() throws IOException { //TODO: rajouter config en argument!
        //Initialisation des éléments de la carte:
        NPCsOnMap = new ArrayList<>();
        hittables = new ArrayList<>();
        towers = new ArrayList<>();
        goldMines = new ArrayList<>();

        //Initialisation de la carte:
        MapFactory mapFactory = new MapFactory();
        map = mapFactory.getMap("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        //Initialisation de la première vague:
        waveFactory = new WaveFactory(map, this, "C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");
        wave = waveFactory.getWave("easy");

        //Initilisation du thread:
        this.gameThread = new Thread();
        running = false;
        paused = true;

        //Initialisation du joueur:
        player = new Player(100, 100);

        //Initialisation du magasin:
        shop = new Shop(map,this,"C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\shops\\shop");
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
                    int max = wave.getLength();
                    for (int i = 0; i < max; i++){
                        NPC nextNPC = wave.getNextEnemy();
                        initializeNPC(nextNPC);
                        Thread.sleep(2000); // place et démarre un NPC toutes les secondes.
                        player.increaseScore(); //augmentation du score du joueur.
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
        running = false;
    }

    /*==================================================================================================================
                                            GESTION ELEMENTS SUR LA CARTE
    ==================================================================================================================*/
    public void initializeNPC(NPC npc) {
        //map.addElementOnMap(npc); TODO à remettre
        //npc.initialize();
        NPCsOnMap.add(npc);
        npc.setOnMap(true);
    }

    public void initializeTower(Tower tower) {
        //map.addElementOnMap(tower); TODO à remettre
        tower.initialize(); //Démarre le tread de la tour.
        player.decreaseGold(tower.getCost());// le joueur paie uniquement au moment où la mine d'or est placée sur la carte
        towers.add(tower);
    }

    public void initializeGoldMine(GoldMine goldMine) {
        //map.addElementOnMap(goldMine); TODO à remettre
        goldMine.initialize();
        player.decreaseGold(goldMine.getCost());
        goldMines.add(goldMine);
    }

    public void killNPC(NPC npc) {
        if (!npc.getIsArrived()) {
            player.increaseGold(npc.getGoldLoot());
        } else {
            player.decreaseHealth(npc.getHealthLoot());
        }
        NPCsOnMap.remove(npc);
    }

    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
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

    public ArrayList<Hittable> getHittables(){return hittables;}

    /*==================================================================================================================
                                                       TESTS
    ==================================================================================================================*/

    public Wave donneVague() {
        return wave;
    }

    public Shop getShop() {
        return shop;
    }

    public ArrayList<GoldMine> getGoldMines(){return goldMines;}
    public ArrayList<Tower> getTowers(){return towers;}
}