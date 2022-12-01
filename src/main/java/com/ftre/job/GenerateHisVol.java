package com.ftre.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ftre.processor.VariantProcessor;
import com.ftre.utils.DateUtil;
import com.ftre.utils.PropertiesReader;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenerateHisVol {

	DateUtil du = new DateUtil();
	VariantProcessor vp = new VariantProcessor();
	Properties prop = PropertiesReader.getProperties();

	public List<Map> execute(List<Map> dataList, List<String> matList, String asOfDate, String cutoffDate,
			String assetType, String name) {
		List<Map> resultList = new ArrayList<Map>();

		try {
			String jacob_file_path = prop.getProperty("jacob.dll.file.path");
			String jacob_file_name = prop.getProperty("jacob.dll.file.name");
			String lib_file = jacob_file_path + jacob_file_name;

			System.setProperty(LibraryLoader.JACOB_DLL_PATH, lib_file);
			LibraryLoader.loadJacobLibrary();
			log.info("JACOB libray is loaded and ready to use...");

			ActiveXComponent pilCom = new ActiveXComponent("Com_pil.PILModule");
			log.info("The library has been loaded, and an activeX component has been created...");

			Variant asOfDateVar = new Variant(du.date2ExcelNumber(asOfDate));
			pilCom.invoke("Date_SetAsOfDate", asOfDateVar);
			pilCom.invoke("Global_FreeAllObjects");

			Variant fdArrayVar = vp.generateFixingData(dataList, assetType, name);
			Variant fixingManager = pilCom.invoke("Generic_MktData_FixingManager_Create", fdArrayVar, new Variant(name),
					new Variant(1));
			log.info("FixingManager created : " + fixingManager);

			if (cutoffDate == null)
				cutoffDate = asOfDate;
			Variant matArrayVar = vp.generateMaturities(matList);
			Variant his_vols = pilCom.invoke("Generic_MktData_HistoricalVolatility_FromFixingManager", fixingManager,
					new Variant(name), matArrayVar, new Variant("HistoVol"), new Variant(), new Variant(),
					new Variant(), new Variant(), new Variant(), new Variant(),
					new Variant(du.date2ExcelNumber(cutoffDate)), new Variant(1));

			if (his_vols.toSafeArray() != null && his_vols.toSafeArray().getUBound() > 0) {
				for (int i = 1; i <= his_vols.toSafeArray().getUBound(); i++) {
					// resultList.add(his_vols.toSafeArray().getDouble(i, 1));
					Map<String, String> map = new HashMap<String, String>();
					map.put("tenor", matList.get(i - 1));
					map.put("vol", String.valueOf(his_vols.toSafeArray().getDouble(i, 1)));
					resultList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

}
