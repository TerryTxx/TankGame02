package tankGame3;

import java.io.*;
import java.util.Vector;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 * records files, IO records
 */
public class Recorder {
    // parameter record beated enemy
    private static int allEnemyTankNum = 0;
    // IO obj
//    private static FileWriter fw = null;
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    private static String recordFile = "src\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //read Recordfile recover all records
    public static Vector<Node> getNodesAndEnemyRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //read in loop
            String line = "";
            while((line = br.readLine())!=null){
                String[] s = line.split(" ");
                Node node = new Node(Integer.parseInt(s[0]),
                                     Integer.parseInt(s[1]),
                                     Integer.parseInt(s[2]));
                nodes.add(node);//into vector
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return nodes;
    }

    // add method, when game out, we put allEnemyTankNuM
    // add x,y, direct
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            //loop the vector, if have then add
            //OOP. and a member, setXX, get enemy vector from myOPanel
            for (int i = 0; i < enemyTanks.size(); i++) {
               //get oneby one
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive){
                    String record = enemyTank.getX()+" " +
                                    enemyTank.getY()+" "+
                                    enemyTank.getDirect();
                    bw.write(record+"\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bw!= null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    // when beats an enemy, allEnemynum ++
    public static void addallEnemyTankNum(){
        Recorder.allEnemyTankNum ++;
    }

}
