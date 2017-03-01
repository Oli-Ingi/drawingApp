/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teiknari.v2.virkni;

import java.awt.Color;
import java.awt.Graphics2D;

/* Þessi klasi er abstract. Allir klasar sem teikna á teikniblöðin, erfa frá
   þessum klasa. Helsti tilgangur þessa klasa er að geta sett alla teikningu
   á teikniblöðin inn í sama fylkið. Einnig er þetta góð forritunarvenja, þar 
   sem þetta styttir kóðann.
*/
public abstract class Form {
    protected int oldX;
    // oldX heldur utan um x staðsetningu á teikniblaðinu.
    protected int oldY;
    // oldY heldur utan um y staðsetningu á teikniblaðinu.
    protected int newX;
    // newX heldur utan um nýrri x staðsetningu en oldX.
    protected int newY;
    // newY heldur utan um nýrri y staðsetningu en oldY.
    protected Color color;
    // color heldur utan um litinn sem er svo notaður þegar hlutur af tagi form er teiknaður.
    protected int size;
    // size heldur utan um stærð á strokunni sem er notuð þegar hlutur af tagi form er teiknaður.
    // í tilviki forma útlínur.
    protected float trans;
    // filler heldur utan um fylli-lit hlutar af tagi form.
    protected Color filler;
    // fill er true ef hlutur á að vera fylltur.
    protected boolean fill;
    
    // Smiður fyrir hluti af tagi form
    public Form() {
    }

    // Getter og setter fyrir lit hlutar af tagi form.
    public void setColor(Color c) {
        this.color = c;
    }
    public Color getColor(){
        return this.color;
    }
   
    // Abstrakt fall sem tekur inn Graphics 2D hlut, er útfært í undirklösum.
    public abstract void paint(Graphics2D g2);
    
}
