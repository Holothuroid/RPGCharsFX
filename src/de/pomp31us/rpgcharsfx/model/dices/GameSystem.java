/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.dices;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author viona-173
 */

@XmlRootElement
public class GameSystem extends ArrayList<DiceMechanic>{
    
    /**
     * Factory method to produce a GameSystem from one or more dice mechanics.
     * @param mechanics Vararg of DiceMechanics
     * @return The GameSystem.
     */
    
    public static GameSystem from(DiceMechanic... mechanics){
    GameSystem system = new GameSystem();
    for(DiceMechanic mech : mechanics) system.add(mech);
    return system;
}   
    
    /**
     * Turns this GameSystem together with a number of other DiceMechanics into a list of GameSystems.
     * @param mechanics The mechanics in the secondary GameSystem.
     * @return An ArrayList of GameSytems made from this and the new mechanics.
     */
    
    public ArrayList<GameSystem> with(DiceMechanic... mechanics){
    ArrayList<GameSystem> output = new ArrayList<>();
    output.add(this);
    output.add(GameSystem.from(mechanics));
    return output;
    }
    
     /**
     * Factory method to produce a GameSystem from one or more dice mechanics, then wrap it into an ArrayList.
     * @param mechanics Vararg of DiceMechanics
     * @return A list containing a single GameSystem.
     */
    
    public static ArrayList<GameSystem> fromwith(DiceMechanic... mechanics){
    ArrayList<GameSystem> output = new ArrayList<>();
    output.add(GameSystem.from(mechanics));
    return output;
    }
    
}
