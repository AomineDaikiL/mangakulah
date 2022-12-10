package id.co.mangakulah.mangaservice.dto.request;

import id.co.mangakulah.mangaservice.dto.ImageInfoDto;

import java.util.List;

public class ScrapingImageRequest {
    private List<ImageInfoDto> imageInfos;
    private String dirPathDownload;

    public List<ImageInfoDto> getImageInfos() {
        return imageInfos;
    }

    public void setImageInfos(List<ImageInfoDto> imageInfos) {
        this.imageInfos = imageInfos;
    }

    public String getDirPathDownload() {
        return dirPathDownload;
    }

    public void setDirPathDownload(String dirPathDownload) {
        this.dirPathDownload = dirPathDownload;
    }
}
