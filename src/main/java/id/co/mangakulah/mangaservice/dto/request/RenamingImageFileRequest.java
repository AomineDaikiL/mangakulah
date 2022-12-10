package id.co.mangakulah.mangaservice.dto.request;

import java.util.List;

public class RenamingImageFileRequest {
    private List<String> directoryPath;

    public List<String> getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(List<String> directoryPath) {
        this.directoryPath = directoryPath;
    }
}
