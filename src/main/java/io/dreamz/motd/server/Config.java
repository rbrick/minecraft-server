package io.dreamz.motd.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter @Setter
public class Config {

    private String versionMessage = "&r&6&lComing Soon™";
    private String motd = "&c&lDreamZ Network is Offline!\n&6sorry :c";
    private String disconnectMessage = "&c&lDreamZ Network is Offline!\n&6sorry :c";




    public static Config read() throws IOException, URISyntaxException {
        File file = new File("config.json");

        if (!file.exists()) {
            URL config = Config.class.getClassLoader().getResource("config.json");
            if (config != null) {
               Files.write( Paths.get("./config.json"), Files.readAllBytes(Paths.get(config.toURI())));
            }
        }

        return Main.GSON.fromJson(new FileReader(file), Config.class);
    }

}
