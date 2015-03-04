package es.deusto.deustotech.adaptui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
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
	private ProgressDialog progressDialog;
	private AdaptUI adaptUI;
	/**private static final String ONTOLOGY_FILE = "test.owl";
	private static final String ONT_PATH = "storage/emulated/0/Download/";
	private static final String ADAPTUI_NAMESPACE = "http://www.morelab.deusto.es/ontologies/test.owl#";*/
	private static final String ADAPTUI_NAMESPACE = "http://www.example.com/pizza.owl#";
	
	private Timer timer;
	private float draw;
	private float drained;
	private float Reasonerdrained;
	private BroadcastReceiver batteryInfoReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);
		
		progressDialog = new ProgressDialog(this); 
		// spinner (wheel) style dialog
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
		// better yet - use a string resource getString(R.string.your_message)
		progressDialog.setMessage("Reading Dataset"); 
		progressDialog.setCanceledOnTouchOutside(false);
		// display dialog
		progressDialog.show(); 
		 
		 
		// start async task
		new MyAsyncTaskClass().execute();  
		
		
				
	}
	private class MyAsyncTaskClass extends AsyncTask<Void, Void, Void> {
		 
        @Override
        protected Void doInBackground(Void... params) {
        	layout = (GridLayout) findViewById(R.id.layout);
    		Collection<View> views = new ArrayList<View>();
    		views.add(layout);
    		
    		// Initializing the framework
    		adaptUI = new AdaptUI(ADAPTUI_NAMESPACE, views);
    				
    				 String	ontology	= "file:storage/emulated/0/Download/b.owl";
    				 String[]	queries		= new String[] {
    													// One of the original LUBM queries
    						"file:storage/emulated/0/Download/a.sparql"
    						
    						};

    				int a = executeQueries(ontology, queries);
    	            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            // put here everything that needs to be done after your async task finishes
            progressDialog.dismiss();
            stop();
            finish();
        }
}
public int executeQueries(String ontology, String[] queries) {
		
		for ( int i = 0; i < queries.length ; i++ ) {
			String query = queries[i];
			
			// First create a Jena ontology model backed by the Pellet reasoner
			// (note, the Pellet reasoner is required)
			OntModel m = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
	
			// Then read the data from the file into the ontology model
			m.read( ontology );
	
			// Now read the query file into a query object
			Query q = QueryFactory.read( query );
	
			// Create a SPARQL-DL query execution for the given query and
			// ontology model
			start();

			QueryExecution qe = SparqlDLExecutionFactory.create( q, m );
			// We want to execute a SELECT query, do it, and return the result set
			ResultSet rs = qe.execSelect();
			// Print the query for better understanding
			System.out.println(q.toString());
			
			// There are different things we can do with the result set, for
			// instance iterate over it and process the query solutions or, what we
			// do here, just print out the results
			ResultSetFormatter.out( rs );		
			//Records current amaout of drained mAh by reasoner
			Reasonerdrained = drained;

			System.out.println("There was " + Reasonerdrained + "mAh" + " drained");

			// And an empty line to make it pretty
			System.out.println();
		}
		return 1;
	}

public  float bat(){		
    registerReceiver(this.batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {			
			int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
			String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
			int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
			int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);				
			
			BatteryManager mBatteryManager =
					(BatteryManager)getSystemService(Context.BATTERY_SERVICE);
					Long energy =
					mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);					
			float currentdraw = energy;
			draw = currentdraw;		
			((TextView)findViewById(R.id.textView)).setText("     PELLET REASONER "+"\n"+"Plugged: "+plugged+"\n"+
					"Technology: "+technology+"\n"+
					"Temperature: "+temperature+"\n"+
					"Voltage: "+voltage+"\n"+
					"Current mA = " + energy + "mA"+ "\n"+
					"Pellet reasoner Drained = " + Reasonerdrained + "mA"+ "\n"+
					"Currentlly Drained = " + drained + "mAh"+ "\n");

		}
	};
	return draw;
}


public void start() {
    if(timer != null) {
        return;
    }
    timer = new Timer();	   
    timer.schedule(new TimerTask() {
        public void run() {	            
           // draw = draw + (bat());
        	float curret =bat(); 
        	drained =drained +(curret/7200);
            		//System.out.println("Current mA = " + curret + "mA"+ "\n"+
					//"Capacity Drained = " + drained + "mAh"+ "\n");
					
    		//batteryInfo=(TextView)findViewById(R.id.textView);

       }
   }, 0, 500 );
}
public void stop() {
    timer.cancel();
    timer = null;
}
}