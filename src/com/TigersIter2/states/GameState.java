package com.TigersIter2.states;

import com.TigersIter2.maps.Map;
import com.TigersIter2.states.State;


public class GameState extends State {

    private Map map;
    //private EntityManager entityManager;
    //private ItemManager itemManager;

    //private Player player;

    public GameState(){
        map = new Map();
        //itemManager = new ItemManager();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
