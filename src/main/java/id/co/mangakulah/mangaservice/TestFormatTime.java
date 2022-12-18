package id.co.mangakulah.mangaservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFormatTime {
    public static void main(String[] args) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        //System.out.println(simpleDateFormat.format(new Date()));

    }
}
