package id.co.mangakulah.mangaservice.dto.request;

import id.co.mangakulah.mangaservice.dto.ImageCounterDto;

import java.util.List;

public class GenerateDbScriptRequestV2 {
    private String directoryPath;
    private String imageLocation;
    private String fileName;
    private String mangaName;
    private Integer idTermManga;
    private Integer seriesId;
    private Integer startIdScript;
    private List<ImageCounterDto> imageCounterList;

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

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getStartIdScript() {
        return startIdScript;
    }

    public void setStartIdScript(Integer startIdScript) {
        this.startIdScript = startIdScript;
    }

    public List<ImageCounterDto> getImageCounterList() {
        return imageCounterList;
    }

    public void setImageCounterList(List<ImageCounterDto> imageCounterList) {
        this.imageCounterList = imageCounterList;
    }
}
