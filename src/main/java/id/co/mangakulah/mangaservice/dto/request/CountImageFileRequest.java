package id.co.mangakulah.mangaservice.dto.request;

public class CountImageFileRequest {
    private String folderMangaName;
    private boolean isSortedByLastModified;

    public String getFolderMangaName() {
        return folderMangaName;
    }

    public void setFolderMangaName(String folderMangaName) {
        this.folderMangaName = folderMangaName;
    }

    public boolean isSortedByLastModified() {
        return isSortedByLastModified;
    }

    public void setSortedByLastModified(boolean sortedByLastModified) {
        isSortedByLastModified = sortedByLastModified;
    }
}
