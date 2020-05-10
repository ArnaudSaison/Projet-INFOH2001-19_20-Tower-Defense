package towerdefense.game.waves;

import towerdefense.game.npcs.NPC;

import java.util.ArrayList;
import java.util.Random;

public class Wave {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    // Liste des ennemis qui composent la vague:
    private ArrayList<NPC> waveNPCs;

    // Prochain ennemi à entrer sur la carte:
    private NPC nextNPC;

    private String difficulty;

    /*==================================================================================================================
                                                   METHODES
    ==================================================================================================================*/
    public Wave(ArrayList<NPC> NPCs, String difficulty) {
        this.waveNPCs = NPCs;
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public NPC getNextEnemy() {
        setNextEnemy();
        return nextNPC;
    }

    public int getLength() {
        return waveNPCs.size();
    }

    public boolean isFinished() {
        return waveNPCs.isEmpty();
    }

    /**
     * Choisit de manière aléatoire un NPC parmi tous les NPCs qui composent la vague
     */
    public void setNextEnemy() {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(waveNPCs.size());
        nextNPC = waveNPCs.get(randomIndex);
        waveNPCs.remove(randomIndex);
    }

    //==============================================Tests===============================================================
    public void toPrint() {
        for (NPC e : waveNPCs) {
//            System.out.println(e.toString());
        }
    }
}
