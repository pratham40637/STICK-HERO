package com.example.stick_hero_final;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class Sound {

    static String background_music ="C:\\Users\\DELL\\Documents\\Stick-hero-final2\\src\\main\\resources\\Cool Upbeat Background Music For Videos _ No Copyright Music.mp3";

    static MediaPlayer bg_music = new MediaPlayer(new Media(new File(background_music).toURI().toString()));




}
