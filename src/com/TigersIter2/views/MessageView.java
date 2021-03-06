package com.TigersIter2.views;

import com.TigersIter2.assets.StaticVar;
import com.TigersIter2.entities.Avatar;
import com.TigersIter2.entities.Equipment;
import com.TigersIter2.entities.Inventory;
import com.TigersIter2.items.TakeableItem;
import com.TigersIter2.stats.PlayerStats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageView extends View {

    private static boolean display = false;
    private static boolean newMessage = false;
    private static List<String> messageList = new ArrayList<String>();
    private static List<String> oldMessageList = new ArrayList<String>();
    private static List<Integer> xPos = new ArrayList<Integer>();
    private static List<Integer> yPos = new ArrayList<Integer>();
    private static Timer messageTimer = new Timer(800, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            display = false;
            xPos.clear();
            yPos.clear();
            messageList.clear();
        }
    });

    public MessageView(){
        setPreferredSize(new Dimension(StaticVar.gameWidth - 400, 200));
    }

    public static void addMessage(String msg){
        messageList.add(msg);
        xPos.add(-1);
        yPos.add(-1);
    }

    public static void addMessage(String msg, int x, int y){
        messageList.add(msg);
        int translatedX, translatedY;

        if((float) (View.cameraLocation.getX())/StaticVar.terrainImageWidth < ((float) StaticVar.xTilesFromEdge)) {
            translatedX = Math.round(x*.75f - 80);
        }
        else if((float) (View.cameraLocation.getX())/StaticVar.terrainImageWidth > (mapXLength - StaticVar.xTilesFromEdge + 1)) {

            translatedX = Math.round((x - ((mapXLength - StaticVar.xTilesFromEdge*2 + 1) * StaticVar.terrainImageWidth))*.75f - 80);
        }
        else {
            translatedX = Math.round((StaticVar.xTilesFromEdge*StaticVar.terrainImageWidth - View.cameraLocation.getX() + x*.75f - 80));
        }

        //Y Stuff Below
        if((float) (View.cameraLocation.getY())/StaticVar.terrainImageHeight < (float) StaticVar.yTilesFromEdge) {
            //tileViews.get(i).get(j).setCurrentYLocation(j);
            //((TileView) getComponent((i * tileViews.get(0).size()) + j)).setCurrentYLocation(j);
            translatedY = Math.round(y - Math.round(StaticVar.terrainImageHeight*1.2f));
        }
        else if((float) (View.cameraLocation.getY())/StaticVar.terrainImageHeight > (mapYLength - StaticVar.yTilesFromEdge)) {
            //tileViews.get(i).get(j).setCurrentYLocation(j - tileViews.get(0).size() + StaticVar.yTilesFromEdge*2);
            //((TileView) getComponent((i * tileViews.get(0).size()) + j)).setCurrentYLocation(j - tileViews.get(0).size() + StaticVar.yTilesFromEdge*2);
            translatedY = Math.round((y - ((mapYLength - StaticVar.yTilesFromEdge*2) * StaticVar.terrainImageHeight)) - Math.round(StaticVar.terrainImageHeight*1.2f));
        }
        else {
            //tileViews.get(i).get(j).setCurrentYLocation(j - (float) (View.cameraLocation.getY()) / StaticVar.terrainImageHeight + StaticVar.yTilesFromEdge);
            translatedY = Math.round(StaticVar.yTilesFromEdge*StaticVar.terrainImageHeight - Math.round(StaticVar.terrainImageHeight*1.2f)) - View.cameraLocation.getY() + y;
            //((TileView) getComponent((i * tileViews.get(0).size()) + j)).setCurrentYLocation(j - (float) (aHandle.getLocation().getY()) / StaticVar.terrainImageHeight + StaticVar.yTilesFromEdge);
        }


        xPos.add(translatedX);
        yPos.add(translatedY);
    }

    public static void drawMessage(){
        display = true;
        newMessage = true;
        oldMessageList.clear();
        for(String s : messageList)
            oldMessageList.add(s);
    }


    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        //gotta use that AA
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if(display) {
            if(newMessage) {
                messageTimer.restart();
                newMessage = false;
            }
            for(int i = 0; i < oldMessageList.size(); ++i) {
                if (xPos.get(i) == -1 && yPos.get(i) == -1) {
                    FontMetrics fm = g2d.getFontMetrics();
                    Rectangle2D r = fm.getStringBounds(oldMessageList.get(i), g2d);
                    xPos.set(i,(StaticVar.gameWidth - (int) r.getWidth()) / 2);
                    yPos.set(i,(StaticVar.gameHeight - (int) r.getHeight()) / 2 + fm.getAscent() - 100);
                }
                g2d.setColor(Color.black);
                g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g2d.drawString(oldMessageList.get(i), xPos.get(i) - 2, yPos.get(i) + 2);
                g2d.setColor(Color.red);
                g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g2d.drawString(oldMessageList.get(i), xPos.get(i), yPos.get(i));
            }
        }


        g2d.dispose();
    }

}
