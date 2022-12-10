package id.co.mangakulah.mangaservice.dto.request;

public class GenerateChapterScriptRequest {
    private String comicName;
    private String chapter;
    private Integer imageCount;
    private Integer imageNameDigit;

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getImageNameDigit() {
        return imageNameDigit;
    }

    public void setImageNameDigit(Integer imageNameDigit) {
        this.imageNameDigit = imageNameDigit;
    }
}
