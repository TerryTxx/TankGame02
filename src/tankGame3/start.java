package tankGame3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * @version 3.0
 * @Authort Xiaoxu Tan
 */

public class start extends JFrame {
    //define mypanel
    myPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        start start = new start();
    }

    public start() {

        System.out.println("Please Select--1.New Game; 2.Last Round");
        String key = scanner.next();
        mp = new myPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//mypanel is the drawing area

        this.setSize(1340,850);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //listen of close window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.out.println("game closed");
                System.exit(0);
            }
        });
    }
}
