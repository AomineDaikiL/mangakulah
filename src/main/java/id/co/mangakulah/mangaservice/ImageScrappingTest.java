package id.co.mangakulah.mangaservice;

import com.google.gson.Gson;
import id.co.mangakulah.mangaservice.dto.ImageInfoDto;
import id.co.mangakulah.mangaservice.dto.ImageInfoDtoV2;
import id.co.mangakulah.mangaservice.dto.request.ScrapingImageRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ImageScrappingTest {
    private static int TIMEOUT = 30000;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String... args) throws InterruptedException, IOException {
        String baseDir = "D:/Images/images/";
        String url = "https://mangatale.co/magic-emperor-chapter-01/";
        Document doc = Jsoup.connect(url).get();
//        Iterator<Element> ie = doc.select("a").iterator();
//        ScrapingImageRequest request = new ScrapingImageRequest();
//        List<ImageInfoDtoV2> infos = new ArrayList<>();
//        while (ie.hasNext()){
//            Element e = ie.next();
//            String chapterUrl = e.attr("href");
//            if (!chapterUrl.contains("chapter")) continue;
//            String chapterName = e.child(0).text();
//            if (chapterName.contains("New")) continue;
//            System.out.println(chapterName.replaceAll("Chapter", "").trim());
//            System.out.println(chapterUrl);
//            String chapterName = e.text().replaceAll("Chapter", "").trim();
//            ImageInfoDtoV2 dto = new ImageInfoDtoV2();
//            dto.setImageUrl(chapterUrl);
//            dto.setChapter(chapterName);
//            infos.add(dto);
//        }
//        Collections.reverse(infos);
//        System.out.println(new Gson().toJson(infos));
        Element ie = doc.getElementById("readerarea");
        Iterator<Element> i = ie.select("img").iterator();
        while (i.hasNext()){
            String imageUrlString = i.next().attr("src");
            System.out.println(imageUrlString);
        }

//        while (ie.hasNext()) {
//            String imageUrlString = ie.next().attr("src");
//            System.out.println(imageUrlString + " ");
//
//            try {
//                HttpURLConnection response = makeImageRequest(url, imageUrlString);
//
//                if (response.getResponseCode() == 200) {
//                    writeToFile(i, response, baseDir);
//                }
//            } catch (IOException e) {
//                // skip file and move to next if unavailable
//                e.printStackTrace();
//                System.out.println("Unable to download file: " + imageUrlString);
//            }
//            i++; // increment image ID whatever the result of the request.
//            Thread.sleep(200l); // prevent yourself from being blocked due to rate limiting
//        }
    }

    private static void writeToFile(int i, HttpURLConnection response, String baseDir) throws IOException {
        // opens input stream from the HTTP connection
        InputStream inputStream = response.getInputStream();
        String baseNum = "00";
        if (i>=10){
            baseNum = "0";
        }else if(i>=100){
            baseNum = "";
        }

        // opens an output stream to save into file
        FileOutputStream outputStream = new FileOutputStream(baseDir + baseNum+ i + ".jpg");

        int bytesRead = -1;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();

        System.out.println("File downloaded");
    }

    private static HttpURLConnection makeImageRequest(String referer, String imageUrlString) throws IOException {
        URL imageUrl = new URL(imageUrlString);
        HttpURLConnection response = (HttpURLConnection) imageUrl.openConnection();

        response.setRequestMethod("GET");
        response.setRequestProperty("referer", referer);

        response.setConnectTimeout(TIMEOUT);
        response.setReadTimeout(TIMEOUT);
        response.connect();
        return response;
    }
}
