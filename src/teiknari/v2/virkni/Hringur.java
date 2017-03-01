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

/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna hringlaga hlut, fylltan eða ekki,
   á Graphics2D hlut.
*/
public class Hringur extends Form{
    
    // Smiður fyrir hluti af tagi Hringur.
    public Hringur(int oldX, int oldY, int newX, int newY, int size, Color c, Color filler, boolean fill, float trans){
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.color = c;
        this.size = size;
        this.fill = fill;
        this.filler = filler;
        this.trans = trans;
    }
    
    // Þetta fall svissar á breytunum newX og oldX annars vegar, og newY og oldY
    // hinsvegar, ef newX/newY eru minni en oldX/oldY. Þetta gerir það að verkum
    // að notandi getur teiknað formið í allar áttir.
    private void findDirection(){
        if(newY<oldY){
            int tmp = newY;
            newY=oldY;
            oldY = tmp;
        }
        if(newX<oldX){
            int tmp = newX;
            newX = oldX;
            oldX = tmp;
        }
    }
    
    // Þetta fall teiknar hring á Graphics2D hlutinn g2, sem er tekinn inn í fallið.
    // Hringurinn er fylltur ef fill breytan er true.
    @Override
    public void paint(Graphics2D g2){
        findDirection();
        g2.setColor(color);
        g2.setStroke(new BasicStroke(size));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        g2.drawOval(oldX, oldY, newX-oldX, newY-oldY);
        
        if(fill){
            g2.setColor(filler);
            g2.fillOval(oldX, oldY, newX-oldX, newY-oldY);
        }
    }
    
}
