package id.co.mangakulah.mangaservice.dto.request;

public class DownloadImageRequest {
    private String baseUrlImage;
    private String baseDownloadFileLocation;
    private Integer chapter;
    private Integer startFromImg;
    private Integer imageCount;
    private Integer imgNumDigit;

    public String getBaseUrlImage() {
        return baseUrlImage;
    }

    public void setBaseUrlImage(String baseUrlImage) {
        this.baseUrlImage = baseUrlImage;
    }

    public String getBaseDownloadFileLocation() {
        return baseDownloadFileLocation;
    }

    public void setBaseDownloadFileLocation(String baseDownloadFileLocation) {
        this.baseDownloadFileLocation = baseDownloadFileLocation;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Integer getStartFromImg() {
        return startFromImg;
    }

    public void setStartFromImg(Integer startFromImg) {
        this.startFromImg = startFromImg;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getImgNumDigit() {
        return imgNumDigit;
    }

    public void setImgNumDigit(Integer imgNumDigit) {
        this.imgNumDigit = imgNumDigit;
    }
}
