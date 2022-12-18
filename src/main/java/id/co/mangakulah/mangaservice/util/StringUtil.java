package id.co.mangakulah.mangaservice.util;

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
            if (chapter.contains(".")){
                return String.format("%05.1f", Float.valueOf(chapter));
            }
        }
        return chapter;
    }

    public static String formatChapterNumber(String chapterName){
        try {
            return String.valueOf(Integer.valueOf(chapterName));
        }catch (Exception e){
            if (chapterName.contains(".")){
                return String.format("%01.1f", Float.valueOf(chapterName));
            }
        }
        return  chapterName;
    }

    public static void main(String[] args) {
        System.out.println(formatChapterNumber("003.1"));
    }
}
