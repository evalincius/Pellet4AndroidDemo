package es.deusto.deustotech.adaptui;

import java.util.ArrayList;
import java.util.Collection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class ActivityExample extends Activity {

	private GridLayout layout;
	private TextView textView;
	private Button button;
	private EditText editText;
	private ProgressDialog progressDialog;
	private AdaptUI adaptUI;
	/**private static final String ONTOLOGY_FILE = "test.owl";
	private static final String ONT_PATH = "storage/emulated/0/Download/";
	private static final String ADAPTUI_NAMESPACE = "http://www.morelab.deusto.es/ontologies/test.owl#";*/
	private static final String ONTOLOGY_FILE = "pizza.owl";
	private static final String ONT_PATH = "storage/emulated/0/Download/";
	private static final String ADAPTUI_NAMESPACE = "http://www.example.com/pizza.owl#";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);
		
		progressDialog = new ProgressDialog(this); 
		// spinner (wheel) style dialog
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
		// better yet - use a string resource getString(R.string.your_message)
		progressDialog.setMessage("Reading Dataset"); 
		// display dialog
		progressDialog.show(); 
		 
		 
		// start async task
		new MyAsyncTaskClass().execute();  
		
		
				
	}
	private class MyAsyncTaskClass extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected Void doInBackground(Void... params) {
        	layout = (GridLayout) findViewById(R.id.layout);
    		//textView = (TextView) findViewById(R.id.textView);
    		//button = (Button) findViewById(R.id.button);
    		//editText = (EditText) findViewById(R.id.editText);
    		
    		Collection<View> views = new ArrayList<View>();
    		views.add(layout);
    		//views.add(textView);
    		//views.add(button);
    		//views.add(editText);
    		
    		// Initializing the framework
    		adaptUI = new AdaptUI(ADAPTUI_NAMESPACE, views);
    				
    				final String	ontology	= "file:storage/emulated/0/Download/lubm.owl";
    				final String[]	queries		= new String[] {
    													// One of the original LUBM queries
    						"file:storage/emulated/0/Download/a.sparql"
    						//"file:storage/emulated/0/Download/pizza.sparql"
    						
    						};

    				int a = adaptUI.getOntManager().executeQueries(ontology, queries);
    				if(a==1){
    	    			System.exit(0);
    				}            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // put here everything that needs to be done after your async task finishes
            progressDialog.dismiss();
        }
}
}