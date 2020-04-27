package towerdefense.game;

import org.junit.Test;
import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.game.towers.GenericTower;

import java.util.ArrayList;

public class TestGameModel {
    @Test
    public void test(){
        //map bidon
        Tile tile = new Tile(1,1,2);
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        tiles.add(tile);
        Map map = new Map(tiles, 2.0,2.0,1,1,"name");

        //test sur un objet generic tower
        GenericTower t = new GenericTower(map);
        System.out.println(t.toStringTower());
    }
}
//TODO: faire les tests pour vérifier le fonctionnement du GameModel.

