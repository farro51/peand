package it.polito.dma.ponyexpressandroid;

import it.polito.dma.ponyexpressandroid.library.CookiesManager;

import java.util.ArrayList;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;


public class SurveyActivity extends ListActivity {

	static final String[] SmartPhones = new String[] {
		"HTC Rezound",  "Samsung Galaxy S II Skyrocket", 
		"Samsung Galaxy Nexus", "Motorola Droid Razr", 
		"Samsung Galaxy S", "Samsung Epic Touch 4G", 
		"iPhone 4S", "HTC Titan"
	};
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		//setContentView(R.id.activity_survey);
		
		ArrayList<RowModel> list=new ArrayList<RowModel>();
		// get the cookie questions
		String quest= getQuestions();
		// convert to string array		
		String[] q = quest.split("*");
		
		for (int i=0; i<q.length ; i++) {
			
			list.add(new RowModel(q[i]));
		}
		
		setListAdapter(new RatingAdapter(list));
	}
	
	
	  protected String getQuestions() {
			CookiesManager cookiesManager = new CookiesManager(getApplicationContext());
			return cookiesManager.getQuestions();
		}
	
	private RowModel getModel(int position) {
		return(((RatingAdapter)getListAdapter()).getItem(position));
	}
		
	
	//Class RatingAdapter
	class RatingAdapter extends ArrayAdapter<RowModel> {
		
		RatingAdapter(ArrayList<RowModel> list) {
			super(SurveyActivity.this, R.layout.survey_list, list);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView;
			ViewWrapper wrapper;
			RatingBar rate;	
			
			if (row==null) {		
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.survey_list, parent, false);
				wrapper=new ViewWrapper(row);
				row.setTag(wrapper);
				//Rating Bar set wrapper
				rate=wrapper.getRatingBar();
				//Listener on Rating Bar
				RatingBar.OnRatingBarChangeListener l= new RatingBar.OnRatingBarChangeListener() {
					public void onRatingChanged(RatingBar ratingBar, 
							float rating, boolean fromTouch) {
						
						Integer myPosition=(Integer)ratingBar.getTag();
						RowModel model=getModel(myPosition);
						model.rating=rating;
						LinearLayout parent=(LinearLayout)ratingBar.getParent();
						TextView label=(TextView)parent.findViewById(R.id.questionText);
						label.setText(model.toString());
					}
				};
				rate.setOnRatingBarChangeListener(l);
			}
			else {
				wrapper=(ViewWrapper)row.getTag();
				rate=wrapper.getRatingBar();
			}

			RowModel model=getModel(position);
			
			wrapper.getLabel().setText(model.toString());
			rate.setTag(new Integer(position));
			rate.setRating(model.rating);		
			return(row);
		}
	}
	
	class RowModel {
		String label;
		float rating=2.0f;
		
		RowModel(String label) {
			this.label=label;
		}
		
		public String toString() {
			if (rating>=3.0) {
				return(label.toUpperCase());
			}	
			return(label);
		}
	}
	
	class ViewWrapper {
		View base;
		RatingBar rate=null;
		TextView label=null;
		
		ViewWrapper(View base) {
			this.base=base;
		}
		
		RatingBar getRatingBar() {
			if (rate==null) {
				rate=(RatingBar)base.findViewById(R.id.rate);
			}
			return(rate);
		}
		
		TextView getLabel() {
			if (label==null) {
				label=(TextView)base.findViewById(R.id.questionText);
			}
			return(label);
		}
	}
	

}
