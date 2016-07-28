/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.names;


import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author 1of3
 * 
 * This is a singleton class. Constructor is private.
 * * 
 */


/**
 *
 */   

@XmlRootElement
public class NamingPatternsList {
    
    

    private static NamingPatternsList instance = null;

    /**
     * Upon first call, loads a NamingPatternList from rpgchars_names.xml in the same file.
     * @return Returns the single instance of this class.
     */
    
    public static NamingPatternsList getInstance() {
        if (instance == null) instance = loadPatterns(new File("rpgchars_names.xml"));
        return instance;
    }
    
   // private NamingPatternsList(){}
    
    private ArrayList<NamingPattern> patterns = new ArrayList<>();

    private static NamingPatternsList loadPatterns(File xml){
       NamingPatternsList output = new NamingPatternsList();
       NamingPattern zeroPattern =new NamingPattern("None");
       zeroPattern.addComp("Alrik");
       output.getPatterns().add(zeroPattern);
      
       try{
       NamingPatternsList loaded = JAXB.unmarshal(xml, NamingPatternsList.class);
       output.getPatterns().addAll(loaded.getPatterns());}
       catch (Exception E) {}
       finally{
       return output;}
    }
   


   
    @XmlElement(name = "pattern")
    public ArrayList<NamingPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(ArrayList<NamingPattern> patterns) {
        this.patterns = patterns;
    }
   
   
   
}
