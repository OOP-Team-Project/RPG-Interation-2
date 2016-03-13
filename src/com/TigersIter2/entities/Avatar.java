package com.TigersIter2.entities;

import com.TigersIter2.assets.FileReader;
import com.TigersIter2.assets.StaticVar;
import com.TigersIter2.items.TakeableItem;
import com.TigersIter2.location.Location;
import com.TigersIter2.location.LocationConverter;
import com.TigersIter2.skills.SkillTree;
import com.TigersIter2.stats.PlayerStats;
import com.TigersIter2.stats.Stats;
import com.TigersIter2.stats.StatsModifier;
import com.sun.org.apache.bcel.internal.generic.INEG;


public class Avatar extends Entity{

    private Location location;  //This is the location used by MODELS to determine where the avatar is
    private Location pixelLocation; //This is the location used by VIEWS to determine where the avatar is (Miles)
    private Inventory inventory;
    private Equipment equipment;
    private Occupation occupation;
    private Pet pet;
    private Vehicle vehicle;
    private PlayerStats stats;
    private SkillTree skills;

    private int direction;
    private boolean canPassWater;
    private boolean canPassMountain;
    private int timeUntilAttack = 0;

    private boolean currentlyMoving = false;
    private boolean onTileWithNPC = false;
    private boolean trading;


    public Avatar(){
        //changed this to actually instantiate location. Not sure what Z is for atm. <-- Z is for hextile stuff in the future (Sam)
        location = new Location(10 * StaticVar.terrainImageWidth,10 * StaticVar.terrainImageHeight,0);
        System.out.println("Location: " + 10 * StaticVar.terrainImageWidth + ", " + 10 * StaticVar.terrainImageHeight);
        pixelLocation = new Location(Math.round(StaticVar.xTilesFromEdge*StaticVar.terrainImageWidth*.75f - 80), Math.round(StaticVar.yTilesFromEdge*StaticVar.terrainImageHeight - Math.round(StaticVar.terrainImageHeight*1.2f)), 0);
        System.out.println("PixelLocation: " + Math.round(StaticVar.xTilesFromEdge*StaticVar.terrainImageWidth*.75f - 80) + ", " + Math.round(StaticVar.yTilesFromEdge*StaticVar.terrainImageHeight - Math.round(StaticVar.terrainImageHeight*1.2f)));
        direction = 270;
        canPassMountain = false; //if anything this should be under skills (Sam)
        canPassWater = false;
        inventory = new Inventory();
        equipment = new Equipment();
        trading = false;
        setOccupation();
    }

    public void saveAvatar(){
        String saveString = "location:\n"+location.getX()+" "+location.getY()+"\n";
        saveString.concat("pixelLocation:\n"+pixelLocation.getX()+" "+pixelLocation.getY()+"\n");
        saveString.concat("direction:\n"+direction+"\n");
        saveString.concat("grass:\ntrue\n");
        saveString.concat("water:\n"+canPassWater +"\n");
        //needs to save location 1050,960
        //needs to save pixelLocation 629,269
        //direction 279
        //can pass mountain, water false false
        // inventory
        //equipment
        //money

    }

    //What is this supposed to do? -Sam
    public void setLocation(Location l) {
        location = l;
    }

    //Should be named updatePosition -Sam
    @Override
    public void update(int xMovement, int yMovement, long elapsed) {
        if(xMovement == 0 && yMovement == 0){
            currentlyMoving = false;
        }
        else{
            location.incrementX(Math.round(xMovement * elapsed * StaticVar.entitySpeed * stats.getMovement()));   //Made it invariant of framerate
            location.incrementY(Math.round(yMovement * elapsed * StaticVar.entitySpeed * stats.getMovement()));
            changeDirection(xMovement, yMovement);
            currentlyMoving = true;
        }

        if(vehicle != null){
            vehicle.update(xMovement * stats.getMovement(), yMovement * stats.getMovement(), elapsed);
        }
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Equipment getEquipment(){
        return equipment;
    }
    
    public PlayerStats getStats(){
        return stats;
    }

    public void equipItemAtIndex(int i){
        if(inventory.getItemAtIndex(i).isEquippable())
            equipment.addItem(inventory.removeItemAtIndex(i));
        else
            System.out.println("That item isn't equippable!");
    }

    public void unequipItemAtIndex(int i){
        if(!equipment.isEmpty())
            inventory.addItem(equipment.removeItemAtIndex(i));
        else
            System.out.println("There is nothing to unequip!");
    }

    public void dropItem(TakeableItem item){
        inventory.getItems().remove(item);
        int xLoc = location.getX();
        int yLoc = location.getY()+100;

        item.setLocation(new Location(xLoc, yLoc, 0));
        item.setPixelLocation(pixelLocation);
        item.setDisplay(true);
    }

    public void mountOrUnmountVehicle(Vehicle v){
        if(vehicle == null) {
            vehicle = v;
            canPassWater = v.getCanPassWater();
            canPassMountain = v.getCanPassMountain();
            stats.addStatModifier(v.getStatsModifier());
        }
        else{
            vehicle = null;
            canPassMountain = false;
            canPassWater = false;
            stats.removeStatModifier(v.getStatsModifier());
        }
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public void setPet(Pet p){
        pet = p;
    }

    public Pet getPet(){
        return pet;
    }

    public int getDirection(){
        return direction;
    }

    public boolean getCanPassWater(){
        return canPassWater;
    }

    public boolean getCanPassMountain(){
        return canPassMountain;
    }

    public Location getLocation() {
        return location;
    }

    private void setOccupation(){

        String[] map1String = FileReader.fileToString(StaticVar.avatarFile).split("\\s+"); //splits up file on any white space
        int occ = FileReader.stringToInt(map1String[0]); //first number of file
        System.out.println("first number of avatar file: " + occ);
        switch(occ){
            case 1: occupation = new Smasher();
                break;
            case 2: occupation = new Summoner();
                break;
            case 3: occupation = new Sneak();
                break;
            default: occupation = new Smasher();
                System.out.println("Error loading occupation");
                break;
        }
        //occupation = o;
        stats = new PlayerStats(occupation);
        skills = new SkillTree(stats);
    }

    public Occupation getOccupation(){
        return occupation;
    }

    public SkillTree getSkills(){
        return skills;
    }

    private void changeDirection(int x, int y){
        if(x == 0){
            if(y > 0)
                direction = 270;
            else if(y < 0)
                direction = 90;
        }
        else if(x > 0){
            if(y > 0)
                direction = 315;
            else if(y < 0)
                direction = 45;
        }
        else{
            if(y > 0)
                direction = 225;
            else if(y < 0)
                direction = 135;
        }
    }

    public boolean isCurrentlyMoving() {
        return currentlyMoving;
    }

    public Location getPixelLocation() {
        return pixelLocation;
    }

    public void setPixelLocation(Location pixelLocation) {
        this.pixelLocation = pixelLocation;
    }

    public void setOnTileWithNPC(boolean b){
        onTileWithNPC = b;
    }

    public boolean getOnTileWithNPC(){
        return onTileWithNPC;
    }

    public void setTrading(boolean t){
        trading = t;
    }

    public boolean getTrading(){
        return trading;
    }

    public int getTimeUntilAttack(){
        return timeUntilAttack;
    }

    public void decrementTimeUntilAttack(){
        if(timeUntilAttack > 0)
            --timeUntilAttack;
    }

    public void resetTimeUntilAttack(){
        timeUntilAttack = stats.getAttackTime();
    }

    public void takeDamage(int attackStrength){
        //calculate some sort of damage
        int damageTaken = attackStrength;
        stats.decreaseCurrentLife(damageTaken);
        System.out.println("Taking damage");
    }

    //added this getter method to get the player stats for item manager -- Breanna
    public PlayerStats getPlayerStats() { return stats; }

    public String getWeaponType(){
        String ret = equipment.getWeaponType();
        if(ret.equals("none")){
            if(occupation.toString().equals("Summoner"))
                ret = "Staff";
            else if(occupation.toString().equals("Smasher"))
                ret = "Brawling";
        }
        return ret;
    }


}