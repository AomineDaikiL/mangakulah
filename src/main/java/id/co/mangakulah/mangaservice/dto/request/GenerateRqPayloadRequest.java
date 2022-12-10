package id.co.mangakulah.mangaservice.dto.request;

public class GenerateRqPayloadRequest {
    private String downloadPath;
    private String baseUrl;
    private String endPointUrl;
    private Integer startFromChap;
    private Integer endOfChap;
    private Integer chapterDigit;

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndPointUrl() {
        return endPointUrl;
    }

    public void setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
    }

    public Integer getStartFromChap() {
        return startFromChap;
    }

    public void setStartFromChap(Integer startFromChap) {
        this.startFromChap = startFromChap;
    }

    public Integer getEndOfChap() {
        return endOfChap;
    }

    public void setEndOfChap(Integer endOfChap) {
        this.endOfChap = endOfChap;
    }

    public Integer getChapterDigit() {
        return chapterDigit;
    }

    public void setChapterDigit(Integer chapterDigit) {
        this.chapterDigit = chapterDigit;
    }
}
