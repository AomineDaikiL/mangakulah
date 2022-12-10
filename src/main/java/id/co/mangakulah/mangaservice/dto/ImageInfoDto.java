package id.co.mangakulah.mangaservice.dto;

public class ImageInfoDto {
    private String imageUrl;
    private Integer chapter;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }
}
