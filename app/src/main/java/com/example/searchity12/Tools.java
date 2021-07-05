package com.example.searchity12;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;


public class Tools {

    public static Date ParseDate(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
}
