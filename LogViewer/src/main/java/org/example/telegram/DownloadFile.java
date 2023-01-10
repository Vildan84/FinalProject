package org.example.telegram;



import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;


public class DownloadFile {

    public void GetFile(String file_id) throws IOException, ParseException {

        MyBot bot = new MyBot();
        String src = "/home/vildan/IdeaProjects/FinalProject/LogViewer/src/main/resources/";
        String filename = "test.csv";

        URL url = new URL("https://api.telegram.org/bot"+bot.getBotToken()+"/getFile?file_id="+file_id);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String res = in.readLine();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(res);
        JSONObject jsonObj = (JSONObject) obj;
        JSONObject path = (JSONObject) jsonObj.get("result");
        String file_path = path.get("file_path").toString();

        File localfile = new File(src + filename);
        InputStream is = new URL("https://api.telegram.org/file/bot"+bot.getBotToken()+"/"+file_path).openStream();
        FileUtils.copyInputStreamToFile(is, localfile);

        in.close();
        is.close();


    }

}
