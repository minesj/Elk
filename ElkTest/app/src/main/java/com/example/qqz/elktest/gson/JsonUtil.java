package com.example.qqz.elktest.gson;


import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil {

    public static boolean has(JsonObject obj, String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        String[] tokens = path.split("\\.");
        JsonObject curObj = obj;
        for (int i = 0; i < tokens.length; i++) {
            String propertyName = tokens[i];
            JsonElement childElem = curObj.get(propertyName);
            if (childElem != null && childElem.isJsonObject()) {
                curObj = childElem.getAsJsonObject();
            } else {
                return false;
            }
        }
        return true;
    }

    public static JsonElement get(JsonObject obj, String path) {
        if (TextUtils.isEmpty(path)) {
            return obj;
        }
        String[] tokens = path.split("\\.");
        JsonObject curObj = obj;
        for (int i = 0; i < tokens.length; i++) {
            String propertyName = tokens[i];
            JsonElement childElem = curObj.get(propertyName);
            if (i == (tokens.length - 1)) {
                return childElem;
            } else if (childElem != null && childElem.isJsonObject()) {
                curObj = childElem.getAsJsonObject();
            } else {
                break;
            }
        }
        return null;
    }

    public static String getAsString(JsonObject obj, String path) {
        JsonElement elem = get(obj, path);
        return (elem != null && elem.isJsonPrimitive()) ? elem.getAsString() : null;
    }

    public static JsonObject getAsJsonObject(JsonObject obj, String path) {
        JsonElement elem = get(obj, path);
        return (elem != null && elem.isJsonObject()) ? elem.getAsJsonObject() : null;
    }
}
