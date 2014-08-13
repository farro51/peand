package it.polito.dma.ponyexpressandroid.library;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polito.dma.ponyexpressandroid.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
 
public class AlertDialogManager {

	
	private EditText codeSource;
	private String code ="";


	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 *               - pass null if you don't want icon
	 * */
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @return String code- pass the catch code
	 * */
	public String getCodeAlertDialog(Context context){
		
		LayoutInflater li = LayoutInflater.from(context);
	    View prompt = li.inflate(R.layout.dialog_prompt, null);
	    
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(prompt);
			
		 codeSource = (EditText) prompt.findViewById(R.id.code);
			
		// Show the message of request code
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,int id) {
			// Pick up the code from the screen	
			code = codeSource.getText().toString();
		  }
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog,int id) {
			// Cancel dialog chart
			dialog.cancel();
		 }
		});
		 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
		return code;	
	
	}
	
	
	
}