
package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Tetris extends JPanel{
        public static void main(String[] args) {
       
        JFrame f=new JFrame(" Jy Tetris");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(17*26+10,28*22+20); //frame er size
        f.setVisible(true);
        f.setLocationRelativeTo(null);
       
      
     
        final Tetris game=new Tetris();
        game.init();
         f.add(game);
        
        f.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent MyK) {
  //              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent MyK) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         
            switch(MyK.getKeyCode()){
                case KeyEvent.VK_UP:
                    game.rotate(-1);
                    break;
                    
                case KeyEvent.VK_DOWN:
                    game.dropDown();
                    break;
                    
                case KeyEvent.VK_LEFT:
                    game.move(-1);
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    game.move(+1);
                    break;
                    
            }
            }

            @Override
            public void keyReleased(KeyEvent MyK) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        new Thread(){ //proti second a niche jawa
        @Override
        public void run(){
        while(true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tetris.class.getName()).log(Level.SEVERE, null, ex);
            }
        game.dropDown();
        }
        }
        }.start();
    }    
    
    
    
    
 private final Point[][][] myPoint={     
        {
     //O er moto hobe
         {new Point(1,0),new Point(1,0),new Point(1,0),new Point(1,1)},
         {new Point(0,1),new Point(0,1),new Point(1,0),new Point(1,1)},
         {new Point(0,0),new Point(0,1),new Point(1,0),new Point(1,1)},
         {new Point(0,0),new Point(0,1),new Point(1,0),new Point(1,1)}
           
        },
     
     {
        
     //I banailam 
         {new Point(0,1),new Point(1,1),new Point(2,1),new Point(3,1)},
         {new Point(1,0),new Point(1,1),new Point(1,2),new Point(1,3)},
         {new Point(0,1),new Point(1,1),new Point(2,1),new Point(3,1)},
         {new Point(1,0),new Point(1,1),new Point(1,2),new Point(1,3)}
         
        },
     {
     //j banailam
         {new Point(0,1),new Point(1,1),new Point(2,1),new Point(2,0)},
         {new Point(1,0),new Point(1,1),new Point(1,2),new Point(2,2)},
         {new Point(0,1),new Point(1,1),new Point(2,1),new Point(0,2)},
         {new Point(1,0),new Point(1,1),new Point(1,2),new Point(0,0)}
         
        },
        {
     //L er moto hobe
         {new Point(0,1),new Point(1,1),new Point(2,1),new Point(2,2)},
         {new Point(1,0),new Point(1,1),new Point(1,2),new Point(0,2)},
         {new Point(0,1),new Point(1,1),new Point(2,1),new Point(0,0)},
         {new Point(1,0),new Point(1,1),new Point(1,2),new Point(2,0)}
         
        }
 };
 
   private final Color[] myColor={Color.CYAN,Color.magenta,Color.orange,Color.blue,Color.black,Color.pink,Color.red};
   private Point pt;
   private int currentPiece;
   private int rotation;
   private ArrayList<Integer> nextPieces=new ArrayList<Integer>();
   private int score,gameLevel;
   private Color[][] rong;
   
   private void init(){ //border banailam r notun piece
   
       rong=new Color[14][24];
       for(int i=0;i<14;i++){
           for(int j=0;j<23;j++){
               if(i==0||i==11||i==24){
                   rong[i][j]=Color.pink;
               
               }
               else{
               rong[i][j]=Color.black;
               }
           }
       }
   newPiece();
   }
   
   public void newPiece(){ //notun notun pice ashbe
       pt=new Point(5,2);
       rotation=0;
       if(nextPieces.isEmpty()){
         Collections.addAll(nextPieces,0,1,2,3);
         Collections.shuffle(nextPieces);
       }
       currentPiece=nextPieces.get(0);
       nextPieces.remove(0);
   
  }
   
   
   private boolean collidesAt(int x,int y,int rotation){
       for(Point p: myPoint[currentPiece][rotation]){
       if(rong[p.x+x][p.y+y]!=Color.black){
       return true;
       }
       }
   return false;
   }
      public void rotate(int i){ //pice gula ghurai
          int newRotation=(rotation+i)%4;
          if(newRotation<0){
              newRotation=3;
          }
          if(!collidesAt(pt.x, pt.y, newRotation)){
          rotation=newRotation;
          }
          repaint();
      }   
      
      public void move(int i){  //daane baame move korai
          if(!collidesAt(pt.x+i, pt.y, rotation)){
              pt.x+=i;
          
          }
          repaint();
      }
      public void dropDown(){ //niche move
          if(!collidesAt(pt.x, pt.y+1, rotation)){
              pt.y+=1;
              
          }
          else{
              fixToWell();
          }
          repaint();
      }
         
 public void fixToWell(){
     for(Point p: myPoint[currentPiece][rotation]){
         rong[pt.x+p.x][pt.y+p.y]=myColor[currentPiece];
     }
     clearRows();
     newPiece();
     
 }
 
 public void deleteRow(int row){
     for(int j=row-1;j>0;j--){
         for(int i=1;i<11;i++){
             rong[i][j+1]=rong[i][j];
         }
     }
     
 }
 public void clearRows(){
     boolean gap;
     int numClear=0;
     for(int j=21;j>0;j--){
         gap=false;
         for(int i=1;i<11;i++){
             if(rong[i][j]==Color.black){
                 gap=true;
                 break;
             }
         }
     
     if(!gap){
         deleteRow(j);
         j+=1;
         numClear+=1;
     
     }
     }
          
     switch(numClear){
         case 1:
             score+=100;
             
     if(score%300==0){
     gameLevel+=1;
     }
             break;
         case 2:
             score+=200;
   
     if(score%300==0){
     gameLevel+=1;
     }
             break;        
         case 3:
             score+=300;
             
     if(score%300==0){
     gameLevel+=1;
     }
             break;
         case 4:
              score+=800;
            
     if(score%300==0){
     gameLevel+=1;
     }
              break;
         default:
             break;
     }
     
 }


  
    private void drawPiece(Graphics g) { 
 g.setColor(myColor[currentPiece]);
 for (Point p : myPoint[currentPiece][rotation]) {
 g.fillRect((p.x + pt.x) * 26,
    (p.y + pt.y) * 26,
    25, 25);
 }
 }
        @Override
      public void paintComponent(Graphics art){
       art.fillRect(0,0,26*17,26*23); //background blck
       for(int i=0;i<12;i++){  //kalo daag banay
           for(int j=0;j<23;j++){
               art.setColor(rong[i][j]);
               art.fillRect(26*i, 26*j, 25, 25);
           }
       }
       
       art.setColor(Color.CYAN);
       art.drawString("Tetris Game", 27*12, 80);
       art.setColor(Color.BLUE);
       art.drawString("Java Lab", 27*12, 100);
       
       
       art.setColor(Color.RED);
       art.drawRect(320, 110, 110, 115);
       art.setColor(Color.yellow);
       art.drawString("Made by Jannat", 27*12, 140);
       art.setColor(Color.DARK_GRAY);
       art.drawString("=============", 27*12, 155);
       art.setColor(Color.GREEN);
       art.drawString("Apnar Score:"+score,27*12,170);
       art.setColor(Color.DARK_GRAY);
       art.drawString("=============", 27*12, 180);
       art.setColor(Color.orange);
       art.drawString("Level: "+gameLevel,27*12,200);

       art.setColor(Color.DARK_GRAY);
       art.drawRect(320, 250, 110, 135);
       art.setColor(Color.RED);
       art.drawString("Instructions:", 27*12, 270);
       art.drawString("=============", 27*12, 285);
       art.setColor(Color.WHITE);
       art.drawString("Press  <  for left", 27*12, 300);
       art.drawString("Press  >  for Right", 27*12, 320);
       art.drawString("Press  ^  for Rotate", 27*12, 340);
       art.drawString("Press down for fast", 27*12, 360);
       art.drawString("Down adds point!", 27*12, 380);
           
       drawPiece(art);
   }

}
