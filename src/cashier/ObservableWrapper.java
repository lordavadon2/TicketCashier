package cashier;

import cashier.model.Session;
import cashier.model.SessionListWrapper;
import cashier.model.Ticket;
import cashier.model.TicketListWrapper;
import cashier.util.DataUtil;
import cashier.view.RootLayoutController;
import cashier.view.TicketEditDialogController;
import cashier.view.TicketWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class ObservableWrapper {

    private Stage primaryStage;
    private BorderPane borderPane;
    private DataUtil util;

    private ObservableList<Ticket> ticketData = FXCollections.observableArrayList();
    private ObservableList<Session> sessionData = FXCollections.observableArrayList();

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Ticket> getTicketData() {
        return ticketData;
    }

    public ObservableList<Session> getSessionData() {
        return sessionData;
    }

    public DataUtil getUtil() {
        return util;
    }

    public ObservableWrapper(Stage primaryStage) {
        this.primaryStage = primaryStage;
        util = new DataUtil(ticketData, sessionData);
    }

    public void appRun(){
        this.primaryStage.setTitle("Система по продаже билетов в кинотеатре");
        this.primaryStage.setResizable(false);

        // Set the application icon.
        this.primaryStage.getIcons().add(
                new Image("file:src/cashier/resources/ico1.png"));

        initRootLayout();

        showMainWindow();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ObservableWrapper.class.getResource("view/RootLayout.fxml"));
            borderPane = (BorderPane) loader.load();

            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setWrapper(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fileM = util.getTicketFilePath();
        if (fileM != null) {
            util.loadTicketDataFromFile(fileM);
        }
        File fileS = util.getSessionFilePath();
        if (fileS != null) {
            util.loadSessionDataFromFile(fileS);
        }
    }

    private void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ObservableWrapper.class.getResource("view/TicketWindow.fxml"));
            Pane mainOverview = (Pane) loader.load();

            borderPane.setCenter(mainOverview);

           TicketWindowController controller = loader.getController();
           controller.setWrapper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showTicketEditDialog(Ticket ticket, Session session){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ObservableWrapper.class.getResource("view/TicketEditDialog.fxml"));
            Pane page = (Pane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование билета");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TicketEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTicket(ticket, session);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
