package towerdefense.game;

import org.junit.Test;

import java.io.File;
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

    File mappFolder = new File("C:\\Users\\Pedro\\Desktop\\INFO\\Projet-INFOH2001-19_20-Tower-Defense\\resources\\maps\\map1");

    public ArrayList<File> getAllMapSpecificationsFiles(File mapFolder){
        ArrayList<File> res = new ArrayList<File>();
        for (File fileEntry : mapFolder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getAllMapSpecificationsFiles(fileEntry);
            } else {
                res.add(fileEntry);
                System.out.println(fileEntry.getName());
            }
        }
        return res;
    }

    @Test
    public void test4(){
        int waveNumber = 1;
        String a = waveNumber+"easyStandardProportion";
        System.out.println(a);

    }
}