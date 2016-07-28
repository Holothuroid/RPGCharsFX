/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx;

import de.pomp31us.rpgcharsfx.io.CampaignMarshaller;
import de.pomp31us.rpgcharsfx.model.campaigndata.Campaign;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author 1of3
 */
public class CampXMLDialog extends Dialog<ButtonType>  {

   
 
    
    private TextArea textArea;
    
    
    public CampXMLDialog(Campaign camp){
        setResizable(true);
       setContentText("Campaign as XML");
        setHeaderText("Campaign as XML");
        
        CampaignMarshaller marshaller = new CampaignMarshaller();

       
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
      
        textArea = new TextArea();
        
        String xml = marshaller.marshal(camp);
        textArea.setText(xml);
        textArea.setEditable(true);
               
        getDialogPane().setContent(textArea);
        
    }


public TextArea getTextArea(){
return textArea;
}

}