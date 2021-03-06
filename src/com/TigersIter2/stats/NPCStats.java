package com.TigersIter2.stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magic_Buddha on 3/4/2016.
 */
public class NPCStats extends Stats {

    private List<NPCStatsModifier> mods;

    public NPCStats() {
        super();
        movement = 4;
        mods = new ArrayList<>();
        influenceRadius = 0;
    }

    @Override
    public int getArmorRating() {
        return hardiness;
    }

    @Override
    public int getOffensiveRating() {
        return attack;
    }

    @Override
    public int getDefensiveRating() {
        return agility;
    }

    public void addStatModifier(NPCStatsModifier sm) {
        mods.add( sm );
        this.hardiness += sm.getHardiness();
        this.attack += sm.getAttack();
        this.agility += sm.getAgility();
        this.attackTime += sm.getAttackTime();
        this.influenceRadius += sm.getInfluenceRadius();
    }

    public void removeStatModifier(NPCStatsModifier sm) {
        if ( mods.contains( sm ) ) {
            mods.remove( sm );
            this.hardiness -= sm.getHardiness();
            this.attack -= sm.getAttack();
            this.agility -= sm.getAgility();
            this.attackTime -= sm.getAttackTime();
            this.influenceRadius -= sm.getInfluenceRadius();
        }
    }
}
