package com.example.mobileshop.person;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public String getNowTime() {
        String timeString = null;
        Time time = new Time();
        //获取现在的时间
        time.setToNow();
        //年
        String year = thanTen(time.year);
        //月
        String month = thanTen(time.month);
        //日期
        String monthDay = thanTen(time.monthDay);
        //小时
        String hour = thanTen(time.hour);
        //分钟
        String minute = thanTen(time.minute);
        timeString = year + "-" + month + "-" + monthDay + "-" + "" + hour + ":" + minute;
        return timeString;
    }

    //10以下前面加个0
    public String thanTen(int str) {
        String string = null;
        if (str < 10) {
            string = "0" + str;
        }else {
            string = "" + str;
        }
        return string;
    }


    //判断是否为闰年
    public boolean judge(int year) {
        boolean yearleap = (year%400==0) || (year%4==0) && (year%100!=0);
        return yearleap;
    }


    //计算各个月的天数
    public int calculate(int year,int month) {
        boolean yearleap = judge(year);
        //天数
        int day;
        if (yearleap && month == 2) {
            day = 29;
        }else if (!yearleap&&month==2) {
            day = 28;
        }else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }else {
            day = 31;
        }
        return day;
    }


    /*
    计算时间差
    startTime   开始时间
    endTime     结束时间
    return  返回时间差
     */
    public String getTimeDifference(String startTime,String endTime) {
        String timeString = "";
        //时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = dateFormat.parse(startTime);
            Date parse1 = dateFormat.parse(endTime);

            //相差
            long diff = parse1.getTime() - parse.getTime();

            //天数
            long day = diff/(24*60*60*1000);
            //小时
            long hour = (diff/(24*60*60*1000) - day*24);
            //分钟
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            //秒
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            //毫秒
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }


    //计算相差的小时
    public String getTimeDifferenceHour(String startTime,String endTime) {
        String timeString ="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = dateFormat.parse(startTime);
            Date parse1 = dateFormat.parse(endTime);
            //相差
            long diff = parse1.getTime() - parse.getTime();

            String string  = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat/(60*60*1000);
            timeString = Float.toString(hour1);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }

    //获取时间中的某一个时间点
    public String getJsonParseShiJian(String str,int type) {
        String shijianString = null;
        //截取
        String nian = str.substring(0,str.indexOf("-"));
        String yue = str.substring(str.indexOf("-") + 1,str.lastIndexOf("-"));
        String tian = str.substring(str.lastIndexOf("-")+1,str.indexOf(" "));
        String shi = str.substring(str.indexOf(" ")+1,str.lastIndexOf(":"));
        String fen = str.substring(str.lastIndexOf(":")+1,str.length());



        //判断
        switch (type) {
            case 1:
                shijianString = nian;
                break;
            case 2:
                shijianString = yue;
                break;
            case 3:
                shijianString = tian;
                break;
            case 4:
                shijianString = shi;
                break;
            case 5:
                shijianString = fen;
                break;
        }
        return shijianString;
    }


    //String变int
    public int strToInt(String str) {
        int value = 0;
        value = Integer.parseInt(str);
        return value;
    }

    //与当前时间比较早晚
    //time     需要比较的时间
    //return  输入的时间比现在晚则返回true

    public boolean compareNowTime(String time) {
        boolean isDayu = false;


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(time);
            //现在的时间
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();

            isDayu = diff <= 0;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return isDayu;
    }


    //把时间戳变yyyy-MM-dd HH:mm格式时间
    public String getDateString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }


    //返回时间戳
    public long dateOne(String time) {
        //时间格式
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        Date date;
        long I = 0;
        try {
            date = sdr.parse(time);
            I = date.getTime();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return I;
    }

    /**
     * 比较两个时间
     *
     * starTime开始时间
     * endString结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public boolean compareTwoTime(String startTime,String  endTime) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = dateFormat.parse(startTime);
            //结束的时间
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            isDayu = diff >= 0;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return isDayu;
    }


    public boolean compareTwoTime2(String startTime,String  endTime) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(startTime);
            //结束的时间
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            isDayu = diff >= 0;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return isDayu;
    }

    //获取年
    public String getTimeYear(String time) {
        //截取
        String substring = time.substring(0,time.lastIndexOf(" "));
        return substring;
    }


    //换算小时，0.5小时-->0小时30分
    private String convertTime(String hour) {
        //截取
        String substring = hour.substring(0,hour.lastIndexOf("."));
        String substring2 = hour.substring(hour.lastIndexOf(".")+1,hour.length());

        //前面加0
        substring2 = "0" + substring2;
        float f2 = Float.parseFloat(substring2);

        f2 = f2*60;
        String string = Float.toString(f2);
        String min = string.substring(0,string.lastIndexOf("."));
        return substring + "小时" + min + "分";
    }
}
