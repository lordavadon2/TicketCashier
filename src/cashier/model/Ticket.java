package cashier.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket {

    private final StringProperty namePayer;
    private final StringProperty nameMovie;
    private final StringProperty dataTime;
    private final StringProperty place;
    private final IntegerProperty cost;

    public Ticket() {
        this(null, null);
    }

    public Ticket(String nameMovie, String dataTime) {
        this.namePayer = new SimpleStringProperty("");
        this.nameMovie = new SimpleStringProperty(nameMovie);
        this.dataTime = new SimpleStringProperty(dataTime);
        this.place = new SimpleStringProperty("1#1");
        this.cost = new SimpleIntegerProperty(0);
    }

    public String getNamePayer() {
        return namePayer.get();
    }

    public StringProperty namePayerProperty() {
        return namePayer;
    }

    public void setNamePayer(String nameSeller) {
        this.namePayer.set(nameSeller);
    }

    public String getNameMovie() {
        return nameMovie.get();
    }

    public StringProperty nameMovieProperty() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie.set(nameMovie);
    }

    public String getDataTime() {
        return dataTime.get();
    }

    public StringProperty dataTimeProperty() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime.set(dataTime);
    }

    public String getPlace() {
        return place.get();
    }

    public StringProperty placeProperty() {
        return place;
    }

    public void setPlace(String place) {
        this.place.set(place);
    }

    public int getCost() {
        return cost.get();
    }

    public IntegerProperty costProperty() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost.set(cost);
    }
}
