package se.engvalls.realsched;

import java.util.Calendar;

import android.app.Activity;
//import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
//import android.widget.Toast;
import android.widget.Button;

public class RealView extends Activity {
	
	private WebView mWebView; 
	private Button button1;
	private Button button2;
	private int week;
	private String uri;
	private String id;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        
        button1 = (Button) findViewById(R.id.prev);
        button2 = (Button) findViewById(R.id.next);
        
        button1.setOnClickListener(new OnClickListener() {
        	
            public void onClick(View v) {
            	//Toast.makeText(RealView.this, "Beep Bop", Toast.LENGTH_SHORT).show();
            	week -= 1;
            	uri = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx";
            	uri += "?format=png&schoolid=82570/sv-se";
            	uri += "&id={" + id + "}&period=&week=" + week;
            	uri += "&printer=1&colors=32&width=800&height=1000";
            	mWebView.loadUrl(uri);
            }
          });
        
        button2.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
            	//Toast.makeText(RealView.this, "Beep Bop", Toast.LENGTH_SHORT).show();
            	week += 1;
            	uri = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx";
            	uri += "?format=png&schoolid=82570/sv-se";
            	uri += "&id={" + id + "}&period=&week=" + week;
            	uri += "&printer=1&colors=32&width=800&height=1000";
            	mWebView.loadUrl(uri);
            }
          });
        
        Calendar cal = Calendar.getInstance();
        week = cal.get(Calendar.WEEK_OF_YEAR);        
        
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true); 
        
        // turn caching off
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        
        //First Extract the bundle from intent
        Bundle bundle = getIntent().getExtras();
        //Next extract the values using the key as
        id = bundle.getString("id");        
        
        
        uri = "http://www.novasoftware.se/ImgGen/schedulegenerator.aspx?format=png&schoolid=82570/sv-se&id={" + id + "}&period=&week=" + week + "&printer=1&colors=32&width=800&height=1000";
        
        mWebView.loadUrl(uri);
        
        
        
    }

	
}