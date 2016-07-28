/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.campaigndata;

import de.pomp31us.rpgcharsfx.model.dices.DString;
import de.pomp31us.rpgcharsfx.model.dices.DiceExpressionParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author viona-173
 */

@XmlRootElement(name = "ability")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Ability extends CampaignObject {
    
   
    private String val;
    
    private Hero owner;

    /* Setter and Getter start */

    
     @XmlAttribute
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        DiceExpressionParser parser = new DiceExpressionParser();
        if (parser.validateAbl(val)) {
            this.val = val;
        }
    }
    
    @XmlTransient
    public Hero getOwner() {
        return owner;
    }

    public void setOwner(Hero owner) {
        this.owner = owner;
    }

    

    /* Setter and Getter end */
 /* Constructors start  */
    public Ability(Hero owner, String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "UnbenannteFähigkeit";
        } else {
            this.name = name;
        }
        this.val = "0";
        this.owner = owner;
    }

    public Ability(Hero owner, String name, String val) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "UnbenannteFähigkeit";
        } else {
            this.name = name;
        }
        this.val = val;
        this.owner = owner;
    }

    public Ability() {
    }

    
    

    /* Constructors end */
    /**
     * Rolls the ability accoring to the Campaign's rules.
     *
     * @return An array consisting of Hero and Ability names plus the results.
     */
    public ArrayList<ArrayList<String>> check() {
        
        String[] out = new String[2];
        out[0] = owner.getName() + " - " + this.name + ": ";      // Wir wollen ["Charakter - Fähigkeit: ", "Ergebnis"] zurückgeben, damit man bei mehreren Checks auf einmal nach dem Ergebnis, also dem zweiten Feld, sortieren kann.
        out[1] = owner.getCampaign().indCheck(val);
        
        ArrayList<String> result = new ArrayList<>(Arrays.asList(out));
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        output.add(result);
        return output;

    }

    
        /**
     * Rolls the ability accoring to the Campaign's rules, applying another Dice Expression as a circumstance modifier first.
     *
     * @param circ A string containing circumstancial modifiers, a dice expression.
     * @return An array consisting of Hero and Ability names plus the results.
     */
    @Override
    public ArrayList<ArrayList<String>> check(String circ) {
        DiceExpressionParser parser = new DiceExpressionParser();       // Rufe einen Parser auf.
        ArrayList<DString> checks = parser.parseAbl(val);                        // Parse die Ability. Bei Kommaseparierten Einträgen erhalte mehrere DStrings, sonst Arraylänge 1.             
        ArrayList<DString> circs = parser.parseAbl(circ);                   // Parse den Modifikator

        ArrayList<DString> circchecks = new ArrayList();                            // Verheirete beide DString-Listen komponentenweise.
        if(checks.size() >= circs.size()) {
            for (int i = 0; i<circs.size(); i++) circchecks.add(checks.get(i).add(circs.get(i)));
            for (int j = circs.size() ; j< checks.size(); j++) circchecks.add(checks.get(j));
        }
        else {
            for (int i = 0; i<checks.size(); i++) circchecks.add(checks.get(i).add(circs.get(i)));
            for (int j = checks.size() ; j< circs.size(); j++) circchecks.add(circs.get(j));
        }
        
        
        String[] out = new String[2];
        out[0] = owner.getName() + " - " + this.name + ": ";      // Wir wollen ["Charakter - Fähigkeit: ", "Ergebnis"] zurückgeben, damit man bei mehreren Checks auf einmal nach dem Ergebnis, also dem zweiten Feld, sortieren kann.
        out[1] = owner.getCampaign().indCheck(circchecks);
        
        ArrayList<String> result = new ArrayList<>(Arrays.asList(out));
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        output.add(result);
        return output;

    }
    
    
    
    @Override
    public String toString() {
        return name + ": " + val;
    }

      
    
    @Override
    public String retrieveInfo() {
       return getOwner().retrieveInfo();
    }
    
    @Override
    public void setInfo(String string) {
       owner.setInfo(string);
    }

    @Override
    public void insert(Collection<CampaignObject> cobjs) {
        owner.insert(cobjs);
    }
    
        @Override
    public void insert(CampaignObject... cobjs) {
        owner.insert(cobjs);
    }
    
    @Override
    public void removeFromParent(){
        owner.getAbl().remove(this);    }
    
    
}
