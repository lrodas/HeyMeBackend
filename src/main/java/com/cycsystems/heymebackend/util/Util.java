package com.cycsystems.heymebackend.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public static String convertIntMonthToStringMonth(Integer month) {
        switch (month) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                return "";
        }
    }

    public static int generateRandomCode() {
        int min = 1000;
        int max = 10000;
        double random_double = Math.random() * (max - min + 1) + min;
        int random_int = (int)(Math.random() * (max - min + 1) + min);
        return random_int;
    }

    public static Date mapDate(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fecha)).getYear(),
                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fecha)).getMonthValue() - 1,
                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fecha)).getDayOfMonth() + 1);

        return calendar.getTime();
    }

    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
