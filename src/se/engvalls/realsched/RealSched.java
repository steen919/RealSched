package se.engvalls.realsched;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class RealSched extends Activity {
	
	private DBHelper db ;
	private ArrayAdapter <CharSequence> adapter;
	private ArrayList<ArrayList<Object>> lista;	
	private Spinner spinner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv.setText(this.getString(R.string.app_welcome) + " " + this.getString(R.string.app_name));
        
        TextView sc = (TextView) findViewById(R.id.selectClass);
        sc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        sc.setText(this.getString(R.string.selectclass));
        
        
        lista = new ArrayList<ArrayList<Object>>();
        db = new DBHelper(this);
        lista = db.getAllRowsAsArrays();

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
      	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      	
      	adapter.add("---");
      	
      	for (Iterator<ArrayList<Object>> i = lista.iterator(); i.hasNext();) {  
      		ArrayList<Object> currentRow = i.next();  
      	    String user = (String) currentRow.get(1);
      	    String active = (String) currentRow.get(3);
      	    if (active.equalsIgnoreCase("yes")) adapter.add(user);
      	}  

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }
    
    public void onStop() {
    	super.onStop();
    }
    
    public void onResume(){
    	super.onResume();
    	
    	
    }
    
    public void onRestart(){
    	super.onRestart();
    	
        lista = db.getAllRowsAsArrays();
        
      	adapter.clear();
      	adapter.add("---");
      	
      	
      	for (Iterator<ArrayList<Object>> i = lista.iterator(); i.hasNext();) {  
      		ArrayList<Object> currentRow = i.next();  
      	    String user = (String) currentRow.get(1);
      	    String active = (String) currentRow.get(3);
      	    if (active.equalsIgnoreCase("yes")) adapter.add(user);
      	}
      	
      	spinner.setAdapter(adapter);
      	spinner.setSelection(0, true);
      	
      	
    	
    }
    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {
    	    	
    	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { 
    		
    		Intent intent = new Intent(RealSched.this,RealView.class);    		    		
    		int position = spinner.getSelectedItemPosition(); 
    		Bundle bundle = new Bundle();

    		if (position!=0) {
    			String selectedItem = spinner.getSelectedItem().toString();
    			for (Iterator<ArrayList<Object>> i = lista.iterator(); i.hasNext();) {  
    	      		ArrayList<Object> currentRow = i.next();  
    	      	    String user = (String) currentRow.get(1);
    	      	    String code = (String) currentRow.get(2);
    	      	    if (user.equalsIgnoreCase(selectedItem))  bundle.putString("id",code);
    	      	}
	    		intent.putExtras(bundle);	    		
	    		startActivity(intent);
    		}
        }

    	public void onNothingSelected(AdapterView<?> parent) {
          // Do nothing.
        }

		
    }  
    
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.admin);
        item.setVisible(true);
        item.setEnabled(true);

        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.lunch:
        	Intent lunchIntent = new Intent(this, RealLunch.class);
        	startActivity(lunchIntent);
            return true;
        case R.id.admin:
        	Intent adminIntent = new Intent(this, RealAdmin.class);
        	startActivity(adminIntent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
}