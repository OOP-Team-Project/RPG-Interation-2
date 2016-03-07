package com.TigersIter2.areaEffects;

import com.TigersIter2.stats.Stats;
import com.TigersIter2.stats.StatsModifier;

/**
 * Created by Nicole on 3/7/16.
 */

public class TakeDamage extends AreaEffect{

    public Stats affectEntity(){
        int reduction = stats.getCurrentLife()/3;
        stats.decreaseCurrentLife(reduction);  // decrements health by 1/3
        return stats;
    }

    public String getEffectName(){
        return "takeDamage";
    }
}
