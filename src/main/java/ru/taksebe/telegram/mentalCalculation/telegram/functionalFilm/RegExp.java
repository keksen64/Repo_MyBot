package ru.taksebe.telegram.mentalCalculation.telegram.functionalFilm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

    public static String getRecomendationFilm(String resp){
        String res = "";
        Pattern pattern = Pattern.compile("alt=\"(.*?)\" width=\"");
        Matcher matcher = pattern.matcher(resp);
        String[] arrayString =new String[100];
        int count = -1;
        int max = 0;
        while (matcher.find()&&max<15) {
            count++;
            int start=matcher.start();
            int end=matcher.end();
            arrayString[count]= resp.substring(start,end);
            System.out.println("Найдено совпадение " + resp.substring(start+5,end-9) + " с "+ start + " по " + (end-1) + " позицию");
            if(resp.substring(start+5,end-9).length()<150){
                res = res + resp.substring(start+5,end-9) + "\n";
            }
            max++;
        }
        return res;
    }


    public static String getSearchFilm(String resp){
        String id = "";
        Pattern pattern = Pattern.compile("<p class=\"name\">(.+?)</span></p>");
        Matcher matcher = pattern.matcher(resp);
        String[] arrayString =new String[100];
        int count = -1;
        while (matcher.find()) {
            count++;
            int start=matcher.start();
            int end=matcher.end();
            arrayString[count]= resp.substring(start,end);
            System.out.println("Найдено совпадение " + resp.substring(start,end) + " с "+ start + " по " + (end-1) + " позицию");
        }
        Pattern pattern0 = Pattern.compile("data-id=\".*?\"");
        Pattern pattern1 = Pattern.compile("data-type=.*?</a>");
        Pattern pattern2 = Pattern.compile("class=\"year\">.*?</span>");
        int count1 = 0;
        String[] resultArrayString =new String[100];
        while (count1 <= count){
           // String s = arrayString[count1];
          //  System.out.println(arrayString[count1]);
            Matcher matcher0 = pattern0.matcher(arrayString[count1]);
            Matcher matcher1 = pattern1.matcher(arrayString[count1]);
            Matcher matcher2 = pattern2.matcher(arrayString[count1]);

            try {
                matcher0.find();
                matcher1.find();
                matcher2.find();
                int start0=matcher0.start();
                int end0=matcher0.end();
                int start1=matcher1.start();
                int end1=matcher1.end();
                int start2=matcher2.start();
                int end2=matcher2.end();
                if(arrayString[count1].substring(start1,end1).contains("film")||arrayString[count1].substring(start1,end1).contains("series")){

                }else {break;}
                resultArrayString[count1] = arrayString[count1].substring(start0,end0) +
                        "||" + arrayString[count1].substring(start1,end1) + " год: " + arrayString[count1].substring(start2,end2);

                System.out.println(resultArrayString[count1]);
                if(count1==0){
                   id = arrayString[count1].substring((start0+9),(end0-1));
                }
                count1 ++;
            }catch (Exception e){
                break;
            }
        }
        return id;
    }
}
