

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import javax.xml.transform.Transformer;
import java.io.*;
import java.net.*;



public class themovieDBproject {


    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();//retorna un JSON
    }

    public static void main(String[] args){
        String JsonPelis = "";
        String JsonActors = "";
        String api_key = "3a612a7f772ad50863c9994c3c7b9204";

        System.out.println("PELICULAS:");

        for (int i = 0; i < 10; i++) {
            int peliId = 620 + i;
            String film = String.valueOf(peliId);
            String peticioActors = "https://api.themoviedb.org/3/movie/"+film+"/credits?api_key="+api_key;
            String peticioPeli = "https://api.themoviedb.org/3/movie/"+film+"?api_key="+api_key;

            try {
                JsonPelis = getHTML(peticioPeli);//peticioActors   peticioPeli
                //System.out.println("PELICULAS:");
                //System.out.println(JsonPelis);
                SJS(JsonPelis);

                JsonActors = getHTML(peticioActors);
                System.out.println("\tActors:");
                //System.out.println("\t" + JsonActors);
                SJCpersonaje(JsonActors);

            } catch (Exception e) {
                System.out.println("La peli "+film+" no existeix");
            }
        }


    }

    public static void SJS (String cadena){//para pelis

        Object obj02 =JSONValue.parse(cadena);
        JSONObject arra02=(JSONObject)obj02;
        System.out.println("\nTitol: " + arra02.get("original_title"));
        System.out.println("Data de publicació: " + arra02.get("release_date"));

    }

    public static void SJCpersonaje (String cadena){//para personajes
        Object obj02 =JSONValue.parse(cadena);
        JSONObject arra02=(JSONObject)obj02;
        JSONArray arra03 = (JSONArray)arra02.get("cast");

        for (int i = 0; i < arra03.size(); i++) {
            JSONObject jb= (JSONObject)arra03.get(i);
            System.out.println("\t" + (i+1) + " - " + jb.get("character")+"<-->"+jb.get("name"));

        }

    }

}
