package com.ftre.processor;

import java.util.List;
import java.util.Map;

import com.ftre.utils.DateUtil;
import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

public class VariantProcessor {

DateUtil du = new DateUtil();
	
	public Variant generateFixingData(List<Map> dataList, String assetType, String name) {
		Variant var = new Variant();
		if(dataList != null && dataList.size() > 0) {
			
			String[][] fixingData = new String[dataList.size() + 2][2];
			fixingData[0][0] = "MKT";
			fixingData[0][1] = assetType;
			fixingData[1][0] = "Dates";
			fixingData[1][1] = name;
			for(int i = 0; i < dataList.size(); i++) {
				Map dataMap = dataList.get(i);
				String value = (String) dataMap.get("value");
				String dateString = (String) dataMap.get("date");
				String dateNumber = du.date2ExcelNumber(dateString).toString();
				fixingData[i+2][0] = dateNumber;
				fixingData[i+2][1] = value;
			}
			
			SafeArray sa = new SafeArray(Variant.VariantVariant, fixingData.length, 2);
			for(int i = 0; i < fixingData.length; i++) {
				for(int j = 0; j < 2; j++) {
					sa.setString(i, j, fixingData[i][j]);
				}
			}
			var.putSafeArray(sa);
		}
		return var;
	}
	
	public Variant generateMaturities(List<String> maturities) {
		Variant var = new Variant();
		if(maturities != null && maturities.size() > 0) {
			
			String[] matArray = new String[maturities.size()];
			for(int i = 0; i < maturities.size(); i++) {
				matArray[i] = maturities.get(i);
			}
			
			SafeArray sa = new SafeArray(Variant.VariantVariant, matArray.length);
			for(int i = 0; i < matArray.length; i++) {
				sa.setString(i, matArray[i]);
			}
			var.putSafeArray(sa);
		}
		return var;
	}
	
}
