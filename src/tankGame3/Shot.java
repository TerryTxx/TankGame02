package tankGame3;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 */
public class Shot implements Runnable {
    int x;
    int y;
    int direct = 0;
    int speed = 2;//bullet speed
    boolean isLive = true; //to check the bullet is still running

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
    @Override
    public void run() {
        while(true){
            // tread of the shot to show on the screen
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct){
                case 0://up
                    y -= speed;
                    break;
                case 1://right
                    x += speed;
                    break;
                case 2://down
                    y += speed;
                    break;
                case 3://left
                    x -= speed;
                    break;
            }
            System.out.println("Bullet x = "+x+" y = "+y);
            // if bullet out of boundary thread should be closed
            if(!(x >= 0 && x <= 1000 && y >=0 && y <= 750 && isLive)){
                System.out.println("this bullet thread is closed");
                isLive = false;//the bullet is not running
                break;
            }

        }
    }
}
