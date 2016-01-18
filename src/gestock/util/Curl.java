package gestock.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
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
    private List<String> cookies;
    private URLConnection authCon;
    private String requestMethod = "GET";
    private String postParameters;
    private String contentType;
    private boolean connected = false;
    private HashMap<String, String> properties;

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
        this.properties = new HashMap<>();
    }

    public void run() throws Exception {
        if (!connected) {
            connected = true;
            if (isSecure) {
                runHttps();
            } else {
                runHttp();
            }
        }
    }

    private void runHttp() throws Exception {
        URL obj = new URL(url);
        authCon = obj.openConnection();
        HttpURLConnection con = (HttpURLConnection) authCon;
        con.setInstanceFollowRedirects(false);

        con.setRequestMethod(requestMethod);
        //add request header

        if (cookieHeader != null) con.setRequestProperty("Cookie", cookieHeader);
        if (contentType != null) con.setRequestProperty("Content-Type", contentType);
        con.setRequestProperty("Accept-Charset", "UTF-8");
        this.properties.forEach(con::setRequestProperty);
        con.setRequestProperty("User-Agent", USER_AGENT);

        if (requestMethod.equals("POST")) {
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParameters);
            wr.flush();
            wr.close();

        }

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
        con.setInstanceFollowRedirects(false);

        con.setRequestMethod(requestMethod);
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setHostnameVerifier((String arg0, SSLSession arg1) -> true);
        if (cookieHeader != null) con.setRequestProperty("Cookie", cookieHeader);
        if (contentType != null) con.setRequestProperty("Content-Type", contentType);
        this.properties.forEach(con::setRequestProperty);
        con.setRequestProperty("Accept-Charset", "UTF-8");

        if (requestMethod.equals("POST")) {
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParameters);
            System.out.println(wr.toString());
            wr.flush();
            wr.close();

        }

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

    private void addCookies(List<String> cookies) {
        this.cookies.addAll(cookies);
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setPostParameters(String postParameters) {
        this.postParameters = postParameters;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }
}
