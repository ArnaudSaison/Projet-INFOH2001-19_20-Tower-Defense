package towerdefense.game.model;

import towerdefense.game.goldmine.GoldMine;
import towerdefense.game.map.Map;
import towerdefense.game.interfaces.*;
import towerdefense.game.npcs.NPC;
import towerdefense.game.towers.Tower;
import java.util.ArrayList;

public class GameModel implements Lootable, Placeable, Drawable, Upgradable, Buyable, ProducesGold {

    private int score;
    private int initHealth;
    private int health;
    private int level;
    private int round;
    private Shop shop;
    private Map map;
    private ArrayList<NPC> NPCs;
    private ArrayList<Tower> towers;
    private ArrayList<GoldMine> goldMines;
    private ArrayList<Lootable> lootables;
    private ArrayList<Placeable> placeables;
    private ArrayList<Drawable> drawables;
    private ArrayList<Upgradable> upgradables;
    private ArrayList<Buyable> buyables;
    private ArrayList<ProducesGold> producesGolds;

    public GameModel() {
    }

    public ArrayList<NPC> getNPCs(){return NPCs;}

    public void initializeLevel(){}

    public void run(){}

    public void killNPC(){}

}