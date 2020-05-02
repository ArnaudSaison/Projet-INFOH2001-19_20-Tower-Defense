package towerdefense.game;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestGameModel {
    @Test
    public void testGameModel() throws IOException {}

    @Test
    public void testRandomMethod(){
        ArrayList<String> uneListe = new ArrayList<String>();
        uneListe.add("a");
        //uneListe.add("b");
        //uneListe.add("c");

        Random randomGenerator = new Random();

        int randomIndex = randomGenerator.nextInt(uneListe.size());
        System.out.println(uneListe.get(randomIndex));
    }

    @Test
    public void test2() {
        int i = 0;
        while (i < 15) {
            testRandomMethod();
            i++;
        }
    }
}