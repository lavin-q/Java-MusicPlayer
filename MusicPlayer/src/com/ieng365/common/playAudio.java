package com.ieng365.common;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class playAudio extends JFrame implements ActionListener {
	static String filePath =null;//播放文件路径	
 
 
	JButton btPause, btExit, btPlay, btLoop, btStop;
	JPanel panel;
	JMenuBar mb;
	JMenu menu;
	JMenuItem menuMi1,menuMi2;
	JTextArea textarea;
	
	public static void main(String[] args) {
		playAudio pa = new playAudio();
	}
 
	// 构造方法
	public playAudio() {
		
		
		// 定义按钮
 
 
		btPause = new JButton("Pause");
		btPause.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));
		btExit = new JButton("EXIT");
		btExit.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));
		btPlay = new JButton("PLAY");
		btPlay.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));
		btLoop = new JButton("LOOP");
		btLoop.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));
		btStop = new JButton("STOP");
		btStop.setFont(new Font("Chaparral Pro Light",Font.BOLD,15));
		
		//定义文本域
		textarea=new JTextArea("Import music fileDialog...");
		textarea.setEditable(false);
		textarea.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,20));
		//定义菜单栏
 
 
		mb=new JMenuBar();
		menu=new JMenu("File<F>");
		menu.setMnemonic('F');
		menu.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,15));
		menuMi1=new JMenuItem("Open<O>");
		menuMi1.setMnemonic('O');
		menuMi1.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,15));
		menuMi2=new JMenuItem("Exit<E>");
		menuMi2.setMnemonic('E');
		menuMi2.setFont(new Font("Chaparral Pro Light",Font.HANGING_BASELINE,15));
		
		//定义面板
		panel = new JPanel();
 
		// 添加面板组件
 
 
		
		panel.add(btPlay);
		panel.add(btLoop);
		panel.add(btPause);
		panel.add(btStop);
		panel.add(btExit);
		
		//添加菜单组件
		mb.add(menu);
		menu.add(menuMi1);
		menu.add(menuMi2);
 
		// 添加事件监听
 
 
		btPause.addActionListener(this);
		btPause.setActionCommand("pause");
		btExit.addActionListener(this);
		btExit.setActionCommand("exit");
		btPlay.addActionListener(this);
		btPlay.setActionCommand("play");
		btLoop.addActionListener(this);
		btLoop.setActionCommand("loop");
		btStop.addActionListener(this);
		btStop.setActionCommand("stop");
		menuMi1.addActionListener(this);
		menuMi1.setActionCommand("open");
		menuMi2.addActionListener(this);
		menuMi2.setActionCommand("exit");
 
		// 流式布局
		//this.setLayout(new GridLayout(2, 1,5,0));
 
		this.setJMenuBar(mb);
		this.add(textarea);
		this.add(panel,BorderLayout.SOUTH);
 
		// 显示窗口
 
 
		this.setIconImage(new ImageIcon("image/music.JPG").getImage());
		this.setTitle("MusicPlayer");// 设置用户界面
		this.setSize(400, 150);// 设置窗口大小，数字代表像素
 
 
		this.setLocation(300, 260);// 设置窗体位置
		this.setResizable(false);// 设置窗体大小是否可调整
 
 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭进程
		this.setVisible(true);// true 显示，false不显示
 
 
	}
 
	// 事件监听
	public void actionPerformed(ActionEvent e) {
		String text="";//文件路径载体
 
 
		Audio player = new Audio(filePath);//初始化播放器
		
        // 打开
 
 
		if (e.getActionCommand().equals("open")) {
			// 打开选择文件选择歌曲
			FileDialog fd = new FileDialog(this,"Chooes music", FileDialog.LOAD); 
	        fd.setVisible(true); // 显示选择框
 
 
	        filePath = fd.getDirectory() +fd.getFile(); // 文件名称=获取此文件对话框的目录+文件名
	       
	        if(filePath!=null)
				System.out.println(text="filePath is: "+filePath);
	        else
	        	System.out.println(text="Couldn't open file");
		}
		// 退出
 
 
		else if (e.getActionCommand().equals("exit")) {
			player.interrupt();
			System.out.println(text="Exit program!");
			System.exit(0);
		}
		// 播放
		else if (e.getActionCommand().equals("play")) {
			if(filePath!=null){
				player.start();
				System.out.println(text="Play music: "+filePath);
			}else
				System.out.println(text="There is not any file!");
			
		}
		// 循环
 
 
		else if (e.getActionCommand().equals("loop")) {
			if(filePath!=null){
				while(!player.isAlive()){
					try{
						player.start();
						System.out.println(text="Loop playing"+filePath);
					}catch(Exception e1){
						System.err.println(text="Loop Error");
					}
				}
			}else
				System.out.println(text="There is not any file!");	
		}
		// 停止
		else if (e.getActionCommand().equals("stop")) {
			player.interrupt();
			System.out.println(text="Stop playing!");
		}
		//暂停
 
 
		else if(e.getActionCommand().equals("pause")){
			player.interrupt();
			System.out.println(text="Pause playing!");
		}
		this.textarea.setText(text);
	}
}
 
/*****************************
 * Audio类实现声音流播放 1.定义音频文件的变量，
 * 变量需要： 一个用于存储音频文件对象名字的String对象 filename；
 * 2.构造函数，初始化filename 
 * 3.线程运行函数重写
 ****************************/
class Audio extends Thread {
	// 1.定义音频文件的变量，变量需要：一个用于存储音频文件对象名字的String对象 filename
 
 
	private String filename;
 
	// 2.构造函数，初始化filename
	public Audio(String filename) {
		this.filename = filename;
	}
 
	// 3.线程运行函数重写
 
 
	public void run() {
		// 1.定义一个文件对象引用，指向名为filename那个文件
		File sourceFile = new File(filename);
 
		// 定义一个AudioInputStream用于接收输入的音频数据
 
 
		AudioInputStream audioInputStream = null;
 
		// 使用AudioSystem来获取音频的音频输入流
		try {
			audioInputStream = AudioSystem.getAudioInputStream(sourceFile);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		// 4,用AudioFormat来获取AudioInputStream的格式
 
 
		AudioFormat format = audioInputStream.getFormat();
 
		// 5.源数据行SoureDataLine是可以写入数据的数据行
		SourceDataLine auline = null;
 
		// 获取受数据行支持的音频格式DataLine.info
 
 
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
		// 获得与指定info类型相匹配的行
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
 
			// 打开具有指定格式的行，这样可使行获得所有所需系统资源并变得可操作
 
 
			auline.open();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
 
		// 允许某一个数据行执行数据i/o
		auline.start();
 
		// 写出数据
 
 
		int nBytesRead = 0;
		
		byte[] abData = new byte[1024];
 
		// 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
 
				// 通过此源数据行将数据写入混频器
 
 
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
 
		} finally {
			auline.drain();
			auline.close();
		}
	}
}
