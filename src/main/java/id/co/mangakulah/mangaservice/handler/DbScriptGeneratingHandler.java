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
import java.util.Random;

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
        System.out.println("Count Of Chapter = "+imgCountArray.length);
        System.out.println(imgCountArray);
        Integer startId = request.getStartIdScript();
        Integer seriesId = request.getSeriesId();

        String baseQueryA = "INSERT INTO `wp_posts` (`ID`, `post_author`, `post_date`, `post_date_gmt`, `post_content`, `post_title`, `post_excerpt`, `post_status`, `comment_status`, `ping_status`, `post_password`, `post_name`, `to_ping`, `pinged`, `post_modified`, `post_modified_gmt`, `post_content_filtered`, `post_parent`, `guid`, `menu_order`, `post_type`, `post_mime_type`, `comment_count`, `wp_manga_search_text`) VALUES\n" +
                "(";
        String finalEndScript = "', 0, 'post', '', 0, NULL);";
        String finalEndScriptRev = "', 0, 'revision', '', 0, NULL);";
        String baseContent="<img src=\\\"";
        String endContent = "\\\" alt=\\\"\\\" class=\\\"alignnone size-medium\\\" />\\r\\n";
        String endOfScriptA = "', '', 'publish', 'open', 'open', '', '";
        String endOfScriptARev = "', '', 'inherit', 'closed', 'closed', '', '";

        String termBaseScript = "INSERT INTO `wp_term_relationships` (`object_id`, `term_taxonomy_id`, `term_order`) VALUES\n" +
                "(";

        String wpPostScript = "";
        String wpTermScript = "";
        String wpPostMetaScript= "";
        Date now = new Date();
        for (int a=0; a<imgCountArray.length; a++){
            now.setTime(now.getTime() + 1000);
            String finalDate = DateFormatUtil.getStandardFormat(now);
            String baseQueryB = ", 1, '"+finalDate+"', '"+finalDate+"', '";
            String endOfScriptB = "', '', '', '"+finalDate+"', '"+finalDate+"', '', 0, 'https://mangakulah.com/?p=";
            String endOfScriptBRev = startId+"-revision-v1', '', '', '"+finalDate+"', '"+finalDate+"', '', "+startId+", 'https://mangakulah.com/?p=";

            String finalContent = "";
            for (int c=1; c<=Integer.valueOf(imgCountArray[a]); c++){
                String content = imageLocation+ StringUtil.formatNumber3Digit(startFromChapter)+"/"+StringUtil.formatNumber3Digit(c)+".jpg";
                finalContent = finalContent + baseContent + content + endContent;
            }
            wpPostScript = wpPostScript + baseQueryA+startId+baseQueryB+ finalContent+"', '"+postTitle+startFromChapter+
                    endOfScriptA+postName+startFromChapter+endOfScriptB+startId+finalEndScript+System.lineSeparator();

            wpTermScript = wpTermScript + termBaseScript+startId+", "+idTermManga+", 0);"+System.lineSeparator()+System.lineSeparator();
            wpPostMetaScript = wpPostMetaScript + generatePostMetaScript(startId, startFromChapter, seriesId)+System.lineSeparator();

            startId++;
            String wpPostScriptRevision =  baseQueryA+startId+baseQueryB+ finalContent+"', '"+postTitle+startFromChapter+
                    endOfScriptARev+endOfScriptBRev+startId+finalEndScriptRev+System.lineSeparator()+System.lineSeparator();
            wpPostScript = wpPostScript + wpPostScriptRevision;

            //System.out.println(wpPostScript+wpTermScript);
            startFromChapter++;
            startId++;
        }
        writeToFile(request.getDirectoryPath(), fileName, wpPostScript, wpTermScript, wpPostMetaScript);

    }

    private void writeToFile(String baseDir, String fileName, String wpPostScript, String wpTermScript, String wpMetaScript) throws IOException {
        String path = baseDir+fileName;

        Charset charset = Charset.forName("US-ASCII");
        String s = wpPostScript+wpTermScript+wpMetaScript;
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

    private String  generatePostMetaScript(Integer postId, Integer chapter, Integer seriesId){
        String firstScript = "INSERT INTO `wp_postmeta` (`post_id`, `meta_key`, `meta_value`) VALUES\n" +
                "(";
        String secondScript = postId + ", '_edit_last', '1'),\n" +
                "(";
        String pref = "1670";
        String thirdScript = postId + ", '_edit_lock', '"+pref+genRandomNumber()+":1'),\n" +
                "(";
        /*String fourthScript = postId + ", '_pingme', '1'),\n" +
                "(";*/
        String fourthScript = postId + ", 'ao_post_optimize', 'a:6:{s:16:\\\"ao_post_optimize\\\";s:2:\\\"on\\\";s:19:\\\"ao_post_js_optimize\\\";s:2:\\\"on\\\";s:20:\\\"ao_post_css_optimize\\\";s:2:\\\"on\\\";s:12:\\\"ao_post_ccss\\\";s:2:\\\"on\\\";s:16:\\\"ao_post_lazyload\\\";s:2:\\\"on\\\";s:15:\\\"ao_post_preload\\\";s:0:\\\"\\\";}'),\n" +
                "(";
        String fourtScriptV2 = postId + ", '_encloseme', '1'),\n" +
                "(";
        String fiveScript = postId + ", 'ero_chapter', '"+ chapter + "'),\n" +
                "(";
        String sixScript = postId + ", 'ero_seri', '"+ seriesId + "'),\n" +
                "(";
        String sixScriptV2 = postId + ", 'wpb_post_views_count', '0'),\n" +
                "(";
        String lastScript = postId + ", 'ab_embedgroup', 'a:1:{i:0;a:1:{s:6:\\\"_state\\\";s:8:\\\"expanded\\\";}}');";
        return firstScript + secondScript + thirdScript + fourthScript + fourtScriptV2 + fiveScript + sixScript + sixScriptV2 + lastScript;

    }

    private int genRandomNumber(){
        Random r = new Random( System.currentTimeMillis() );
        return 100000 + r.nextInt(200000);
    }

}
