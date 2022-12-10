package id.co.mangakulah.mangaservice.dto.request;

public class GenerateDbScriptRequest {
    private String directoryPath;
    private String imageLocation;
    private String fileName;
    private String mangaName;
    Integer idTermManga;
    Integer startFromChapter;
    String imgCount;
    Integer startIdScript;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public Integer getIdTermManga() {
        return idTermManga;
    }

    public void setIdTermManga(Integer idTermManga) {
        this.idTermManga = idTermManga;
    }

    public Integer getStartFromChapter() {
        return startFromChapter;
    }

    public void setStartFromChapter(Integer startFromChapter) {
        this.startFromChapter = startFromChapter;
    }

    public String getImgCount() {
        return imgCount;
    }

    public void setImgCount(String imgCount) {
        this.imgCount = imgCount;
    }

    public Integer getStartIdScript() {
        return startIdScript;
    }

    public void setStartIdScript(Integer startIdScript) {
        this.startIdScript = startIdScript;
    }
}
