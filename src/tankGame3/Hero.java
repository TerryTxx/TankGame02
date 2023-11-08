package tankGame3;

import java.util.Vector;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 * hero of my tank
 */
public class Hero extends Tank{
    // define a shot class
    Shot shot = null;//we tank can shot
    // shoot by multiply bullets
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    // define shot by press J
    public void shotEnemyTank(){

        if (shots.size() ==5){
            return;
        }

        // creat shot obj by Hero direct and X,Y
        switch (getDirect()){//Hero Object direct
            case 0:
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX() + 60,getY() +20,1);
                break;
            case 2:
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY() + 20,3);
                break;
        }
        //start shot thread
        // then add responce in panel to J press of shoting
        shots.add(shot);
        new Thread(shot).start();
    }

}
