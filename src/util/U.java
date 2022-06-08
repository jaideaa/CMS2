/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Calendar;

/**
 *
 * @author jaide
 */
public class U {
    
    public static boolean OPEN_WINDOW = true;
    public static Object dados = null;
    
    /* -------------------------------------------------------------------- */
    
    public static String generateId(String prefix) {
        String id = "";
        
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int diaDoAno = c.get(Calendar.DAY_OF_YEAR);
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        int segundo = c.get(Calendar.SECOND);
        
        // atribui o ano
        id += String.valueOf(ano);
        
        // atribui o dia do ano
        if (diaDoAno < 10) {
            id += "00" + diaDoAno;
        } else if ((diaDoAno > 10) && (diaDoAno < 100)) {
            id += "0" + diaDoAno;
        } else {
            id += String.valueOf(diaDoAno);
        }
        
        // hora, minuto. segundo
        int soma = hora + minuto + segundo;
        if (soma < 10) {
            id += "00" + soma;
        } else if ((soma > 10) && (soma < 100)) {
            id += "0" + soma;
        } else {
            id += String.valueOf(soma);
        }
        
        if (segundo < 10) {
            id += "0" + segundo;
        } else {
            id += String.valueOf(segundo);
        }
        
        return prefix + id;
    }
    
}
