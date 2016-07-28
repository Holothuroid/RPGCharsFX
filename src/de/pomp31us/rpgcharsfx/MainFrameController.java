package de.pomp31us.rpgcharsfx;

import de.pomp31us.rpgcharsfx.io.CampaignMarshaller;
import de.pomp31us.rpgcharsfx.model.templates.DefaultCampaignTemplate;
import de.pomp31us.rpgcharsfx.model.dices.DiceExpressionParser;
import de.pomp31us.rpgcharsfx.model.campaigndata.Campaign;
import de.pomp31us.rpgcharsfx.model.campaigndata.CampaignObject;
import de.pomp31us.rpgcharsfx.model.campaigndata.CampaignObjectSelection;
import de.pomp31us.rpgcharsfx.model.campaigndata.Hero;
import de.pomp31us.rpgcharsfx.model.campaigndata.Ability;
import de.pomp31us.rpgcharsfx.io.LoadSave;
import de.pomp31us.rpgcharsfx.model.names.NamingPattern;
import de.pomp31us.rpgcharsfx.model.names.NamingPatternsList;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.UnsupportedFlavorException;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;



public class MainFrameController implements Initializable {

    
    
/**
 * List of Campaigns for edit/undo/redo.
 */
    
 private ArrayList<Campaign> camps = new ArrayList<>();  
 
  
    /**
     * The current index in the edit/undo/redo list.
     */
 
  private int index=0;    

 

 /**
  * File that current campaign was loaded from / saved to.
  */
  private File file = null;
  
  
  /**
   * The CampaignObject whose .info is currently displayed in infoBox.
   */
  
  CampaignObject currentlyDisplayed;
 
  
  /**
   * This variable contains a list of NamingPatterns for random characters.
   * A pattern can be chosen with setNamingPattern().
   */
  
  NamingPatternsList namingPatternsList; 
  
  
    @FXML
    TextArea logText;
    
    @FXML
    TextArea infoBox;
    
    @FXML
    CheckBox infoBoxReadOnly;
    
    @FXML
    TextField modifier;
    
    @FXML
    TextField freeCheck;

    @FXML
    TreeView campTree;
    
   /* File menu */
    
    public void newFile(){
        camps = new ArrayList<>();
        ChoiceDialog chooseTemplate = new ChoiceDialog(DefaultCampaignTemplate.None, DefaultCampaignTemplate.class.getEnumConstants());
        chooseTemplate.setContentText("Choose campaign template");
        Optional<DefaultCampaignTemplate> answer = chooseTemplate.showAndWait();
        if(answer.isPresent()){
            Campaign camp = new Campaign(answer.get());
            camps.add(camp);
            index = 0;
            displayCamp();
            currentlyDisplayed = getCamp();
            infoBox.setText(getCamp().retrieveInfo());
        }
    }
    
    public void saveFile(){
        if (file == null) saveAsFile();
        else {
            LoadSave ls = new LoadSave(".rpgc");
            ls.save(getCamp(), file);
        }
    }
    
    public void saveAsFile(){
        LoadSave ls = new LoadSave(".rpgc");
        File file2 = ls.saveAs(getCamp());
        if (file2 == null) errorDialog("Error: Campaign wasn't saved."); 
        else file = file2;
        
    }
    
    public void openFile(){
        LoadSave ls = new LoadSave(".rpgc");
     try {
         camps = new ArrayList<>();
         camps.add((Campaign) ls.load());
     } catch (ClassNotFoundException ex) {
         errorDialog("File did not contain an RPGChars campaign or was corrupt.");
     }
    }
    
    
    /* Edit menu */
    
    public void undo(){
        saveInfoBTW();
        if (index >0) index--;
        displayCamp();
    }
    
    public void redo(){
        saveInfoBTW();
        if (index < camps.size()-1) index++;
        displayCamp();
    }
    
    public void copy(){ 
        saveInfoBTW();
        Clipboard clipper = Toolkit.getDefaultToolkit().getSystemClipboard();
        CampaignObjectSelection cos = new CampaignObjectSelection(getSelect());
        clipper.setContents(cos, cos);
    }
    
    public void paste() { 
      Clipboard clipper = Toolkit.getDefaultToolkit().getSystemClipboard();
      if (!clipper.isDataFlavorAvailable(CampaignObjectSelection.flavor)) return;
     try {
         saveCurrentEdit();
         Collection<CampaignObject> cobjs = (Collection<CampaignObject>) clipper.getData(CampaignObjectSelection.flavor);
         getLeadSelect().insert(cobjs);
         displayCamp();
     } catch (UnsupportedFlavorException ex) {
         Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) {
         Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
     }
       
       
       
       
       
    }
        
    public void cut(){
        copy();
        removeNode();
    }
    
    
    /**
     * Adds a new character to the campaign. User is prompted for character name.
     */
    
    public void newChar(){
        saveCurrentEdit();
        Optional<String> name = new TextInputDialog("Character name").showAndWait();
        if (name.isPresent()) {
            getCamp().addNewChar(name.get());
            displayCamp();
        }
    }
    
    /**
     * Adds a random character to the campaign.
     */
    
    public void randomChar(){
        saveCurrentEdit();
        getCamp().randomChar();
        displayCamp();
    }
    
    public void editChar(){
        saveCurrentEdit();
        
        displayCamp();
    }
    
    public void editAbility(){
        saveCurrentEdit();
        
        displayCamp();
    }
    
    public void removeNode(){ // Wie baut man hier ein Undo ein?
        saveCurrentEdit();
        for(CampaignObject cobj : getSelect()) cobj.removeFromParent();
        displayCamp();
    }
    
    public void addAbility(){
        saveCurrentEdit();
        
        displayCamp();
    }
    
    
    /* Campaign menu */
    
    /**
     * Sets the NamingPattern for random names.
     */
    
    public void setNamingPattern(){
        saveCurrentEdit();
        ChoiceDialog chooseNamePattern = new ChoiceDialog("None",namingPatternsList.getPatterns());
        chooseNamePattern.setContentText("Select naming pattern for random names");
        Optional<NamingPattern> answer = chooseNamePattern.showAndWait();
        if(answer.isPresent()){
            if (answer.get() instanceof NamingPattern)
                getCamp().setNamePattern((NamingPattern) answer.get());
            else 
                getCamp().setNamePattern(null);
            displayCamp();
        }
    }
    

    
    public void showXML(){
       CampXMLDialog xmlDialog = new CampXMLDialog(getCamp());
       Optional<ButtonType> result = xmlDialog.showAndWait();
       if (xmlDialog.getResult() == ButtonType.CANCEL) return;
       else{
       try{
       saveCurrentEdit();
       CampaignMarshaller marshaller = new CampaignMarshaller();
       Campaign newCamp = marshaller.fromString(xmlDialog.getTextArea().getText()) ;
       getCamp().setInfo(newCamp.retrieveInfo());
       getCamp().setChars(newCamp.getChars());
       getCamp().setNamePattern(newCamp.getNamePattern());
       getCamp().setName(newCamp.getName());
       displayCamp();
       }
       catch(Exception e){errorDialog("XML corrupt");}
    }    }
    
    /* Help menu */
    
    /**
     * Opens help in browser.
     */
    
    public void help() {
    try {
        Desktop.getDesktop().browse(new URI("http://docs.google.com/document/d/1UL-EWZ8o5HrSMQCZ6Epiz5UsaSMgfCw7DTas0jvb6ds/edit?usp=sharing"));
    } catch (IOException ex) {
        
    } catch (URISyntaxException ex) {
        Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    
    /* Check buttons */
    
    /**
     * Checks the current selection of CampaignObjects together with modifier,
     * according to the current Campaigns rules.
     * Appends the result to logText.
     */
    
    public void checkSelected(){
        if (getSelect() == null || getSelect().isEmpty()) return;
        
        String mod = modifier.getText().replace(" ","");
        
        if ("".equals(mod)) logText.appendText(CampaignObject.checker(getSelect()));
        else{
            DiceExpressionParser parser = new DiceExpressionParser();
            if (parser.validateAbl(mod)) logText.appendText(CampaignObject.checker(mod,getSelect()));
            else errorDialog("Modifier is not a number or valid dice expression.");
        }
    }
    
    /**
     * Checks the input of freeCheck according to the current Campaigns rules.
     * Appends the result to logText.
     */
    
    public void checkFreely(){
        String mod = freeCheck.getText();
        if (mod == "") return;
        String output = "\n\nIndependent Check: ";
        DiceExpressionParser parser = new DiceExpressionParser();
        if (parser.validateAbl(mod)) {
            output += getCamp().indCheck(mod);
            logText.appendText(output);
        }
        else errorDialog("Input is not a number or valid dice expression.") ;
    }
    
    
    
    
    /* Concerning info text area */
    
    /**
     * Loads the .info text of LeadSelect to infoBox.
     */
    
    public void loadInfo(){
        infoBox.setText(getLeadSelect().retrieveInfo());
        currentlyDisplayed = getLeadSelect();
    }
    
    /**
     * Sets infoBox editable and sets its read only checkbox false.
     */
    
    public void infoEditable(){
        infoBox.setEditable(true);
        infoBoxReadOnly.setSelected(false);
    }
    
   
    /**
     * Saves the current text of infoBox to its corresponding CampaignObject.
     */
    
    public void saveInfoBTW(){
        infoBoxReadOnly.setSelected(true);
        currentlyDisplayed.setInfo(infoBox.getText());
    }
   
    
    /**
     * When read only Check Box is clicked, the editable status of infoBox is changed
     * accordingly.
     */
    
    public void readOnlyClicked(){
        if (infoBoxReadOnly.isSelected()) infoBox.setEditable(false);
        else infoEditable();
    }
    
    
    /**
     * Sets infoBox editable, when double clicked.
     * @param evt The MouseEvent fired by clicking infoBox.
     */
    
    public void infoBoxClicked(MouseEvent evt){
        if(evt.getClickCount()==2) infoEditable();
    }
    
    /**
     * Clears the log text area.
     */
    
    public void clearLog(){
        logText.clear();
    }
    
 
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        namingPatternsList = NamingPatternsList.getInstance();
      
        
       campTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       newFile();
       if(camps.isEmpty()){
           System.exit(1);
       }
        
    }

    
    
    private void displayCamp() {
        Campaign camp = getCamp();
        TreeItem<CampaignObject> item = treeItemize(camp);
        campTree.setRoot(item);
    }
    
    /**
     * Shortcut for the currently displayed campaign.
     * @return The campaign at position .index in camps.
     */
    
    private Campaign getCamp(){
        return camps.get(index);
    }
    
    
    /**
     * Displays an error dialog.
     * @param string The error message.
     */
    
    private void errorDialog(String string){
        new Alert(Alert.AlertType.ERROR,string).showAndWait();
        
    }

    /**
     * Returns the CampaignObject that is associated with top item selected in campTree.
     * @return The value of the selected TreeItem<CampaignObject>.
     */
    
 private CampaignObject getLeadSelect(){ // Tut nicht.
    TreeItem<CampaignObject> selectedItem = (TreeItem<CampaignObject>) campTree.getSelectionModel().getSelectedItem();
    CampaignObject co= selectedItem == null ? getCamp() : selectedItem.getValue() ;
     return co;
}
    
    /**
     * Returns all CampaignObjects associated with items selected in campTree.
     * @return The values of the selected TreeItem<CampaignObject> as List.
     */
    
    private Collection<CampaignObject> getSelect(){
        List selectedItems =  campTree.getSelectionModel().getSelectedItems();
        Collection<CampaignObject> cos = new ArrayList<>();
        for (Object item : selectedItems) cos.add(((TreeItem<CampaignObject>) item).getValue());
        return cos;
        
    }
    
    /**
     * Turns a Campaign with Heroes and their Abilities into a tree to be used with TreeView.
     * @param camp The Campaign at the root of the tree.
     * @return 
     */
    
    private TreeItem<CampaignObject> treeItemize(Campaign camp){
        TreeItem<CampaignObject> tic = new TreeItem<>(camp);
        for (Hero hero: camp.getChars()){
            TreeItem<CampaignObject> tih = new TreeItem<>(hero);
            for(Ability abl: hero.getAbl()){
                TreeItem<CampaignObject> tia = new TreeItem<>(abl);
                tih.getChildren().add(tia);
            }
            tic.getChildren().add(tih);
        }
        tic.setExpanded(true);
        return tic;
    }
    
    /**
     * Saves the current version of getCamp(), makes a deep clone and adds the clone to the list of edits before the currently displayed campaign.
     * Moves up the edit index and removes all further edits from the list.
     * Called by all methods that modify the displayed campaign.
     */

    private void saveCurrentEdit() {
        saveInfoBTW();
       for (int i= index+1; i<camps.size(); i++) camps.remove(camps.get(i)); // Remove all newer edits.
       camps.add(index,getCamp().duplicate());
       index++;
    }
    
}
