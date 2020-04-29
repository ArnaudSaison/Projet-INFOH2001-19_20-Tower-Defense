package towerdefense.game.model;

import towerdefense.Config;
import towerdefense.game.npcs.NPC;

import java.util.ArrayList;

public class GameModel implements Runnable{
    // Thread
    private boolean running;
    private boolean paused;
    private Thread gameThread;

    // Attributs du jeu
    private int timeBeforeBeginning;
    private ArrayList<WaveFactory> waves;

    // Eléments de la carte

    // ====================== Initilisation ======================
    /**Constructeur du jeu
     * */
    public GameModel(Config config) {
        // ********** Initialisation des attributs **********


        // ********** Wave Factory **********
        WaveFactory waveFactory = WaveFactory;
        ArrayList<WaveFactory> waves = waveFactory.getWaves();

        // ********** initilisation du thread **********
        this.gameThread = new Thread();
    }

    public void initialize() {
        gameThread.start();
    }

    // ====================== Gestion des Threads ======================
    /**Routine du thread
     * */
    public void run() {
        int lastWave = waves.size() - 1;
        int currentWave = 0;
        boolean lastNPC = false;

        while(running) {
            while(!paused) {
                if (!lastNPC) {
                    NPC npc = waves.get(currentWave).getNextEnemy();
                    lastNPC = waves.get(currentWave).isFinished();
                } else if (currentWave <= lastWave) {
                    currentWave ++;
                } else {
                    for () {
                        wave.nextLevel();
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void placeNPC() {

    }

    /**Mettre le jeu en pause
     * appelle tous les threads de tous les objets pour les mettre en pause
     * */
    public void pauseGame() {
        paused = true;
    }

    /**Relancer le jeu après une pause
     * appelle tous les threads de tous les objets pour arrêter la pause
     * */
    public void resumeGame() {
        paused = false;
    }

    /**Arrête le jeu
     * appelle tous les threads de tous les objets pour les mettre en pause
     * */
    public void stopGame() {
        running = false;
    }

    // ====================== Getters et Setters ======================

}