package tankGame3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 * drawing area for tank games
 */
//to listen keyboard, implement Keylistener
//to let panel re-drawing the bullet, to make Pannel implents runnable, as a thread
public class myPanel extends JPanel implements KeyListener,Runnable {
    //define my tank
    Hero hero = null;
    //define enemy tanks in Vector
    // delete tanks from vector
    Vector<EnemyTank> enemyTanks = new Vector<>();

    // vector store nodes, recover enemy
    Vector<Node> nodes = new Vector<>();

    // define bombs in Vector
    // when bullet hits tanks, bomb shows
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    // three pic of bombs
    Image ig1 = null;
    Image ig2 = null;
    Image ig3 = null;

    public myPanel(String key) {
        // myPanel set to record
        nodes = Recorder.getNodesAndEnemyRec();
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(500,100);//initialize my tank
        hero.setSpeed(3);
        switch(key){
            case "1":
                //initialize enemy tank
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);

                    enemyTank.setEnemyTanks(enemyTanks);

                    enemyTank.setDirect(2);//tube to down

                    new Thread(enemyTank).start();
                    // add a bullet for enemyTank
                    Shot EnemyShot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    enemyTank.shots.add(EnemyShot);
                    // start enemt shot
                    new Thread(EnemyShot).start();
                    // add enemy
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());//tube to down
                    new Thread(enemyTank).start();
                    // add a bullet for enemyTank
                    Shot EnemyShot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    enemyTank.shots.add(EnemyShot);
                    // start enemt shot
                    new Thread(EnemyShot).start();
                    // add enemy
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("please choose again~");
        }

        // initialize three pics
        ig1 = Toolkit.getDefaultToolkit().getImage(myPanel.class.getResource("bomb1.png"));
        ig2 = Toolkit.getDefaultToolkit().getImage(myPanel.class.getResource("bomb2.png"));
        ig3 = Toolkit.getDefaultToolkit().getImage(myPanel.class.getResource("bomb3.png"));
        // background audio
        new PlayAudio("Tank.wav").start();

    }
    // adding defeated numbers
    public void showInfo(Graphics g){
        // client scores
        g.setColor(Color.BLACK);
        Font Scorefont = new Font("larva", Font.BOLD, 25);
        g.setFont(Scorefont);
        g.drawString("You have beats: ", 1020, 30);
       drawTank(1020,60,g,0,0);
       g.setColor(Color.BLACK);
       g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);

        Font tipsFont = new Font("larva", Font.PLAIN, 20); // Smaller and plain style
        g.setFont(tipsFont); // Set the new font
        g.drawString("Tips: ", 1020, 160);
        g.drawString("W - Move Up", 1020, 190);
        g.drawString("A - Move Left", 1020, 220);
        g.drawString("S - Move Down", 1020, 250);
        g.drawString("D - Move Right", 1020, 280);
        g.drawString("J - Fire", 1020, 310);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        g.fillRect(0,0,1000,750);//fill the Rect
        if (hero != null && hero.isLive) {
            // drawTanks and encapsulate it
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }
        // draw hero's bullet
//        if(hero.shot != null && hero.shot.isLive == true){//bullet is not null and still using
//            System.out.println("bullet is re-drawing");
//            g.draw3DRect(hero.shot.x, hero.shot.y, 3,3,false);
//        }
        // loop and get hero's all bullet collection
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
           if(shot != null && shot.isLive){//bullet is not null and still using
                g.draw3DRect(shot.x, shot.y, 3,3,false);
            }else {
               // remove from vendor
               hero.shots.remove(shot);
           }
        }

        // if vector has bombs, show it
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            //according to bomb object's life value
            if (bomb.life > 6) {
                g.drawImage(ig3, bomb.x, bomb.y, 60, 60, this);
            }else if(bomb.life > 3){
                g.drawImage(ig2, bomb.x, bomb.y, 60, 60, this);
            }else {
                g.drawImage(ig1, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();//to life down and show the bomb viersion
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }

        // draw all enemytanks by loop the vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //get current enemies
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                // draw all bullets
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //get each and draw
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 3, 3, false);
                    } else {
                        //remove from vector
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }
    //method to get the tank
    /**
     * @param x left top x-axis
     * @param y left top y-axis
     * @param g painting pen
     * @param direct tank moving direction
     * @param type of tank
     */
    public void drawTank(int x, int y, Graphics g,int direct, int type) {
        switch (type){
            //set the colour according the tank type
            case 0://our hero tank
                g.setColor(Color.cyan);
                break;
            case 1://enemy tank
                g.setColor(Color.yellow);
                break;
        }
        //draw the tank by the direction
        switch (direct){
            case 0://upper towards
                g.fill3DRect(x, y, 10, 60,false);//left wheel
                g.fill3DRect(x + 30, y, 10,60,false);//right wheel
                g.fill3DRect(x + 10, y + 10, 20,40,false);//rect cover
                g.fillOval(x + 10,y + 20,20,20);//oval cover
                g.drawLine(x+20,y+30,x+20,y);//tube
                break;
            case 1://right towards
                g.fill3DRect(x, y, 60, 10,false);//upper wheel
                g.fill3DRect(x, y + 30, 60,10,false);//lower wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//rect cover
                g.fillOval(x + 20,y + 10,20,20);//oval cover
                g.drawLine(x+30,y+20,x+60,y+20);//tube
                break;
            case 2://down towards
                g.fill3DRect(x, y, 10, 60,false);//left wheel
                g.fill3DRect(x + 30, y, 10,60,false);//right wheel
                g.fill3DRect(x + 10, y + 10, 20,40,false);
                g.fillOval(x + 10,y + 20,20,20);//oval cover
                g.drawLine(x+20,y+30,x+20,y+60);//tube
                break;
            case 3://left towards
                g.fill3DRect(x, y, 60, 10,false);//left wheel
                g.fill3DRect(x , y+ 30, 60, 10,false);//right wheel
                g.fill3DRect(x + 10, y + 10, 40,20,false);
                g.fillOval(x + 20,y + 10,20,20);//oval cover
                g.drawLine(x+30,y+20,x,y+20);//tube
                break;
            default:
                System.out.println("no changes at moment");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //wasd for direction
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            //W key to change direction to upper
            hero.setDirect(0);
                //S2:change x,y to moving by moveXX()
            if(hero.getY() >0 ) {
                hero.moveUp();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            //W key to change direction to right
            hero.setDirect(1);
            if((hero.getX() + 60) < 1000) {
                hero.moveRight();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            //W key to change direction to down
            hero.setDirect(2);
            if((hero.getY() + 60) < 750) {
                hero.moveDown();
            }
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            //W key to change direction to left
            hero.setDirect(3);
            if(hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        // user press "j" to shot
        if(e.getKeyCode()==KeyEvent.VK_J){
//if(hero.shot == null || !hero.shot.isLive) {
//  hero.shotEnemyTank();
// }
            hero.shotEnemyTank();
        }
        //repaint the panel
        this.repaint();
    }

    // function to check if hero's bullet hitted enemytank
    // adding in run function
    // put vendor of bullet into hittanks
//    public void HitEnemyTank(){
//        for(int j = 0; j < hero.shots.size(); j++)
//        {
//            Shot shot = hero.shots.get(j);
//        }
//    }
    public void HitTanks(Shot s, Tank tank){
        // check hitted or not
        switch (tank.getDirect()){
            case 0:
            case 2:
                if( s.x > tank.getX() && s.x< (tank.getX()+40)
                 && s.y > tank.getY() && s.y < (tank.getY()+60)){
                        s.isLive = false;
                    tank.isLive = false;
                    // when bullet hitted the tank,remove the enemytank from the Vecotr
                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank){
                        Recorder.addallEnemyTankNum();
                    }
                    // add bombs object
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if( s.x > tank.getX() && s.x< (tank.getX()+60)
                        && s.y > tank.getY() && s.y < (tank.getY()+40)){
                    s.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank){
                        Recorder.addallEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void hitEnemyTank(){
        if(hero.shot != null && hero.shot.isLive){// heros bullet hasnot hitted
            // loop all enemyTanks
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                HitTanks(hero.shot, enemyTank);
            }
        }
    }
    
    public void hitHero(){
        // loop all enemy tanks
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //check shot hit the hero
                if (hero.isLive && shot.isLive){
                    HitTanks(shot,hero);
                }
            }
        }
    }

    @Override
    public void run(){// 100ms to repaint to show the bullet moving
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // check bullet hitted tanks
//            if(hero.shot != null && hero.shot.isLive){// heros bullet hasnot hitted
//                // loop all enemyTanks
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    HitTanks(hero.shot, enemyTank);
//                }
//            }
            hitEnemyTank();
            //check hero is hit by enemy
            hitHero();
            this.repaint();
        }
    }
}
