package org.sekularac.domaci.utils;
/**
 * Klasa sa pomocnim metodam
 * @author user
 *
 */
public final class Utils {
    /**
     * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne moze da se konvertuje vraca se vrednost 0.
     * @param o
     * @return
     */
    public static int safeConvertToInt(Object o){
        int id = 0;
        try {
            id = Integer.parseInt(o == null ? "0" : o.toString());
        } catch(NumberFormatException e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne moze da se konvertuje vraca se vrednost 0.
     * @param o
     * @return
     */
    public static double safeConvertToDouble(Object o){
        double id = 0;
        try{
            id = Double.parseDouble(o == null ? "0" : o.toString());
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Pomocna metoda koja sluzi da konvertuje object o u string i u slucaju da je o == null vraca se prazan string.
     * @param o
     * @return
     */
    public static String safeConvertToStr(Object o){
        return o == null ? "" : o.toString();
    }
}
