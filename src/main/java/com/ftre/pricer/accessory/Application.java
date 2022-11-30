package com.ftre.pricer.accessory;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Map> generateHisVol(@RequestParam("dataList") List<Map> dataList, @RequestParam("matList") List<String> matList,
			@RequestParam("asofDate") String asOfDate, @RequestParam("cutoffDate") String cutoffDate,
			@RequestParam("assetType") String assetType, @RequestParam("name") String name) {
		GenerateHisVol processor = new GenerateHisVol();
		try {
			return processor.execute(dataList, matList, asOfDate, cutoffDate, assetType, name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
