package org.lf.diary.utils;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @Author: PengZH
 * @Date: 2020/3/8 23:00
 * @Description:
 */

public class DateUtils {

    /**
     * 获取当前时间和前K天时间
     */
    public static List<Date> getDateBeforeKDays(int k)   {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -k);  //设置为前k天
        dBefore = calendar.getTime();   //得到前k天的时间
        List<Date> res = new ArrayList<>();
        res.add(dNow);
        res.add(dBefore);
        return res;
    }
    //日期转字符串
    public static String getDateStr(Date date){
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm  " );
        String dateStr = sdf.format(date);
        return dateStr;
    }

}
