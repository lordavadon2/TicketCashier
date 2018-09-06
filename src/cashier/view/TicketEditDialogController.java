package cashier.view;

import cashier.model.Session;
import cashier.model.Ticket;
import cashier.util.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static cashier.util.Validator.checkInputData;

public class TicketEditDialogController {

    @FXML
    private TextField payerField;
    @FXML
    private TextField movieField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField rowField;
    @FXML
    private TextField placeField;
    @FXML
    private TextField costField;


    private Stage dialogStage;
    private Ticket ticket;
    private Session session;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTicket(Ticket ticket, Session session) {
        this.ticket = ticket;
        this.session = session;
        String[] dateTime = Parser.encodeData(session.getDataTime(), " ");
        String[] rowPlace = Parser.encodeData(ticket.getPlace(), "#");

        payerField.setText(ticket.getNamePayer());
        movieField.setText(session.getNameMovie());
        movieField.setEditable(false);
        dateField.setText(dateTime[0]);
        dateField.setEditable(false);
        timeField.setText(dateTime[1]);
        timeField.setEditable(false);
        rowField.setText(rowPlace[0]);
        placeField.setText(rowPlace[1]);
        costField.setText(String.valueOf(ticket.getCost()));
        dateTime = null;
        rowPlace = null;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            String dateTime = Parser.decodeData(dateField.getText(), timeField.getText(), " ");
            String rowPlace = Parser.decodeData(rowField.getText(), placeField.getText(), "#");
            ticket.setNamePayer(payerField.getText());
            ticket.setNameMovie(movieField.getText());
            ticket.setDataTime(dateTime);
            ticket.setPlace(rowPlace);
            ticket.setCost(Integer.parseInt(costField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (payerField.getText() == null || payerField.getText().length() == 0) {
            errorMessage += "Введите имя покупателя!\n";
        }

        if (rowField.getText() == null || rowField.getText().length() == 0
                || checkInputData(rowField.getText(),"^[1-7]$")) {
            errorMessage += "Введите ряд!\n";
        }

        if (placeField.getText() == null || placeField.getText().length() == 0
                || checkInputData(placeField.getText(),"^[1-9]{1}$|^[0-1]{1}[0-6]{1}$|^17$")) {
            errorMessage += "Ввведите место!\n";
        }

        if (costField.getText() == null || costField.getText().length() == 0
                || checkInputData(costField.getText(),"^[3-9]{1}[0-9]{1}$|^[1-4]{1}[0-9]{1}[0-9]{1}$|^500$")){
            errorMessage += "Ввведите цену билета!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверное значение");
            alert.setHeaderText("Пожалуйста введите правльное значение в поле:");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
