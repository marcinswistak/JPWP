package model;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameSubscene extends SubScene
{
    private final static String FONT_PATH = "src/model/resources/District.tff";
    private final static String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";

    private boolean isHidden;



    public GameSubscene() {
        super(new AnchorPane(),600,400);
        prefWidth(600);
        prefHeight(600);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,600,400,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
        setLayoutX(1500);
        setLayoutY(180);
        isHidden = true;
    }

    public void moveSubScene()
    {

    TranslateTransition transition = new TranslateTransition();
    transition.setDuration(Duration.seconds(0.3));
    transition.setNode(this);
    transition.setToX(-1080);
    if(isHidden==true)
    {
        transition.setToX(-1080);
        isHidden=false;
    }
    else
    {
        transition.setToX(0);
        isHidden=true;
    }
    transition.play();

    }

    public AnchorPane getPane()
    {
        return (AnchorPane) this.getRoot();
    }

}
