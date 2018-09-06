package cashier;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    ObservableWrapper wrapper;

    @Override
    public void start(Stage primaryStage) throws Exception{
        wrapper = new ObservableWrapper(primaryStage);
        wrapper.appRun();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        File ticketFile = wrapper.getUtil().getTicketFilePath();
        if (ticketFile != null) {
            wrapper.getUtil().saveTicketDataToFile(ticketFile);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
