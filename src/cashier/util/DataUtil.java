package cashier.util;

import cashier.Main;
import cashier.ObservableWrapper;
import cashier.model.Session;
import cashier.model.SessionListWrapper;
import cashier.model.Ticket;
import cashier.model.TicketListWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;

public class DataUtil {

    private ObservableList<Ticket> ticketData;
    private ObservableList<Session> sessionData;

    public DataUtil(ObservableList<Ticket> ticketData, ObservableList<Session> sessionData) {
        this.ticketData = ticketData;
        this.sessionData = sessionData;
    }

    public File getSessionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(ObservableWrapper.class);
        String filePath = prefs.get("filePath2", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setSessionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(ObservableWrapper.class);
        if (file != null) {
            prefs.put("filePath2", file.getPath());
        } else {
            prefs.remove("filePath2");
        }
    }

    public void loadSessionDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SessionListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            SessionListWrapper wrapper = (SessionListWrapper) um.unmarshal(file);

            sessionData.clear();
            sessionData.addAll(wrapper.getSessions());

            // Сохраняем путь к файлу в реестре.
            setSessionFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public File getTicketFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath1", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setTicketFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath1", file.getPath());
        } else {
            prefs.remove("filePath1");
        }
    }

    public void loadTicketDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TicketListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            TicketListWrapper wrapper = (TicketListWrapper) um.unmarshal(file);

            ticketData.clear();
            ticketData.addAll(wrapper.getTickets());

            // Сохраняем путь к файлу в реестре.
            setTicketFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveTicketDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TicketListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            TicketListWrapper wrapper = new TicketListWrapper();
            wrapper.setTickets(ticketData);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setTicketFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить данные\n");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}
