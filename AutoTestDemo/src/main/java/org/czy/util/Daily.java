package org.czy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Daily {
	
	private static String[] time = {"00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00",
							"05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00",
							"10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00",
							"15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00",
							"20:30","21:00","21:30","22:00","22:30","23:00","23:30","24:00"};
	
	public static String getCurrentDate(){
		String CurrentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return CurrentDate;
	}
	
	public static String getCurrentDateMin(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分ss");//初始化Formatter的转换格式。
		String hms = formatter.format(time);
		return hms;
	}
	
	public static String formatYYMMDD(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	
	public static String[] getTime() {
		return time;
	}

	public static String formatHMS(long time){
		Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = time / dd;
        Long hour = (time - day * dd) / hh;
        Long minute = (time) / mi;
        Long second = (time - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = time - day * dd - hour * hh - minute * mi - second * ss;
        
        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
		
	}
	
	public static void main(String[] args) {
		long startTime=System.currentTimeMillis();   //获取开始时间
		try {
			Thread.sleep(444864);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTime=System.currentTimeMillis(); //获取结束时间

		System.out.println(formatHMS(endTime-startTime));
//		String regex1 = "(/([a-zA-Z])*|([a-zA-Z]|\\[[0-9]\\])*)*$";
//		String regex2 = ".//]([a-zA-Z]|/)*$"; 
//		Pattern pattern1 = Pattern.compile(regex1);
//		System.out.println(pattern1.matcher("/html/body/div[2]/div[5]/div[1]/div[3]/div[1]/div[1]").matches());
//		Pattern pattern2 = Pattern.compile(regex2);
//		System.out.println(pattern2.matcher(".//*[@id='J_NormalLogin']/div[1]/input[1]").matches());
	}
}
