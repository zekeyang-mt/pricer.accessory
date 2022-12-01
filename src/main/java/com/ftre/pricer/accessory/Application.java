package com.ftre.pricer.accessory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftre.config.ServerConfig;
import com.ftre.job.GenerateHisVol;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Import({ServerConfig.class})
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/generic_mktData_historicalVolatility_fromFixingManager")
	public List<Map> generateHisVol(@RequestParam("dataList") String jDataList, @RequestParam("matList") String jMatList,
			@RequestParam("asofDate") String asOfDate, @RequestParam("cutoffDate") String cutoffDate,
			@RequestParam("assetType") String assetType, @RequestParam("name") String name) {
		GenerateHisVol processor = new GenerateHisVol();
		ObjectMapper mapper = new ObjectMapper();
		List<Map> dataList = new ArrayList<Map>();
		List<String> matList = new ArrayList<String>();
		
		try {
			dataList = mapper.readValue(jDataList, new TypeReference<List<Map>>(){});
			matList = mapper.readValue(jMatList, new TypeReference<List<String>>(){});
			
			return processor.execute(dataList, matList, asOfDate, cutoffDate, assetType, name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
