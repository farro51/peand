package it.polito.dma.ponyexpressandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class QlistActivity extends Activity {

	private Button sendButton;
	private ArrayList<String> questionsList;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qlist);
		//sendButton = (Button) findViewById(R.id.buttonSendSurvey);ç
		/*LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		sendButton = new Button(this);
		sendButton.setLayoutParams(params);
		sendButton.setGravity(Gravity.BOTTOM);
		sendButton.setText("Send");*/
		
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2" };
		    // use your custom layout
		adapter = new ArrayAdapter<String>(this,R.layout.survey_list, R.id.questionText, values);
		  ListView lv = (ListView) findViewById(R.id.listViewQuestions);
		   lv.setAdapter(adapter);

		   String item = adapter.getItem(1);
		
		
		    
		
	}
}
