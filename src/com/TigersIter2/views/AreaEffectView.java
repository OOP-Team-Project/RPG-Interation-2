package com.TigersIter2.views;

import com.TigersIter2.areaEffects.AreaEffect;
import com.TigersIter2.assets.StaticVar;
import com.TigersIter2.assets.sprites.AreaEffectSprite;
import com.TigersIter2.assets.sprites.ItemSprite;
import com.TigersIter2.entities.Avatar;
import com.TigersIter2.items.Interactive;
import com.TigersIter2.items.Item;
import com.TigersIter2.location.Location;
import com.TigersIter2.maps.TerrainMap;
import java.awt.*;

public class AreaEffectView extends View {

    Avatar aHandle;
    AreaEffect aEffectHandle;
    Location pixelLocation;
    int mapXLength, mapYLength = 0;

    public AreaEffectView(){
        setPreferredSize(new Dimension(StaticVar.gameWidth, StaticVar.gameHeight));
    }

    public AreaEffectView(AreaEffect ae, Avatar a, TerrainMap map) {
        aHandle = a;
        aEffectHandle = ae;
        mapXLength = map.getMapWidth();
        mapYLength = map.getMapHeight();
        pixelLocation = new Location(-1, -1, -1);

        setPreferredSize(new Dimension(StaticVar.gameWidth, StaticVar.gameHeight));
    }

    private void drawItem(Graphics2D g2d){
        if(aEffectHandle.getAreaEffectType() == 0) {
            g2d.drawImage(AreaEffectSprite.healDamage, pixelLocation.getX(), pixelLocation.getY(), null);
        }
        else if(aEffectHandle.getAreaEffectType() == 1){
            g2d.drawImage(AreaEffectSprite.instantDeath, pixelLocation.getX(), pixelLocation.getY(), null);
        }
        else if(aEffectHandle.getAreaEffectType() == 2){
            g2d.drawImage(AreaEffectSprite.levelUp, pixelLocation.getX(), pixelLocation.getY(), null);
        }
        else if(aEffectHandle.getAreaEffectType() == 3){
            g2d.drawImage(AreaEffectSprite.takeDamage, pixelLocation.getX(), pixelLocation.getY(), null);
        }
        else if(aEffectHandle.getAreaEffectType() == 4) {
            g2d.drawImage(AreaEffectSprite.teleport, pixelLocation.getX(), pixelLocation.getY(), null);
        }
        else if(aEffectHandle.getAreaEffectType() == 5) {
            g2d.drawImage(AreaEffectSprite.trap, pixelLocation.getX(), pixelLocation.getY(), null);
        }
    }

    public void paintComponent(Graphics g){
        if(aEffectHandle.getDisplay()) {
            if(pixelLocation.getX() == -1)
                pixelLocation.setX(aEffectHandle.getLocation().getX());
            if(pixelLocation.getY() == -1)
                pixelLocation.setY(aEffectHandle.getLocation().getY());

            switch (View.currentMapMode) {
                case PLAYER_FOLLOW_MODE:

                    if ((float) (aHandle.getLocation().getX()) / StaticVar.terrainImageWidth < ((float) StaticVar.xTilesFromEdge)) {
                        pixelLocation = (new Location(Math.round(aEffectHandle.getLocation().getX() * .75f - 80), pixelLocation.getY(), 0));

                    } else if ((float) (aHandle.getLocation().getX()) / StaticVar.terrainImageWidth > (mapXLength - StaticVar.xTilesFromEdge + 1)) {
                        pixelLocation = (new Location(Math.round((aEffectHandle.getLocation().getX() - ((mapXLength - StaticVar.xTilesFromEdge * 2 + 1) * StaticVar.terrainImageWidth)) * .75f - 80), pixelLocation.getY(), 0));
                    } else {
                        pixelLocation = (new Location(Math.round((aEffectHandle.getLocation().getX() - aHandle.getLocation().getX() + StaticVar.xTilesFromEdge * StaticVar.terrainImageWidth) * .75f) - 80, pixelLocation.getY(), 0));

                    }

                    //Y Stuff Below
                    if ((float) (aHandle.getLocation().getY()) / StaticVar.terrainImageHeight < (float) StaticVar.yTilesFromEdge) {
                        pixelLocation = (new Location(pixelLocation.getX(), Math.round(aEffectHandle.getLocation().getY() - Math.round(StaticVar.terrainImageHeight * 1.2f)), 0));
                    } else if ((float) (aHandle.getLocation().getY()) / StaticVar.terrainImageHeight > (mapYLength - StaticVar.yTilesFromEdge)) {
                        pixelLocation = (new Location(pixelLocation.getX(), Math.round((aEffectHandle.getLocation().getY() - ((mapYLength - StaticVar.yTilesFromEdge * 2) * StaticVar.terrainImageHeight)) - Math.round(StaticVar.terrainImageHeight * 1.2f)), 0));
                    } else {
                        pixelLocation = (new Location(pixelLocation.getX(), aEffectHandle.getLocation().getY() - aHandle.getLocation().getY() + Math.round(StaticVar.yTilesFromEdge * StaticVar.terrainImageHeight) - StaticVar.terrainImageHeight - 20, 0));
                    }
                    break;

                case SCROLL_MODE:
                    if ((float) (View.cameraLocation.getX()) / StaticVar.terrainImageWidth < ((float) StaticVar.xTilesFromEdge)) {
                        pixelLocation = (new Location(Math.round(aEffectHandle.getLocation().getX() * .75f - 80), pixelLocation.getY(), 0));
                    } else if ((float) (View.cameraLocation.getX()) / StaticVar.terrainImageWidth > (mapXLength - StaticVar.xTilesFromEdge + 1)) {
                        pixelLocation = (new Location(Math.round((aEffectHandle.getLocation().getX() - ((mapXLength - StaticVar.xTilesFromEdge * 2 + 1) * StaticVar.terrainImageWidth)) * .75f - 80), pixelLocation.getY(), 0));
                    } else {
                        pixelLocation = (new Location(Math.round((aEffectHandle.getLocation().getX() - View.cameraLocation.getX() + StaticVar.xTilesFromEdge * StaticVar.terrainImageWidth) * .75f) - 80, pixelLocation.getY(), 0));

                    }

                    //Y Stuff Below
                    if ((float) (View.cameraLocation.getY()) / StaticVar.terrainImageHeight < (float) StaticVar.yTilesFromEdge) {
                        pixelLocation = (new Location(pixelLocation.getX(), Math.round(aEffectHandle.getLocation().getY() - Math.round(StaticVar.terrainImageHeight * 1.2f)), 0));
                    } else if ((float) (View.cameraLocation.getY()) / StaticVar.terrainImageHeight > (mapYLength - StaticVar.yTilesFromEdge)) {
                        pixelLocation = (new Location(pixelLocation.getX(), Math.round((aEffectHandle.getLocation().getY() - ((mapYLength - StaticVar.yTilesFromEdge * 2) * StaticVar.terrainImageHeight)) - Math.round(StaticVar.terrainImageHeight * 1.2f)), 0));
                    } else {
                        pixelLocation = (new Location(pixelLocation.getX(), aEffectHandle.getLocation().getY() - View.cameraLocation.getY() + Math.round(StaticVar.yTilesFromEdge * StaticVar.terrainImageHeight) - StaticVar.terrainImageHeight - 20, 0));
                    }

                default:
                    break;
            }

            Graphics2D g2d = (Graphics2D) g.create();
            //gotta use that AA
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            drawItem(g2d);

            g2d.dispose();
        }
    }

}