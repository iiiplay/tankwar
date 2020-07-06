package com.app17.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tools {
    static Image getImage(String imageName) {
        Image image = new ImageIcon("assets/images/" + imageName).getImage();

        return image;
    }

    static void playAudio(String fileName) {
        Media sound = new Media(new File("assets/audios/" + fileName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
