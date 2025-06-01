package com.safetynet.api.alerts.datas;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.safetynet.api.alerts.exceptions.JsonDataException;

@Component
public class JsonDatas {
	private final Logger log = LogManager.getLogger();
	
	//private final String fileJsonPath = "./datas/data.json";
	private final String fileJsonPath;
	private JsonObject fileCache;
	
	public JsonDatas(@Value("${json.data.path}") String fileJsonPath)  {
		this.fileJsonPath = fileJsonPath;
		try {
			JsonReader reader = new JsonReader(new FileReader(fileJsonPath));
			fileCache = JsonParser.parseReader(reader).getAsJsonObject();
			log.info("Json file successfully loaded");
			reader.close();
		} catch (IOException e) {
			log.error("Failed to read the data.json file",e);
			throw new JsonDataException("Unable to read the datas");
		}
		
	}

	public JsonObject getFileCache() {
		return fileCache;
	}

	public void setFileCache(JsonObject fileCache) {
		this.fileCache = fileCache;
	}
	
	public void writeJsonToFile() {
		Gson gson = new Gson();
				
		try {
			FileWriter filewriter = new FileWriter(fileJsonPath);
			gson.toJson(fileCache, filewriter);
			log.info("Json file successfully saved");
			filewriter.close();
						
		} catch (IOException e) {
			log.error("Failed to write the data.json file",e);
			throw new JsonDataException("Unable to update the datas");
		}
		
		
		
	}
		
	

	
	
	
	
	
}
