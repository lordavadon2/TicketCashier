package cashier.view;

import cashier.ObservableWrapper;
import cashier.model.Session;
import cashier.model.Ticket;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


import java.io.File;

public class TicketWindowController {

    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, String> namePayerColumn;
    @FXML
    private TableColumn<Ticket, String> nameMovieColumn;
    @FXML
    private TableColumn<Ticket, String> dateTimeMColumn;
    @FXML
    private TableColumn<Ticket, String> placeColumn;
    @FXML
    private TableColumn<Ticket, Integer> costColumn;

    @FXML
    private TableView<Session> sessionTable;
    @FXML
    private TableColumn<Session, String> nameMovie2Column;
    @FXML
    private TableColumn<Session, String> dateTimeColumn;
    @FXML
    private TextField filterSessionField;
    @FXML
    private TextField filterTicketField;

    private ObservableWrapper wrapper;

    public TicketWindowController() {
    }

    @FXML
    private void initialize() {
        namePayerColumn.setCellValueFactory(cellData -> cellData.getValue().namePayerProperty());
        nameMovieColumn.setCellValueFactory(cellData -> cellData.getValue().nameMovieProperty());
        dateTimeMColumn.setCellValueFactory(cellData -> cellData.getValue().dataTimeProperty());
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());

        nameMovie2Column.setCellValueFactory(cellData -> cellData.getValue().nameMovieProperty());
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dataTimeProperty());

    }

    public void setWrapper(ObservableWrapper wrapper) {
        this.wrapper = wrapper;

        ticketTable.setItems(wrapper.getTicketData());
        sessionTable.setItems(wrapper.getSessionData());
    }

    @FXML
    private void handleFilterSessionTable(){
            FilteredList<Session> filteredData = new FilteredList<>(wrapper.getSessionData(), p -> false);
            filterSessionField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(sess -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (sess.getNameMovie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    }
                    if (sess.getDataTime().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    }
                    return false; // Does not match.
                });
            });
            SortedList<Session> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(sessionTable.comparatorProperty());
            sessionTable.setItems(sortedData);
    }

    @FXML
    private void handleFilterTicketTable(){
            FilteredList<Ticket> filteredData = new FilteredList<>(wrapper.getTicketData(), p -> false);
            filterTicketField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(tick -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (tick.getNamePayer().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    }
                    if (tick.getNameMovie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    }
                    if (tick.getDataTime().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    }
                    return false; // Does not match.
                });
            });
            SortedList<Ticket> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(ticketTable.comparatorProperty());
            ticketTable.setItems(sortedData);
    }


    @FXML
    private void handleDeleteTicket(){
        int selectedIndex = ticketTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ticketTable.getItems().remove(selectedIndex);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Неверный выбор");
            alert.setHeaderText("Не выбран билет для удаления");
            alert.setContentText("Пожалуйста выберите билет в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditTicket() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        Session selectedSession = sessionTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null && selectedTicket != null) {
            boolean okClicked = wrapper.showTicketEditDialog(selectedTicket, selectedSession);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Запись не выбрана");
            alert.setHeaderText("Выберите билет для редактирования");
            alert.setContentText("Выберите билет в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewTicket() {
        Ticket tempTicket = new Ticket();
        Session selectedSession = sessionTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            boolean okClicked = wrapper.showTicketEditDialog(tempTicket, selectedSession);
            if (okClicked) {
                wrapper.getTicketData().add(tempTicket);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(wrapper.getPrimaryStage());
            alert.setTitle("Запись не выбрана");
            alert.setHeaderText("Выберите сессию");
            alert.setContentText("Выберите сессию в таблице!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleOpenSession() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети файл с данными о сеансах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(wrapper.getPrimaryStage());

        if (file != null) {
            wrapper.getUtil().loadSessionDataFromFile(file);
        }
    }

}
