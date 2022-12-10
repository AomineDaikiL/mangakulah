package id.co.mangakulah.mangaservice.dto;

public class ImageCounterDto {
    private String chapterName;
    private Integer imgCount;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getImgCount() {
        return imgCount;
    }

    public void setImgCount(Integer imgCount) {
        this.imgCount = imgCount;
    }
}
