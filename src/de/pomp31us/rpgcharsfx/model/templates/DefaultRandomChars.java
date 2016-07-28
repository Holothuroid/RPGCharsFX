/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.templates;

import java.util.ArrayList;
import de.pomp31us.rpgcharsfx.model.dices.DString;
import static de.pomp31us.rpgcharsfx.model.templates.DefaultMechanics.*;


/**
 *
 * @author viona-173
 */
public enum DefaultRandomChars {
    DnDRandomChars(RandomStat.list(DnDcreation.getMech(), "Strength", "Constitution", "Dexterity", "Intelligence", "Charisma", "Wisdom")),
    OSHRandomChars(RandomStat.list(OSHcreation.getMech(), "Brawn", "Cunning", "Daring", "Commitment", "Charm", "Awareness" ).conjoin(new RandomStat("Attack", "0!"))),
    
    ;
    
    ArrayList<RandomStat> stats;

    private DefaultRandomChars(ArrayList<RandomStat> list) {
        this.stats = list;
    }

    public static DefaultRandomChars getDnDRandomChars() {
        return DnDRandomChars;
    }

    public ArrayList<RandomStat> getStats() {
        return stats;
    }
    
    
}
