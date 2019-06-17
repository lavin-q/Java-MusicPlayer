/*import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.ControllerListener;
import javax.media.Time;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import javazoom.jl.decoder.Manager;



public class Player implements ActionListener, ControllerListener,Runnable{
 
 
 JFrame j=new JFrame("音乐播放器");
 JLabel TablePlaer=new JLabel("播放列表");
 JButton BAdd=new JButton("添加歌曲");
 JButton BDelect=new JButton("删除歌曲");
 JButton BDelectTable=new JButton("清空列表");
 
 JButton BMoveNext=new JButton("下一曲");
 JButton BMovePrevious=new JButton("上一曲");
 JButton BPlayer=new JButton("暂停");
 JButton BStop=new JButton("停止");
 JButton BSet=new JButton("显示歌词");
 JButton BEnd=new JButton("停止");
 String[] s={"顺序播放","单曲循环","随机播放"};        //下拉列表选项数组
 JComboBox select=new JComboBox(s);          //创建下拉选项
 JPanel p1=new JPanel();           //播放列表区域
 JPanel p=new JPanel(); 
 JPanel p2=new JPanel();           //按钮区域
 JPanel p3=new JPanel(); 
 JLabel l=new JLabel(); 
 JPanel p5=new JPanel(); //放置播放列表
 JPanel p6=new JPanel(); //放置播放歌曲的名称
 
 static JPanel pp=new JPanel();
 static JLabel lb;
 public static JTextArea jt=new JTextArea();
 
 
 static int index;  //播放列表的下标
 int count;
 int flag;   //标记是随机播放还是顺序播放
 int countSecond; //获取音乐的总时间值
 static int newtime = 0;
 int ischanging = 0; //当鼠标是对游标进行点击，进度值也会改变
 int ispressing = 0; //判断鼠标是否对游标进行点击
 File MusicName = null;
 static java.util.List<File> MusicNames = null;  //运用泛型，创建File对象
 File currentDirectory = null;
 List list;// 文件列表
 FileDialog open; // 定义文件对话框对象
 
 Random rand = new Random();
 
 String filename;
 
 
 //进度条
 JButton timeInformation = new JButton();
 JSlider timeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0); //(SwingConstants.HORIZONTAL)用于定向进度条为水平方向的常量的集合
                     //( 0, 100, 0)用指定的最小值、最大值和初始值创建一个水平滑块。
 
 
 // 播放
 Player player = null; 
 MusicFileChooser fileChooser = new MusicFileChooser();
 
 
 static JTextPane tp=new JTextPane();  //显示歌词区域
 static JTextArea are=new JTextArea(); //显示图片区域
 
 public Player(){
  j.setSize(1200, 700);
  j.setLayout(null);
  j.getContentPane().setBackground(Color.BLACK);
  j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  p.setBounds(2, 563, 1180, 95);
  p.setLayout(new BorderLayout());
 
  p1.setBounds(2, 3, 298, 30);   
  p1.setBackground(new Color(255,255,255));
 
  p2.setLayout(new GridLayout(2,3,20,20));
  p2.setBackground(Color.LIGHT_GRAY);
 
  p3.setLayout(new GridLayout(2,0,200,10));
  p3.setBackground(new Color(255,255,255));
 
  p5.setBounds(2, 35, 298, 526);
  p5.setLayout(null);
  p5.setBackground(new Color(255,255,255));
 
  p6.setBounds(301, 3,880, 30);
  p6.setLayout(null);
  p6.setBackground(new Color(255,255,255));
 
 
 
  l.setBounds(250, 4, 600, 30);  //设置显示播放的歌曲
  p6.add(l);
 
  实现图片插入
   * 
  ImageIcon ic=new ImageIcon("image\\2.3.jpg");
  ic=new ImageIcon(ic.getImage().getScaledInstance(880, 477, 2)); //获取图片以及设置图片大小
 
  lb=new JLabel(ic);
  lb.setOpaque(false);   
  pp.setOpaque(false);  //设置为透明
 
  pp.setBounds(241, 80,990, 500);
 
 
  are.setBounds(241, 56,990, 520);
  are.setOpaque(false);
 
  tp.setBackground(new Color(255,255,255));
  tp.setBounds(301, 35,880, 49);
 
 
  pp.add(are);
  pp.add(lb);
 
  // 文件列表
  list = new List(10);
  ((Window) list).setBounds(100, 55, 187, 495); //列表区域
  ((AbstractButton) list).addActionListener(this);
  j.add((Component) list);
  j.add(jt);
  j.add(tp);
 
  BAdd.setBounds(5,20, 90,30);
  BAdd.setBackground(new Color(255,255,255));
  BDelect.setBounds(5, 80, 90, 30);
  BDelect.setBackground(new Color(255,255,255));
  BDelectTable.setBounds(5, 140, 90, 30);
  BDelectTable.setBackground(new Color(255,255,255));
  TablePlaer.setBounds(30, 100, 200, 50);
  TablePlaer.setFont(new Font("宋体",1, 20));
 
  p1.add(TablePlaer);
  BMovePrevious.setBackground(new Color(255,255,255));
  BPlayer.setBackground(new Color(255,255,255));
  BMoveNext.setBackground(new Color(255,255,255));
  BStop.setBackground(new Color(255,255,255));
  select.setBackground(new Color(255,255,255));
  BSet.setBackground(new Color(255,255,255));
  p2.add(BMovePrevious);
  p2.add(BPlayer);
  p2.add(BMoveNext);
  p2.add(BStop);
  p2.add(select);
  p2.add(BSet);
  p2.setBackground(new Color(255,255,255));
 
  p.add(p2,BorderLayout.WEST);
  p.add(p3,BorderLayout.CENTER);
 
 
  p5.add(p);
  p5.add(BAdd);
  p5.add(BDelect);
  p5.add(BDelectTable);
 
 
  BAdd.addActionListener(this);
  BDelect.addActionListener(this);
  BDelectTable.addActionListener(this);
 
  BMoveNext.addActionListener(this);
  BPlayer.addActionListener(this);
  BMovePrevious.addActionListener(this);
  BStop.addActionListener(this);
  select.addActionListener(this);
  BSet.addActionListener(this);
  timeInformation.setEnabled(false);
   
   * 实现进度条
   *  
 
   timeSlider.setMajorTickSpacing(1);//调用此方法设置主刻度标记的间隔。传入的数字表示在每个主刻度标记之间以值衡量的距离。
   timeSlider.setPaintTicks(true); //要绘制主刻度，setPaintTicks 必须设置为 true
   timeSlider.addChangeListener(new ChangeListener() { //创建一个新的 ChangeListener 添加到滑块。
    public void stateChanged(ChangeEvent arg0) {
     if (player != null && ispressing == 1) {
      newtime = (int)((JSlider)arg0.getSource()).getValue();
      timeInformation.setText("当前时间:"+newtime/60+":"+newtime%60+"  ||  "+" 总时间: "+countSecond/60+":"+countSecond%60);
      ischanging = 1;
     }
    }
   });
   timeSlider.addMouseListener(new MouseAdapter(){
    public void mousePressed(MouseEvent arg0) {
     ispressing = 1;   //当鼠标对游标进行点击时
    }
    public void mouseReleased(MouseEvent arg0) {
     ispressing = 0;   //当鼠标不对游标进行点击时
    }
   });
   timeInformation.setText("当前时间：00:00  ||  总时间：00:00");
   timeInformation.setBackground(new Color(255,255,255));
   p3.add(timeInformation,BorderLayout.NORTH);
   p3.add(timeSlider,BorderLayout.SOUTH);
 
   j.add(pp);
   j.add(p5);
   j.add(p);
   j.add(p1);
   j.add(p6);
   j.setVisible(true);
//  j.setResizable(false);
 }
 
 
  * 主函数
  * 
 
 public static void main(String[] args) throws IOException, InterruptedException { //InterruptedException:当线程在活动之前或活动期间处于正在等待、休眠或占用状态且该线程被中断时，抛出该异常
  Player play=new Player();
  Thread timeRun = new Thread(play);
  timeRun.start(); 
 }
 public void actionPerformed(ActionEvent e) {
  String cmd = e.getActionCommand();     //通过获取字符串来判断是播放还是暂停，
  String box=(String)select.getSelectedItem();   //判断播放的顺序
  if(e.getSource()==BAdd)
  {
   if (player == null) {
    if (fileChooser.showOpenDialog(j) == MusicFileChooser.APPROVE_OPTION) {
     this.MusicName = fileChooser.getSelectedFile();
     File cd = fileChooser.getCurrentDirectory(); //获取当前路径
     if (cd != this.currentDirectory|| this.currentDirectory == null) {
      FileFilter[] fileFilters = (FileFilter[]) fileChooser.getChoosableFileFilters(); //FileFilter 是一个抽象类，JFileChooser 使用它过滤显示给用户的文件集合
      File files[] = cd.listFiles(); //cd.listFiles()表示返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
      this.MusicNames = new ArrayList<File>();
      for (File file : files) { //每次循环都将数组中的文件对象赋给file这个变量，然后再在循环体中对这个变量进行操作如：
             //for(int i=0;i<files.length;i++){ file = files[i];……}
       filename = file.getName().toLowerCase();   //获取所有的音乐名称
       for (FileFilter filter : fileFilters) {
        if (!file.isDirectory() && filter.accept(file)) {
         this.MusicNames.add(file);
          list.add(filename);
         filename=e.getActionCommand();
        }
       }
      }
     }
     index = MusicNames.indexOf(MusicName); //定义歌曲的下标
     count = MusicNames.size();
     PlayFile();
    }
   } else {
    player.start();
   }
 
  }
 
  if(cmd.equals("暂停")){
   BPlayer.setText("播放");
   player.stop();  
   }
  if(cmd.equals("播放")){
   BPlayer.setText("暂停");
   player.start();
  }
 
  if(e.getSource()==BStop){   //停止
    if (player != null) {
 
     player.stop();
     timeInformation.setText("当前时间:00:00  ||  总时间:00:00");
     timeSlider.setValue(0);
     player.setMediaTime(new Time(0)); //设置时间为零
  }
    }
  if(e.getSource()==BMoveNext){  //下一曲
    if (player != null) {
     if("顺序播放".equals(box)){
      nextMusic();
     }
     if("随机播放".equals(box)){
      int index = (int) rand.nextInt(this.MusicNames.size())+1;
       if (this.MusicNames != null && !this.MusicNames.isEmpty()) {
         if ( ++index == this.MusicNames.size()) {
          index=(int) rand.nextInt(this.MusicNames.size())+1;
         }
         player.stop();   //若点击上一曲，则将当前的歌曲停止播放，播放上一曲
         try {
          player=Manager.createRealizedPlayer(MusicNames.get(index).toURI().toURL());
          player.prefetch();
          player.setMediaTime(new Time(0.0));// 从某个时间段开始播放
          player.addControllerListener(this);
          l.setText("正在播放："+this.MusicNames.get(index).toString());
          list.select(index);
          player.start(); 
          flag=1;
         } catch (NoPlayerException | CannotRealizeException | IOException e1) {
          e1.printStackTrace();
         }
       }
     }
    }
  }
  if(e.getSource()==BMovePrevious){  //上一曲
    if (player != null) {
     if("顺序播放".equals(box)){
      PreviousMusic();
     }
     if("随机播放".equals(box)){
      int index = (int) rand.nextInt(this.MusicNames.size())+1;
      if (this.MusicNames != null && !this.MusicNames.isEmpty()) {
       if ( index--==(int) rand.nextInt(this.MusicNames.size())+1) {
        index = this.MusicNames.size() - 1;
       }
       player.stop();    //若点击上一曲，则将当前的歌曲停止播放，播放上一曲
       try {
        player=Manager.createRealizedPlayer(MusicNames.get(index).toURI().toURL());
        player.prefetch();
        player.setMediaTime(new Time(0.0));// 从某个时间段开始播放
        player.addControllerListener(this);
        l.setText("正在播放："+this.MusicNames.get(index).toString());
        list.select(index);
        player.start(); 
        flag=1;
       } catch (NoPlayerException | CannotRealizeException | IOException e1) {
        e1.printStackTrace();
       }
      }
    }
    }
  }
  if(e.getSource()==BDelect){    //删除歌曲
   index =list.getSelectedIndex();
   list.remove(index);
   MusicNames.remove(this.index);
  }
  if(e.getSource()==BDelectTable){   //清空列表
 
   list.removeAll();
   MusicNames.removeAll(MusicNames);
   player.stop();
   player = null;
  }
 
 
  //双击列表时实现播放
  list.addMouseListener(new MouseAdapter() {
   public void mouseClicked(MouseEvent e) {
    // 双击时处理
    if (e.getClickCount() == 2) {
     if(player!=null){
      player.stop();
     }
     // 播放选中的文件
     index=list.getSelectedIndex();
     PlayFile();
    } 
   }
  });
 
}
 
 // 因为实现了"ControllerListener"接口，本方法用于处理媒体播放器传来的事件；
 public void controllerUpdate(ControllerEvent e) {
  String box=(String)select.getSelectedItem();   //判断播放的顺序
  if (e instanceof EndOfMediaEvent) {
   player.setMediaTime(new Time(0));
   if ("单曲循环".equals(box)) {
    player.start();
   }
   if("顺序播放".equals(box)){
    nextMusic();
   }
   if("随机播放".equals(box)){
     if (this.MusicNames != null && !this.MusicNames.isEmpty()) {
       int index = (int) rand.nextInt(this.MusicNames.size())+1;
       try {
        player=Manager.createRealizedPlayer(MusicNames.get(index).toURI().toURL());
        player.prefetch();
        player.setMediaTime(new Time(0.0));// 从某个时间段开始播放
        player.addControllerListener(this);
        l.setText("正在播放："+this.MusicNames.get(index).toString());
        list.select(index);
        player.start(); 
        flag=1;
       } catch (NoPlayerException | CannotRealizeException | IOException e1) {
        e1.printStackTrace();
       }
     }
   }
  }
 }
 
  *//**
  * 获取MP3歌曲名
  * 
  * @MP3文件路径
  * @歌曲名
  *//*
 public String getMusicName(String str) {
  int i;
  for (i = str.length() - 1; i > 0; i--) {
   if (str.charAt(i) == '\\')
    break;
  }
  str = str.substring(i + 1, str.length() - 4);
  return str;
 }
 
 *//**
  * 下一首 实现函数
  *//*
 public void nextMusic() {
 }
 
 *//**
  * 上一首实现函数
  *//*
 public void PreviousMusic() {
 }
 
 
 *//**
  * 播放MP3文件主函数
  *//*
 public void PlayFile() {
  try {
   player = Manager.createRealizedPlayer(MusicNames.get(index).toURI().toURL());
   player.prefetch();
   player.setMediaTime(new Time(0.0));// 从某个时间段开始播放
   player.addControllerListener(this);
   l.setFont(new Font("宋体",0,20));
   l.setText("正在播放："+this.MusicNames.get(index).toString()); //显示正在播放的歌曲
   list.select(index);
   player.start();
 
   Mythread11 tt=new Mythread11();
   tt.start();
 
  } catch (Exception e1) { //当选到一首音乐不能播放时，对其进行处理
   dealError();
   return;
  }
  this.setFrame();
  }
 
 public void setFrame()
 {
  countSecond = (int)player.getDuration().getSeconds();
  timeSlider.setMaximum(countSecond);
  timeSlider.setValue(0);
  newtime = 0;
 }
 
 
private void dealError() {  
  // TODO Auto-generated method stub
 MusicNames.remove(index);
 if( --count == index )
  index = 0;
 if( count == 0)
  player = null;
 else
  PlayFile();
 }
 
*//**
 * MP3文件筛选内部类
 *//*
class MusicFileChooser extends JFileChooser {
 }
 
 
*//**
 * MP3文件筛选辅助内部类
 * 
 *//*
class MyFileFilter extends FileFilter { //FileFilter 是一个抽象类，JFileChooser 使用它过滤显示给用户的文件集合
 String[] suffarr;
 String decription;
 
 public MyFileFilter() {
  super();
 }
 
 public MyFileFilter(String[] suffarr, String decription) {
  super();
  this.suffarr = suffarr;
  this.decription = decription;
 }
 
 public boolean accept(File f) {
  for (String s : suffarr) {
   if (f.getName().toUpperCase().endsWith(s)) {
    return true;
   }
  }
  return f.isDirectory();
 }
 
 public String getDescription() {
  return this.decription;
 }
}
 
*//**
 * 读取显示时间进度条
 *//*
public void run() {
 while(true) {
  sleep();
  if(player != null) {
   if(ispressing == 0) {
    if(ischanging == 1) {
     newtime = timeSlider.getValue();
     player.setMediaTime(new Time(((long)newtime)*1000000000));
     ischanging = 0;
    } else {
     newtime = (int)player.getMediaTime().getSeconds();
     timeSlider.setValue(newtime);
     timeInformation.setText("当前时间："+newtime/60+":"+newtime%60+"  ||  "+" 总时间： "+countSecond/60+":"+countSecond%60);
 
    }
 
   }
  }
 }
}
 
//实现歌词的线程
class Mythread11 extends Thread { 
 public void run() {
  // TODO Auto-generated method stub
  try{
   LRC lrc = ReadLRC.readLRC("Traveling Light.lrc"); 
   Lyrics ls = ParseLRC.parseLRC(lrc); 
   playTest(ls);
  }catch(Exception e){
 
  }
 }
 
}
static void playTest(Lyrics ls) throws InterruptedException {
 tp.setFont(new Font("宋体",1,20));
 tp.setForeground(Color.BLUE);
 StyledDocument doc = tp.getStyledDocument();
 SimpleAttributeSet center = new SimpleAttributeSet();
 StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);  //将歌词区中显示
 doc.setParagraphAttributes(0, doc.getLength(), center, false);
 tp.setText("艺术家：" + ls.getAr());
 tp.setText("专辑：" + ls.getAl());
 tp.setText("歌曲：" + ls.getTi());
 tp.setText("歌词制作：" + ls.getBy());
 for (Lyric l : ls.getLyrics()) {
  tp.setText( l.getTxt());
  Thread.sleep(l.getTimeSize());
 }
}
 
}*/