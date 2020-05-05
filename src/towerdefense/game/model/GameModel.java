package towerdefense.game.model;

import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.npcs.NPC;
import towerdefense.game.waves.Wave;
import towerdefense.game.waves.WaveFactory;

import java.io.IOException;
import java.util.ArrayList;


public class GameModel implements Runnable{
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    // Thread
    private boolean running;
    private boolean paused;
    private Thread gameThread;

    // Attributs du jeu
    private int timeBeforeBeginning;
    private Wave wave;
    private ArrayList<NPC> NPCsOnMap;
    private WaveFactory waveFactory;
    private Map map;
    Player player;

    // Eléments de la carte

  
    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    /**Constructeur du jeu
     * */
    public GameModel() throws IOException { //TODO: rajouter config en argument!
        // ********** Initialisation des attributs **********
        NPCsOnMap = new ArrayList<>();

        //************Map*********************
        MapFactory mapFactory = new MapFactory();
        map = mapFactory.getMap("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

        // ********** Wave Factory **********
        waveFactory = new WaveFactory(map,this,"C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");
        wave = waveFactory.getWave("easy",0,0);

        // ********** initilisation du thread **********
        this.gameThread = new Thread();

        //***********initialisation du joueur***********
        player = new Player(100,100);
    }

    /*==================================================================================================================
                                                   GESTION DES THREADS
    ==================================================================================================================*/
    /**Routine du thread
     * */

    //TODO : mettre le gameModel en pause via le sleep, ça va freezer le jeu ?
    public void run() {
        while(running) {
            try{
                while(!paused) {
                    if (!wave.isFinished()) {
                        NPC nextNPC = wave.getNextEnemy();
                        placeNPC(nextNPC);
                        nextNPC.initialize();
                        Thread.sleep(1000); // place et démarre un NPC toutes les secondes.
                        wave.affiche();
                        System.out.println("=========================================================================");
                    } else {
                        while(!NPCsOnMap.isEmpty()){
                            Thread.sleep(100); //Attend qu'il n'y ait plus de NPC sur la carte avant de relancer une nouvelle vague.
                        }
                        wave = waveFactory.getNextWave(wave);
                        System.out.println("*************************Wave***************************************");
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            try{
                while(paused){
                        Thread.sleep(100);
                    }
            }catch (InterruptedException e) {
                        e.printStackTrace(); }
            }
    }


    public void initialize(){
    gameThread.start();
    running = true;
    paused =false;
    }

    public void placeNPC(NPC npc) {
        npc.setOnMap(true);
        NPCsOnMap.add(npc);
    }

    public void killNPC(NPC npc){
        if (!npc.getIsArrived()) {
            incrementPlayerGold(npc);
            incrementPlayerHealth(npc);
        }else{
            decrementPlayerHealth(npc);
        }
        NPCsOnMap.remove(npc);
    }


    public void incrementPlayerGold(NPC npc){player.increaseGold(npc.getGoldLoot());}

    public void incrementPlayerHealth(NPC npc){player.increaseHealth(npc.getHealthLoot());}

    public void decrementPlayerHealth(NPC npc){player.decreaseHealth(npc.getHealthLoot());}

    /**Mettre le jeu en pause
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
                                                   GETTEURS/SETTEURS
    ==================================================================================================================*/
    public ArrayList<NPC> getNPCsOnMap(){return NPCsOnMap;}

    public Player getPlayer(){return player;}

    public Boolean getPaused(){return paused;}

    /*==================================================================================================================
                                                       TESTS
    ==================================================================================================================*/

    public Wave donneVague() {
        return wave;
    }
}