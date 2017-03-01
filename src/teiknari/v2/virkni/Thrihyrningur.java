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
import java.awt.Polygon;
import java.awt.Shape;


/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna þríhyrning á
   Graphics2D hlut.
*/
public class Thrihyrningur extends Form{
    
    private final int xPoly[];
    // xPoly er heiltölufylki sem heldur utan um staðsetningu allra x punkta í forminu poly.
    private final int yPoly[];
    // yPoly er heiltölufylki sem heldur utan um staðsetningu allra y punkta í forminu poly.
    private final Shape poly;
    // poly er hlutur af tagi Shape. poly er teiknað á Graphics2D hlut, og er 
    // í tilviki þessa klasa, þríhyrningur.
    
    // Smiður fyrir hluti af tagi þríhyrningur.
    public Thrihyrningur(int oldX, int oldY, int newX, int newY, int size, Color c, Color filler, boolean fill,float trans){
        this.color = c;
        this.size = size;
        this.fill = fill;
        this.filler = filler;
        this.trans = trans;
        
        xPoly = new int[]{oldX,oldX+(newX-oldX)/2,newX};
        yPoly = new int[]{oldY,oldY-(newX-oldX)/2,newY};

        poly = new Polygon(xPoly,yPoly,xPoly.length);
        
    }
    
    // Þetta fall teiknar hlutinn poly á Graphics2D hlutinn g2, sem er tekinn inn í fallið.
    // ef breytan fill er true þá er poly fyllt.
    @Override
    public void paint(Graphics2D g2){
        g2.setColor(color);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        g2.setStroke(new BasicStroke(size));
        g2.draw(poly);
        
        if(fill){
            g2.setColor(filler);
            g2.fill(poly);
        }
    }
    
}
