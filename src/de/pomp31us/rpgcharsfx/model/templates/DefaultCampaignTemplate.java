/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.templates;

import de.pomp31us.rpgcharsfx.model.dices.GameSystem;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;

import static de.pomp31us.rpgcharsfx.model.templates.DefaultMechanics.*;
import static de.pomp31us.rpgcharsfx.model.templates.DefaultRandomChars.*;

/**
 *
 * @author 1of3
 */
public enum DefaultCampaignTemplate implements Serializable {
 
    None("The default template",
           "The default template. Nothing to see here.",
            new ArrayList<GameSystem>(),
            new RandomChar()),
    
    DnD("Dungeons & Dragons", 
            "Dungeons & Dragons\n"
            + "The classic game with the standard rules for D&D3+. \n\n"
            + "Primary System: \n 0! : +D20 (with natural results)"
            + "\n\n Secondary System: \n"
            + "0! Sum the damage dice", 
            GameSystem.from(plusD20noteAll.getMech()).with(sum.getMech()),
            DnDRandomChars.getStats() ),
    
    OSH("Old School Hack", 
            "Old School Hack\n"
            + "It's free. Get it from oldschoolhack.net\n\n"
            + "Primary System:\n"
            + "0! +1d12\n"
            + "1! Attack: +2d10 (1 Face Die)\n"
            + "2! Attack with two Face Dice. (You are a fighter, apparently.)",
            
            GameSystem.fromwith(plusD12.getMech(), OSHAttack.getMech(), OSHAttack2.getMech()), 
            OSHRandomChars.getStats()),
    
    
    Fudge("Fudge / Fate (4dF)", 
            "Fudge or Fate\n"
            + "\n"
            + "Primary System:\n"
            + "0! +4dF"
            ,
            
            GameSystem.fromwith(fudge.getMech()),
            new RandomChar()),
    
    WoD("Old / Classic World of Darkness", 
            "The Old or Classic World of Darkness\n"
            + "\n"
            + "Primary System:\n"
            + "0! d10 Pool. \n"
              + "If parameter $x is given, successes are counted against difficulty x. \n"
              + "1s are noted, but not automatically substracted."
              + "Else the dice are rolled and sorted. \n\n"
            + "1! With Specialty: Reroll 10s.\n\n"
                               
            ,            
            GameSystem.fromwith(oWoD.getMech(), oWoDSpecial.getMech()),
            new RandomChar()),
    
        Gurps("Gurps, 3d6 Rollunder", 
            "Gurps\n"
            + "\n"
            + "Primary System:\n"
            + "0! 3d6, calcuate difference to given stat.\n"
            + "Note the result.\n"
            + "\n"
            + "\n\n Secondary System: \n"
            + "0! Sum the damage dice", 
                        
            GameSystem.from(rollunder3d6.getMech()).with(sum.getMech()),
            new RandomChar()),
    
      Splittermond("Splittermond", 
            "Splittermond\n"
            + "The new \"big\" German RPG \n\n"
            + "Primary System: "
            + "\n 0! : +2d10, botch on 2-3, crit on 19-20\n"
            + "1! : Safety Roll : 2d10 keep highest "
            + "2! : Risk Roll : 4d10, botch on any combination of 1&2, crit on combination of 9&10",
  
            GameSystem.fromwith(SplittermondBasic.getMech(), SplittermondSafety.getMech(), SplittermondRisk.getMech()),
           new RandomChar() ),
      
      
    ;

   
    

    
    private String descr;
    private String info;
    private ArrayList<GameSystem> systems;
    private ArrayList<RandomStat> randomStats;

    

    

    private DefaultCampaignTemplate(String descr, String info, ArrayList<GameSystem> systems, ArrayList<RandomStat> randomStats) {
        this.descr = descr;
        this.info = info;
        this.systems = systems;
        this.randomStats = randomStats;
    }
    



    
    public String getDescr() {
        return descr;
    }

    public ArrayList<GameSystem> getSystems() {
        return systems;
    }

   
    public ArrayList<RandomStat> getRandomStats() {
        return randomStats;
    }

    public String getInfo() {
        return info;
    }

    
    
}
