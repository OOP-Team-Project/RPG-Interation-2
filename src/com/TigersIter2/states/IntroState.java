package com.TigersIter2.states;

import com.TigersIter2.assets.StaticVar;
import com.TigersIter2.assets.sprites.IntroSprite;
import com.TigersIter2.assets.sprites.SmasherSprite;
import com.TigersIter2.entities.Avatar;
import com.TigersIter2.entities.Smasher;
import com.TigersIter2.views.AvatarView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by slichtenheld on 2/29/2016.
 */
public class IntroState extends State {

    private String name;

    private int counter;

    public IntroState(StateManager stateManager){
        super(stateManager);
    }

    @Override
    public void init() {

        counter = 0;
        //pull in picture for intro screen - Sam
        IntroSprite.init();
        System.out.println("IntroState initialized");
        name = "IntroState";


        SmasherSprite.init();
        Avatar avatar = new Avatar();
        avatar.setOccupation(new Smasher());
        AvatarView avatarView = new AvatarView(avatar);

        this.add(avatarView);
    }

    @Override
    public void update() {
        counter++;
        System.out.println("IntroState counter: " + counter);
        if (counter >= 100) {
            stateManager.setState(stateManager.GAME);
            counter = 0;
        }
        System.out.println(isVisible());
    }

//    @Override
//    public void draw(Graphics g) {
//        Graphics2D g2d = (Graphics2D)g.create();
//        g2d.drawImage(IntroSprite.introImage,0,0,null);
//        g2d.dispose();
//    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0,0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.RED);
        g2d.drawString("Intro paintComponent. Components: " + this.getComponentCount(), 260, 150);
        g2d.dispose();
        System.out.println("WTF");
    }


    @Override
    public void handleInput() {

    }

    @Override
    public String returnName() {
        return name;
    }


}
