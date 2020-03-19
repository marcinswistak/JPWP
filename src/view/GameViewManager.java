package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Bullet;
import model.CAR;
import model.SmallInfoLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Element;
import java.awt.*;

public class GameViewManager
{
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private final static String FIRE_PATH = "view/resources/carchooser/shotsFired.png";

    private static final int GAME_WIDTH=600;
    private static final int GAME_HEIGHT =800;

    private Stage menuStage;
    private ImageView car;

    private ImageView shotFire;
    private final static String ROCK_IMAGE = "view/resources/carchooser/theRockv1.png";
    private final static String BRANCH_IMAGE = "view/resources/carchooser/fallenBranch.png";
    private ImageView[] whiteRocks;
    private ImageView[] fallenBranches;
    Random randomPositionGenerator;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private boolean isSpacePressed =false;

    private int angle;
    private AnimationTimer gameTimer;


    private GridPane gridPane1;
    private GridPane gridPane2;
    private GridPane gridPane3;
    private GridPane gridPane4;
    private final static String BACKGROUND_IMAGE = "view/resources/theRoad.png";
    private final static String BACKGROUND_ROAD_LINES_IMAGE = "view/resources/theRoadLineV3.png";

    private ImageView goldStar;
    private SmallInfoLabel pointsLabel;
    private ImageView playerLifes[];
    private int playerLife;
    private int points;
    private final static String GOLD_STAR_IMAGE = "view/resources/goldStar.png";

    private final static double GOLD_STAR_RADIUS = 19.5;
    private final static int CAR_RADIUS = 22 ;
    private final static int ROCK_RADIUS = 21;
    private final static double FALLEN_BRANCH_RADIUS = 19.5;


    private Bullet[] bullets = new Bullet[100];






    public GameViewManager()
    {
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();

    }

    private void createKeyListeners()
    {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()== KeyCode.LEFT)
                {

                    isLeftKeyPressed = true;

                }
                else if (keyEvent.getCode()==KeyCode.RIGHT)
                {
                    isRightKeyPressed = true;

                }

                if(keyEvent.getCode()==KeyCode.UP)
                {
                    isUpKeyPressed=true;
                }
                else if(keyEvent.getCode()==KeyCode.DOWN)
                {
                    isDownKeyPressed=true;
                }

                if(keyEvent.getCode()==KeyCode.SPACE)
                {

                    isSpacePressed = true;
                }

            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {


                if(keyEvent.getCode()== KeyCode.LEFT)
                {
                    isLeftKeyPressed=false;
                }
                else if (keyEvent.getCode()==KeyCode.RIGHT)
                {
                    isRightKeyPressed=false;
                }

                if(keyEvent.getCode()==KeyCode.UP)
                {
                    isUpKeyPressed=false;
                }
                else if(keyEvent.getCode()==KeyCode.DOWN)
                {
                    isDownKeyPressed=false;

                }
                if(keyEvent.getCode()==KeyCode.SPACE)
                {

                    isSpacePressed = false;
                }
            }
        });

        //gameScene.setOnKeyPressed(new);
    }


    private void initializeStage()
    {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane,GAME_WIDTH,GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

    }

    private double calculateDistance(double x1, double x2, double y1, double y2)
    {
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));

    }
    private void removeLife()
    {
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;

        if(playerLife < 0)
        {
            gameStage.close();
            gameTimer.stop();
            menuStage.show();
        }
    }

    private void checkIfElementsCollide()
    {
        if(CAR_RADIUS+GOLD_STAR_RADIUS>calculateDistance(car.getLayoutX()+24,goldStar.getLayoutX()+30,car.getLayoutY()+44,goldStar.getLayoutY()+28))
        {
            setNewElementPosition(goldStar);

            points++;
            String textToSet = "POINTS";
            if(points<10){
                textToSet = textToSet+"0";
            }
            pointsLabel.setText(textToSet+points);
        }

        for(int i=0; i<whiteRocks.length; i++)
        {
            if(CAR_RADIUS + ROCK_RADIUS>calculateDistance(car.getLayoutX()+24,whiteRocks[i].getLayoutX()+26,car.getLayoutY()+44,whiteRocks[i].getLayoutY()+21))
            {
                removeLife();
                setNewElementPosition(whiteRocks[i]);
            }
        }
        for(int i=0; i<fallenBranches.length; i++)
        {
            if(CAR_RADIUS + FALLEN_BRANCH_RADIUS>calculateDistance(car.getLayoutX()+24,fallenBranches[i].getLayoutX()+21,car.getLayoutY()+44,fallenBranches[i].getLayoutY()+20))
            {
                removeLife();
                setNewElementPosition(fallenBranches[i]);
            }
        }

    }

    public void createNewGame(Stage menuStage, CAR choosenCar)
    {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createGameElements(choosenCar);
        createCar(choosenCar);

        createGameLoop();

        gameStage.show();
    }

    private void createCar(CAR choosenCar)
    {
        car = new ImageView(choosenCar.getUrl());
        car.setLayoutX(GAME_WIDTH/2);
        car.setLayoutY(GAME_HEIGHT-170);
        gamePane.getChildren().add(car);

        //animacja ognia - proby
        shotFire = new ImageView(FIRE_PATH);
        gamePane.getChildren().add(shotFire);
        shotFire.setLayoutX(1080);
        shotFire.setLayoutY(1080);

    }


    private void createGameElements(CAR choosenCar)
    {
        playerLife = 2;
        goldStar = new ImageView(GOLD_STAR_IMAGE);
        setNewElementPosition(goldStar);
        gamePane.getChildren().add(goldStar);
        pointsLabel = new SmallInfoLabel("POINTS : 00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);
        playerLifes = new ImageView[3];

        for(int i=0; i<playerLifes.length; i++)
        {
            playerLifes[i]= new ImageView(choosenCar.getUrlLifeIndicator());
            playerLifes[i].setLayoutX(445+(i*50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }


        whiteRocks = new ImageView[3];
        for(int i=0; i<whiteRocks.length;i++)
        {
            whiteRocks[i] = new ImageView(ROCK_IMAGE);
            setNewElementPosition(whiteRocks[i]);
            gamePane.getChildren().add(whiteRocks[i]);
        }
        fallenBranches = new ImageView[3];
        for(int i=0; i<fallenBranches.length;i++)
        {
            fallenBranches[i] = new ImageView(BRANCH_IMAGE);
            setNewElementPosition(fallenBranches[i]);
            gamePane.getChildren().add(fallenBranches[i]);
        }
    }

    private void moveGameElements()
    {
        goldStar.setLayoutY(goldStar.getLayoutY()+2);

        for(int i=0; i<whiteRocks.length; i++)
        {
            whiteRocks[i].setLayoutY(whiteRocks[i].getLayoutY()+5);

        }
        for(int i=0; i<fallenBranches.length; i++)
        {
            fallenBranches[i].setLayoutY(fallenBranches[i].getLayoutY()+5);

        }
    }

    private void checkIfElementsAreBehindCarAndRelocateThem()
    {
        if(goldStar.getLayoutY()>1200)
        {
            setNewElementPosition(goldStar);
        }

        for(int i=0; i<whiteRocks.length; i++)
        {
            if(whiteRocks[i].getLayoutY()>900)
            {
                setNewElementPosition(whiteRocks[i]);
            }
        }
        for(int i=0; i<fallenBranches.length; i++)
        {
            if(fallenBranches[i].getLayoutY()>900)
            {
                setNewElementPosition(fallenBranches[i]);
            }
        }

    }

    private void setNewElementPosition(ImageView image)
    {
        image.setLayoutX(randomPositionGenerator.nextInt(370));
        image.setLayoutY(-(randomPositionGenerator.nextInt(3200)+600 ));
    }


    private void createGameLoop()
    {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBackground();

                showMuzzleFlash();
                moveGameElements();
                checkIfElementsAreBehindCarAndRelocateThem();
                checkIfElementsCollide();



                moveCar();



                //changeCarImage();

            }
        };
        gameTimer.start();
    }


    public void showMuzzleFlash()
    {


        if (isSpacePressed )
        {

            shotFire.setLayoutX(car.getLayoutX()+10);
            shotFire.setLayoutY(car.getLayoutY()-15);



        }
        if(isSpacePressed==false)
        {
            shotFire.setLayoutX(1080);
            shotFire.setLayoutY(1080);


        }
    }






    private void moveCar()
    {

        if(isLeftKeyPressed && !isRightKeyPressed)
        {

            if(angle > -1)
            {
                angle -=1;
            }
            //car.setRotate(angle);
            //shotFire.setRotate(angle);
            if(car.getLayoutX()>0)
            {
                car.setLayoutX(car.getLayoutX()-3);
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed)
        {

            if(angle < 1)
            {
                angle +=1;
            }
            //car.setRotate(angle);
            //shotFire.setRotate(angle);
            if(car.getLayoutX()<522)
            {
                car.setLayoutX(car.getLayoutX()+3);
            }

        }
        if(!isLeftKeyPressed && !isRightKeyPressed)
        {

            if(angle<0)
            {
                angle+=1;
            }else if( angle>0)
            {
                angle-=1;
            }
            //car.setRotate(angle);
            //shotFire.setRotate(angle);
        }
        if(isLeftKeyPressed && isRightKeyPressed)
        {
            if(angle<0)
            {
                angle+=1;
            }else if( angle>0)
            {
                angle-=1;
            }
            //car.setRotate(angle);
            //shotFire.setRotate(angle);
        }
        if(isUpKeyPressed && !isDownKeyPressed)
        {
            car.setLayoutY(car.getLayoutY()-2);
        }

        if(!isUpKeyPressed && isDownKeyPressed)
        {
            car.setLayoutY(car.getLayoutY()+2);
        }
        if (isDownKeyPressed && isUpKeyPressed)
        {

            //cos jest wpisane ale ogolnie to ma sie dziac nic
            car.setLayoutY(car.getLayoutY()+0);
        }
        if (!isDownKeyPressed && !isUpKeyPressed)
        {

            //nic sie nie dzieje gdy nie sa wcisniete klawisze xd
            car.setLayoutY(car.getLayoutY()+0);
        }

    }

    private void createBackground()
    {
        //dwa GridPane bo gdy jeden sie bedzie rpzesuwal to
        //drugi pojedzie od razu za nim zeby nie bylo pustych miejsc
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();
        gridPane3 = new GridPane();
        gridPane4 = new GridPane();

        for(int i=0; i<12; i++)
        {
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage3 = new ImageView(BACKGROUND_ROAD_LINES_IMAGE);
            ImageView backgroundImage4 = new ImageView(BACKGROUND_ROAD_LINES_IMAGE);
            GridPane.setConstraints(backgroundImage1,i%3,i/3);
            GridPane.setConstraints(backgroundImage2,i%3 ,i/3);
            GridPane.setConstraints(backgroundImage3,1,i/3);
            GridPane.setConstraints(backgroundImage4,1,i/3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
            gridPane3.getChildren().add(backgroundImage3);
            gridPane4.getChildren().add(backgroundImage4);
        }
        gridPane3.setLayoutX(180);
        gridPane4.setLayoutX(180);
        gridPane4.setLayoutY(-1023);
        gridPane2.setLayoutY(-1023);
        gamePane.getChildren().add(gridPane1);
        gamePane.getChildren().add(gridPane2);
        gamePane.getChildren().add(gridPane3);
        gamePane.getChildren().add(gridPane4);

    }

   /* private void changeCarImage()
    {
        if(isSpacePressed==true)
        {

        }
        else if(isSpacePressed==false)
        {
            car = new ImageView("view/resources/carchooser/redCar1.png");
        }

    }*/

    private void moveBackground()
    {
        gridPane1.setLayoutY(gridPane1.getLayoutY()+3);
        gridPane2.setLayoutY(gridPane2.getLayoutY()+3);
        gridPane3.setLayoutY(gridPane3.getLayoutY()+3);
        gridPane4.setLayoutY(gridPane4.getLayoutY()+3);
        if(gridPane1.getLayoutY()>=1023)
        {
            gridPane1.setLayoutY(-1023);
        }
        if(gridPane2.getLayoutY()>=1023)
        {
            gridPane2.setLayoutY(-1023);
        }
        if(gridPane3.getLayoutY()>=1023)
        {
            gridPane3.setLayoutY(-1000);
        }
        if(gridPane4.getLayoutY()>=1023)
        {
            gridPane4.setLayoutY(-1023);
        }

    }

}


