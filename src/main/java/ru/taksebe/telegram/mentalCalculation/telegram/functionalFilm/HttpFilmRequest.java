package ru.taksebe.telegram.mentalCalculation.telegram.functionalFilm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.BufferedReader;
import java.io.InputStreamReader;


public class HttpFilmRequest {

    private final String USER_AGENT = "Mozilla/5.0";

    public static String searchFilm(String name) throws Exception {
        HttpFilmRequest http = new HttpFilmRequest();
        System.out.println("Testing 1 - Send Http GET request");
        String resp = http.sendGetSearch(name);
        return resp;
    }
    public static String recomendationFilm(String id) throws Exception {
        HttpFilmRequest http = new HttpFilmRequest();
        System.out.println("Testing 1 - Send Http GET request");
        String resp = http.sendGetRecomendation(id);
        return resp;
    }

    // HTTP-запрос GET
    private String sendGetSearch(String name) throws Exception {
        name = name.replaceAll(" ","");
        String url = "https://www.kinopoisk.ru/index.php?kp_query=" + name;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        // Добавляем заголовок запроса
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        //System.out.println(result.toString());
        return result.toString();
    }

    private String sendGetRecomendation(String id) throws Exception {
        Thread.sleep(1000);
        if(id.equals("")){
            return "1";
        }
        String url = "https://www.kinopoisk.ru/film/" + id + "/like/";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        // Добавляем заголовок запроса
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36");
//        request.addHeader(":path:", "/film/"+ id +"/like/");
       request.addHeader("method", "GET");
         request.addHeader("cookie", "i=An07CSIimr3e52w4wSJob5vgVJe74lBhdM1FYL0EgJCuG6M5ofzk6T6ejcFAqapbmVApURdnCIf+xevzzdt82wG4B/w=; gdpr=0; _ym_uid=1651961075986299969; _ym_isad=2; _yasc=BlFJkV4pxWIiizrUCaEKptCZ6sDsWEdB5qviBmPlwbpQUg==; PHPSESSID=0d013f0aff2f7c837cd25e55aa621d2f; yandex_gid=10750; tc=5591; _csrf_csrf_token=opXibMw265Nj_tbwRCyVHP951nxDP8jX3dEsVRaeVdI; mobile=no; desktop_session_key=65e504e638d6c1735d63fae3efba5f85abf1158853fc5d3d170f3f810ba2942e46bea911360d1da68b8b3ecd30c7544782c44b0643febe5e84d8364504be4411e8fec0d5733ca8fb007e570e4c59a8e055f2003f82f0b6952d1c24282c42ce30; desktop_session_key.sig=croaaznlqIpuHR-uit-i4AmbrBU; mda_exp_enabled=1; _ym_visorc=b; spravka=dD0xNjUxOTY1ODczO2k9OTUuNzIuMTkuNzU7RD1BNjgyRDcyRTFFODVDM0Q2MzNCNzJEMDgwNzZFNkYwNTE1QThGNTU5OUM5MTRCNDZFNzE1N0U5RjkzRkY0N0IwQjI0MUFDNjU7dT0xNjUxOTY1ODczNTQzNjQ3OTcyO2g9MzE3NDZlY2UxYzI0MzA0MzBkYmIxM2RlYjUwNDkwYjg=; user_country=ru; yandex_plus_metrika_cookie=true; cycada=kIwB+DOGmGXgjCrrpJ60/A4bEyiEwP+mqMjdAOPLNYU=; _ym_d=1651965989; ya_sess_id=noauth:1651965989; yandex_login=; ys=c_chck.3078867430; mda2_beacon=1651965989933; sso_status=sso.passport.yandex.ru:synchronized");
 //       request.addHeader("accept-encoding", "gzip, deflate, br");
        request.addHeader("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        request.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        HttpResponse response = client.execute(request);
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
       // System.out.println(result.toString());
        return result.toString();
    }
}
