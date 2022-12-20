package id.co.mangakulah.mangaservice;

import com.google.gson.Gson;
import id.co.mangakulah.mangaservice.dto.ImageCounterDto;
import id.co.mangakulah.mangaservice.manager.ImageScrapingManager;
import id.co.mangakulah.mangaservice.util.FileUtil;
import id.co.mangakulah.mangaservice.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestFormatTime {
    public static void main(String[] args) throws IOException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        //System.out.println(simpleDateFormat.format(new Date()));

        String baseDir = "D:/Images/Mangas/my-wife-is-a-demon-queen/";
        File directoryPath = new File(baseDir);
        //System.out.println(directoryPath.listFiles().length);
        File[] files = directoryPath.listFiles();
        //Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        List<ImageCounterDto> imgCountList = new ArrayList<>();
        for(int i=0; i<files.length; i++) {
            ImageCounterDto dto = new ImageCounterDto();
            dto.setChapterName(StringUtil.formatChapterNumber(files[i].getName()));
            File file = new File(baseDir+files[i].getName());
            if (file.isFile()) continue;
            dto.setImgCount(file.listFiles().length);
            imgCountList.add(dto);
        }

        ImageScrapingManager scrapingHandler = new ImageScrapingManager();
        String fileName = "image_counter";
        int i = 1;
        while (scrapingHandler.isFileExist(baseDir, i, fileName)){
            i++;
        }
        String path = baseDir+fileName+"_"+i+".txt";

        System.out.println("Final path for ImageCounter -> "+path);
        FileUtil.writeToFile(baseDir, fileName+"_"+i+".txt", new Gson().toJson(imgCountList));

    }
}
