/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teiknari.v2.virkni;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna texta á
   Graphics2D hlut.
*/
public class Strengur extends Form{
    
    private final String strengur;
    // strengur geymir stafi sem eru svo teiknaðir á Graphics2D hlut.
    
    // Smiðurinn fyrir hluti af tagi Strengur.
    public Strengur(int xPos, int yPos, String strengur, int size, Color color, float trans){
        this.strengur = strengur;
        this.oldX = xPos;
        this.oldY = yPos;
        this.size = size+15;
        this.color = color;
        this.trans = trans;
    }
    
    
    // Fallið teiknar stafastreng á Graphics2D hlutinn g2, sem tekinn er inn í fallið.
    @Override
    public void paint(Graphics2D g2){
        g2.setColor(color);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        g2.setFont(new Font("Tahoma",Font.PLAIN,size));
        g2.drawString(strengur,oldX,oldY); 
    }
    
}
