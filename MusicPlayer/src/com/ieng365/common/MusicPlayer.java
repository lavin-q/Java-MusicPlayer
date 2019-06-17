package com.ieng365.common;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MusicPlayer extends JFrame{
    //显示(歌曲名称)播放状态的标签
    JLabel songNameLabel = null;
   
    //四个播放功能键按钮
    JButton btnLast = null; //上一曲
    JButton btnPlay = null; //播放/停止
    JButton btnNext = null; //下一曲
    JButton btnLoop = null; //循环
   
    //歌曲列表
    JList songsList = null;
    AudioClip songs[] = null;
    AudioClip currentSong = null;
    int index=0; //当前歌曲在JList中的位置(序号)
    //歌曲文件名数组---String
    String[] strSongNames={ "song1.wav","song2.wav","song3.wav","song4.wav","song5.wav","song6.wav" };
    final String DIR="songs\\";
   
    //播放音乐的线程
    Thread playerThread=null;
    boolean isPlayOrStop = true;//true代表播放状态
    boolean isLoop = false; //是否为循环状态
   
    public MusicPlayer() {
      super("音乐播放器");
      setBounds(300, 50, 310, 500);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(null);
      //hello();
   
      //显示(歌曲名称)播放状态的标签
      songNameLabel = new JLabel();
      Font songNameFont = new Font("黑体",Font.ITALIC,18);
      songNameLabel.setFont(songNameFont);
      songNameLabel.setText("我的音乐播放器");
      songNameLabel.setBounds(10, 10, 300, 40);
      getContentPane().add(songNameLabel);
   
      //四个播放功能键按钮
      btnLast = new JButton();
      btnPlay = new JButton();
      btnNext = new JButton();
      btnLoop = new JButton();
      //位置大小
      btnLast.setBounds(10, 70, 50, 40);
      btnPlay.setBounds(70, 70, 50, 40);
      btnNext.setBounds(130, 70, 50, 40);
      btnLoop.setBounds(190, 70, 50, 40);
      //设置图片
      btnLast.setText("L");//上一曲 
      btnPlay.setText("S/P") ; //播放/停止
      btnNext.setText("N"); //下一曲
      btnLoop.setText("Lo"); //循环
      //添加到框架
      getContentPane().add(btnLast);
      getContentPane().add(btnPlay);
      getContentPane().add(btnNext);
      getContentPane().add(btnLoop);
      //添加监听
      MyMouseListener mml = new MyMouseListener();
      btnLast.addMouseListener((MouseListener) mml);
      btnPlay.addMouseListener((MouseListener) mml);
      btnNext.addMouseListener((MouseListener) mml);
      btnLoop.addMouseListener((MouseListener) mml);
   
   
      //歌曲列表的标题
      JLabel listLabel = new JLabel("播放列表");
      listLabel.setBounds(10, 120, 100, 30);
      Font listLabelFont = new Font("黑体",Font.BOLD,16);
      listLabel.setFont(listLabelFont);
      getContentPane().add(listLabel);
   
      //歌曲列表
      /*
      songsList = new JList();
      songsList.setBounds(10, 150, 250, 300);
      songsList.setBackground(Color.CYAN);
      //把所有歌曲名逐个添加到List中
      //songsList.setListData(strSongNames);
      for(int i=0;i<strSongNames.length;i++){
        DefaultListModel dm = (DefaultListModel)songsList.getModel();
        dm.add(i,strSongNames[i]);
      }
      getContentPane().add(songsList);
      */
   
      DefaultListModel lm = new DefaultListModel();
      songsList = new JList(lm);
      songsList.setBounds(10, 150, 250, 300);
      songsList.setBackground(Color.CYAN);
      //把所有歌曲名逐个添加到List中
      //songsList.setListData(strSongNames);
      songs = new AudioClip[strSongNames.length];
      for(int i=0;i<strSongNames.length;i++){
        lm.add(i,strSongNames[i]);
        songs[i] = loadSound(strSongNames[i]);
      }
      getContentPane().add(songsList);
      //lm.remove(3);
   
      //对JList控件的监听技术实现
      songsList.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            // TODO Auto-generated method stub
            currentSong.stop();
            index = songsList.getSelectedIndex();
            isPlayOrStop = true;
            playerThread = new Thread( new MusicRun() );
            playerThread.start();
        }
      });
   
   
      //单开一个线程，专用于播放音乐
      playerThread = new Thread( new MusicRun() );
      playerThread.start();
      setVisible(true);
    }
   
   
   
    private AudioClip loadSound(String fileName) {
      try {
        URL url = new URL("file:songs\\"+fileName);
        AudioClip au = Applet.newAudioClip(url);
        return au;
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }
   
   
    //讲解音乐播放的基本技术
    private void hello() {
      try {
        File f = new File("songs\\song1.wav");
        URL url = f.toURI().toURL();
        //URL url = new URL("file:songs\\song1.wav");
        AudioClip au = Applet.newAudioClip(url);
        au.play();
        //au.loop();
        //au.stop();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
   
    private class MyMouseListener extends MouseAdapter{
      public void mouseClicked(MouseEvent e) {
        JButton btn = (JButton) e.getSource();
        currentSong.stop();
        if(btn==btnPlay){
          isPlayOrStop = !isPlayOrStop;
        }else if(btn==btnLast){
          index--;
          if(index<0){
            index = strSongNames.length-1;
          }
          //isPlayOrStop=true;
        }else if(btn==btnNext){
          index++;
          index = index%strSongNames.length;
   
        }else if(btn==btnLoop){
          isLoop = !isLoop;
        }
   
        if(isPlayOrStop){//播放
          playerThread = new Thread( new MusicRun() );
          playerThread.start();
        }else{//停止
          songsList.setSelectedIndex(index);
          songNameLabel.setText("停止播放:"+strSongNames[index]);
          btnPlay.setIcon( new ImageIcon("images2/2.png"));
        }
      }
    }
   
    private class MusicRun implements Runnable{
      public void run() {
        currentSong = songs[index];
        if(isLoop){
          currentSong.loop();
          songNameLabel.setText("循环播放:"+strSongNames[index]);
        }
        if (isPlayOrStop) {
          currentSong.play();
        }
        //在播放列表中选定当前歌曲
        songsList.setSelectedIndex(index);
        //把播放按钮的图标切换成“停止”
        btnPlay.setIcon( new ImageIcon("images2/5.png"));
   
        if(!isLoop){
          songNameLabel.setText("正在播放:"+strSongNames[index]);
        }
      }
    }
   
   
    public static void main(String[] args) {
      new MusicPlayer();
    }
   
  }