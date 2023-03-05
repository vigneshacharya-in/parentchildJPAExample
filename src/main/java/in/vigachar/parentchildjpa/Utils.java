package in.vigachar.parentchildjpa;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import in.vigachar.parentchildjpa.dtos.PostDTO;
import in.vigachar.parentchildjpa.entity.Post;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static String getTitle(String metadata) {
        JsonObject jsonObject = new JsonParser().parse(metadata).getAsJsonObject();
        return jsonObject.get("title").getAsString();
    }

    public static int getlikes(String metadata) {
        JsonObject jsonObject = new JsonParser().parse(metadata).getAsJsonObject();
        return jsonObject.get("likes").getAsInt();
    }
}
