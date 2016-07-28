/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.dices;

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Comparator.*;
import java.util.List;

/**
 *
 * @author viona-173
 */
public class DString extends ArrayList<Die> {

    private int rule;
    private ArrayList<Integer> params = new ArrayList<Integer>();
    private ArrayList<String> critlog = new ArrayList<String>();

    /* Constructors start */
    /**
     * Parses the input with DiceExpressionParser and turns it into a DString. A
     * standard constructor is also availab.e
     *
     * @param text A dice expression.
     * @param i is the index of the GameSystem an Ability will retrieve from its
     * Campaign, when check() is called.
     */
    public DString(String text, int i) {
        DiceExpressionParser parser = new DiceExpressionParser();
        this.addAll(parser.parse(text));

        // if (this.isEmpty()) add(0,0);
        this.rule = i;

    }

    public DString(String text) {
        DiceExpressionParser parser = new DiceExpressionParser();
        this.addAll(parser.parse(text));

        // if (this.isEmpty()) add(0,0);
    }

    public DString(int i) {
        this.add(i);
    }

    public DString() {
        //  add(0,0);
    }

    /* Constructors end */
    /**
     * Würfelt alle Würfel + ihre individuellen Modifikatoren und gibt diese
     * Liste aus.
     *
     * @return Die Liste der Ergebnisse.
     */
    public DString roll() {
        DString out = new DString();
        if (this.isEmpty()) {
            return out.add(0, 0);
        }
        for (Die d : this) {
            out.add(0, d.roll());
        }
        out.critlog = this.critlog;
        out.sort(comparing(Die::getMod).reversed());
        return out;
    }

    /**
     * Würfelt alle Würfel und addiert das Ergebnis.
     *
     * @return
     */
    public DString sum() {
        DString cache = roll();
        int a = 0;
        for (Die d : cache) {
            a += d.getMod();
        }
        DString out = new DString();
        out.add(0, a);
        out.critlog = this.critlog;
        return out;
    }

    public DString countUnder(int i) {
        int cache = 0;
        for (Die d : this) {
            cache += d.countUnder(i);
        }
        DString out = new DString();
        out.add(0, cache);
        out.critlog = this.critlog;
        return out;
    }

    public DString countOver(int i) {
        int cache = 0;
        for (Die d : this) {
            cache += d.countOver(i);
        }
        DString out = new DString();
        out.add(0, cache);
        out.critlog = this.critlog;
        return out;
    }

    public DString aPool(int... table) {
        int cache = 0;
        for (Die d : this) {
            cache += d.aPool(table);
        }
        DString out = new DString();
        out.add(0, cache);
        out.critlog = this.critlog;
        return out;
    }

    /**
     * Würfelt alle Würfel. Gibt ihre Summe und die individuellen natürlichen
     * Ergebnisse (ohne Modifikatoren) aus.
     *
     * @param choice
     * @return Summe und Einzelergebnisse als String.
     */
//  
    public DString choose(int... choice) {
        DString out = new DString();
        if (choice.length == 0) {
            return out;   // Wenn das Array leer ist, gib einen leeren DString zurück.
        }
        DString cache = this.roll();
        cache.sort(comparing(Die::getMod));
        for (int i : choice) // Für jede Zahl in der Auswahl...
        {
            if (Math.abs(i) <= cache.size() && !(i == 0)) {  // wenn sie kleiner-gleich der Zahl der gewürfelten Würfel und nicht 0 ist...
                if (i > 0) {
                    out.add(cache.get(i - 1));                    // nimm den entsprechenden Wert aus dem Wurf
                }
                if (i < 0) {
                    out.add(cache.get(cache.size() + i)); // oder bei negativen Zahlen zähl von hinten.          
                }
            }
        }
        out.critlog = this.critlog;
        return out;

    }

    public DString keep(int i) {
        DString out = new DString();
        if (i <= 0) {
            return out;
        }
        DString cache = roll();
        cache.sort(comparing(Die::getMod));
        for (int j = 1; j <= i; j++) {
            out.add(cache.choose(-j));
        }
        out.critlog = this.critlog;
        return out;
    }

    public DString drop(int i) {
        DString out = new DString();
        if (i <= 0) {
            return out;
        }
        DString cache = roll();
        cache.sort(comparing(Die::getMod));
        for (int j = 1; j <= i; j++) {
            out.add(cache.choose(j));
        }
        out.critlog = this.critlog;
        return out;
    }


    /**
     * Rolls the dice. If a dice's result is included in spawns... the die will
     * be rolled again.
     *
     * @param spawns The numbers that spawn another die on hit.
     * @return A DString. In conjunction with countOver(int) or aPool(int...) you
 can use this to recreate games like Shadowrun or WoD.
     */
    public DString spawn(int... spawns) { // Ungetestet!!! 
        DString out = roll();
        List spawnlist = Arrays.asList(spawns);
        
        for(int i = 0; i<this.size();i++) if (spawnlist.contains(out.get(i).getMod()))
        {
            int repeat = 0;
            while(repeat < 50){
                out.add(out.get(i).getMod());
                repeat++;
                if (!spawnlist.contains(this.get(i).roll())) break;
            }
                    
        }
         
        
        out.critlog = this.critlog;
        return out;
    }

    /**
     * Use this for rollunder systems that interested in the difference to the checked stat to account for quality.
     * If you want to check like 3d6 against a bound, make sure to sum() first. Otherwise differences will be calculated for each of the three dice.
     * @param bound The number to rolled under, the stat.
     * @return A DString noting the differences between rolled results and the bound.
     */
    
    public DString diffToUnder(int bound){
        noteUnder(bound);
        DString out = new DString();
        out.critlog = this.critlog;
        for (Die d :this ) out.add(0,bound-d.getMod());
        return out;
    }
    
    
    /**
     * Use this for rollover systems that interested in the difference to the checked stat to account for quality.
     * If you want to check like 3d6 against a bound, make sure to sum() first. Otherwise differences will be calculated for each of the three dice.
     * @param bound The number to rolled under, the stat.
     * @return A DString noting the differences between rolled results and the bound.
     */
    
    public DString diffToOver(int bound){
        noteOver(bound);
        DString out = new DString();
        out.critlog = this.critlog;
        for (Die d :this ) out.add(0,d.getMod()-bound);
        return out;
    }
    
    /**
     * Checks whether certain numbers are included in the rolled DString. Notes
     * the finds critlog.
     *
     * @param crit The numbers to be checked.
     * @return The DSTring with updated critlog.
     */
    public DString note(int... crit) {
        DString out = roll();
        out.critlog = this.critlog;
        for (int i : crit) {
            for (Die d : out) {
                if (i == d.getMod()) {
                    out.critlog.add(Integer.toString(i));
      
                }
            }
        }
        return out;
    }

    /**
     * Checks whether the numbers in the DString are below or equal the bound
     * and notes finds in critlog.
     *
     * @param bound The bound to be checked.
     * @return The DSTring with updated critlog.
     */
    public DString noteUnder(int bound) {
        DString out = roll();
        out.critlog = this.critlog;
        for (Die d : this) {
            if (d.getMod() <= bound) {
                out.critlog.add("≤" + Integer.toString(bound));
            }
        }
        return out;
    }

    /**
     * Checks whether the numbers in the DString are equal or above the bound
     * and notes finds in critlog.
     *
     * @param bound The bound to be checked.
     * @return The DSTring with updated critlog.
     */
    public DString noteOver(int bound) {
        DString out = roll();
        out.critlog = this.critlog;
        for (Die d : this) {
            if (d.getMod() >= bound) {
                out.critlog.add("≥" + Integer.toString(bound));
            }
        }
        return out;
    }
    
    
      /**
     * Notes all rolled result in the DString's critlog. Useful for games that have crits differ by character ability like D&D or Gurps.
     *
     *
     * @return The DSTring with updated critlog.
     */
    public DString noteAll() {
        DString out = roll();
        out.critlog = this.critlog;
        for (Die d : out) 
                out.critlog.add( Integer.toString(d.getMod()));
          
        return out;
    }
    

    /**
     * Add a new Die to the DString.
     *
     * @param size The size of the Die.
     * @return Returns the whole DString to allow for further manipulation.
     */
    public DString add(int size) {
        this.add(new Die(size));
        return this;        
    }

    /**
     * Adda a new Die to the DString.
     *
     * @param size Size of the Die.
     * @param mod Mod of the Die.
     * @return Returns the whole DString to allow for further manipulation.
     */
    public DString add(int size, int mod) {
        this.add(new Die(size, mod));
        return this;
    }

    /**
     * Parses a dice expression with DiceExpressionParser and adds the result
     *
     * @param text A string
     * @return This DSTring to allow for further modification.
     */
    public DString add(String text) {
        DiceExpressionParser parser = new DiceExpressionParser();
        this.addAll(parser.parse(text));
        return this;
    }
    
    /**
     * Combines two DStrings, adds their params.
     * @param that The DString to be added.
     * @return The combined DString.
     */
    
    public DString add(DString that) {
        this.addAll(that);
        this.critlog.addAll(that.critlog);
        for(int i=0; i< that.params.size(); i++) 
            if(i < this.params.size()) this.params.set(i, this.params.get(i) + that.params.get(i));
            else this.params.add(that.params.get(i));
        return this;
    }

    public DString addTrick (DTrick trick){
        for (Die d: this) d.addTrick(trick);
        return this;
    }
    
    public DString addTrick (List<DTrick> tricks){
        for (Die d: this) d.addTrick(tricks);
        return this;
    }

    /**
     * Treats static modifiers as d1, then changes the size of all Dice in the
     * DString according to input. -dx removes a die as does -1.
     *
     * @param input The new a size of all Dice.
     * @return Returns the modified DString to allow further modification.
     */
    public DString standardize(int input) {
        int totalmod = 0;
        int cancel = 0;
        
        for (Die d : this) {
            totalmod = totalmod + d.getMod();
            d.setMod(0);
        }
               
        if (totalmod > 0) for(int i = 0; i < totalmod; i++) this.add(1);
        if (totalmod < 0) cancel = -totalmod;
              
         for (Die d : this) {
          if(d.getSize() > 0) d.setSize(input);}
          for(Die d: this) if(d.getSize() < 0) cancel++;
          this.removeIf(d -> d.getSize() <= 0);
        
         
        for(int i = 0; i< cancel; i++) if(!this.isEmpty()) this.remove(0);
        if (this.isEmpty()) this.add(0);
        return this;
    }

    public DString simplify() {   // Arbeitet nicht.
        int totalmod = 0;

        for (Die d : this) {
            totalmod += d.getMod();
            d.setMod(0);
        }
        for (int i = 0; i < this.size(); i++) {
            if (get(i).getSize() == 0) {
                remove(i);
            }
        }

        this.sort(comparing(Die::getSize));
        add(new Die(0, totalmod));
        return this;
    }

    public boolean isNumbers() {
        boolean out = true;
        for (Die d : this) {
            out = out && (d.getSize() == 0);
        }
        return out;
    }

    public int toInt() {
        int out = 0;
        this.sum();
        for (Die d : this) {
            out += d.getMod();
        }
        return out;
    }

    public String toText() {
        if (critlog.isEmpty()) {
            return this.toString();
        }

        String out = toString() + " (Scored ";
        for (int i = 0; i < critlog.size() - 1; i++) {
            out += critlog.get(i) + ",";
        }
        out += critlog.get(critlog.size() - 1) + ")";
        return out;
    }

    @Override
    public String toString() {
        if (size() == 1 && isNumbers()) {
            return Integer.toString(get(0).getMod());
        } else {
            return super.toString().replaceAll("(\\[|\\])","");
        }
    }

    /**
     * Returns the DString in reduced standard form.
     *
     * @return The DString as a string.
     */
    public String toShortString() {
        return this.simplify().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;  // Wenn sie gleich sind, sind sie natürlch equal.
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;  // Kein richtiger DString => falsch.
        }
        DString that = (DString) obj;  // Betrachte obj nun als DString. Nenne es that.

        // Vereinfache beide und vergleiche sie wie ArrayLists.
        this.simplify();
        that.simplify();
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (Die d : this.simplify()) {
            hash = 31 * hash + d.hashCode();
        }
        return hash;
    }

    /* Setters and Getters start */
    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public ArrayList<Integer> getParams() {
        return params;
    }

    public void setParams(ArrayList<Integer> params) {
        this.params = params;
    }

    public ArrayList<String> getCritlog() {
        return critlog;
    }

    public void setCritlog(ArrayList<String> critlog) {
        this.critlog = critlog;
    }

    /* Setters and Getters end */
}
