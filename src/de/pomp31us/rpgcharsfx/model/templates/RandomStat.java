/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.templates;

import de.pomp31us.rpgcharsfx.model.dices.DString;
import de.pomp31us.rpgcharsfx.model.dices.DiceMechanic;
import de.pomp31us.rpgcharsfx.model.campaigndata.Hero;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author viona-173
 */
public class RandomStat implements Serializable{
    private String name;
    private String defaultvalue = "";
    private DiceMechanic method = null;

    /**
     * Gets rolls the value for the random stat and adds it as an Ability to a Hero.
     * @param hero The hero to be trained in the ability.
     */
    
    public void train(Hero hero){
        DString result;
        if (method == null) {hero.addNewAbl(name, defaultvalue);
                            return;}
        else result = method.apply(new DString(0));
       try{
        hero.addNewAbl(name + result.getCritlog().toString().replaceAll("\\["," ").replaceAll("\\]",""), defaultvalue + result.toString());
       }
       catch (NullPointerException e){
           hero.addNewAbl(name, defaultvalue + result.toString());
       }
        
    }
    
    
    /**
     * Creates a list of new RandomStats sharing a common DiceMechanic.
     * @param commonMechanic The single mechanic to be part of all RandomStats.
     * @param stats The names of the stats to be created.
     * @return An ArrayList of RandomStats.
     */
    
    public static RandomChar list(DiceMechanic commonMechanic, String... stats){
      RandomChar list = new RandomChar();
      for (String stat : stats) list.add(new RandomStat(stat,commonMechanic));
      return list;
    }
    
    
    /**
     * Creates a list of Randomstat (of type RandomChar), adding this stat and the argument.
     * @param that the second element to the list.
     * @return A list of type RandomChar.
     */
    
    public RandomChar conjoin(RandomStat that){
        RandomChar thislist = new RandomChar();
        thislist.add(this);
        thislist.add(that);
        return thislist;
    }
    
     /**
     * Creates a list of Randomstat (of type RandomChar), adding this stat and the argument's stats.
     * @param thatlist The later elements of the new list.
     * @return A list of type RandomChar.
     */
    
    public RandomChar conjoin(RandomChar thatlist){
        RandomChar thislist = new RandomChar();
        thislist.add(this);
        thislist.addAll(thatlist);
        return thislist;
    }
    
    public RandomStat(String name, DiceMechanic method) {
        this.name = name;
        this.method = method;
    }

    public RandomStat(String name, String defaultvalue) {
        this.name = name;
        this.defaultvalue = defaultvalue;
    }

    public RandomStat(String name, String defaultvalue, DiceMechanic method) {
        this.name = name;
        this.defaultvalue = defaultvalue;
        this.method = method;
    }
       
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiceMechanic getMethod() {
        return method;
    }

    public void setMethod(DiceMechanic method) {
        this.method = method;
    }
    
}
