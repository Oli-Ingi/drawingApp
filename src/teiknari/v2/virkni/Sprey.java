/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teiknari.v2.virkni;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna sprey
   áhrif á Graphics2D hlut.
*/
public class Sprey extends Form{
    
    int secondX;
    // secondX geymir random x staðsetningu á teikniblaði sem miðast við oldX sem er svo tekið inn
    // í hlut af tagi Sprey í gegnum smiðinn.
    int secondY;
    // secondY geymir random y staðsetningu á teikniblaði sem miðast við oldX sem er svo tekið inn
    // í hlut af tagi Sprey í gegnum smiðinn.
    

    
    public Sprey(int oldX, int oldY, Color c, int size, float trans){
        this.color = c;
        this.trans = trans;
        this.size = size;
        this.oldX = (oldX-size/2)+(int)(Math.random()*oldX%size);
        this.oldY = (oldY-size/2)+(int)(Math.random()*oldY%size);
        secondX = (oldX-size/2)+(int)(Math.random()*oldX%size);
        secondY = (oldY-size/2)+(int)(Math.random()*oldY%size);     
    }
    
    // Fallið teiknar (eða fyllir bara í raun) litla hringi á Graphics2D hlutinn g2
    // sem er tekinn inn í fallið.
    @Override
    public void paint(Graphics2D g2){
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        
        g2.fillOval(oldX, oldY, 1, 1);
        if(secondX!=oldX && secondY!=oldY){
            g2.fillOval(secondX, secondY, 1, 1);
        }
    }
}
