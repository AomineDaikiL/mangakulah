package id.co.mangakulah.mangaservice.contoller;

import com.google.gson.JsonElement;
import id.co.mangakulah.mangaservice.dto.request.*;
import id.co.mangakulah.mangaservice.service.ImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "mangakulah/image/")
public class ImageController {

    @Autowired
    ImageService imageService;

    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,  paramType = "header", example = "Bearer access_token")
    })*/
    @ApiOperation("Bulk Download Manga's Image By URL")
    @PostMapping(value = "bulkdownload", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean downloadBulkImage(@RequestBody DownloadBulkImageRequest request){
        return imageService.bulkDownloadImage(request);
    }

    @ApiOperation("Download Manga's Image By URL")
    @PostMapping(value = "download", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean downloadSpecifiedImage(@RequestBody DownloadImageRequest request){
        return imageService.downloadImage(request);
    }

    @ApiOperation("Generate Request Payload for Image Scraping")
    @PostMapping(value = "generaterequestpayload", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String generateRequestPayload(@RequestBody GenerateRqPayloadRequest request){
        return imageService.generateRequestPayload(request);
    }

    @ApiOperation("Generate Image's Chapter Script")
    @PostMapping(value = "generatescript", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean generateScriptChapter(@RequestBody GenerateChapterScriptRequest request){
        return imageService.generateChapterScript(request);
    }

    @ApiOperation("Image Scraping From Site Page by URL")
    @PostMapping(value = "scraping", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean scrapingImage(@RequestBody ScrapingImageRequest request){
        return imageService.scrapingImage(request);
    }

    @ApiOperation("Image Scraping From Site Page by URL V2")
    @PostMapping(value = "scrapingV2", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean scrapingImageV2(@RequestBody ScrapingImageRequestV2 request){
        return imageService.scrapingImageV2(request);
    }

    @ApiOperation("Renaming Image File ASC")
    @PostMapping(value = "renamefile", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean renamingImageFile(@RequestBody RenamingImageFileRequest request){
        return imageService.renamingImageFile(request);
    }

}
