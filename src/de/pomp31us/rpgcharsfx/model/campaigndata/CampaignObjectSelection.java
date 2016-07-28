/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.campaigndata;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author 1of3
 */

    public class CampaignObjectSelection implements Transferable, ClipboardOwner{
        public static DataFlavor flavor = new DataFlavor(java.util.Collection.class,"RPGChars Campaign Objects");
        Collection<CampaignObject> cobjs;

    public CampaignObjectSelection(CampaignObject... cobjs) {
        List<CampaignObject> list = Arrays.asList(cobjs);
        this.cobjs = list;
    }

    public CampaignObjectSelection(Collection<CampaignObject> cobjs) {
        
        this.cobjs = cobjs;
    }
             
        
        @Override
        public DataFlavor[] getTransferDataFlavors() {
             DataFlavor[] flavors = new DataFlavor[]{flavor};
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor == this.flavor;
        }

        @Override
        public Collection<CampaignObject> getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if(flavor == CampaignObjectSelection.flavor) return (Collection<CampaignObject>) cobjs;
            throw new UnsupportedFlavorException(flavor);
        }

        @Override
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
            
        }
        
    }

