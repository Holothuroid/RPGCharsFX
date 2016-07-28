/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.dices;

import java.util.ArrayList;
import javax.swing.JOptionPane;





/**
 *
 * @author viona-173
 */
public class DiceExpressionParser { 
   

    
   public boolean validateAbl(String input){    
       return convertDialect(input.replaceAll("(!|\\?)","")).matches("(([\\+]|-)?\\d+d?(\\d*|f))(([\\+]|-)+\\d*d?(\\d*|f))*(\\$\\d+)*(,(([\\+]|-)?\\d+d?(\\d*|f))(([\\+]|-)+\\d*d?(\\d*|f))*(\\$\\d+)*)*");    
   }
       
  public boolean validate(String input){
      return convertDialect(input.replaceAll("(!|\\?)","")).matches("(([\\+]|-)?\\d+d?(\\d*|f))(([\\+]|-)+\\d*d?(\\d*|f))*(\\$\\d+)*");    
  }

  public ArrayList<DString> parseAbl(String input){
      ArrayList<DString> out = new ArrayList<>();
      
  try {    String[] dStrings = convertDialect(input).split(",");
  
      for(String s : dStrings) out.add(parse(s));
  }
  catch (ArrayIndexOutOfBoundsException e){
      JOptionPane.showMessageDialog(null, "The input was not recognized as a valid number or dice expression.");
  }
       return out;
  }
          
   
 public DString parse(String input){
   DString output = new DString();
   
   int rule =0; // Zählt die Ausrufezeichen, setzt rule = dieser Anzahl, entfernt die Ausrufezeichen.
   for(int i = 0; i<input.length();i++) if (input.charAt(i)=='!')  rule++;
   for(int i = 0; i<input.length();i++) if (input.charAt(i)=='?')  rule=rule+10;
   input = input.replaceAll("(!|\\?)","");
   output.setRule(rule);
       
   input = convertDialect(input); // Entfernt alle Whitespaces, wandelt dialect um.
   
   if(!validate(input)) {
       output.getCritlog().add("Error: Parser received illegal dice expression!");
       return  output;
   }
   
   String[] segsdollar = input.split("\\$");
   for(int j = 1; j<segsdollar.length; j++) output.getParams().add(Integer.parseInt(segsdollar[j]));
           
   
   String[] segsplus  = segsdollar[0].split("(?=(\\+|-))");   // Zerlegt den String jeweils vor + und -.
   for(String seg : segsplus){                                    // Geht die Segmente durch
         if(seg.matches("((\\+|-)?\\d+)")) output.add(0,Integer.parseInt(seg));  // Wenn Segment Zahl, mach d0+mod
         else if(seg.matches("((\\+|-)?\\d+df)")) {
                for(int i=0;i< Math.abs(Integer.parseInt(seg.split("d")[0]));i++) // Wenn Segment ndf mach n Fudge-Dice.
                output.add(3,-2);
                    }
         else for(int i=0;i< Math.abs(Integer.parseInt(seg.split("d")[0]));i++)    // Wenn Segment ndx, mach n mal...               
                     output.add((int) Math.signum(Integer.parseInt(seg.split("d")[0])) * Integer.parseInt(seg.split("d")[1])  );       // ...  1d(signum(n))             
       }

    return output;    
 }

 


   String convertDialect(String input) { 
       input = input.replaceAll(" ", "");                  // Whitspaces entfernen   
       input = input.replaceAll(";", ",");
       input = input.replaceAll("§", "$");
       input = input.replaceAll("D", "d"); 
       input = input.replaceAll("w", "d");  // Deutsch (W)würfel zu engl. (D)ie
       input = input.replaceAll("W", "d");
       input = input.replaceAll("%", "100");  // d% zu d100
       input = input.replaceAll("dF", "df");
        
       input = input.replaceAll("\\+d", "+1d");  // dx zu 1dx
       input = input.replaceAll("-d", "-1d");
       input = input.replaceAll(",d", ",1d");
       if (!input.isEmpty() && input.charAt(0) == 'd') input = 1 + input;
       input = input.replaceAll("d-", "d6-");  // Würfel ohne explizite Größe werden d6.
       input = input.replaceAll("d\\+", "d6+");
       input = input.replaceAll("d,","d6,");     
       if (input.endsWith("d")) input = input+"6";
      
         return input; 
             
   } 
    


   
   
}