/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx.model.templates;

import de.pomp31us.rpgcharsfx.model.dices.DString;
import de.pomp31us.rpgcharsfx.model.dices.DiceMechanic;
import java.io.Serializable;
import java.util.ArrayList;
import static de.pomp31us.rpgcharsfx.model.dices.DTrick.*;

/**
 *
 * @author 1of3
 */
public enum DefaultMechanics implements Serializable {
    sum("SUM", s->s.sum()),
    plusD4("+d4", s->s.add(4).sum()),
    plusD6("+d6", s->s.add(6).sum()),
    plusD8("+d8", s->s.add(8).sum()),
    plusD10("+d10", s->s.add(10).sum()),
    plusD12("+d12", s->s.add(12).sum()),
    plusD20("+d20", s->s.add(20).sum()),  
    plusD4noteAll("+d4, note results", s->new DString(4).noteAll().add(s).sum()),
    plusD6noteAll("+d6, note results", s->new DString(6).noteAll().add(s).sum()),
    plusD8noteAll("+d8, note results", s->new DString(8).noteAll().add(s).sum()),
    plusD10noteAll("+d10, note results", s->new DString(10).noteAll().add(s).sum()),
    plusD12noteAll("+d12, note results", s->new DString(12).noteAll().add(s).sum()),
    plusD20noteAll("+d20, note results", s->new DString(20).noteAll().add(s).sum()),
    plus2D6("+2d6", s->s.add("2d6").sum()),
    plus3D6("+3d6", s->s.add("3d6").sum()),
    fudge("+4dF", s->s.add("4d3-8").sum()),
    OSHAttack("Old-School Hack attack (1 face die)", s-> new DString(10).note(10).add(10).add(s).sum()),
    OSHAttack2("Old-School Hack attack (2 face die)", s-> new DString(10).add(10).note(10).add(s).sum()),    
    DnDcreation("D&D3+ Random Stats, +4d6 keep 3, determine modifiers", s -> new DString("4d6").keep(3).sum().noteAll().aPool(-5,-4,-4,-3,-3,-2,-2,-1,-1,0,0,1,1,2,2,3,3,4,4,5,5)),
   OSHcreation("OSH Random Stats", s -> s.add("2d10").sum().aPool(-2,-2,-2,-2,-2,-1,-1,-1,0,0,0,1,1,2,2,3,3,4,4,5)),
   oWoD("Old / Classic World of Darkness", s-> { try{return s.standardize(10).note(1).countOver(s.getParams().get(0));}
    catch(IndexOutOfBoundsException e){return s.standardize(10).roll();}}),
   oWoDSpecial("Old/Classic WoD with Specialty",  s-> { try{return s.standardize(10).spawn(10).note(1).countOver(s.getParams().get(0));}
    catch(IndexOutOfBoundsException e){return s.standardize(10).spawn(10).roll();}}),
   d10Pool("d10 Pool", s ->s.standardize(10).roll() ),
   d6Pool("d6 Pool", s ->s.standardize(6).roll() ),
   d12Pool("d12 Pool", s ->s.standardize(12).roll()),
   d6PoolExplode("d6 Pool", s ->s.standardize(6).addTrick(EXPLODE).roll() ),
   rollunder3d6("3d6 rollunder", s-> new DString("3d6").noteAll().diffToUnder(s.toInt())),
   SplittermondBasic("+2d10, note botches (2-3) & crits (19-20)", s-> new DString("2d10").sum().note(2,3,19,20).add(s).sum()),
   SplittermondSafety("+2d10 keep highest", s-> new DString("2d10").keep(1).add(s).sum()),
   SplittermondRisk("+4d10, note botches (1-2) & crits (9-10) to find crits", s-> new DString("4d10").note(1,2,9,10).add(s).sum()),
   //d100Rollunder("d100 Rollunder", s->new DString(100).diffToUnder(s.toInt())),
   
   
;
    
            
            
    
    private String descr;
    private DiceMechanic mech;

    private DefaultMechanics(String descr, DiceMechanic mech){
        this.descr = descr;
        this.mech = mech;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public DiceMechanic getMech() {
        return mech;
    }

    public void setMech(DiceMechanic mech) {
        this.mech = mech;
    }
    
    
}
