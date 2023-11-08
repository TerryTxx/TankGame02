package tankGame3;

import java.util.Vector;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 * Enemy Tank
 * 1. Prevent local tanks from overlapping
 * 2. Record the player's total score, save and exit (IO)
 * 3. Record the coordinates of the current tank(x,y,direct), save and exit (IO)
 * 4. When playing a game, you can choose between a new game or the previous game
 */
public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();

    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }
    // add myPanel members set into
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
    // 8 situations
    public boolean isTouchTank(){
        switch (this.getDirect()){
            case 0://up
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //get tanks from vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //except itself
                    if(enemyTank != this){
                        //up or down
                        //  enemy x range[x,x+40]   ,y range [y,y+60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() ==2) {
                        // current tank leftpoint[this.x, this.y]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+60) {
                                return true;
                            }
                         //current tank rightpoint[this.x+40, this.y]
                            if (this.getX() +40>= enemyTank.getX()
                                    && this.getX()+40 <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+60) {
                                return true;
                            }
                        }
                        // left or right
                        //  enemy x range[x,x+60]   ,y range [y,y+40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() ==3) {
                            //current tank leftpoint[this.x, this.y]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+40) {
                                return true;
                            }
                            //current tank rightpoint[this.x+40, this.y]
                            if (this.getX()+40  >= enemyTank.getX()
                                    && this.getX()+40 <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+40) {
                                return true;
                            }
                        }

                    }

                }
                break;
            case 1://right
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //get tanks from vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //except itself
                    if(enemyTank != this){
                        //up or down
                        //  enemy x range[x,x+40]   ,y range [y,y+60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() ==2) {
                            // current tank rightpoint[this.x+60, this.y]
                            if (this.getX()+60 >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+60) {
                                return true;
                            }
                            //current tank btmpoint[this.x+60, this.y+40]
                            if (this.getX() +60>= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX()+40
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY()+60) {
                                return true;
                            }
                        }
                        // left or right
                        //  enemy x range[x,x+60]   ,y range [y,y+40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() ==3) {
                            //current tank righttoppoint[this.x+60, this.y]
                            if (this.getX()+60 >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+40) {
                                return true;
                            }
                            //current tank rightbtmpoint[this.x+60, this.y+40]
                            if (this.getX()+60  >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX()+60
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY()+40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://down
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //get tanks from vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //except itself
                    if(enemyTank != this){
                        //up or down
                        //  enemy x range[x,x+40]   ,y range [y,y+60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() ==2) {
                            // current tank leftbtmpoint[this.x, this.y+60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+40
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY()+60) {
                                return true;
                            }
                            //current tank rightbtmpoint[this.x+40, this.y+60]
                            if (this.getX() +40>= enemyTank.getX()
                                    && this.getX()+40 <= enemyTank.getX()+40
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY()+60) {
                                return true;
                            }
                        }
                        // left or right
                        //  enemy x range[x,x+60]   ,y range [y,y+40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() ==3) {
                            //current tank leftpoint[this.x, this.y+60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+60
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY()+40) {
                                return true;
                            }
                            //current tank rightpoint[this.x+40, this.y+60]
                            if (this.getX()+40  >= enemyTank.getX()
                                    && this.getX()+40 <= enemyTank.getX()+60
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY()+40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://left
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //get tanks from vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //except itself
                    if(enemyTank != this){
                        //up or down
                        //  enemy x range[x,x+40]   ,y range [y,y+60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() ==2) {
                            // current tank leftpoint[this.x, this.y]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+60) {
                                return true;
                            }
                            //current tank leftpoint[this.x, this.y+40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+40
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY()+60) {
                                return true;
                            }
                        }
                        // left or right
                        //  enemy x range[x,x+60]   ,y range [y,y+40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() ==3) {
                            //current tank leftpoint[this.x, this.y]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+40) {
                                return true;
                            }
                            //current tank leftpoint[this.x, this.y+40]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX()+60
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY()+40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true){
            if ( isLive && shots.size() < 2){//no bullets now, add
                Shot s = null;
                // check direct and add
                switch(getDirect()){
                    case 0:
                        s = new Shot(getX()+20, getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX()+60, getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX()+20, getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY()+20,3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            switch (getDirect()) {
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if(getY() >0 && !isTouchTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if((getX() + 60) < 1000 && !isTouchTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if((getY()+60) < 750 && !isTouchTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchTank() ) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            setDirect((int)(Math.random()*4));
            // multi-thread check when the thread will stop
            if(!isLive){
                break;
            }
        }
    }
}
