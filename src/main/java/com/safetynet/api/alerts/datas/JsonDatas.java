package com.safetynet.api.alerts.datas;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

@Component
public class JsonDatas {
	private final String fileJsonPath = "./datas/data.json";
	private JsonObject fileCache;
	
	public JsonDatas() throws FileNotFoundException {
		JsonReader reader = new JsonReader(new FileReader(fileJsonPath));
		fileCache = JsonParser.parseReader(reader).getAsJsonObject();
	}

	public JsonObject getFileCache() {
		return fileCache;
	}

	public void setFileCache(JsonObject fileCache) {
		this.fileCache = fileCache;
	}
		
	

	
	
	
	
	
}
