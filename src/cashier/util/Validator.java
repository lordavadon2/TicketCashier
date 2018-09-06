package cashier.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean checkInputData(String inputData, String inputPattern) {
        Pattern pattern = Pattern.compile(inputPattern);    //"^[A-Za-z][:][\\\\][a-zA-Z0-9._\\\\]+[\\s][^\\s]+$"
        Matcher matcher = pattern.matcher(inputData);
        if (!matcher.matches()) {return true;}
        return false;
    }
}
