package com.cycsystems.heymebackend.util;

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
}
