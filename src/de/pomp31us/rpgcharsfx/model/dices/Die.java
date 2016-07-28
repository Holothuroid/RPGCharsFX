/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.dices;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static de.pomp31us.rpgcharsfx.model.dices.DTrick.*;

/**
 *
 * @author viona-173
 */
public class Die //implements Comparable 
{
    
   private int size;
   private int mod;
   ArrayList<DTrick> funky = new ArrayList<>();
    
      
    final private static Random	RANDOM	= new Random(); 
    
    /**
     * Würfelt einen Würfel mit n Seiten. n=0 liefert Null. d(-n) = -dn.
     * @param sides Die Größe des Würfels.
     * @return 
     */
    
    public static int singleD(int sides){
        if (sides > 0) return RANDOM.nextInt(sides ) + 1;    // positive Zahl n =>  1 - n       
        else if (sides == 0) return 0;                        // 0 => 0
        else return -(RANDOM.nextInt(-sides ) + 1); // negative Zahl n => negative Zahl
    }
    
  
    
    
    /* Setters ans Getters start */

    public int getSize() {
        return size;
    }

    public int getMod() {
        return mod;
    }

    public ArrayList<DTrick> getFunky() {
        return funky;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public void setFunky(ArrayList<DTrick> funky) {
        this.funky = funky;
    }
    
    public void addTrick(DTrick trick){
        this.funky.add(trick);
    }
        
    
    public void addTrick(List<DTrick> tricks){
        funky.addAll(tricks);
    }
    
    
    /* Setters and Getters end */    
        
    
        /* Constructors start */

    public Die(int size) {
        this.size = size;
    }

    public Die(int size, int mod) {
        this.size = size;
        this.mod = mod;
    }

    public Die(int size, ArrayList<DTrick> funky) {
        this.size = size;
        this.funky = funky;
    }
        
    public Die(int size, DTrick trick) {
        this.size = size;
        this.funky.add(trick);
    }    
        
        /* Constructors end */
        
        /** Würfelt den Würfel und addiert den Modifikator hinzu.
         * 
         * @return ist ein int.
         */
    
        public int roll(){
            if (size == 0) return mod;
            
            int a =  singleD(size);
            
            if (funky.contains(EXPLODE)){
                int repeat=0;
                while(repeat < 50 && a % size == 0) {
                    a+= singleD(size);
                    repeat++;
            }}
                
            if(funky.contains(ODD) && a % 2 == 0) a=-a;
            
            return a + mod;
        }
        
        
        
        public int countUnder(int i){
            int roll = roll();
            if (roll <=i) return 1;
                    else return 0;
        }
        
        public int countOver(int i){
            int roll = roll();
            if (roll >=i) return 1;
                    else return 0;
        }
        
        public int aPool(int... table){
            int roll = Math.abs(roll());
            if (roll < table.length) return table[roll];
            else return 0;
        }
        
        
        /**
         * Würfelt den Würfel. Gibt sowohl Ergebnis +mod als auch das unmodifizierte Würfelergebnis aus.
         * @return ist ein String.
         */
        
        public String nat(){
            int a = roll();
            String erg = ""+ a;
            return erg + "[" + a + "]";
        }
        
        
    @Override
        public String toString(){
        String output = "";
        if (size == 0 && mod == 0) output = "0";   
        else if (size > 0)  output += "1d" + size;
             if (size <0) output += "-1d" + Math.abs(size);
             if (mod >0) output += "+" + mod;
             if (mod <0) output += mod;
        return output;     
         }    

   @Override
        public boolean equals(Object obj){
           if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        
        Die that = (Die) obj;
        return this.size == that.size && this.mod == that.mod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.size;
        hash = 97 * hash + this.mod;
        return hash;
    }

//    @Override
//    public int compareTo  (Object o) {
//       Die that = (Die) o;
//       if (this.equals(that)) return 0;
//       if (this.size > that.size) return 1;
//       if (this.size < that.size) return -1;
//       if (this.size == that.size){
//           if (this.mod>that.mod) return 1;
//           if (this.mod<that.mod) return -1;
//       }
//    }



        
}
