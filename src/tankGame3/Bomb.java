package tankGame3;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 */
public class Bomb {
    int x,y;
    int life = 9 ;// lifecycle of bomb
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // life is descinding
    public void lifeDown(){
        if(life > 0){
            life --;
        }else{
            isLive = false;
        }
    }
}
