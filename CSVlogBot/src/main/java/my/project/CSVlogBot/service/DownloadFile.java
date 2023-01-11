package my.project.CSVlogBot.service;


import my.project.CSVlogBot.config.BotConfig;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;

@Component
public class DownloadFile {
    final BotConfig config;

    public DownloadFile(BotConfig config) {
        this.config = config;
    }

    public void GetFile(String file_id) throws IOException, ParseException {

        URL url = new URL("https://api.telegram.org/bot"+config.getToken()+"/getFile?file_id="+file_id);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String res = in.readLine();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(res);
        JSONObject jsonObj = (JSONObject) obj;
        JSONObject path = (JSONObject) jsonObj.get("result");
        String file_path = path.get("file_path").toString();

        File localfile = new File(config.getSrc() + config.getFilename());
        InputStream is = new URL("https://api.telegram.org/file/bot"+config.getToken()+"/"+file_path).openStream();
        FileUtils.copyInputStreamToFile(is, localfile);

        in.close();
        is.close();
    }
}
