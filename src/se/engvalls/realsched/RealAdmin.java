package se.engvalls.realsched;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RealAdmin extends Activity {
	
	private DBHelper db;
	private ArrayList<ArrayList<Object>> lista;
	private ListView lView;
	private ArrayAdapter<String> adapter;
		
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.admin);
		lView = (ListView) findViewById(R.id.ListView01);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice);
				
		lista = new ArrayList<ArrayList<Object>>();
        db = new DBHelper(this);
        lista = db.getAllRowsAsArrays();
    	
        for (Iterator<ArrayList<Object>> i = lista.iterator(); i.hasNext();) {  
      		ArrayList<Object> currentRow = i.next();  
      	    String user = (String) currentRow.get(1);     	    	
      	    adapter.add(user);      	    
      	}  
		
		
		lView.setAdapter(adapter);
		lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		int a = 0;
		for (Iterator<ArrayList<Object>> i = lista.iterator(); i.hasNext();) {  
      		ArrayList<Object> currentRow = i.next();  
      	    String active = (String) currentRow.get(3);
      	    if (active.equalsIgnoreCase("yes")) {
      	    	lView.setItemChecked(a, true);
      	    }
      	    a++;
      	} 

		lView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {				
			
				long selectedIndex = arg3 + 1;
				
				long id = (Long) db.getRowAsArray(selectedIndex).get(0);
				String user = db.getRowAsArray(selectedIndex).get(1).toString();
				String code = db.getRowAsArray(selectedIndex).get(2).toString();
				String active = db.getRowAsArray(selectedIndex).get(3).toString();
				
				if (active.equals("yes")){
					db.updateRow(id, user, code, "no");
				} else {
					db.updateRow(id, user, code, "yes");
				}
			}

		}); 
		
	}
	
}
