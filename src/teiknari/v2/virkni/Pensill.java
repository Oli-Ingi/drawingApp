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

/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna beina línu
   á Graphics2D hlut. 
*/
public class Pensill extends Form {
    
    // Smiður fyrir hluti af tagi Pensill.
    // Kannski að ég taki það fram, þá er kallað endurtekið á þetta fall ef
    // valið tól notandans er pensill, þannig í raun eru teiknaðar fullt af litlum
    // línum.
    public Pensill(int oldX, int oldY, int newX, int newY, int size, Color c, float trans){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.color = c;
        this.size = size;
        this.trans = trans;
    }
    
    
    // Þetta fall teiknar línu á Graphics2D hlutinn g2, sem er tekinn inn í fallið.
    @Override
    public void paint(Graphics2D g2){
        g2.setColor(color);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        g2.setStroke(new BasicStroke(size));
        g2.drawLine(oldX, oldY, newX, newY);
    }
}
