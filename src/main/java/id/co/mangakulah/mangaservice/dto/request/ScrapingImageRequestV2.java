package id.co.mangakulah.mangaservice.dto.request;

public class ScrapingImageRequestV2 {
    private String folderMangaName;
    private String urlMangaChapter;

    public String getFolderMangaName() {
        return folderMangaName;
    }

    public void setFolderMangaName(String folderMangaName) {
        this.folderMangaName = folderMangaName;
    }

    public String getUrlMangaChapter() {
        return urlMangaChapter;
    }

    public void setUrlMangaChapter(String urlMangaChapter) {
        this.urlMangaChapter = urlMangaChapter;
    }
}
