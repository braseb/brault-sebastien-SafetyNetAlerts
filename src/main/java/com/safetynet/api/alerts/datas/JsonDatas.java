package com.safetynet.api.alerts.datas;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

@Component
public class JsonDatas {
	private final Logger log = LogManager.getLogger();
	
	private final String fileJsonPath = "./datas/data.json";
	private JsonObject fileCache;
	
	public JsonDatas() throws IOException, FileNotFoundException {
		JsonReader reader = new JsonReader(new FileReader(fileJsonPath));
		fileCache = JsonParser.parseReader(reader).getAsJsonObject();
		log.info("Fichier json correctement chargé");
		reader.close();
	}

	public JsonObject getFileCache() {
		return fileCache;
	}

	public void setFileCache(JsonObject fileCache) {
		this.fileCache = fileCache;
	}
	
	public boolean writeJsonToFile() {
		Gson gson = new Gson();
		boolean ret = false;
		
		try {
			FileWriter filewriter = new FileWriter(fileJsonPath);
			gson.toJson(fileCache, filewriter);
			log.info("Fichier json correctement enregistré");
			filewriter.close();
			ret = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}
		
	

	
	
	
	
	
}
