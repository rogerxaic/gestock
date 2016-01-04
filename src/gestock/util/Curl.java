package gestock.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Roger on 11/20/2015.
 */
public class Curl {
    private final String USER_AGENT = "Gestock/1.0";
    private String url;
    private int responseCode;
    private String response;
    private boolean isSecure;
    private String cookieHeader;
    private URLConnection authCon;

    public Curl(String url) {
        this(url, null);
    }

    public Curl(String url, String cookieHeader) {
        this.url = url;
        this.cookieHeader = cookieHeader;
        if (this.url.startsWith("http://")) {
            this.isSecure = false;
        } else if (this.url.startsWith("https://")) {
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

    private void runHttp() throws Exception {
        URL obj = new URL(url);
        authCon = obj.openConnection();
        HttpURLConnection con = (HttpURLConnection) authCon;

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        if (cookieHeader != null) con.setRequestProperty("Cookie", cookieHeader);

        int responseCodeUrl = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.responseCode = responseCodeUrl;
        this.response = response.toString();
    }

    private void runHttps() throws Exception {
        URL obj = new URL(this.url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setHostnameVerifier((String arg0, SSLSession arg1) -> true);
        if (cookieHeader != null) con.setRequestProperty("Cookie", cookieHeader);

        int responseCodeUrl = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

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

    public String getCookies() {
        StringBuilder sb = new StringBuilder();
        List<String> cookies = authCon.getHeaderFields().get("Set-Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                if (sb.length() > 0) {
                    sb.append("; ");
                }
                String value = cookie.split(";")[0];
                sb.append(value);
            }
        }
        return sb.toString();
    }
}
