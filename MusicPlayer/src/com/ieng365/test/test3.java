package com.ieng365.test;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import javax.media.*;

public class test3 {

    public static void main(String[] args) {
        frame myframe = new frame();
        myframe.setSize(500, 500);
        myframe.setVisible(true);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.setLocation(500, 500);

    }

}

class frame extends JFrame implements ControllerListener {

    Player myPlayer = null;
    URL url = null;
    File file = new File("F:\\FFOutput\\薛之谦 - 怪咖.mp3");
    Component vc, cc;
    Panel mypanel = new Panel();

    public frame() {
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            myPlayer = Manager.createPlayer(url);
        } catch (NoPlayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.add(mypanel);
        myPlayer.addControllerListener(this);
        myPlayer.prefetch();

    }

    public void controllerUpdate(ControllerEvent e) {
        // 如果是Player达到prefect状态，则开始
        if (e instanceof PrefetchCompleteEvent) {
            myPlayer.start();
            return;
        }
        // 如果是已经完成实例，则加入控制面板，视频面板
        if (e instanceof RealizeCompleteEvent) {
            vc = myPlayer.getVisualComponent();
            if (vc != null)
                add(vc);
            cc = myPlayer.getControlPanelComponent();
            if (cc != null)
                add(cc, BorderLayout.SOUTH);
            pack();
        }
    }

}