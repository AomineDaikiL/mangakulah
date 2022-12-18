package id.co.mangakulah.mangaservice.contoller;

import id.co.mangakulah.mangaservice.dto.request.DownloadBulkImageRequest;
import id.co.mangakulah.mangaservice.dto.request.GenerateDbScriptRequest;
import id.co.mangakulah.mangaservice.dto.request.GenerateDbScriptRequestV2;
import id.co.mangakulah.mangaservice.service.ScriptGeneratorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "mangakulah/scriptgenerator/")
public class ScriptGeneratorController {

    @Autowired
    ScriptGeneratorService scriptGeneratorService;

    @ApiOperation("Generate DB Script to Upload Manga's Chapter")
    @PostMapping(value = "generatedbscript", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean generateDbScript(@RequestBody GenerateDbScriptRequest request){
        return scriptGeneratorService.generateDbScript(request);
    }

    @ApiOperation("Generate DB Script to Upload Manga's Chapter V2")
    @PostMapping(value = "generatedbscriptV2", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Boolean generateDbScriptV2(@RequestBody GenerateDbScriptRequestV2 request){
        return scriptGeneratorService.generateDbScriptV2(request);
    }

}

