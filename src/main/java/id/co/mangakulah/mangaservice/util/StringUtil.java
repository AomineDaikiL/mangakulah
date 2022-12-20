package id.co.mangakulah.mangaservice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String formatDigit(Integer number, Integer digit){
        return String.format("%0"+digit+"d", number);
    }

    public static String formatNumber3Digit(Integer number){
        return String.format("%03d", number);
    }

    public static String formatChapter3Digit(String chapter){
        try {
            return String.format("%03d", Integer.valueOf(chapter));
        }catch (Exception e){
            if (chapter.contains(".") && chapter.length() < 5){
                try {
                    return String.format("%05.1f", Float.valueOf(chapter));
                }catch (Exception e2){}
            }
        }
        return chapter;
    }

    public static String formatChapterNumber(String chapterName){
        try {
            return String.valueOf(Integer.valueOf(chapterName));
        }catch (Exception e){
            if (chapterName.contains(".") && chapterName.length() < 4){
                try {
                    return String.format("%01.1f", Float.valueOf(chapterName));
                }catch (Exception e2){}
            }
        }
        return  chapterName;
    }

    public static String getChapterNumberFromUrl(String url){
        Pattern decimalNumPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = decimalNumPattern.matcher(url);

        List<String> decimalNumList = new ArrayList<>();
        while (matcher.find()) {
            decimalNumList.add(matcher.group());
        }
        return decimalNumList.get(decimalNumList.size()-1).replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(formatChapterNumber("003.1"));
    }
}
