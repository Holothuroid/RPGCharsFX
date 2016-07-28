/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.io;

import de.pomp31us.rpgcharsfx.model.campaigndata.Campaign;
import de.pomp31us.rpgcharsfx.model.campaigndata.Hero;
import de.pomp31us.rpgcharsfx.model.campaigndata.Ability;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import javax.xml.bind.UnmarshalException;


/**
 *
 * @author 1of3
 */
public class CampaignMarshaller {
    
    /**
     * Marshals a campaign to an xml String.
     * @param camp The camapaign to be marshalled.
     * @return A string containig xml.
     */
    
    public String marshal(Campaign camp){
        StringWriter sw = new StringWriter();
        
        JAXB.marshal(camp, sw);
        
        return sw.toString();
    }
    
    
    /**
     * Unmarshals a campaign in xml format to a Campagin object.
     * @param xml A string containing xml.
     * @return An object of type Campaign.
     * @throws DataBindingException In case xml is corrupt.
     */
    
    public Campaign fromString (String xml) throws DataBindingException {
        StringReader reader = new StringReader(xml);
        Campaign camp = JAXB.unmarshal(reader, Campaign.class);
        for(Hero hero : camp.getChars()){
            hero.setCampaign(camp);
            for(Ability skill : hero.getAbl()) skill.setOwner(hero);
        }
        return camp; 
    }
       
    
    
}
