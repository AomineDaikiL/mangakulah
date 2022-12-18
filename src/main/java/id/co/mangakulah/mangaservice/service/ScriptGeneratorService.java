package id.co.mangakulah.mangaservice.service;

import id.co.mangakulah.mangaservice.dto.request.DownloadBulkImageRequest;
import id.co.mangakulah.mangaservice.dto.request.GenerateDbScriptRequest;
import id.co.mangakulah.mangaservice.dto.request.GenerateDbScriptRequestV2;
import id.co.mangakulah.mangaservice.handler.DbScriptGeneratingHandler;
import org.springframework.stereotype.Service;

@Service
public class ScriptGeneratorService {

    public Boolean generateDbScript(GenerateDbScriptRequest request){
        try {
            DbScriptGeneratingHandler handler = new DbScriptGeneratingHandler();
            handler.generateDbScript(request);
        }catch (Exception e){
            System.out.println("Failed to bulk download image");
            return false;
        }
        return true;
    }
    public Boolean generateDbScriptV2(GenerateDbScriptRequestV2 request){
        try {
            DbScriptGeneratingHandler handler = new DbScriptGeneratingHandler();
            handler.generateDbScriptV2(request);
        }catch (Exception e){
            System.out.println("Failed to bulk download image");
            return false;
        }
        return true;
    }

}
