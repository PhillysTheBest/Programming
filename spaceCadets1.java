import java.io.*;
import java.net.*;

public class spaceCadets1 {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter email ID or username:");
            String emailId = br.readLine();
            if (emailId.contains("@")) {
                emailId = emailId.split("@")[0]; 
            }

            String webPage = "https://www.ecs.soton.ac.uk/people/"; 
            String urlAddress = webPage + emailId;
            URL url = new URL(urlAddress);
            BufferedReader urlReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String savedLine = null;
           
            while ((line = urlReader.readLine()) != null) {
                if (line.contains("property=\"og:title\" content=\"")) {
                    savedLine = line;
                    break; 
                }
            }  

            if (savedLine != null) {
                int start = savedLine.indexOf("content=\"") + 9; 
                int end = savedLine.indexOf("\"", start); 
                String name = savedLine.substring(start, end).trim();
                System.out.println(name);
            } 
            else {
                System.out.println("Name not found on the web page.");
            }
            urlReader.close();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
