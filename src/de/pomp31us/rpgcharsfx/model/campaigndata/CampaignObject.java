/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.campaigndata;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import static java.util.Comparator.comparing;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TreeItem;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparing;

/**
 *
 * @author 1of3
 */

@XmlRootElement
public abstract class CampaignObject  implements Serializable, Cloneable {
    
    protected String name;
    
    
    
    @XmlAttribute
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (!(name == null || name.trim().isEmpty())) {
            this.name = name;
        }
    }
    
    
    public abstract String retrieveInfo();
    public abstract void setInfo(String string);
    public abstract ArrayList<ArrayList<String>> check();
    public abstract ArrayList<ArrayList<String>> check(String circ);
    public void insert(Collection<CampaignObject> cobjs){};
    public void insert(CampaignObject... cobjs){};
    public void removeFromParent(){     }
    
    /**
     * Calls .check() for a Collection of CampaignObjects.
     * @param objs A collection of CampaignObjects.
     * @return A String including the rolled results for all CampaignObjects.
     */
    
    public static String checker(Collection<CampaignObject> objs){
        
        ArrayList<ArrayList<String>> toSort = new ArrayList<>();
        for (CampaignObject obj : objs) toSort.addAll(obj.check());
       
        toSort.sort(comparing(as -> Integer.parseInt(as.get(1).split("( |,)")[0])));
        Collections.reverse(toSort);

        String output="\n";
        for (ArrayList<String> as : toSort) 
            output += "\n" + as.get(0) + as.get(1);    // Mach hübsche Zeilen draus.
        return output;
    }        
    
    
    /**
     * Calls .check(String circ) for a Collection of CampaignObjects.
     * @param circ A modifier that is applied before rolling.
     * @param objs The CampaignObjects to be checked.
     * @return The rolled results for all CampaignObjects as a String.
     */
    
     public static String checker(String circ, Collection<CampaignObject> objs){
        
        ArrayList<ArrayList<String>> toSort = new ArrayList<>();
        for (CampaignObject obj : objs) toSort.addAll(obj.check(circ));
       
        toSort.sort(comparing(as -> Integer.parseInt(as.get(1).split("( |,)")[0])));
        Collections.reverse(toSort);

        String output="\n";
        for (ArrayList<String> as : toSort) 
            output += "\n" + as.get(0) + as.get(1);    // Mach hübsche Zeilen draus.
        return output;
    }
   
    public void toClipboard(){
        Clipboard clipper = Toolkit.getDefaultToolkit().getSystemClipboard();
        CampaignObjectSelection cos = new CampaignObjectSelection(this);
        clipper.setContents(cos, cos);
    }
    
   
   
}
