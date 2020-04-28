package towerdefense.game.projectiles;

public class Shell extends Projectile {

    public Shell(int damage) {
        super();
        super.damage = damage;
    }
    @Override
    public String toString(){
        return getClass().getName() +super.toString();
    }
}
