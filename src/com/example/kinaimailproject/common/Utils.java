package com.example.kinaimailproject.common;

import java.util.Calendar;

public class Utils {

	public static  String getStrToday(){

		//カレンダーオブジェクトの取得
		Calendar cal = Calendar.getInstance();

		//intをStiringにできないのはなぜ？
		Long year = (long) cal.get(Calendar.YEAR);
		Long month =  (long)cal.get(Calendar.MONTH);
		Long day =  (long)cal.get(Calendar.DATE);

		String str = year.toString() + "/" + month.toString() + "/" + day.toString();


		return str;

	}

	//isimpty
	public static boolean isEmpty(String str){

		if(str == "" || str == null){
			return true;
		}
		return false;

	}

}
