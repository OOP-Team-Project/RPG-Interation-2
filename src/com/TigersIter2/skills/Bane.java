package com.TigersIter2.skills;

import com.TigersIter2.entities.NPC;
import com.TigersIter2.stats.PlayerStats;

/**
 * Created by Magic_Buddha on 3/5/2016.
 */

/**
 * This skill attacks an entity using magic damage
 * derived from int and skill level. Mana cost increases as the skill levels up.
 * Requires handle to playerStats on intantiation
 * Requires handle to target npc
 */
public class Bane extends ActiveSkill {

    private final int MANA_COST = 5;
    private final int MAGIC = 30;
    private final int MAGIC_PER_LEVEL = 15;
    private final int MANA_COST_LEVEL_MULTIPLIER = 3;

    private int magicDamage;
    private int manaCost;
    private double probability;
    private PlayerStats playerStats;

    public Bane(PlayerStats playerStats) {
        super();
        this.playerStats = playerStats;
        magicDamage = 0;
        manaCost = MANA_COST;
        probability = 0.0;
    }

    /**
     * update method gets called on each level up.
     */
    @Override
    protected void update() {
        probability = .5 + .1 * skillLevel;
        magicDamage = skillLevel * MAGIC_PER_LEVEL + MAGIC;
        manaCost = MANA_COST + skillLevel * MANA_COST_LEVEL_MULTIPLIER;
    }

    /**
     * returns true if attack successful.
     */
    public boolean activate(NPC target) {
        if ( skillLevel > 0 && Math.random() < probability && playerStats.getCurrentMana() >= manaCost) {
            playerStats.decreaseCurrentMana(manaCost);
            // target.attack(magicDamage + playerStats.getOffensiveRating());
            return true;
        } else return false;
    }

    public String toString() {
        return "Bane";
    }
}
