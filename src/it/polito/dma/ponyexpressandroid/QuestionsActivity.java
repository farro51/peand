package it.polito.dma.ponyexpressandroid;




import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class QuestionsActivity extends ListActivity {

	private Button sendButton;
	private ArrayList<String> questionsList;
	private ArrayAdapter<String> adapter;
	
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    //setContentView(R.layout.activity_question);
    
    sendButton = (Button) findViewById(R.id.buttonSendSurvey);
    

    
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        "Linux", "OS/2" };
    // use your custom layout
        adapter = new ArrayAdapter<String>(this,
        R.layout.survey_list, R.id.questionText, values);
    setListAdapter(adapter);
    
    
    
    sendButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
			String dato = adapter.getItem(1);
			
		}});

  }
 

  
  
  
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
  }





} 
	
	
   
	
	
