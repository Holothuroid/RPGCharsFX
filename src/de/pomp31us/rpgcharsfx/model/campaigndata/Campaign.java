/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.campaigndata;


import de.pomp31us.rpgcharsfx.model.templates.RandomStat;
import de.pomp31us.rpgcharsfx.model.names.NamingPattern;
import de.pomp31us.rpgcharsfx.model.templates.DefaultCampaignTemplate;
import de.pomp31us.rpgcharsfx.model.dices.GameSystem;
import de.pomp31us.rpgcharsfx.model.dices.DString;
import de.pomp31us.rpgcharsfx.model.dices.DiceExpressionParser;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author viona-173
 */
@XmlRootElement
@XmlType(propOrder = {"info", "chars", "namePattern"})
public class Campaign extends CampaignObject {
    
  
    private ArrayList<Hero> chars = new ArrayList<>();
    private ArrayList<GameSystem> rules = new ArrayList<>();
    private ArrayList<RandomStat> randomStats = new ArrayList<>();
    private NamingPattern namePattern;
    private String info;

    /* Constructors start */
    public Campaign(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "UnbenannteKampagne";
        } else {
            this.name = name;

            this.info = name;
        }

    }

    public Campaign(DefaultCampaignTemplate template){
       this.name = "UnbenannteKampagne";
        loadTemplate(template);
    }
    
    
    public Campaign(DefaultCampaignTemplate template, String name){
    if (name == null || name.trim().isEmpty()) this.name = "UnbenannteKampagne";
    else   this.name = name;
        
 
        loadTemplate(template);
    }
    
    /**
     * Rolls a dice expression with the this campaign's rules.
     *
     * @param expression A dice expression to be parsed and rolled.
     * @return A String
     */
    public String indCheck(String expression) {
        DiceExpressionParser parser = new DiceExpressionParser();
        ArrayList<DString> checks = parser.parseAbl(expression);
        return indCheck(checks);

    }

    /**
     * Rolls a list of DStrings with the this campaign's rules.
     *
     * @param checks The list of DStrings to be rolled.
     * @return The result of individual DStrings separated by commas.
     */
    public String indCheck(ArrayList<DString> checks) {
        String out = "";
        try{
        if (rules.size() > 0 && checks.get(0).getRule() < rules.get(0).size()) {
            out = rules.get(0).get(checks.get(0).getRule()).apply(checks.get(0)).toText();
        } else  out = checks.get(0).noteAll().sum().toText();
        

        for (int i = 1; i < checks.size(); i++) {                    // Für jeden kommaseparierten Eintrag NACH dem ersten...
            if (i < rules.size() && checks.get(i).getRule() < rules.get(i).size()) {       // Prüfe ob für die angegebene Regel ein Eintrag im System existiert.
                out += ", " + rules.get(i).get(checks.get(i).getRule()).apply(checks.get(i)).toText();  // Falls ja, wende die entsprechende Regel auf die Würfel an.
            } else {
                out += ", " + checks.get(i).noteAll().sum().toText(); // Sonst würfel, notiere alle Ergebnisse und addiere sie.
            }
        }
        }
        catch (IndexOutOfBoundsException e){
            out = "0 - Error: Game system expected a parameter.";
        }
        finally{ return out; }
    }

    public Campaign() {
        this.name = "UnbenannteKampagne";
        this.info = name;
    }

    public Campaign(String name, GameSystem... systems) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "UnbenannteKampagne";
        } else {
            this.name = name;
            this.rules.addAll(Arrays.asList(systems));
            this.info = this.name;
        }

    }

    public Campaign(GameSystem... systems) {
        this.name = "UnbenannteKampagne";
        this.rules.addAll(Arrays.asList(systems));
        this.info = name;
    }


    /* Constructors end */
 /* Setter and Getter start*/

    @XmlElement(name = "char")
    public ArrayList<Hero> getChars() {
        return chars;
    }

    @XmlTransient
    public ArrayList<GameSystem> getRules() {
        return rules;
    }

   
    public void setChars(ArrayList<Hero> chars) {
        this.chars = chars;
    }

    public void setRules(ArrayList<GameSystem> rules) {
        this.rules = rules;
    }

    @Override
    public String retrieveInfo(){
        return getInfo();
    }
    
    @XmlElement(name = "info")
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }
    
    @XmlTransient
    public ArrayList<RandomStat> getRandomStats() {
        return randomStats;
    }

    public void setRandomStats(ArrayList<RandomStat> randomStats) {
        this.randomStats = randomStats;
    }

    @XmlElement(name = "namingpattern")
    public NamingPattern getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(NamingPattern namePattern) {
        this.namePattern = namePattern;
    }

    
    
    /* Setter and Getter end */
    @Override
    public String toString() {
        return name;
    }

    public String toText() {
        String output = getName() + "\n\n";
        for (int i = 0; i < chars.size(); i++) {
            output += "#" + (i + 1) + ":" + chars.get(i).toText() + "\n\n";
        }
        return output;
    }

    /**
     * Creates a new Hero and adds it to this campaign.
     *
     * @param name The new Hero's charname.
     */
    public void addNewChar(String name) {
        Hero chara = new Hero(name, this);
        this.chars.add(chara);
    }

    
    /**
     * Adds a Hero to this campaign.
     * @param hero The hero to be added.
     */
    
    public void addChar(Hero hero) {
        hero.setCampaign(this);
        chars.add(hero);
    }

    
    /**
     * Adds a new Hero to this campaign, relying on the campaign's randomStats and namePattern.
     * @return The newborn hero.
     */
    
    public Hero randomChar() {
        String charname;
        if(namePattern != null) charname= namePattern.createName();
            else charname = "Alrik";
        Hero hero = new Hero(charname, this);
        for (RandomStat stat : randomStats) stat.train(hero);
        chars.add(hero);
        return hero;
    }

    /**
     * Removes a Hero from this campaign.
     * @param hero The Hero to be removed.
     * @return True, if removal was successful.
     */
    
    public boolean removeChar(Hero hero) {
        hero.setCampaign(null);
        return chars.remove(hero);
    }
    
    /**
     * Removes a Hero from this campaign.
     * @param i The hero's index in the list.
     * @return The Hero that was removed.
     */
    
    public Hero removeChar(int i) {
        chars.get(i).setCampaign(null);
        return chars.remove(i);
    }

    /**
     * Loads a DefaultCampaignTemplate. Sets this campaigns rules, info and random chargen.
     * @param template The Template to be loaded.
     */
    
    public void loadTemplate(DefaultCampaignTemplate template){
        this.randomStats = template.getRandomStats();
        this.rules = template.getSystems();
        this.info = template.getInfo();
    }
    

    
    public void loadTemplate(Campaign camp){
        this.namePattern = camp.getNamePattern();
        this.randomStats = camp.getRandomStats();
        this.rules = camp.getRules();
        this.info = camp.retrieveInfo();
    }
    
    
 
    @Override
    public ArrayList<ArrayList<String>> check() {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (Hero hero : chars) {
            output.addAll(hero.check());
        }
        return output;
    }

    @Override
    public ArrayList<ArrayList<String>> check(String circ) {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (Hero hero : chars) {
            output.addAll(hero.check(circ));
        }
        return output;
    }

    @Override
    public void insert(CampaignObject... cobjs) {
    ArrayList<Ability> superflousAbilities = new ArrayList<>();
    for (CampaignObject cobj : cobjs )  {
        if (cobj instanceof Hero) chars.add((Hero) cobj);
        else if (cobj instanceof Campaign) {
            for (Hero hero : ((Campaign) cobj).getChars()) chars.add(hero);
        }
        else superflousAbilities.add((Ability) cobj);
    
    }
    
    if (!superflousAbilities.isEmpty()) {
        Hero alrik = new Hero("NewCharacter", this);
        alrik.setAbl(superflousAbilities)        ;
        this.getChars().add(alrik);
    }
    
    }

 /**
  * Makes an exact copy of this campaign.
  * @return An exact copy of this campaign.
  */   
    
 public Campaign duplicate(){
     Campaign dc = new Campaign(this.name);
     dc.info = this.info;
     for (Hero hero: chars){
         Hero dh = new Hero(hero.getName(), dc);
         dh.setInfo(hero.retrieveInfo());
         for (Ability abl : hero.getAbl()) {
             Ability da = new Ability (dh, abl.getName(), abl.getVal());
             dh.getAbl().add(da);
         }
         dc.getChars().add(dh);
    }
    
     dc.namePattern = this.namePattern;
     dc.rules = this.rules;
     dc.randomStats = this.randomStats;
     return dc;
}

}