/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.io;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import de.pomp31us.rpgcharsfx.model.campaigndata.Campaign;


/**
 *
 * @author viona-173
 */
public class LoadSave {
    
    private String extension;
    private final JFileChooser chooser = new JFileChooser();
    
   // Constructor
   public LoadSave(String extension){
        this.extension = extension;
        VariableFileFilter filter = new VariableFileFilter(extension);
        chooser.setFileFilter(filter);
    }

 
    public File saveAs(Object obj) {
        FileOutputStream stream = null;
        File file = null;
        try {
            chooser.setSelectedFile(new File(obj.toString().replaceAll(" ","_") + extension));
            chooser.showSaveDialog(null);
            file = chooser.getSelectedFile();
            stream = new FileOutputStream(file);
            ObjectOutputStream writer = new ObjectOutputStream(stream);
            writer.writeObject(obj);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return file;
    }
    
    
    public void save(Object obj, File file){
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            ObjectOutputStream writer = new ObjectOutputStream(stream);
            writer.writeObject(obj);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Object load() throws ClassNotFoundException{
        
        Object obj = null;
        try {
            FileInputStream stream;
            chooser.showOpenDialog(null);
             File file = chooser.getSelectedFile();
            stream = new FileInputStream(file);
            ObjectInputStream reader = new ObjectInputStream(stream);
            obj =  reader.readObject();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } 
            
         catch (IOException ex) {
            ex.printStackTrace();
        }
       return obj;
    }
    
 
    
    private class VariableFileFilter extends javax.swing.filechooser.FileFilter {

    String extension;

    public VariableFileFilter(String extension) {
        this.extension = extension;
    }
    
    
    
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return extension;
    }
    
}
    
}
