package cashier.util;

public class Parser {

    public static String[] encodeData(String data, String spliterator){
        String[] newData = data.split(spliterator);
        return newData;
    }

    public static String decodeData(String data1, String data2, String spliterator){
        String newData = data1 + spliterator + data2;
        return newData;
    }
}
