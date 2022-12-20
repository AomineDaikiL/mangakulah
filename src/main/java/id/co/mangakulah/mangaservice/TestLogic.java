package id.co.mangakulah.mangaservice;

import com.google.common.base.CharMatcher;
import com.google.gson.Gson;
import id.co.mangakulah.mangaservice.dto.ImageInfoDto;
import id.co.mangakulah.mangaservice.dto.request.ScrapingImageRequest;
import id.co.mangakulah.mangaservice.util.DateFormatUtil;
import id.co.mangakulah.mangaservice.util.StringUtil;
import org.springframework.boot.json.GsonJsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestLogic {
    public static void main(String[] args) throws IOException {
        String imageLocation = "https://i1.wp.com/mangakulah.com/wp-content/images/v/villain-to-kill/";
        String fileName = "sql_script.sql";
        String postTitle = "Villain to Kill Chapter ";
        String postName = "villain-to-kill-chapter-";

        String currentString = "https://komikcast.site/chapter/return-of-the-8th-class-magician-chapter-01.80-bahasa-indonesia/";
        //String str = currentString.replaceAll("[^\\d.]", "");
        //String str = currentString.replaceAll("[\\D]", "");
        //String str = CharMatcher.inRange('0', '9').retainFrom(currentString);
        //System.out.println(str);


        Pattern decimalNumPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = decimalNumPattern.matcher(currentString);

        List<String> decimalNumList = new ArrayList<>();
        while (matcher.find()) {
            decimalNumList.add(matcher.group());
        }

        System.out.println(decimalNumList.get(decimalNumList.size()-1).replaceAll("-", ""));
    }

    private static void createdDirectory(String baseDownloadFileLocation){
        File file = new File(baseDownloadFileLocation);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }


}
