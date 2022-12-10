package id.co.mangakulah.mangaservice;

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

public class TestLogic {
    public static void main(String[] args) throws IOException {
        String imageLocation = "https://i1.wp.com/mangakulah.com/wp-content/images/v/villain-to-kill/";
        String fileName = "sql_script.sql";
        String postTitle = "Villain to Kill Chapter ";
        String postName = "villain-to-kill-chapter-";
        Integer idTermManga = 38;
        Integer startFromChapter = 5;
        Integer imgCount = 7;
        Integer startId = 644;


        String baseQueryA = "INSERT INTO `wp_posts` (`ID`, `post_author`, `post_date`, `post_date_gmt`, `post_content`, `post_title`, `post_excerpt`, `post_status`, `comment_status`, `ping_status`, `post_password`, `post_name`, `to_ping`, `pinged`, `post_modified`, `post_modified_gmt`, `post_content_filtered`, `post_parent`, `guid`, `menu_order`, `post_type`, `post_mime_type`, `comment_count`, `wp_manga_search_text`) VALUES\n" +
                "(";
        Date now = new Date();
        String finalDate = DateFormatUtil.getStandardFormat(now);
        String baseQueryB = ", 1, '"+finalDate+"', '"+finalDate+"', '";

        String baseContent="<img src=\\\"";
        String endContent = "\\\" alt=\\\"\\\" class=\\\"alignnone size-medium\\\" />\\r\\n";
        String finalContent = "";
        String endOfScriptA = "', '', 'publish', 'open', 'open', '', '";
        String endOfScriptB = "', '', '', '"+finalDate+"', '"+finalDate+"', '', 0, 'https://mangakulah.com/?p=";
        String finalEndScript = "', 0, 'post', '', 0, NULL);";

        for (int c=1; c<=imgCount; c++){
            String content = imageLocation+ StringUtil.formatNumber3Digit(startFromChapter)+"/"+StringUtil.formatNumber3Digit(c)+".jpg";
            finalContent = finalContent + baseContent + content + endContent;
        }
        String wpPostScript = baseQueryA+startId+baseQueryB+ finalContent+"', '"+postTitle+startFromChapter+
                endOfScriptA+postName+startFromChapter+endOfScriptB+startId+finalEndScript;
        System.out.println(wpPostScript);

        String termBaseScript = "INSERT INTO `wp_term_relationships` (`object_id`, `term_taxonomy_id`, `term_order`) VALUES\n" +
                "(";
        String wpTermScript = termBaseScript+startId+", "+idTermManga+", 0);";
        System.out.println(wpTermScript);



        String baseDir = "D:/Images/Mangas/villain-to-kill/";
       // createdDirectory(baseDir);
        String path = baseDir+fileName;
        System.out.println("Final path -> "+path);
        FileWriter myWriter = new FileWriter(path);
        try {
            myWriter.write(wpPostScript+System.lineSeparator()+System.lineSeparator()+wpTermScript+
                    System.lineSeparator()+System.lineSeparator()+System.lineSeparator());
        } catch (IOException e) {
            //e.printStackTrace();
        }
//        for (int i = startFromChapter; i<startFromChapter+2; i++){
//            try {
//                myWriter.write(j.getChapterName() +"="+j.getImgCount()+System.lineSeparator());
//            } catch (IOException e) {
//                //e.printStackTrace();
//            }
//        }
        myWriter.close();


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
