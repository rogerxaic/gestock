package gestock.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by Roger on 11/20/2015.
 */
public class Curl {
    protected String url;
    protected int responseCode;
    protected String response;
    private final String USER_AGENT = "Gestock/1.0";

    public Curl(String url) {
        this.url = url;
    }

    public void run() throws Exception{
        URL obj = new URL(this.url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setHostnameVerifier((String arg0, SSLSession arg1) -> true);

        int responseCodeUrl = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.responseCode = responseCodeUrl;
        this.response = response.toString();
    }
}
