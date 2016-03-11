package com.TigersIter2.managers;

import com.TigersIter2.areaEffects.*;
import com.TigersIter2.entities.Avatar;
import com.TigersIter2.entities.Entity;
import com.TigersIter2.entities.NPC;
import com.TigersIter2.location.Location;
import com.TigersIter2.location.LocationConverter;
import com.TigersIter2.stats.Stats;
import com.TigersIter2.stats.StatsModifier;

/**
 * Created by Nicole on 3/7/16.
 */
public class AreaEffectManager {

    private Entity entityOnTile;
    private AreaEffect areaEffect;
    private InstantDeath instantDeath;
    private TakeDamage takeDamage;
    private HealDamage healDamage;
    private LevelUp levelUp;


    public AreaEffectManager(Entity entity, AreaEffect effectType, Location l){
        entityOnTile = entity;
        areaEffect = effectType;
        areaEffect.setLocation(l);
        instantDeath = new InstantDeath();
        takeDamage = new TakeDamage();
        healDamage = new HealDamage();
        levelUp = new LevelUp();
    }

    public void affectEntityOnTile(){
        areaEffect.affectEntity(entityOnTile);
    }

    public void checkTile(){
            if(LocationConverter.PixelLocationToHex(entityOnTile.getLocation()).getX() == LocationConverter.PixelLocationToHex(areaEffect.getLocation()).getX() &&
                    LocationConverter.PixelLocationToHex(entityOnTile.getLocation()).getY() == LocationConverter.PixelLocationToHex(areaEffect.getLocation()).getY())
            {
                affectEntityOnTile();
            }
    }

}
