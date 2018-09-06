package cashier.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Session {

    private final StringProperty nameMovie;
    private final StringProperty dataTime;

    public Session() {
        this(null);
    }

    public Session(String nameMovie) {
        this.nameMovie = new SimpleStringProperty(nameMovie);
        this.dataTime = new SimpleStringProperty("01.01.2018 12:00");
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
}
