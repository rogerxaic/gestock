package gestock.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Roger on 11/20/2015.
 */
public class Curl {
    private final String USER_AGENT = "Gestock/1.0";
    protected String url;
    protected int responseCode;
    protected String response;
    private boolean isSecure;

    public Curl(String url) {
        this.url = url;
        if (this.url.startsWith("http://")) {
            this.isSecure = false;
        } else if (this.url.startsWith("http://")) {
            this.isSecure = true;
        } else {
            throw new IllegalArgumentException("String has to start /w http or https");
        }
    }

    public void run() throws Exception {
        if (isSecure) {
            runHttps();
        } else {
            runHttp();
        }
    }

    public void runHttp() throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

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

    public void runHttps() throws Exception {
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

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponse() {
        return response;
    }

    public String getUrl() {
        return url;
    }
}
