package com.example.rxmindapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class APIResponseDeserializer implements JsonDeserializer<APIResponse> {

    @Override
    public APIResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        final Gson gson = new Gson();
        final JsonObject obj = je.getAsJsonObject(); //our original full json string
        final JsonObject JEReplyStatus = obj.getAsJsonObject("replyStatus");

        ReplyStatus RS = new ReplyStatus();
        RS.setSuccess(JEReplyStatus.get("success").getAsBoolean());
        RS.setImageCount(JEReplyStatus.get("imageCount").getAsInt());
        List<NlmRxImage> parsedData = new ArrayList<>();
        if(RS.getImageCount() != 0){
        final JsonArray nlmRxImagesArray = obj.getAsJsonArray("nlmRxImages");

        for (Object object : nlmRxImagesArray) {
            NlmRxImage id = new NlmRxImage();
            JsonElement element = (JsonElement) object;
            JsonObject jsonObject = element.getAsJsonObject();
            id.setName(jsonObject.get("name").getAsString());
            id.setImageUrl(jsonObject.get("imageUrl").getAsString());

            parsedData.add(id);
        }}
        APIResponse item = new APIResponse();
        item.setReplyStatus(RS);
        item.setNlmRxImages(parsedData);
        return item;
    }
}