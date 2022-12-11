package id.co.mangakulah.mangaservice.handler;

import id.co.mangakulah.mangaservice.dto.request.GenerateDbScriptRequest;
import id.co.mangakulah.mangaservice.util.DateFormatUtil;
import id.co.mangakulah.mangaservice.util.StringUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class DbScriptGeneratingHandler {

    public void generateDbScript(GenerateDbScriptRequest request) throws IOException {
        String imageLocation = request.getImageLocation();
        String fileName = request.getFileName();
        String mangaName = request.getMangaName();
        String postTitle = mangaName+" Chapter ";
        String postName = postTitle.toLowerCase().replaceAll(" ", "-");
        Integer idTermManga = request.getIdTermManga();
        Integer startFromChapter = request.getStartFromChapter();
        String imgCountSplitByComma = request.getImgCount();
        String[] imgCountArray = imgCountSplitByComma.split(",");
        System.out.println(imgCountArray);
        Integer startId = request.getStartIdScript();

        String baseQueryA = "INSERT INTO `wp_posts` (`ID`, `post_author`, `post_date`, `post_date_gmt`, `post_content`, `post_title`, `post_excerpt`, `post_status`, `comment_status`, `ping_status`, `post_password`, `post_name`, `to_ping`, `pinged`, `post_modified`, `post_modified_gmt`, `post_content_filtered`, `post_parent`, `guid`, `menu_order`, `post_type`, `post_mime_type`, `comment_count`, `wp_manga_search_text`) VALUES\n" +
                "(";
        String finalEndScript = "', 0, 'post', '', 0, NULL);";
        String baseContent="<img src=\\\"";
        String endContent = "\\\" alt=\\\"\\\" class=\\\"alignnone size-medium\\\" />\\r\\n";
        String endOfScriptA = "', '', 'publish', 'open', 'open', '', '";

        String termBaseScript = "INSERT INTO `wp_term_relationships` (`object_id`, `term_taxonomy_id`, `term_order`) VALUES\n" +
                "(";

        String wpPostScript = "";
        String wpTermScript = "";
        Date now = new Date();
        for (int a=0; a<imgCountArray.length; a++){
            now.setTime(now.getTime() + 1000);
            String finalDate = DateFormatUtil.getStandardFormat(now);
            String baseQueryB = ", 1, '"+finalDate+"', '"+finalDate+"', '";
            String endOfScriptB = "', '', '', '"+finalDate+"', '"+finalDate+"', '', 0, 'https://mangakulah.com/?p=";

            String finalContent = "";
            for (int c=1; c<=Integer.valueOf(imgCountArray[a]); c++){
                String content = imageLocation+ StringUtil.formatNumber3Digit(startFromChapter)+"/"+StringUtil.formatNumber3Digit(c)+".jpg";
                finalContent = finalContent + baseContent + content + endContent;
            }
            wpPostScript = wpPostScript + baseQueryA+startId+baseQueryB+ finalContent+"', '"+postTitle+startFromChapter+
                    endOfScriptA+postName+startFromChapter+endOfScriptB+startId+finalEndScript+System.lineSeparator()+System.lineSeparator();
            wpTermScript = wpTermScript + termBaseScript+startId+", "+idTermManga+", 0);"+System.lineSeparator();
            //System.out.println(wpPostScript+wpTermScript);
            startFromChapter++;
            startId++;
        }
        writeToFile(request.getDirectoryPath(), fileName, wpPostScript, wpTermScript);

    }

    private void writeToFile(String baseDir, String fileName, String wpPostScript, String wpTermScript) throws IOException {
        String path = baseDir+fileName;

        Charset charset = Charset.forName("US-ASCII");
        String s = wpPostScript+wpTermScript;
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path), charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

//        try {
//            System.out.println(wpPostScript+wpTermScript);
//            myWriter.write(wpPostScript+System.lineSeparator()+System.lineSeparator()+wpTermScript);
//            System.out.println("File created on "+path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
