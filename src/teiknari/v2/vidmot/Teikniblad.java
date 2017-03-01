package teiknari.v2.vidmot;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import java.util.Timer;
import javax.swing.SwingUtilities;
import teiknari.v2.virkni.Ferhyrningur;
import teiknari.v2.virkni.Ferhyrningur3D;
import teiknari.v2.virkni.Form;
import teiknari.v2.virkni.Hringur;
import teiknari.v2.virkni.MjukurFerhyrningur;
import teiknari.v2.virkni.Pensill;
import teiknari.v2.virkni.Picture;
import teiknari.v2.virkni.Sprey;
import teiknari.v2.virkni.Strengur;
import teiknari.v2.virkni.Thrihyrningur;

/* Þessi klasi, sem erfir frá Jpanel, sér um allt sem tengist teikningu á myndirnar.
   Myndirnar fimm sem hægt er að opna í viðmótinu eru tilviksbreytur af þessum klasa í
   klasanum Gluggi.
*/
public class Teikniblad extends javax.swing.JPanel {

    // g2 er Graphics 2D hlutur sem er notaður til að teikna á panelinn.
    private Graphics2D g2;
    // tool segir til um hvaða tól er valið til að teikna með.
    private int tool;
    // size segir til um stærðina á tólinu. (Yep :) )
    private int size;
    // þessar fjórar breytur halda utan um hvar á blaðinu tólin eiga að teikna.
    private int oldX, oldY, newX, newY;    
    // mainColor heldur utan um aðallitinn.
    Color mainColor;
    // filler heldur utan um fyllingar-litinn fyrir formin.
    Color filler;
    // counter heldur utan um núverandi staðsetningu í fylkinu shapes.
    private int counter;
    // shapes heldur utan um öll object, af tagi form, sem teiknuð hafa verið, og teikna
    // skal á myndina.
    private Form[] shapes;
    // erasers heldur sér utanum allar penslastrokur með strokleðurs-tólinu, svo hægt sé
    // að breyta litnum á þeim, þegar bakgrunninum er breytt.
    private Form[] erasers;
    // eraseCounter heldur utan um núverandi staðsetningu í erasers fylkinu.
    private int eraseCounter;
    // undoArray heldur utan um staðsetningar sem benda á hvar counter breytan var við byrjun
    // og enda hvers mousepressed og mousereleased events.
    private int[] undoArray;
    // undoCounter heldur utan um núverandi staðsetningu í fylkinu undoArray.
    private int undoCounter;
    // fill heldur utan um hvort form skuli vera fyllt eða ekki.
    private boolean fill;
    // temp er form sem er notað þegar teikna á form, þ.e. á meðan músin er dregin.
    private Form temp;
    // BucketPourAll segir til um hvort fatan eigi að hella á bakgrunn, eða yfir alla myndina.
    private boolean BucketPourAll;
    // strengur er textastrengurinn sem teiknaður er á myndina, þegar textatólið er valið.
    private String strengur;
    // transValue er float tala sem heldur utan um gegnsæishlutfall tóla.
    private float transValue;
    // sprayTimer er Timer notaður til að hægja á spreyinu þegar spreybrúsinn er valinn.
    private Timer sprayTimer;
    // sprayTimerTask heldur utan hvað á að gerast þegar sprayTimer segir að eitthvað eigi að gerast.
    private SprayTimerTask sprayTask;
    // name er nafnið á myndinni, sem er aðallega notað og útskýrt í klasanum Gluggi.
    private String name;
    // tab segir til um hvar í tabbed-pane glugganum í klasanum Gluggi, þessi mynd er.
    private int tab;
    
    
    
    /* Smiðurinn fyrir teikniblað. Hér eru tilviksbreyturnar frumstillar, þær sem
       þess þurfa.
       Einnig eru músarhlýðar stilltir hér.
    */
    public Teikniblad() {
        initComponents();
        
        shapes = new Form[150000];
        size = 5;
        counter = 0;
        mainColor = Color.BLACK;
        filler = Color.LIGHT_GRAY;
        tool=0;
        erasers = new Form[150000];
        eraseCounter = 0;
        undoCounter = 0;
        undoArray = new int[1000];
        fill = false;
        temp = null;
        sprayTimer = new Timer();     
        
        
        addMouseListener(new MouseAdapter(){
            
            // Þegar músin er færð yfir teikniblaðið sér þessi handler til þess
            // að ef texta-tólíð er valið, þá sé músarbendillinn texta-bendill.
            @Override public void mouseEntered(MouseEvent e){
                tool= Gluggi.getSelectedTool();

                if(tool==11){
                    setCursor (Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }
                else{
                    setCursor(Cursor.getDefaultCursor());
                }
            }
            /* Handler fyrir það þegar notandi heldur músinni niðri (án þess að hreyfa hana)
               á teikniblaðinu.
               Hér eru upplýsingar um tilviksbreytur fengnar frá klasanum Gluggi, um það
               hvernig form skuli teiknuð, stærð o.fl.
               Hér er einnig séð til þess að einungis vinstri músarhnappur hafi áhrif
               á blaðið.
               Hér er einnig séð til þess að einungis sé hægt að teikna á blaðið á meðan
               shapes fylkið hefur pláss fyrir form.
               Einnig er hér séð um að tólin teikni á rétta staðsetningu á blaðinu,
               þ.e. þar sem músin er.
            */
            @Override public void mousePressed(MouseEvent e){
                if(e.getButton()==1){
                    transValue = Gluggi.getTransValue();
                    tool = Gluggi.getSelectedTool();
                    mainColor = Gluggi.getMainColor();
                    filler = Gluggi.getFiller();
                    size = Gluggi.getStrokeSize();
                    oldX = e.getX();
                    oldY = e.getY();
                    fill = Gluggi.getFill();


                    if(counter<150000){
                        if(!(tool==2 || tool==0)){ 
                            undoArray[undoCounter] = counter;
                            undoCounter++;
                        }
                        if(tool==1 || tool==3){
                                newX = oldX+1;
                                newY = oldY+1;
                                addPensill();
                        }
                        if(tool==2){
                            BucketPourAll = Gluggi.getBucketMode();

                            if(BucketPourAll){
                                int option = 0;
                                if(counter!=0){
                                    option = JOptionPane.showConfirmDialog(null, 
                                    "Ertu viss um að þú viljir hella yfir alla myndina?\n"
                                    + "Það er ekki hægt að hætta við eftir þessa aðgerð.","Staðfesting",
                                    JOptionPane.OK_CANCEL_OPTION);
                                }
                                if(option==0){
                                    clearPic();
                                    setBackground(mainColor);
                                }
                                
                            }
                            else{

                                for(int i = 0; i<eraseCounter; i++){
                                    erasers[i].setColor(mainColor);
                                }
                                setBackground(mainColor);
                            }
                        }
                        if(tool==11){
                            addString();
                        }
                        if(tool==4){
                            if(sprayTask==null){
                                sprayTask = new SprayTimerTask();
                                sprayTimer.scheduleAtFixedRate(sprayTask,0,50);
                            }
                        }
                        if(tool!=4){
                            repaint();
                        }
                    }
                    else{
                        MaxedOut();
                    }
                
                }
            }
        });
        addMouseMotionListener(new MouseAdapter(){

            /* Handler fyrir það þegar dregur músinni yfir teikniblaðið.
               
               Hér er séð til þess að einungis sé hægt að hafa áhrif á teikniblaðið,
               á meðan vinstri músarhnappur er niðri.
               Hér er einnig séð til þess að einungis sé hægt að teikna á blaðið á meðan
               shapes fylkið hefur pláss fyrir form.
               Einnig er hér séð um að tólin teikni á rétta staðsetningu á blaðinu,
               þ.e. þar sem músin er.
            */
            @Override public void mouseDragged(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e)){
                    if(counter<150000){
                        newX = e.getX();
                        newY = e.getY();
                        if(tool==1 || tool==3){
                                addPensill();

                                oldX = newX;
                                oldY = newY;          
                        }
                        else if(tool==5){

                            temp = new Ferhyrningur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
                        }
                        else if(tool==6){
                            temp = new Hringur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
                        }
                        else if(tool==10){   
                            temp = new Thrihyrningur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
                        }
                        else if(tool==9){
                            temp = new MjukurFerhyrningur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
                        }
                        else if(tool==7){
                            temp = new Pensill(oldX,oldY,newX,newY,size,mainColor,transValue);
                        }
                        else if(tool==8){
                            temp = new Ferhyrningur3D(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
                        }
                        else if(tool==4){
                            oldX = newX;
                            oldY = newY;
                        }
                        if(tool!=4) repaint();
                    }
                    else{
                        MaxedOut();
                    }
                }
            }
        });
        
        addMouseListener(new MouseAdapter(){
            
            /* Handler fyrir það þegar notandi sleppir músinni, þ.e. hættir að þrýsta
               músarhnapp niður á teikniblaðinu.
               
               Hér er séð til þess að einungis vinstri músarhnappur hafi áhrif
               á blaðið.
               Hér er einnig séð til þess að einungis sé hægt að teikna á blaðið á meðan
               shapes fylkið hefur pláss fyrir form.
               Einnig er hér séð um að tólin teikni á rétta staðsetningu á blaðinu,
               þ.e. þar sem músin er.
            */
            @Override public void mouseReleased(MouseEvent e){
                if(e.getButton()==1){
                    if(counter<150000){
                        temp=null;
                        newX = e.getX();
                        newY = e.getY();
                        if(counter<100000){
                            if(tool==7){
                                addPensill();
                                repaint();
                            }
                            if(tool==5 || tool==8 || tool==9){
                                addRect();
                            }
                            if(tool==6){
                                addOval();
                            }
                            if(tool==10){
                                addThrihyrningur();
                            }
                            if(tool==4){
                                sprayTask.cancel();
                                sprayTask=null;       
                            }
                        }
                    }
                    else{
                        MaxedOut();
                    }
                }
            }
        });
    }
    
    // Þetta fall ákveður hvað verkefni skuli vinna þegar timerinn gefur til kynna
    // að tími sé til.
    private class SprayTimerTask extends TimerTask {
        @Override
        public void run() {
            drawSpray();
        }
    }
    // Opnar glugga sem segir notanda að myndin hafi náð hámarksstærð.
    private void MaxedOut(){
        JOptionPane.showMessageDialog(this, "Þessi mynd hefur náð hámarksstærð.");
    }
        
    // Opnar glugga þar sem notandi getur skrifað texta í, og setur strenginn í shapes fylkið.
    // Einnig hækkar fallið breytuna counter í samræmi við staðsetningu í shapes.
    public void addString(){
        strengur = JOptionPane.showInputDialog("Skrifaðu textann þinn hér:");
        if(strengur!=null){
            shapes[counter++] = new Strengur(oldX,oldY,strengur,size,mainColor,transValue);
        }
    }
    
    // Fallið bætir við þríhyrningsformi af tagi Thrihyrningur, í fylkið shape.
    // Einnig hækkar fallið breytuna counter í samræmi við staðsetningu í shapes.
    public void addThrihyrningur(){
        shapes[counter++] = new Thrihyrningur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
    }
    
    // Fallið bætir við penslastroku (í raun lítilli línu) af tagi Pensill, í fylkið shape.
    // Ef valið tól er strokleður, þá setur fallið strokleðursstrokuna einnig í fylkið 
    // erasers. Einnig eru hér counter og eraseCounter breyturnar hækkaðar í samræmi við
    // staðsetningu í viðeigandi fylkjum.
    public void addPensill(){
        if(tool==3){
            shapes[counter++]= new Pensill(oldX,oldY,newX,newY,size+10,getBackground(),1.0f);
            erasers[eraseCounter++] = shapes[counter-1];
        }
        else{
            shapes[counter++]= new Pensill(oldX,oldY,newX,newY,size,mainColor,transValue);
        }
    }
    
    // Fallið bætir við spreyi af tagi Sprey, þ.e. mjög litlum hringjum í fylkið shapes, og hækkar
    // counter til samræmis. Einnig er myndin endurteiknuð með spreyinu.
    public void drawSpray(){       
        shapes[counter++] = new Sprey(oldX,oldY,mainColor,size,transValue);
        repaint();   
    }
    
    // Fallið bætir við ferhyrning í shapes fylkið. Eftir því hvaða tól er valið,
    // er bætt við ferhyrning af tagi Ferhyrningur, MjukurFerhyrningur, eða Ferhyrningur3D.
    // counter er svo uppfært til samræmis.
    public void addRect(){
        if(tool==5){
            shapes[counter++] = new Ferhyrningur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
        }
        else if(tool==8){
            shapes[counter++] = new Ferhyrningur3D(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
        }
        else{
            shapes[counter++] = new MjukurFerhyrningur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
        }
    }
    
    // Fallið bætir við hring af tagi Hringur í shapes fylkið. Einnig er counter
    // uppfært til samræmis.
    public void addOval(){
        shapes[counter++] = new Hringur(oldX,oldY,newX,newY,size,mainColor,filler,fill,transValue);
    }
    
    // Þetta fall stillir counter breytuna eftir því hvar músinni var síðast þrýst
    // niður, en sú staðsetning er geymd í fylkinu undoArray, sem er uppfært til samræmis
    // með sínum undoCounter.
    public void undo(){

        if(undoArray[undoCounter]==0 && counter!=0){
            undoArray[undoCounter] = counter;
        }        
        if(undoCounter>0) undoCounter--;
        if(counter>0){
            counter = undoArray[undoCounter];
        }
        repaint();    
    }
    
    // Þetta fall stillir counter breytuna eftir því hvar músinni var þrýst niður, áður
    // en smellt var á undo takkann, þ.e. staðsetninguna sem var vistuð í undoArray.
    // undoArray er svo uppfært til samræmis.
    public void redo(){
        if(undoArray[undoCounter+1]!=0 || counter==0) undoCounter++;
        if(undoArray[undoCounter]!=0) counter=undoArray[undoCounter];
        repaint();
    }
    
    // Þetta fall stillir counter og eraseCounter í 0 þannig að engin form
    // eru teiknuð á blaðið. Einnig er bakgrunnur blaðsins stilltur sem hvítur.
    public void clearPic(){
        counter=0;
        eraseCounter = 0;
        for(int i = 0; i<undoCounter; i++){
            undoArray[i] = 0;
        }
        undoCounter=0;
        setBackground(Color.WHITE);
        repaint();
    }
    
    // Næstu 8 föll eru getterar og setterar sem eru aðallega notaðar af klasanum
    // Gluggi.
    public void setSize(int Size){
        this.size = Size;
    }
    
    public void setColor(Color c){
        this.mainColor = c;
    }
    
    public void setFiller(Color c){
        this.filler = c;
    }
    public int getCounter(){
        return this.counter;
    }
 
    public void setPicName(String name){
        this.name = name;
    }
    public String getPicName(){
        return name;
    }
    
    public void setTab(int tab){
        this.tab = tab;
    }
    
    public int getTab(){
        return this.tab;
    }
    
    // Þetta fall bætir formi af tagi Picture inn á teikniblaðið og í shapes fylkið. 
    //counter og undoCounter eru uppfærðar til samræmis.
    public void addImage(BufferedImage image){
        int option=0;
        if(image.getHeight()>this.getHeight() || image.getWidth()>this.getWidth()){
           option = JOptionPane.showConfirmDialog(null, 
                                    "Þess mynd er of stór fyrir teikniblaðið og því.\n"
                                  + "verður aðeins hluti af henni sýnilegur.\n"
                                  + "Viltu samt opna myndina?","Staðfesting",
                                    JOptionPane.OK_CANCEL_OPTION);
        }
        if(option==0){
            undoArray[undoCounter] = counter;
            undoCounter++;
            shapes[counter++] = new Picture(image);

            repaint();
        }
    }
    
    // Þetta fall býr til hlut g2 af tagi Graphics2D sem er svo teiknað á til að
    // Fá myndina fram á teikniblaðið.
    // Allir hlutir sem eru í shapes miðað við staðsetningu sem fæst með counter breytunni
    // eru teiknaðir.
    // Einnig er formið temp teiknað ef það er til staðar.
    @Override
    public void paintComponent(Graphics g){
        g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        for(int i = 0; i<counter; i++){
            shapes[i].paint(g2);
        }
        if(temp!=null){
            temp.paint(g2);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
