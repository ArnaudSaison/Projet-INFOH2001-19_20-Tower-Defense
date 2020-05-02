package towerdefense.game.waves;

import towerdefense.game.npcs.NPC;

import java.util.ArrayList;
import java.util.Random;

public class Wave {
    // Liste des ennemis qui composent la vague:
    private ArrayList<NPC> waveNPCs;
    private String difficulty;
    // Prochain ennemi a entrer sur la carte:
    private NPC nextNPC;

    // Occurrence de la vague:
    private int occurrence;

    public Wave(ArrayList<NPC> NPCs, String difficulty, int occurrence ) {
        this.waveNPCs = NPCs;
        this.difficulty = difficulty;
        this.occurrence = occurrence;

    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getOccurrence(){return occurrence;}

    public NPC getNextEnemy(){
        setNextEnemy();
        return nextNPC;}

    public boolean isFinished() {
        return waveNPCs.isEmpty();
    }

    /**Choisit de manière aléatoire un NPC parmi tous les NPCs qui composent la vague*/
    public void setNextEnemy() {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(waveNPCs.size());
        nextNPC = waveNPCs.get(randomIndex);
        waveNPCs.remove(randomIndex);
    }

    // tests:
    public void affiche(){
        for(NPC e :waveNPCs){
            System.out.println(e.toString());
        }
    }
}
