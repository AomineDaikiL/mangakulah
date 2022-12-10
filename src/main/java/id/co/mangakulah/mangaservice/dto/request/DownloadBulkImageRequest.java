package id.co.mangakulah.mangaservice.dto.request;

import java.util.List;

public class DownloadBulkImageRequest {
    //private List<String> urlList;
    private String baseUrlImage;
    private Integer startFromChapter;
    private String baseDownloadFileLocation;

    /*public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }*/

    public String getBaseDownloadFileLocation() {
        return baseDownloadFileLocation;
    }

    public void setBaseDownloadFileLocation(String baseDownloadFileLocation) {
        this.baseDownloadFileLocation = baseDownloadFileLocation;
    }

    public String getBaseUrlImage() {
        return baseUrlImage;
    }

    public void setBaseUrlImage(String baseUrlImage) {
        this.baseUrlImage = baseUrlImage;
    }

    public Integer getStartFromChapter() {
        return startFromChapter;
    }

    public void setStartFromChapter(Integer startFromChapter) {
        this.startFromChapter = startFromChapter;
    }
}
