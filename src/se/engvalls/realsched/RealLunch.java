package se.engvalls.realsched;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.util.Xml.Encoding;
import android.webkit.WebSettings;
import android.webkit.WebView;
//import android.widget.Toast;

public class RealLunch extends Activity {
	
	WebView mWebView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch);
        
        URL url = null;
		try {
			url = new URL("http://www.barbq.se/index.php?sida=31&id=330");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "ISO_8859_1"));
            for (String line; (line = reader.readLine()) != null;) {
                builder.append(line.trim());
            }
        } catch (UnknownHostException e) {
			e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {			
        
            if (reader != null) 
            	
            	try { 
            		reader.close(); 
            	} 
            	catch (IOException logOrIgnore) {
            		
            	}
        
        }
		
		mWebView = (WebView) findViewById(R.id.lunchview);
        
        // turn caching off
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        
		
		try {
	        String start = "<div class=\"content\">";
	        String end = "<div class=\"boka\">";
	        String part = builder.substring(builder.indexOf(start) + start.length());
	        String question = part.substring(0, part.indexOf(end)-end.length());
	        //System.out.println(question);
	        /*
			Context context = getApplicationContext();
	        CharSequence text = question;
	        int duration = Toast.LENGTH_LONG;
	
	        Toast toast = Toast.makeText(context, text, duration);
	        toast.show();
			*/
	        mWebView.setBackgroundColor(color.background_dark);
	        mWebView.loadDataWithBaseURL("", "<html><body style='color: rgb(255,102,0)'>" + question + "</body></html>", "text/html", Encoding.UTF_8.toString(),"");
			
	    } catch(Throwable e) {
			e.printStackTrace();
			mWebView.loadDataWithBaseURL("", "<html><body><h1>Sidan www.barbq.se ligger nere!</h1></body></html>", "text/html", Encoding.UTF_8.toString(),"");
		
		}
    }
}