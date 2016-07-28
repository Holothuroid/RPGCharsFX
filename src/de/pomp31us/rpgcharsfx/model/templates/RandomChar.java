/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.templates;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * This class provides two useful methods for concatenating lists of RandomStats.
 */
public class RandomChar extends ArrayList<RandomStat> implements Serializable {
  
     /**
     * Combine two lists of type RandomChar.
     * @param that The list to be appended.
     * @return The combined list.
     */  
    
  public RandomChar conjoin (RandomChar that){
      this.addAll(that);
      return this;
  }
    
   /**
     * Add another element to this list.
     * @param that The element to be appended.
     * @return This list.
     */
  
    public RandomChar conjoin (RandomStat that){
      this.add(that);
      return this;
  }
  
}
