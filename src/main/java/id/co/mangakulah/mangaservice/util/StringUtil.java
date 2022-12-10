package id.co.mangakulah.mangaservice.util;

public class StringUtil {

    public static String formatDigit(Integer number, Integer digit){
        return String.format("%0"+digit+"d", number);
    }

    public static String formatNumber3Digit(Integer number){
        return String.format("%03d", number);
    }
}
