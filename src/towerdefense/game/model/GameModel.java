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
    private Wave wave;
    private WaveFactory waveFactory;
    private Map map;
    Player player;

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
    public GameModel() throws IOException { //TODO: rajouter config en argument!
        //Initialisation des éléments de la carte:
        NPCsOnMap = new ArrayList<>();
        hittables = new ArrayList<>();

        //Initialisation de la carte:
        MapFactory mapFactory = new MapFactory();
        map = mapFactory.getMap("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        //Initialisation de la première vague:
        waveFactory = new WaveFactory(map, this, "C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");
        wave = waveFactory.getWave("easy", 0, 0);

        //Initilisation du thread:
        this.gameThread = new Thread();

        //Initialisation du joueur:
        player = new Player(100, 100);
    }

    /*==================================================================================================================
                                                   GESTION DES THREADS
    ==================================================================================================================*/

    /**
     * Routine du thread
     */

    //TODO : mettre le gameModel en pause via le sleep, ça va freezer le jeu ?  ++Gérer la fin du jeu
    public void run() {
        while (running) {
            try {
                while (!paused) {
                    if (!wave.isFinished()) {
                        NPC nextNPC = wave.getNextEnemy();
                        initializeNPC(nextNPC);
                        Thread.sleep(1000); // place et démarre un NPC toutes les secondes.
                        wave.affiche();
                        System.out.println("=========================================================================");
                    } else if (!NPCsOnMap.isEmpty()) {
                        Thread.sleep(100); //Attend qu'il n'y ait plus de NPC sur la carte avant de relancer une nouvelle vague.
                    }
                    wave = waveFactory.getNextWave(wave);
                    System.out.println("*************************Wave***************************************");
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        try {
            while (paused) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        //map.addElementOnMap(npc);
        npc.initialize();
        NPCsOnMap.add(npc);
        npc.setOnMap(true);
    }

    public void killNPC(NPC npc) {
        if (!npc.getIsArrived()) {
            player.increaseGold(npc.getGoldLoot());
        } else {
            player.decreaseHealth(npc.getHealthLoot());
        }
        NPCsOnMap.remove(npc);
    }

    public void initializeTower(Tower tower) {
        //map.addElementOnMap(tower);
        tower.initialize(); //Démarre le tread de la tour.
        player.decreaseGold(tower.getCost());// le joueur paie uniquement au moment où la mine d'or est placée sur la carte
        towers.add(tower);
    }

    public void initializeGoldMine(GoldMine goldMine) {
        //map.addElementOnMap(goldMine);
        goldMine.initialize();
        player.decreaseGold(goldMine.getCost());
        goldMines.add(goldMine);
    }

    /*==================================================================================================================
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    public ArrayList<NPC> getNPCsOnMap() {
        return NPCsOnMap;
    }

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
}