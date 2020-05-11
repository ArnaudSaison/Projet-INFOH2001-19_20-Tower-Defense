package towerdefense.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.map.Tile;
import towerdefense.game.npcs.HeadedDir;

import java.util.ArrayList;

public class PositionTest {

    public Map getTestMap() {
        String l1 = "XX";
        String l2 = "XX";
        ArrayList<String> tiles = new ArrayList<>();
        tiles.add(l1);
        tiles.add(l2);

        return new Map(tiles, 10, 2);
    }

    @Test
    public void testAngle1(){ // cas limite
        Position pos = new Position(0.0, 0.0);
        int quadrant = pos.getQuadrant();
        double angle = pos.getAngle();
        HeadedDir mainDirection = pos.getMainDirection();

        assertEquals(0, quadrant);
        assertEquals(0, angle, 0.01);
        assertEquals(HeadedDir.RIGHT, mainDirection);
    }

    @Test
    public void testAngle3(){ // cas limite
        Position pos = new Position(1.0, 0.0);
        int quadrant = pos.getQuadrant();
        double angle = pos.getAngle();
        HeadedDir mainDirection = pos.getMainDirection();

        assertEquals(0, quadrant);
        assertEquals(0, angle, 0.01);
        assertEquals(HeadedDir.RIGHT, mainDirection);
    }

//    @Test
//    public void testAngle2(){
//        Position pos = new Position(5.0, 4.0);
//        int quadrant = pos.getQuadrant();
//        double angle = pos.getAngle();
//        HeadedDir mainDirection = pos.getMainDirection();
//
//        assertEquals(3, quadrant);
//        assertEquals(321.34, angle, 0.01);
//        assertEquals(HeadedDir.RIGHT, mainDirection);
//    }

    @Test
    public void testAngle5(){ // cas limite
        Position pos = new Position(0.0, -1.0);
        int quadrant = pos.getQuadrant();
        double angle = pos.getAngle();
        HeadedDir mainDirection = pos.getMainDirection();

        assertEquals(0, quadrant);
        assertEquals(90, angle, 0.01);
        assertEquals(HeadedDir.UP, mainDirection);
    }

//    @Test
//    public void testAngle6(){
//        Position pos = new Position(1.0, 0.0);
//        int quadrant = pos.getQuadrant();
//        double angle = pos.getAngle();
//        HeadedDir mainDirection = pos.getMainDirection();
//
//        assertEquals(0, quadrant);
//        assertEquals(0, angle, 0.01);
//        assertEquals(HeadedDir.RIGHT, mainDirection);
//    }

    @Test
    public void testAngle7(){ // cas limite
        Position pos = new Position(-1.0, 0.0);
        int quadrant = pos.getQuadrant();
        double angle = pos.getAngle();
        HeadedDir mainDirection = pos.getMainDirection();

        assertEquals(1, quadrant);
        assertEquals(180, angle, 0.01);
        assertEquals(HeadedDir.LEFT, mainDirection);
    }

//    @Test
//    public void testAngle8(){
//        Position pos = new Position(-1.0, -1.0);
//        int quadrant = pos.getQuadrant();
//        double angle = pos.getAngle();
//        HeadedDir mainDirection = pos.getMainDirection();
//
//        assertEquals(0, quadrant);
//        assertEquals(0, angle, 0.01);
//        assertEquals(HeadedDir.RIGHT, mainDirection);
//    }

    @Test
    public void tesadd() {
        Map map = getTestMap();
        Position pos1 = new Position(4.0, 5.0, map);
        Position pos2 = new Position(2.0, -3.0, map);
        Position resActual = pos1.getAdded(pos2);
        Position resExpected = new Position(6.0, 2.0);

        assertEquals(resExpected.getX(), resActual.getX(), 0.1);
        assertEquals(resExpected.getY(), resActual.getY(), 0.1);
    }

    @Test
    public void tesNorm() {
        Map map = getTestMap();
        double normActual = new Position(4.0, 3.0, map).getNorm();
        double normExpected = 5.0;

        assertEquals(normExpected, normActual, 0.1);
    }

    @Test
    public void tesNormalized() {
        Map map = getTestMap();
        Position normActual = new Position(4.0, 3.0, map).getNormalized();
        Position normExpected = new Position(4.0 / 5.0, 3.0 / 5.0);

        assertEquals(normExpected.getX(), normActual.getX(), 0.1);
        assertEquals(normExpected.getY(), normActual.getY(), 0.1);
    }
}
