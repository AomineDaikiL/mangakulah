package id.co.mangakulah.mangaservice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class ImageScrappingTest {
    private static int TIMEOUT = 30000;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String... args) throws InterruptedException, IOException {
        String baseDir = "D:/Images/images/";
        String url = "https://komikcast.site/chapter/overgeared-chapter-157-bahasa-indonesia/";
        Document doc = Jsoup.connect(url).get();
        Iterator<Element> ie = doc.select("img").iterator();
        int i = 1;

        while (ie.hasNext()) {
            String imageUrlString = ie.next().attr("src");
            System.out.println(imageUrlString + " ");

            try {
                HttpURLConnection response = makeImageRequest(url, imageUrlString);

                if (response.getResponseCode() == 200) {
                    writeToFile(i, response, baseDir);
                }
            } catch (IOException e) {
                // skip file and move to next if unavailable
                e.printStackTrace();
                System.out.println("Unable to download file: " + imageUrlString);
            }
            i++; // increment image ID whatever the result of the request.
            Thread.sleep(200l); // prevent yourself from being blocked due to rate limiting
        }
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
