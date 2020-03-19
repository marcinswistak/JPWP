package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label
{
    public final static String FONT_PATH = "src/model/resources/District.ttf";
    public final static String BACKGROUND_IMAGE = "view/resources/carchooser/yellow_chosecar_panel.png";
    public InfoLabel(String text) {
        setPrefWidth(350);
        setPrefHeight(50);
        //setPadding(new Insets(40, 40, 40, 40));
        setAlignment(Pos.CENTER);
        setText(text);
        setWrapText(true);
        setLabelFont();
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE,380,50,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));

    }



    public void setLabelFont() {
        try{
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
            //setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        }catch (FileNotFoundException e)
        {
            setFont(Font.font("Verdana",23));
        }
    }
}
