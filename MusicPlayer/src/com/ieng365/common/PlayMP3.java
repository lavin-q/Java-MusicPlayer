package com.ieng365.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.player.Player;

public class PlayMP3 {
    public static void main(String[] args) throws Exception {
        File file=new File("F:\\FFOutput\\薛之谦 - 怪咖.mp3");
        FileInputStream fis=new FileInputStream(file);
        BufferedInputStream stream=new BufferedInputStream(fis);
        Player player=new Player(stream);
        player.play();
    }
    //MP3播放方法
    public static void play(String filePath) throws Exception{
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream stream=new BufferedInputStream(fis);
        Player player=new Player(stream);
        player.play();
    }
}

