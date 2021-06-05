package batch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.json.Json;
// import com.google.gson.JsonArray;

import com.google.gson.JsonArray;
import lombok.Builder;
import lombok.Getter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Builder
@Getter
public class AlaramDTO {
    private String to;
    private JSONArray data;
}