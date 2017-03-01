/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teiknari.v2.vidmot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import teiknari.v2.virkni.Ferhyrningur;
import teiknari.v2.virkni.Hringur;

/* Þessi klasi, sem erfir frá Jpanel, heldur utan um öll litablöð á viðmótinu.
   Þ.e. þá panela sem sýna núverandi og nýlega liti, ásamt gegnsæispanelnum.
*/
public class SyniBlad extends JPanel {
    
    // Hér eru tilviksbreyturnar tilgreindar sem hlutur af tagi Syniblad hefur.
    // Nöfnin eru nokkuð lýsandi, einnig er hlutur af tagi Graphics2D útskýrður
    // í Teikniblad klasanum.
    protected Graphics2D g2;
    protected Ferhyrningur syniFerhyrningur;
    protected Hringur syniHringur;
    protected int size;

    // Smiðurinn fyrir litaglugga. Hér eru tilviksbreyturnar frumstillar, þær sem
    public SyniBlad() {
        
    }

    // Þetta fall stillir gegnsæishlutfallið á breytunni syniFerhyrningur.
    // Er bara notað í panelnum sem sýnir gegnsæi.
    public void setTransParency(float trans) {
        syniFerhyrningur.setTransparency(trans);
        repaint();
    }

    // Næstu tvö föll eru getter og setter fyrir litina sem tilviksbreyturnar eiga að hafa.
    public void setColor(Color c) {
        syniFerhyrningur.setColor(c);
        syniFerhyrningur.setFiller(c);
        repaint();
    }
    
    public Color getColor(){
        return syniFerhyrningur.getColor();
    }

    // Þetta fall teiknar ferhyrning á sýniblaðið. Það tekur inn boolean breytu
    // sem segir til um hvort ferningurinn eigi að vera stór eða lítill.
    public void drawRect(boolean Big) {
        if (Big) {
            syniFerhyrningur = new Ferhyrningur(10, 10, this.getWidth() - 10, this.getHeight() - 10, 0, Color.BLACK, Color.BLACK, true, 1.0f);
        } else {
            syniFerhyrningur = new Ferhyrningur(0, 0, this.getWidth(), this.getHeight(), 0, Color.WHITE, Color.WHITE, true, 1.0f);
        }
        repaint();
    }
    
    // Þetta fall teiknar hring á sýniblaðið. Er einungis notað í panelnum fyrir gegnsæi
    // til að sýna visually hversu mikið gegnsæið er.
    public void drawCircle(){
        syniHringur = new Hringur(20,20,this.getWidth()-20,this.getHeight()-20,5,Color.BLACK,Color.BLACK,true,1.0f);
    }
    

    // Þetta fall býr til hlut g2 af tagi Graphics2D sem er svo teiknað á til að
    // Fá myndina fram á sýniblaðið.
    // fallið teiknar svo ferhyrning og hring á blaðið ef þau form eru til staðar.
    @Override
    public void paintComponent(Graphics g) {
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        if (syniFerhyrningur != null) {
            syniFerhyrningur.paint(g2);
        }
        if (syniHringur != null) {
            syniHringur.paint(g2);
        }
    }
    
}
