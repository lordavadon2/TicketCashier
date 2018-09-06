package cashier.view;

import cashier.ObservableWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {

    private ObservableWrapper wrapper;

    public void setWrapper(ObservableWrapper wrapper) {
        this.wrapper = wrapper;
    }


    @FXML
    private void handleNew() {
        wrapper.getTicketData().clear();
        wrapper.getUtil().setTicketFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети файл с данными о билетах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(wrapper.getPrimaryStage());

        if (file != null) {
            wrapper.getUtil().loadTicketDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File ticketFile = wrapper.getUtil().getTicketFilePath();
        if (ticketFile != null) {
            wrapper.getUtil().saveTicketDataToFile(ticketFile);
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Виберети куда сохранить данные о билетах");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(wrapper.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            wrapper.getUtil().saveTicketDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе.");
        alert.setHeaderText("About");
        alert.setContentText("Автор: Леонид Бондаренко\nСпециально для BrainAcademia");

        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
