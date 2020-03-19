package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {


    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private static final int MENU_BUTTONS_START_X = 50;
    private static final int MENU_BUTTONS_START_Y = 100;

    private List<GameButton> menuButtons;
    private GameSubscene creditsSubscene;
    private GameSubscene helpSubscene;
    private GameSubscene scoreSubscene;
    private GameSubscene carChooserSubScene;

    private GameSubscene sceneToHide;
    List<CarPicker> carList;
    private CAR choosenCar;
    public ViewManager() throws FileNotFoundException //metoda
    {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane,1180,768);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("TheGame");
        createBackground();
        createButtons();
        createLogo();
        createSubScenes();
        carChooserSubScene.getPane().getChildren().add(createCarToChoose());
        carChooserSubScene.getPane().getChildren().add(createButtonToStart());
        //GameSubscene subScene = new GameSubscene();
        //subScene.setLayoutX(200);
        //subScene.setLayoutY(100);
        //mainPane.getChildren().add(subScene);



    }

    public GameButton createButtonToStart() throws FileNotFoundException {
        GameButton startButton = new GameButton("START");
        startButton.setLayoutX(220);
        startButton.setLayoutY(320);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(choosenCar !=null)
                {
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage,choosenCar);
                }
            }
        });
        return startButton;
    }

    private HBox createCarToChoose()
    {
        HBox box = new HBox();
        box.setSpacing(20);
        carList = new ArrayList<>();
        for(CAR car:CAR.values())
        {

            CarPicker carToPick = new CarPicker(car);
            carList.add(carToPick);
            box.getChildren().add(carToPick);
            carToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (CarPicker car:carList)
                    {
                        car.setIsCircleChoosen(false);
                    }
                    carToPick.setIsCircleChoosen(true);
                    choosenCar = carToPick.getCar();
                }
            });
        }
        box.setLayoutX(430-(118*2));
        box.setLayoutY(80);
        return box;
    }

    public Stage getMainStage() //metoda
    {
        return mainStage;
    }

    private void showSubScene(GameSubscene subScene)
    {
        if(sceneToHide != null)
        {
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes()
    {
        creditsSubscene = new GameSubscene();
        mainPane.getChildren().add(creditsSubscene);

        helpSubscene = new GameSubscene();
        mainPane.getChildren().add(helpSubscene);

        scoreSubscene = new GameSubscene();
        mainPane.getChildren().add(scoreSubscene);

        //carChooserSubScene = new GameSubscene();
        //mainPane.getChildren().add(carChooserSubScene);

        createCarSchooserSubScene();

    }

    private void createCarSchooserSubScene()
    {
        carChooserSubScene = new GameSubscene();
        mainPane.getChildren().add(carChooserSubScene);

        InfoLabel chooseCarLabel = new InfoLabel("Choose your car");
        chooseCarLabel.setLayoutX(110);
        chooseCarLabel.setLayoutY(18);
        carChooserSubScene.getPane().getChildren().add(chooseCarLabel);
    }

    private void addMenuButton(GameButton button)
    {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() throws FileNotFoundException {
        createStartButton();
        createHelpButton();
        createScoresButton();
        createCreditsButton();
        createExitButton();

    }

    private void createStartButton() throws FileNotFoundException {
        GameButton startButton = new GameButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(e -> showSubScene(carChooserSubScene));
    }
    private void createScoresButton() throws FileNotFoundException {
        GameButton scoresButton = new GameButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(e -> showSubScene(scoreSubscene));
    }
    private void createHelpButton() throws FileNotFoundException {
        GameButton helpButton = new GameButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(e -> showSubScene(helpSubscene));

    }
    private void createCreditsButton() throws FileNotFoundException {
        GameButton creditsButton = new GameButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(e -> showSubScene(creditsSubscene));
    }
    private void createExitButton() throws FileNotFoundException {
        GameButton exitButton = new GameButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(e -> mainStage.close());
    }


    private void createBackground()
    {
        Image backgroundImage = new Image("view/resources/deep-blue.jpg",900,900,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo()
    {
        ImageView logo = new ImageView("view/resources/logo.png");
        logo.setLayoutX(400);
        logo.setLayoutY(50);
        logo.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }


}
