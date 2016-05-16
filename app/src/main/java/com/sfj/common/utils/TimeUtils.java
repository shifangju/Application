package com.sfj.common.utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/10.
 */
public class TimeUtils {

    /**
     *
     * @return 20160101
     */
    public static String getYmd(){
        Calendar c = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(c.get(Calendar.YEAR));
        int month = c.get(Calendar.MONTH)+1;
        sb.append(month>=10?month:("0"+month));
        int day = c.get(Calendar.DAY_OF_MONTH);
        sb.append(day>=10?day:("0"+day));
        return sb.toString();
    }
}
