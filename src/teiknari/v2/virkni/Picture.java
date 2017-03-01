/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teiknari.v2.virkni;

import java.awt.Graphics2D;
import java.awt.Image;

/* Klasinn erfir frá klasanum Form. Tilgangur klasans er að teikna mynd
   á Graphics2D hlut. 
*/
public class Picture extends Form{
    
    
    Image image;
    // image er hlutur af tagi Image sem geymir mynd. Í þessu tilviki myndir
    // af tagi PNG eða JPEG.
    
    // Smiður fyrir hluti af tagi Picture.
    public Picture(Image image){
        this.image = image;
    }
    
    
    // Þetta fall teiknar myndina í image á Graphics2D hlutinn g2, sem er tekinn inn í fallið.
    @Override
    public void paint(Graphics2D g2){
        
        g2.drawImage(image,0,0,null);
    }
    
}
