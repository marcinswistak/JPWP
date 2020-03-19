package model;


public enum CAR {
    BLACK("view/resources/carchooser/redCarCannon.png","view/resources/carchooser/lifeIndicator.png"),
    WHITE("view/resources/carchooser/samochod_02.png","view/resources/carchooser/lifeIndicator.png");


    String urlCar;
    String urlLifeIndicator;
    private CAR(String urlCar,String urlLifeIndicator)
    {
        this.urlCar = urlCar;
        this.urlLifeIndicator = urlLifeIndicator;
    }

    public String getUrl()
    {
        return this.urlCar;
    }
    public String getUrlLifeIndicator()
    {
        return this.urlLifeIndicator;
    }
}
