package id.co.mangakulah.mangaservice.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import id.co.mangakulah.mangaservice.constant.ImageConstant;
import id.co.mangakulah.mangaservice.dto.*;
import id.co.mangakulah.mangaservice.dto.request.*;
import id.co.mangakulah.mangaservice.handler.ImageFileRenamingHandler;
import id.co.mangakulah.mangaservice.handler.ImageScrapingHandler;
import id.co.mangakulah.mangaservice.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ImageService {

    public Boolean bulkDownloadImage(DownloadBulkImageRequest request){
        try {
            String baseDownloadFileLocation = request.getBaseDownloadFileLocation();
            String baseUrlImage = request.getBaseUrlImage();
            Integer chapter = request.getStartFromChapter();
            downloadImageBulkV2(baseUrlImage, baseDownloadFileLocation, chapter);

        }catch (Exception e){
            System.out.println("Failed to bulk download image");
            return false;
        }
        return true;
    }

    public Boolean downloadImage(DownloadImageRequest request){
        try {
            String baseDownloadFileLocation = request.getBaseDownloadFileLocation();
            String baseUrlImage = request.getBaseUrlImage();
            Integer chapter = request.getChapter();
            Integer startFromImg = request.getStartFromImg();
            Integer imgCount = request.getImageCount();
            Integer imgNumDigit = request.getImgNumDigit();

            downloadImage(baseUrlImage, baseDownloadFileLocation, chapter, startFromImg, imgCount, imgNumDigit);

        }catch (Exception e){
            System.out.println("Failed to download image");
            return false;
        }
        return true;
    }

    public String generateRequestPayload(GenerateRqPayloadRequest request){
        try {
            ScrapingImageRequest rq = new ScrapingImageRequest();
            List<ImageInfoDto> dtoList = new ArrayList<>();
            String directory = request.getDownloadPath();
            String baseUrl = request.getBaseUrl();
            String endPoint = request.getEndPointUrl();
            Integer digit = request.getChapterDigit();
            String pattern = "%02d";
            if (digit == 3){
                pattern = "%03d";
            }

            for(int i= request.getStartFromChap(); i<= request.getEndOfChap(); i++){
                ImageInfoDto dto = new ImageInfoDto();
                dto.setChapter(i);
                dto.setImageUrl(baseUrl+String.format(pattern, i)+endPoint);
                dtoList.add(dto);
            }
            rq.setDirPathDownload(directory);
            rq.setImageInfos(dtoList);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJsonTree(rq));
            return gson.toJsonTree(rq).toString();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to generateRequestPayload");
        }
        return null;
    }

    public Boolean scrapingImage(ScrapingImageRequest request){
        try {
            String baseDir = request.getDirPathDownload();
            List<ImageInfoDto> infos = request.getImageInfos();
            ImageScrapingHandler scrapingHandler = new ImageScrapingHandler();
            List<ImageCounterDto> imgCountList = new ArrayList<>();
            infos.forEach(i -> {
                try {
                    String dir = baseDir+String.format("%03d", i.getChapter());
                    createdDirectory(dir);
                    int count = scrapingHandler.scrapingImage(dir, i.getImageUrl());
                    ImageCounterDto imgCount = new ImageCounterDto();
                    imgCount.setChapterName("chapter-"+i.getChapter());
                    imgCount.setImgCount(count-1);
                    imgCountList.add(imgCount);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println("InterruptedException happen !!!");
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("IOException happen !!!");
                }
            });

            String fileName = "image_counter";
            String fileNameForScript = "image_counter_split_comma";
            int i = 1;
            while (scrapingHandler.isFileExist(baseDir, i, fileName)){
                i++;
            }
            String path = baseDir+fileName+"_"+i+".txt";

            System.out.println("Final path for ImageCounter -> "+path);
            FileWriter myWriter = new FileWriter(path);
            AtomicReference<String> contentForScript = new AtomicReference<>("");
            imgCountList.forEach(j -> {
                try {
                    myWriter.write(j.getChapterName() +"="+j.getImgCount()+System.lineSeparator());
                    contentForScript.set(contentForScript.get() + j.getImgCount() + ",");
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            });
            myWriter.close();
            FileUtil.writeToFile(baseDir, fileNameForScript+"_"+i+".txt", contentForScript.get());


        }catch (Exception e){
            return false;
        }
        return true;
    }


    public Boolean renamingImageFile(RenamingImageFileRequest request){
        try {
            List<String> directoryPaths = request.getDirectoryPath();
            ImageFileRenamingHandler handler = new ImageFileRenamingHandler();
            directoryPaths.forEach(i -> {
                handler.renamingFile(i);
            });

        }catch (Exception e){
            return false;
        }
        return true;
    }


    public Boolean generateChapterScript(GenerateChapterScriptRequest request){
        try {
            String comicName = request.getComicName();
            String chapter = request.getChapter();
            String dir = ImageConstant.CHAPTER_SCRIPT_BASE_PATH_DIR;
            Integer size = request.getImageCount();
            Integer digit = request.getImageNameDigit();

            createdDirAndFile(dir, comicName);
            String a = ImageConstant.IMG_BASE_PATTERN+comicName.charAt(0)+"/"+comicName+"/"+String.format("%03d", Integer.valueOf(chapter))+"/";
            String b = ImageConstant.IMG_PATTERN_OTHER_CHAPTER;
            //String c = ImageConstant.IMG_PATTERN_FIRST_CHAPTER;


            String path = dir+comicName+".txt";
            Integer startIdx = 1;
            String pattern = "%03d";
            if (digit < 3){
                pattern = "%02d";
            }
            FileWriter myWriter = new FileWriter(path);
            for (int i = startIdx; i<=size; i++){
                myWriter.write(a+String.format(pattern, i)+b+System.lineSeparator());
            }
            myWriter.close();
            System.out.println("File has been created = "+path);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to generate chapter script");
            return false;
        }
        return true;
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
        }
    }

    private void downloadImageBulkV2(String baseUrl, String baseDownloadFileLocation, Integer chapter) throws Exception {
        List<InputStream> isList = new ArrayList<>();
        Map<Integer, String> mapFileLocation = new HashMap<>();
        getImageByUrl(baseUrl, baseDownloadFileLocation, isList, chapter, mapFileLocation, 0);

    }


    private void getImageByUrl(String baseUrl, String baseDownloadFileLocation, List<InputStream> streamList, int chapter,
                               Map<Integer, String> mapFileLocation,  Integer exceptionCounter) throws Exception {
        String baseNumImg = "00";
        if (streamList.size()>=10 && streamList.size() < 100){
            baseNumImg = "0";
        }else if(streamList.size() >= 100 && streamList.size() < 1000){
            baseNumImg = "";
        }
        String baseNumChap = "00";
        if (chapter >= 10 && chapter < 100){
            baseNumChap = "0";
        }else if(chapter >= 100 && chapter < 10000){
            baseNumChap = "";
        }
        int index = streamList.size();
        if (index == 0){
            //index++;
        }

        String endPath = baseNumChap+chapter+"/"+baseNumImg+ index+".png";
        String url = baseUrl + endPath;
        String downloadFileLocation = baseDownloadFileLocation + endPath;
        URL u = new URL(url);
        System.out.println("Starting download image from "+u.toURI().toString());
        InputStream is = null;
        try {
            is = u.openStream();
        } catch (IOException e) {
            try {
                u = new URL(u.toURI().toString().replaceAll("png", "jpg"));
                downloadFileLocation = downloadFileLocation.replaceAll("png", "jpg");
                is = u.openStream();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ioException) {
                try{
                    baseNumImg = "0";
                    if (streamList.size()>=10 && streamList.size() < 1000){
                        baseNumImg = "";
                    }
                    endPath = baseNumChap+chapter+"/"+baseNumImg+ index+".png";
                    url = baseUrl + endPath;
                    downloadFileLocation = baseDownloadFileLocation + endPath;
                    u = new URL(url);
                    is = u.openStream();
                }catch (IOException io2){
                    try {
                        u = new URL(u.toURI().toString().replaceAll("png", "jpg"));
                        downloadFileLocation = downloadFileLocation.replaceAll("png", "jpg");
                        is = u.openStream();
                    }catch (IOException io3){
                        createdDirectory(baseDownloadFileLocation+baseNumChap+chapter+"/");
                        if (streamList.isEmpty()){
                            System.out.println("Failed to download all image for chapter "+chapter);
                            exceptionCounter ++;
                        }else {
                            System.out.println("Successfully downloaded all image for chapter "+chapter);
                            uploadImgToDirectory(streamList, mapFileLocation);
                            System.out.println("Successfully uploaded all image for chapter"+chapter);
                        }
                        if (exceptionCounter >= 3){
                            System.out.println("There some exception, proses has been stopped");
                            throw new Exception("There some exception, proses has been stopped");
                        }
                        System.out.println("Going to download image for next chapter ...");
                        chapter = chapter + 1;
                        mapFileLocation = new HashMap<>();
                        streamList = new ArrayList<>();
                        getImageByUrl(baseUrl, baseDownloadFileLocation, streamList, chapter, mapFileLocation, exceptionCounter);
                    }
                }
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
        }
        System.out.println("Successfully download image from "+u.toURI().toString());
        streamList.add(is);
        //System.out.println("Current Size StreamList = "+streamList.size());
        try{
            downloadFileLocation = downloadFileLocation.replaceAll("png", "jpg");
        }catch (Exception e){}
        mapFileLocation.put(streamList.size(), downloadFileLocation);
        getImageByUrl(baseUrl, baseDownloadFileLocation, streamList, chapter, mapFileLocation, 0);

    }

    private void uploadImgToDirectory(List<InputStream> inputStreamList, Map<Integer, String> mapFileLocation){
        //Write image to file using FileOutputStream
        for (InputStream is : inputStreamList){
            String downloadFileLocation = mapFileLocation.get(inputStreamList.indexOf(is)+1);
            OutputStream fos = null;
            try {
                fos = new FileOutputStream(downloadFileLocation);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int ch;
            while (true) {
                try {
                    if (!((ch = is.read()) != -1)) break;
                    fos.write(ch);
                } catch (IOException e) {
                    e.printStackTrace();
                } //read till end of file
            }

            System.out.println("Image from specified URL has been downloaded at "
                    +downloadFileLocation);
            if (inputStreamList.indexOf(is) == inputStreamList.size()-1){
                try {
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void createdDirectory(String baseDownloadFileLocation){
        System.out.println("Starting create directory, path = "+baseDownloadFileLocation);
        File file = new File(baseDownloadFileLocation);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    private void downloadImage(String baseUrl, String baseDownloadFileLocation, Integer chapter, Integer startFromImg,
                               Integer imageCount, Integer imgNumDigit) throws MalformedURLException, URISyntaxException {

        String baseChapNum = "00";
        if (chapter >= 10 && chapter < 100){
            baseChapNum = "0";
        }else if(chapter >= 100){
            baseChapNum = "";
        }
        createdDirectory(baseDownloadFileLocation+baseChapNum+chapter+"/");


        String baseImgNum = "00";
        if (imgNumDigit == 2){
            baseImgNum = "0";
        }else if(imgNumDigit == 1){
            baseImgNum = "";
        }
        for (int i = startFromImg; i<startFromImg+imageCount; i++) {
            if (i >= 10 && i < 100) {
                baseImgNum = "0";
                if (imgNumDigit < 3){
                    baseImgNum = "";
                }
            }
            //String endPath = baseChapNum + chapter + "f/" + baseImgNum + i + ".png";
            String endPath = baseChapNum + chapter + "/" + baseImgNum + i + ".png";
            String downloadFileLocation = baseDownloadFileLocation + endPath;
            //String downloadFileLocation = "D:/Images/Manga/OnePunchMan/OP01.png";
            String url = baseUrl + endPath;
            URL u = new URL(url);

            //Read image from specified URL using InputStream
            InputStream is = null;
            try {
                is = u.openStream();
            } catch (IOException e) {
                try {
                    u = new URL(u.toURI().toString().replaceAll("png", "jpg"));
                    downloadFileLocation = downloadFileLocation.replaceAll("png", "jpg");
                    is = u.openStream();
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    System.out.println("Failed to download image with URL "+u.toURI().toString());
                    System.out.println();
                    continue;
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
            System.out.println("Successfully download image from " + u.toURI().toString());
            //Write image to file using FileOutputStream
            OutputStream fos = null;
            try {
                fos = new FileOutputStream(downloadFileLocation);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int ch;
            while (true) {
                try {
                    if (!((ch = is.read()) != -1)) break;
                    fos.write(ch);
                } catch (IOException e) {
                    e.printStackTrace();
                } //read till end of file
            }

            System.out.println("Image from specified URL has been downloaded at "
                    + downloadFileLocation);
            System.out.println();
            if (i == startFromImg + imageCount - 1) {
                try {
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Congrats, all images have been downloaded.");

    }


    private void downloadImageBulk(List<URL> urlList, String baseDownloadFileLocation){
        AtomicReference<String> baseNum = new AtomicReference<>("00");
        createdDirectory(baseDownloadFileLocation);
        urlList.forEach(i -> {
            int idx = urlList.indexOf(i)+1;
            if (idx >= 10 && idx < 100) baseNum.set("0");
            String downloadFileLocation = baseDownloadFileLocation +baseNum.get().toString()+ idx+".png";
            //String downloadFileLocation = "D:/Images/Manga/OnePunchMan/OP01.png";

            //Read image from specified URL using InputStream
            InputStream is = null;
            try {
                is = i.openStream();
            } catch (IOException e) {
                try {
                    URL u = new URL(i.toURI().toString().replaceAll("png", "jpg"));
                    downloadFileLocation = downloadFileLocation.replaceAll("png", "jpg");
                    is = u.openStream();
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
            System.out.println("Successfully download image from "+downloadFileLocation);
            //Write image to file using FileOutputStream
            OutputStream fos = null;
            try {
                fos = new FileOutputStream(downloadFileLocation);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int ch;
            while (true) {
                try {
                    if (!((ch = is.read()) != -1)) break;
                    fos.write(ch);
                } catch (IOException e) {
                    e.printStackTrace();
                } //read till end of file
            }

            System.out.println("Image from specified URL has been downloaded at "
                    +downloadFileLocation);
            System.out.println();
            if (urlList.indexOf(i) == urlList.size()-1){
                try {
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Congrats, all images have been downloaded.");

    }
}
