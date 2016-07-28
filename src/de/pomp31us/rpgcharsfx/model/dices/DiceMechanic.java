/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.dices;

import java.io.Serializable;
import java.util.function.Function;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author viona-173
 */

@XmlRootElement
public interface DiceMechanic extends Function<DString,DString>, Serializable {
        
    public default DiceMechanic andThen(DiceMechanic after){
        return s -> {
            return after.apply(this.apply(s));
        };
    };
    
    public default DiceMechanic compose(DiceMechanic before){
        return s -> {
            return this.apply(before.apply(s));
        };
    };
    
         
    
}
