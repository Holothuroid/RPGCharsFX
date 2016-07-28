/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.campaigndata;



import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author viona-173
 */

@XmlRootElement(name = "char")
@XmlType(propOrder={"info","abl"})
    public class Hero extends CampaignObject {
        
        
       
        private ArrayList<Ability> abl = new ArrayList<>();
        private String info;
        private Campaign campaign;

        public Hero(String name, Campaign campaign) {
            this.campaign = campaign; // Hier fehlt noch eine Fehlerabfrage.

            if (name == null || name.trim().isEmpty()) {
                this.name = "Alrik";  // Idealstandardcharaktere heißen immer Alrik.
            } else {
                this.name = name;
            }
            
            this.info = getName();
        }

    public Hero(Campaign campaign) {
        this.name = "Alrik";
        this.campaign = campaign;
        this.info = name;
    }

    public Hero() {
    }
        
        
        
        

        /* Setter and Getter start */
        
      
        @XmlElement(name = "ability")
        public ArrayList<Ability> getAbl() {
            return abl;
        }

        @XmlTransient
        public Campaign getCampaign() {
            return campaign;
        }

  

        public void setAbl(ArrayList<Ability> abl) {
            this.abl = abl;
        }

        public void setCampaign(Campaign campaign) {
            this.campaign = campaign;
        }

    @XmlElement(name = "info")    
    public String getInfo() {
        return info;
    }

    @Override
    public String retrieveInfo(){
        return getInfo();
    }
    
    
    public void setInfo(String info) {
        this.info = info;
    }
        
        /* Setter and Getter end */
    
        @Override
        public String toString() {
            return name;
        }

        public String toText(){
             String output = getName() + "\n";
            for (Ability i : abl) {
                output += i.toString() + "\n";
            }

            return output;
        }
        
        /**
         * Fügt der ArrayList abl eine neue Fähigkeit .
         *
         * @param name ist der Name der Fähigkeit. Ihr Wert ist standarmäßig 0,
         * die Regel #1.
         */
        public void addNewAbl(String name) {
            Ability skill = new Ability(this, name);
            this.abl.add(skill);
        }

        public void addNewAbl(String name, String val) {
            Ability skill = new Ability(this, name, val);
            this.abl.add(skill);
        }

  
    @Override
    public ArrayList<ArrayList<String>> check() {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for(Ability a : abl) output.addAll(a.check());
        return output;
    }

    @Override
    public ArrayList<ArrayList<String>> check(String circ) {
               ArrayList<ArrayList<String>> output = new ArrayList<>();
        for(Ability a : abl) output.addAll(a.check(circ));
        return output;
    }

    
        @Override
    public void insert(Collection<CampaignObject> cobjs) {
    for (CampaignObject cobj : cobjs)  {
        if (cobj instanceof Ability) abl.add((Ability) cobj);
        else campaign.insert(cobj);
    }}
        
    @Override
    public void insert(CampaignObject... cobjs) {
    for (CampaignObject cobj : cobjs)  {
        if (cobj instanceof Ability) abl.add((Ability) cobj);
        else campaign.insert(cobj);
    }    
        
    }

    public void removeFromParent(){
        campaign.getChars().remove(this);
    }
    
    }