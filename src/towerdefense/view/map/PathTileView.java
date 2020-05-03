package towerdefense.view.map;

import towerdefense.game.map.Map;
import towerdefense.game.map.PathTile;

import java.util.ArrayList;

/**
 * GUI : Classe représentant une case de chemin
 */
public class PathTileView extends TileView {
    // ==================== Attributs ====================


    // ==================== Initialisation ====================

    /**
     * Constructeur de la classe représentant une case de chemin
     */
    public PathTileView(Map map, PathTile tile) {
        super(map, tile); // Appel au constructeur de TileView

        initPathTileView(tile.getConnections());
        initHoverIndicator(false);
    }

    /**
     * Méthode qui renvoie le chemin vers la bonne représentation selon les connections que possède la case
     * */
    private void initPathTileView(ArrayList<PathTile.Connections> co) {
        String res = "grass";
        int rotation = 0;
        double scaleX = 1;
        double scaleY = 1;

//        if (            (co.contains(PathTile.Connections.RIGHT) &&      // ═══
//                        co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM)) ||
//                       (co.contains(PathTile.Connections.RIGHT) &&      // deadend ═══
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM)) ||
//                       (!co.contains(PathTile.Connections.RIGHT) &&     // deadend ═══
//                        co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM))) {
//            res = "grass —";
//
//        } else if (    (!co.contains(PathTile.Connections.RIGHT) &&     // ║
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) ||
//                       (!co.contains(PathTile.Connections.RIGHT) &&     // deadend ║
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) ||
//                       (!co.contains(PathTile.Connections.RIGHT) &&     // deadend ║
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM))) {
//            res = "grass I";
//
//        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ╚═
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass J";
//            scaleX *= -1;
//
//        } else if (     !co.contains(PathTile.Connections.RIGHT) &&      // ═╝
//                        co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass J";
//
//        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ╔═
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass ¬";
//            scaleX *= -1;
//
//        } else if (     !co.contains(PathTile.Connections.RIGHT) &&      // ═╗
//                        co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass ¬";
//
//        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ╠═
//                        !co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass ╣";
//            scaleX *= -1;
//
//        } else if (     !co.contains(PathTile.Connections.RIGHT) &&      // ═╣
//                        co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass ╣";
//
//
//        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ═╩═
//                        co.contains(PathTile.Connections.LEFT) &&
//                        co.contains(PathTile.Connections.TOP) &&
//                        !co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass ⊥";
//
//        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ═╦═
//                        co.contains(PathTile.Connections.LEFT) &&
//                        !co.contains(PathTile.Connections.TOP) &&
//                        co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass T";
//
//        } else if (            co.contains(PathTile.Connections.RIGHT) &&      // ═╬═
//                co.contains(PathTile.Connections.LEFT) &&
//                co.contains(PathTile.Connections.TOP) &&
//                co.contains(PathTile.Connections.BOTTOM)) {
//            res = "grass +";
//
//        }

        if (            (co.contains(PathTile.Connections.RIGHT) &&      // ═══
                co.contains(PathTile.Connections.LEFT) &&
                !co.contains(PathTile.Connections.TOP) &&
                !co.contains(PathTile.Connections.BOTTOM)) ||
                (co.contains(PathTile.Connections.RIGHT) &&      // deadend ═══
                        !co.contains(PathTile.Connections.LEFT) &&
                        !co.contains(PathTile.Connections.TOP) &&
                        !co.contains(PathTile.Connections.BOTTOM)) ||
                (!co.contains(PathTile.Connections.RIGHT) &&     // deadend ═══
                        co.contains(PathTile.Connections.LEFT) &&
                        !co.contains(PathTile.Connections.TOP) &&
                        !co.contains(PathTile.Connections.BOTTOM))) {
            res = "grass —";
            rotation = 180;

        } else if (    (!co.contains(PathTile.Connections.RIGHT) &&     // ║
                !co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) ||
                (!co.contains(PathTile.Connections.RIGHT) &&     // deadend ║
                        !co.contains(PathTile.Connections.LEFT) &&
                        !co.contains(PathTile.Connections.TOP) &&
                        co.contains(PathTile.Connections.BOTTOM)) ||
                (!co.contains(PathTile.Connections.RIGHT) &&     // deadend ║
                        !co.contains(PathTile.Connections.LEFT) &&
                        co.contains(PathTile.Connections.TOP) &&
                        !co.contains(PathTile.Connections.BOTTOM))) {
            res = "grass I";

        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ╚═
                !co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                !co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass ¬";
            rotation = 180;

        } else if (     !co.contains(PathTile.Connections.RIGHT) &&      // ═╝
                co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                !co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass ¬";
            scaleX *= -1;
            rotation = 180;

        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ╔═
                !co.contains(PathTile.Connections.LEFT) &&
                !co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass J";
            rotation = 180;

        } else if (     !co.contains(PathTile.Connections.RIGHT) &&      // ═╗
                co.contains(PathTile.Connections.LEFT) &&
                !co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass J";
            scaleX *= -1;
            rotation = 180;

        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ╠═
                !co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass ╣";
            rotation = 180;

        } else if (     !co.contains(PathTile.Connections.RIGHT) &&      // ═╣
                co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass ╣";
            scaleX *= -1;
            rotation = 180;

        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ═╩═
                co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                !co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass T";
            rotation = 180;

        } else if (     co.contains(PathTile.Connections.RIGHT) &&      // ═╦═
                co.contains(PathTile.Connections.LEFT) &&
                !co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass ⊥";
            rotation = 180;

        } else if (            co.contains(PathTile.Connections.RIGHT) &&      // ═╬═
                co.contains(PathTile.Connections.LEFT) &&
                co.contains(PathTile.Connections.TOP) &&
                co.contains(PathTile.Connections.BOTTOM)) {
            res = "grass +";
            rotation = 180;

        }

        initTexture(res + ".png", rotation, scaleX, scaleY);
    }
}
