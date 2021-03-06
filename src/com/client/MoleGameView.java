package com.client;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by sist on 2016-02-11.
 */
public class MoleGameView extends JPanel implements Runnable, MouseMotionListener{

    NotiBar notiBar=new NotiBar();
    Rectangle[] rectangles=new Rectangle[9];
    Image[] molesImage=new Image[5];
    Image[] combosImage=new Image[11];
    Image moleImage, groundImage,hammerImage,comboImage;

    String[] stringsImage={
        ".\\img\\mole1.png",".\\img\\mole2.png",
        ".\\img\\mole3.png",".\\img\\mole4.png",
        ""
    };

    String[] stringsCombo={
        ".\\img\\0.png",".\\img\\1.png",
        ".\\img\\2.png",".\\img\\3.png",
        ".\\img\\4.png",".\\img\\5.png",
        ".\\img\\6.png",".\\img\\7.png",
        ".\\img\\8.png",".\\img\\9.png",
        ".\\img\\10.png",
    };

    int c_combo=0;      //보통 게임에서 콤보는 연속성을 지니는 기술이나 행위를 말한다.
                         //Normal Mole, Luck Mole을 miss없이 가격하면 콤보가 증가합니다
                         //두더지를 놓치지 않고 맞추면 콤보가 올라 갑니다.
    int m_combo=0;      //콤보 조건 확인시 필요.

    int left,top,width,height;
    int stringsNumber2=0;
    int count=0;
    int timerVar=600;

    JLabel jLabel;
    int hammerX,hammerY;

    //게임 시간 체크용도
    Timer timer=new Timer(10,null);

    //MoleGameView 스레드를 실행(run())하기 위한 용도로 스레드 추가
    Thread thread;

    public MoleGameView(){
        this.addMouseMotionListener(this);

        groundImage=Toolkit.getDefaultToolkit().getImage(".\\img\\ground.png");
        hammerImage=Toolkit.getDefaultToolkit().getImage(".\\img\\starhammericon.png");

        for(int i=0;i<8;i++){
            rectangles[i]=new Rectangle();
        }

        //두더지 위치 설정.
        rectangles[0].setRect(76,76,156,156);
        rectangles[1].setRect(199,119,179,199);
        rectangles[2].setRect(336,100,416,180);
        rectangles[3].setRect(64,178,144,258);
        rectangles[4].setRect(219,203,299,283);
        rectangles[5].setRect(368,163,448,243);
        rectangles[6].setRect(136,279,216,359);
        rectangles[7].setRect(352,250,432,330);

        jLabel=new JLabel();
        jLabel.setBounds(20,20,100,50);
        jLabel.setText(0+"");
        this.add(jLabel);

        for(int i=0;i<5;i++){
            molesImage[i]=Toolkit.getDefaultToolkit().getImage(stringsImage[i]);
        }

        for(int j=0;j<11;j++){
            combosImage[j]=Toolkit.getDefaultToolkit().getImage(stringsCombo[j]);
        }


    }

    @Override
    public void paint(Graphics g) {
        notiBar.jProgressBar.setValue(timerVar);
        notiBar.jProgressBar.setString(timerVar+" ");
        notiBar.jProgressBar.setStringPainted(false);
        g.drawImage(groundImage,0,0,560,370,this);
        g.drawImage(moleImage,left,top,width,height,this);
        g.drawImage(hammerImage,hammerX-35,hammerY-35,70,70,this);
        g.drawImage(comboImage,30,10,60,60,this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(3,3,BufferedImage.TYPE_INT_ARGB),new Point(0,0),"null"));
        hammerX=e.getX();
        hammerY=e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.setCursor(this.getToolkit().createCustomCursor(
                new BufferedImage(3,3,BufferedImage.TYPE_INT_ARGB),new Point(0,0),"null"));
        hammerX=e.getX();
        hammerY=e.getY();
        repaint();
    }

    //두더지 나왔다 들어갔다하는 부분 스레드로 구현.
    @Override
    public void run() {
        try{
            while(true){
                setImage();             //콤보 이미지, 큰 망치 이미지 출력
                if(timerVar>2000)
                    Thread.sleep(1100);
                else if(timerVar>800)
                    Thread.sleep(1000);
                else
                    Thread.sleep(900);

                if(c_combo>0){
                    if(m_combo !=c_combo){
                        m_combo=0;
                        c_combo=0;
                    }
                }

                if(c_combo==3 && timerVar>1000){
                    c_combo=0;
                    moleImage=molesImage[4];
                    repaint();

                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setImage(){
        //랜덤 두더지 이미지
        int no=(int)(Math.random()*3);
        moleImage=molesImage[no];

        if(moleImage==molesImage[0] || moleImage==molesImage[1]){
            c_combo++;
        }
        //랜덤 이미지 위치
        int i=(int)(Math.random()*8);
        left=rectangles[i].getLeft();
        top=rectangles[i].getTop();
        width=rectangles[i].getWidth();
        height=rectangles[i].getHeight();

        switch(m_combo){
            case 0:{
                comboImage=combosImage[0];          //콤보숫자가 나타내기 위해 초기화.
                break;
            }
            case 1:{
                comboImage=combosImage[1];
                break;
            }
            case 2:{
                comboImage=combosImage[2];
                break;
            }
            case 3:{
                comboImage=combosImage[3];
                break;
            }
            case 4:{
                comboImage=combosImage[4];
                break;
            }
            case 5:{
                comboImage=combosImage[5];
                break;
            }
            case 6:{
                comboImage=combosImage[6];
                break;
            }
            case 7:{
                comboImage=combosImage[7];
                break;
            }
            case 8:{
                comboImage=combosImage[8];
                break;
            }
            case 9:{
                comboImage=combosImage[9];
                break;
            }
            case 10:{
                comboImage=combosImage[10];
                break;
            }
        }
        repaint();
    }


}







































