package es.deusto.deustotech.adaptui;

import java.util.ArrayList;
import java.util.Collection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

@SuppressLint("SdCardPath")
public class ActivityExample extends Activity {

	private GridLayout layout;
	private TextView textView;
	private Button button;
	private EditText editText;

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
		
		layout = (GridLayout) findViewById(R.id.layout);
		textView = (TextView) findViewById(R.id.textView);
		button = (Button) findViewById(R.id.button);
		editText = (EditText) findViewById(R.id.editText);
		
		Collection<View> views = new ArrayList<View>();
		views.add(layout);
		views.add(textView);
		views.add(button);
		views.add(editText);
		
		// Initializing the framework
		adaptUI = new AdaptUI(ADAPTUI_NAMESPACE, views);
		
		// Simulating context change with a listener
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				final String	ontology	= "file:storage/emulated/0/Download/full-lubm.owl";
				final String[]	queries		= new String[] {
													// One of the original LUBM queries
						"file:storage/emulated/0/Download/1stexample.sparql",
						// A SPARQL-DL query
						//"file:examples/data/lubm-sparql-dl.sparql",
						// A SPARQL-DL with the SPARQL-DL extensions vocabulary
						//"file:examples/data/lubm-sparql-dl-extvoc.sparql" 
						};

				textView.setText("LOOK LogCat ");
				adaptUI.getOntManager().executeQueries(ontology, queries);
				
				
				//textView.setText(adaptUI.getOntManager().getReasoner().getReasonerName());
				// Adaptation method 1. Adapting individually each view
				// The default constructor works fine
				//adaptViewsIndividually();
				
				// TODO: Change layout color (the name of the class is "Background")
				// layout.setBackgroundColor(...);
				
				// TODO: Change button size (text size), and background and text color
				// ...
				// button.setBackgroundColor(...);
				// button.setTextSize(...);
				// button.setTextColor(...);
				button.invalidate();
				
				// TODO: Change edit text size (text size), and background and text color
				// ...
				// editText.setBackgroundColor(...);
				// editText.setTextSize(...);
				// editText.setTextColor(...);
				editText.invalidate();
				
				// TODO: Change text view size (text size), and background and text color
				// ...
				// textView.setTextSize(...);
				// textView.setBackgroundColor(...);
				// textView.setTextColor(...);
				textView.invalidate();
				
				
				// Adaptation method 2. We tell AdaptUI to adapt every loaded view
				// It needs the views to be added using the AdaptUI(namespace, views)
				// constructor
//				Map<String, Integer> viewsMap = adaptUI.adaptLoadedViews();
//				button.setBackgroundColor(viewsMap.get("viewBackgroundColor"));
				
			}
		});
		
		//If we want the process to be run in local it is necessary to 
		//map the corresponding ontologies before loading the main one
		//mapOntology(namespace, file)
		adaptUI.mapOntology("http://xmlns.com/foaf/0.1/", 									adaptUI.getExternalDirectory("foaf.rdf"));
		adaptUI.mapOntology("http://daml.umbc.edu/ontologies/cobra/0.4/device", 			adaptUI.getExternalDirectory("soupa.rdf"));
		adaptUI.mapOntology("http://u2m.org/2003/02/UserModelOntology.rdf", 				adaptUI.getExternalDirectory("UserModelOntology.rdf"));
		adaptUI.mapOntology("http://swrl.stanford.edu/ontologies/3.3/swrla.owl", 			adaptUI.getExternalDirectory("swrla.rdf"));
		adaptUI.mapOntology("http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl", adaptUI.getExternalDirectory("sqwrl.rdf"));
		
		adaptUI.loadOntologyFromFile(ONT_PATH, ONTOLOGY_FILE);
	}
	
	/*
	private void adaptViewsIndividually() {
		layout.setBackgroundColor(adaptUI.adaptViewBackgroundColor(ADAPTUI_NAMESPACE, "Background"));
		
		button.setBackgroundColor(adaptUI.adaptViewBackgroundColor(ADAPTUI_NAMESPACE, button.getClass().getSimpleName()));
		button.setTextColor(adaptUI.adaptViewTextColor(ADAPTUI_NAMESPACE, button.getClass().getSimpleName()));
		button.setTextSize(adaptUI.adaptViewTextSize(ADAPTUI_NAMESPACE, button.getClass().getSimpleName()));
		editText.setBackgroundColor(adaptUI.adaptViewBackgroundColor(ADAPTUI_NAMESPACE, editText.getClass().getSimpleName()));
		editText.setTextColor(adaptUI.adaptViewTextColor(ADAPTUI_NAMESPACE, editText.getClass().getSimpleName()));
		editText.setTextSize(adaptUI.adaptViewTextSize(ADAPTUI_NAMESPACE, editText.getClass().getSimpleName()));
		textView.setBackgroundColor(adaptUI.adaptViewBackgroundColor(ADAPTUI_NAMESPACE, textView.getClass().getSimpleName()));
		textView.setTextColor(adaptUI.adaptViewTextColor(ADAPTUI_NAMESPACE, textView.getClass().getSimpleName()));
		textView.setTextSize(adaptUI.adaptViewTextSize(ADAPTUI_NAMESPACE, textView.getClass().getSimpleName()));
	}
	*/
}