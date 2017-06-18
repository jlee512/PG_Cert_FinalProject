package backend.servlets;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by jlee512 on 13/06/2017.
 */

/**
 *  This class is used to verify the google recapthca response in accordance with the documentation link below:
 *  https://developers.google.com/recaptcha/intro
 */

public class VerifyRecaptcha {

    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    public static final String secret = "6LdFMSUUAAAAAO0__tnThpaXRqOtaq7edM-Eq0Lh";
    private final static String USER_AGENT = "Mozilla/5.0";

    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try{
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            // Add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String postParams = "secret=" + secret + "&response="
                    + gRecaptchaResponse;

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + postParams);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            String jsonRecaptchaString = response.toString();

            JSONParser parser = new JSONParser();
            Object jsonParsed = parser.parse(jsonRecaptchaString);
            JSONObject recaptcha_response = (JSONObject) jsonParsed;
            boolean recaptcha_boolean = (boolean) recaptcha_response.get("success");

            System.out.println(recaptcha_boolean);

            /*To be updated*/
            return recaptcha_boolean;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*------------------------------*/
    /*End of Class*/
    /*------------------------------*/

}
