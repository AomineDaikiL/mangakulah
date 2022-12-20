package id.co.mangakulah.mangaservice;

import com.google.gson.Gson;
import id.co.mangakulah.mangaservice.constant.ImageConstant;
import id.co.mangakulah.mangaservice.dto.ImageCounterDto;
import id.co.mangakulah.mangaservice.dto.ImageInfoDto;
import id.co.mangakulah.mangaservice.dto.ImageInfoDtoV2;
import id.co.mangakulah.mangaservice.dto.request.ScrapingImageRequest;
import id.co.mangakulah.mangaservice.manager.ImageScrapingManager;
import id.co.mangakulah.mangaservice.service.ImageService;
import id.co.mangakulah.mangaservice.util.FileUtil;
import id.co.mangakulah.mangaservice.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
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

    public static void main(String... args) throws IOException {
        String urlMangaChapter = "https://komikcast.site/komik/strongest-anti-m-e-t-a/";

        String folderName = "strongest-anti-m-e-t-a".replaceAll(" ", "-").toLowerCase();
        String baseDir = ImageConstant.IMAGE_SCRAPING_BASE_PATH_DIR+folderName+"/";
        ImageScrapingManager scrapingHandler = new ImageScrapingManager();
        List<ImageCounterDto> imgCountList = new ArrayList<>();

        List<ImageInfoDtoV2> chapterUrlList = new ImageScrapingManager().getAllChapterUrl(urlMangaChapter);
        System.out.println("COUNT of CHAPTER ::: "+chapterUrlList.size());
        chapterUrlList.forEach(i -> {
            try {
                String dir = baseDir+ StringUtil.formatChapter3Digit(i.getChapter());
                int fileSize = createdDirectoryV2(dir);
                if (fileSize > 0) {
                    System.out.println("Skip download Chapter-"+i.getChapter());
                    return;
                }
                int count = scrapingHandler.scrapingImage(dir, i.getImageUrl());
                ImageCounterDto imgCount = new ImageCounterDto();
                imgCount.setChapterName(StringUtil.formatChapterNumber(i.getChapter()));
                imgCount.setImgCount(count-1);
                imgCountList.add(imgCount);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("InterruptedException happen !!!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOException happen !!!");
            }
        });

        String fileName = ImageConstant.IMAGE_COUNTER_FILE_NAME;
        int i = 1;
        while (scrapingHandler.isFileExist(baseDir, i, fileName)){
            i++;
        }
        String path = baseDir+fileName+"_"+i+".txt";

        System.out.println("Final path for ImageCounter -> "+path);
        FileUtil.writeToFile(baseDir, fileName+"_"+i+".txt", new Gson().toJson(imgCountList));
    }

    private static int createdDirectoryV2(String baseDownloadFileLocation){
        System.out.println("Starting create directory, path = "+baseDownloadFileLocation);
        File file = new File(baseDownloadFileLocation);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }else{
            return file.listFiles().length;
        }
        return 0;
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
