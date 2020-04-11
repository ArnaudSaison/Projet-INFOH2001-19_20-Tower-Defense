package towerdefense.game.model;
public class Tile {
    private int tileX;
    private int tileY;

    public Tile (){
        tileX = 0;
        tileY = 0;
    }



    public int getTileX(){
        return tileX;
    }

    public int getTileY(){
        return tileY;
    }

    public void setTileX(int tileX){
        this.tileX = tileX;
    }

    public void setTileY(int tileY){
        this.tileY = tileY;
    }
}