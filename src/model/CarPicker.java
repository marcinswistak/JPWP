package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CarPicker extends VBox
{
    private ImageView circleImage;

    private ImageView carImage;
    private String circleNotChoosen = "view/resources/carchooser/circleNotChoosen.png";
    private String circleChoosen = "view/resources/carchooser/circleChoosen.png";

    private CAR car;
    private boolean isCircleChoosen;

    public CarPicker(CAR car)
    {
        circleImage = new ImageView(circleNotChoosen);
        carImage = new ImageView(car.getUrl());

        this.car = car;
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(carImage);
    }

    public CAR getCar()
    {
        return car;
    }
    public boolean getIsCircleChoosen()
    {
        return isCircleChoosen;
    }
    public void setIsCircleChoosen(boolean isCircleChoosen)
    {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen: circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }



}
