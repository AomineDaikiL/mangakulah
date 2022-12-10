package id.co.mangakulah.mangaservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    private final static String STANDARD = "yyyy-MM-dd HH:mm:ss";

    public static String getStandardFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD);

        return simpleDateFormat.format(date);
    }
}
