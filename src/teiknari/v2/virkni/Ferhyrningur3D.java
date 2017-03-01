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

/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna þrívíddar-
   ferhyrning á Graphics2D hlut.
*/
public class Ferhyrningur3D extends Form{


    
    // Smiður fyrir hluti af tagi Ferhyrningur3D
    // Tilviksbreytunum er lýst í klasanum Form.
    public Ferhyrningur3D(int oldX, int oldY, int newX, int newY, int size, Color c, Color filler, boolean fill, float trans){
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
    
    // Þetta fall teiknar 3D-ferhyrning á Graphics2D hlutinn g2, sem er tekinn inn í fallið.
    // Ef fill er true þá er kassinn fylltir. (öll smíðaköll á þennan klasa gera ráð fyrir
    // fylltum ferhyrning).
    @Override
    public void paint(Graphics2D g2){
        findDirection();
        g2.setColor(color);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        g2.setStroke(new BasicStroke(size));
        g2.draw3DRect(oldX, oldY, newX-oldX, newY-oldY, true);
        
        if(fill){
            g2.setColor(filler);
            g2.fill3DRect(oldX, oldY, newX-oldX, newY-oldY,true);
        }
    }
}
