package kr.susemi99.sqlitedblistview;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateFormatter {
  public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
  public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

  /**
   * 날짜/시간 변환
   *
   * @param calendar Calendar
   * @param pattern  yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static String format(Calendar calendar, String pattern) {
    return format(calendar.getTimeInMillis(), pattern);
  }


  /**
   * 날짜/시간 변환
   *
   * @param timeMillis millisecond
   * @param pattern    yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static String format(long timeMillis, String pattern) {
    return new SimpleDateFormat(pattern, Locale.getDefault()).format(timeMillis);
  }


  /**
   * 현재 날짜/시간 변환
   *
   * @param pattern yyyy-MM-dd'T'HH:mm:ssZ
   * @return 2016-12-31T13:24:45.678
   */
  public static String format(String pattern) {
    return new SimpleDateFormat(pattern, Locale.getDefault()).format(System.currentTimeMillis());
  }

  /**
   * 로그 표시용 변환
   *
   * @param timeInMills millisecond
   * @return 2016-12-31T13:24:45.678
   */
  public static String format(long timeInMills) {
    return new SimpleDateFormat(ISO8601, Locale.getDefault()).format(timeInMills);
  }

  /**
   * Date String 을 밀리세컨드로 변환
   *
   * @param pattern yyyy-MM-dd
   * @param dateStr 2016-10-21
   * @return millisecond
   */
  public static long dateStringToMillis(String pattern, String dateStr) {
    DateFormat sdFormat = new SimpleDateFormat(pattern);
    try {
      return sdFormat.parse(dateStr).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
      return 0;
    }
  }
}