package id.co.mangakulah.mangaservice.handler;

import com.google.gson.Gson;
import id.co.mangakulah.mangaservice.dto.ImageInfoDtoV2;
import id.co.mangakulah.mangaservice.dto.request.ScrapingImageRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ImageScrapingHandler {
    private static int TIMEOUT = 30000;
    private static final int BUFFER_SIZE = 4096;

    public int scrapingImage(String baseDir, String url) throws InterruptedException, IOException {
        try{
            connect(url);
        }catch (IOException e){
            System.out.println("trying to connect ...1");
            try{
                connect(url);
            }catch (IOException e1){
                System.out.println("trying to connect ...2");
                try{
                    connect(url);
                }catch (IOException e2){
                    System.out.println("trying to connect ...3");
                    try{
                        connect(url);
                    }catch (IOException e3){
                        System.out.println("trying to connect ...4");
                        try{
                            connect(url);
                        }catch (IOException e4){
                            System.out.println("trying to connect ...5");
                        }
                    }
                }
            }
        }
        Document doc = Jsoup.connect(url).get();
        Iterator<Element> ie = null;
        if (url.contains("komikcast")){
            ie = doc.select("img").iterator();
        }else{
            Element e = doc.getElementById("readerarea");
            ie = e.select("img").iterator();
        }
        int i = 1;

        while (ie.hasNext()) {
            String imageUrlString = ie.next().attr("src");
            System.out.println(imageUrlString + " ");
            //if (!imageUrlString.contains("chapter")) continue; //mangaTale
            if (!imageUrlString.contains("img") && url.contains("komikcast")) continue;

            try {
                HttpURLConnection response = makeImageRequest(url, imageUrlString, 0);

                if (response.getResponseCode() == 200) {
                    writeToFile(i, response, baseDir);
                }
            } catch (IOException e) {
                // skip file and move to next if unavailable
                //e.printStackTrace();
                //String fileName = "image-counter";
                //createdDirAndFile(baseDir, fileName);
                //writeImageCount(baseDir, fileName, chapter, i-1);
                System.out.println("Unable to download file: " + imageUrlString);
            }
            i++; // increment image ID whatever the result of the request.
            Thread.sleep(200l); // prevent yourself from being blocked due to rate limiting
        }
        return i;
    }

    private Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private void writeToFile(int i, HttpURLConnection response, String baseDir) throws IOException {
        // opens input stream from the HTTP connection
        InputStream inputStream = response.getInputStream();

        // opens an output stream to save into file
        FileOutputStream outputStream = new FileOutputStream(baseDir+"/"+ String.format("%03d", i) + ".jpg");

        int bytesRead = -1;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();

        System.out.println("File downloaded");
    }

    private HttpURLConnection makeImageRequest(String referer, String imageUrlString, Integer counter) throws MalformedURLException {
        URL imageUrl = new URL(imageUrlString);
        HttpURLConnection response = null;
        try {
            response = (HttpURLConnection) imageUrl.openConnection();

            response.setRequestMethod("GET");
            response.setRequestProperty("referer", referer);

            response.setConnectTimeout(TIMEOUT);
            response.setReadTimeout(TIMEOUT);
            response.connect();
        }catch (IOException e){
            System.out.println("Failed to download image");
            if(counter < 3){
                counter++;
                makeImageRequest(referer, imageUrlString, counter);
            }
        }
        return response;
    }

    private void createdDirAndFile(String directory, String comicName){
        File file = new File(directory);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
                File myObj = new File(comicName+".txt");
                try {
                    myObj.createNewFile();
                }catch (Exception e){}
            } else {
                System.out.println("Failed to create directory!");
            }
        }else{
            File myObj = new File(comicName+".txt");
            try {
                myObj.createNewFile();
            }catch (Exception e){}
        }
    }

    public void writeImageCount(String dir, String comicName, Integer chapter, Integer imgCount) throws IOException {
        String path = dir+comicName+".txt";
        FileWriter myWriter = new FileWriter(path);
        myWriter.write("chapter-"+chapter+" = "+imgCount+System.lineSeparator());
        myWriter.close();
    }

    public boolean isFileExist(String baseDir, Integer i, String fileName){
        String path = baseDir+fileName+"_"+i+".txt";
        File file = new File(path);
        if (file.exists()){
            return true;
        }
        return false;
    }

    public List<ImageInfoDtoV2> getAllChapterUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Iterator<Element> ie = doc.select("a").iterator();
        List<ImageInfoDtoV2> infos = new ArrayList<>();
        if (url.contains("komikcast")){
            while (ie.hasNext()){
                Element e = ie.next();
                String chapterUrl = e.attr("href");
                if (!chapterUrl.contains("chapter")) continue;
                String chapterName = e.text().replaceAll("Chapter", "").trim();
                ImageInfoDtoV2 dto = new ImageInfoDtoV2();
                dto.setImageUrl(chapterUrl);
                dto.setChapter(chapterName);
                infos.add(dto);
            }
        }else if (url.contains("mangatale")){
            while (ie.hasNext()){
                Element e = ie.next();
                String chapterUrl = e.attr("href");
                if (!chapterUrl.contains("chapter")) continue;
                String chapterName = e.child(0).text();
                if (chapterName.contains("New")) continue;
                chapterName = chapterName.replaceAll("Chapter", "").trim();
                ImageInfoDtoV2 dto = new ImageInfoDtoV2();
                dto.setImageUrl(chapterUrl);
                dto.setChapter(chapterName);
                infos.add(dto);
            }
        }

        Collections.reverse(infos);
        //System.out.println(new Gson().toJson(infos));
        return infos;
    }

}
