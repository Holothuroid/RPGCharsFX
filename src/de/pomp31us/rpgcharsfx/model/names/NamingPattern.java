/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.names;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;


/**
 *
 * @author 1of3
 */

@XmlRootElement(name = "namepattern")
public class NamingPattern implements Serializable {
    
    String id;
    ArrayList<NameComponent> components = new ArrayList<>();
    ArrayList<NameStructure> structures = new ArrayList<>();

    public NamingPattern(String id) {
        this.id = id;
    }

    public NamingPattern() {
    }

    
    
    public void addComp(String... words){
        NameComponent comp = new NameComponent();
        comp.setWords(words);
        this.getComponents().add(comp);
    }    
    
    public void addStructure(String structure){
        NameStructure struct = new NameStructure();
        struct.setStructure(structure);
        struct.setRatio(1);
        this.getStructures().add(struct);
    }
    
    public void addStructure(int ratio, String structure){
        NameStructure struct = new NameStructure();
        struct.setStructure(structure);
        struct.setRatio(ratio);
        this.getStructures().add(struct);
    }
    
    
    public String createName() {
      String structure = findStructure();
      String output = useStructure(structure);
      return output.replaceAll("_"," ");
    }
    
    
    
    public String findStructure(){
        ArrayList<String> effectiveStructures = new ArrayList<>();
        for (NameStructure structure : structures){
            if (structure.getRatio()<1)
                effectiveStructures.add(structure.getStructure());
            else for (int i = 0; i< structure.getRatio(); i++){
                effectiveStructures.add(structure.getStructure());
            }
        }
        
        for (String structure : effectiveStructures) {
            if (structure == "") {
                for (int i=0; i<getComponents().size(); i++) structure += "$" + i;
            }
        }
        
        if (effectiveStructures.isEmpty()) {
            String structure = "";
            for (int i = 0; i< components.size(); i++) 
                structure += "$" + i + " ";
            effectiveStructures.add(structure);
            
        }
        
        Random random = new Random();
        int i = random.nextInt(effectiveStructures.size());
        return effectiveStructures.get(i);
            
    }
    
    public String useStructure(int i){
        return useStructure(this.getStructures().get(i));
    }
    
    public String useStructure(NameStructure structure){
        return useStructure(structure.getStructure());
    }
    
    public String useStructure(String structure){

        for (int i = 0; i< this.getComponents().size(); i++) 
            while (structure.contains("$" +i))
            structure=  structure.replaceFirst("\\$" + i,this.getComponents().get(i).randomWord()   );
        
        for (int i = 0; i< this.getComponents().size(); i++) 
            while (structure.contains("/" +i))
            structure=  structure.replaceFirst("/" + i, capitalize(this.getComponents().get(i).randomWord()) );        
        
        for (int i = 0; i< this.getComponents().size(); i++) 
            while (structure.contains("\\" +i))
            structure=  structure.replaceFirst("\\" + i, uncapitalize(this.getComponents().get(i).randomWord()) );        
        
        return structure;
    }
    
    
    public static String capitalize(String text){
        return text.substring(0,1).toUpperCase() +text.substring(1);
    }
    
    public static String uncapitalize(String text){
        return text.substring(0,1).toLowerCase() +text.substring(1);
    }
    
    
    @Override
    public String toString(){
       return id; 
    }
            
    
    
    @XmlElement(name = "component")
    public ArrayList<NameComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<NameComponent> components) {
        this.components = components;
    }
    
    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   
    

   

    @XmlElement(name = "structure")
    public ArrayList<NameStructure> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<NameStructure> structures) {
        this.structures = structures;
    }
    
    
    
    
    
    @XmlRootElement
    public static class NameStructure {
       
        String structure;
        int ratio;
        
        @XmlValue
        public String getStructure() {
            return structure;
        }

        public void setStructure(String structure) {
            this.structure = structure;
        }

        @XmlAttribute
        public int getRatio() {
            return ratio;
        }

        public void setRatio(int ratio) {
            this.ratio = ratio;
        }

        
    }
    
    
    @XmlRootElement
    public static class NameComponent {
       
        ArrayList<String> words = new ArrayList();
        
        public String randomWord(){
        Random random = new Random();
        return this.getWords().get(random.nextInt(this.getWords().size())) ; 
    }        
        
        @XmlValue
        public ArrayList<String> getWords() {
            return words;
        }

        public void setWords(String... words) {
            this.words.clear();
            for(String word : words) this.words.add(word);
        }
        
        public void setWords(ArrayList<String> words){
            this.words = words;
        }       
        
    }
    
}
