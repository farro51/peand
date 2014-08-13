package it.polito.dma.ponyexpressandroid.response;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.PersistentCookieStore;

import it.polito.dma.ponyexpressandroid.MainActivity;
import it.polito.dma.ponyexpressandroid.LoginActivity;
import it.polito.dma.ponyexpressandroid.R;
import it.polito.dma.ponyexpressandroid.library.CookiesManager;
import android.content.Context;
import android.content.Intent;
import android.webkit.CookieSyncManager;

public class LoginResponseHandler {

	private LoginActivity act;
	private String url;

	public LoginResponseHandler(LoginActivity c1) {
		act = c1;
		url = act.getString(R.string.server_ip_address);
	}

	public void onSuccess(JSONObject user) {
		String id = "";
		JSONArray questions;
		try {
			if(user.getString("authorization").equals("false")){
				act.onFailure("User or password is incorrect!!");
				
			}else{
			CookiesManager cookiesManager  = new CookiesManager(act);
			id = user.getString("id_user");
			questions = user.getJSONArray("questions");
			cookiesManager.createCookie("id_user", id, url);
			StringBuilder questionString = new StringBuilder();
				
		    for(int i=0;i<questions.length();i++){
		    	questionString.append(questions.getJSONObject(i).getString("id"));
		    	questionString.append("- ");
		    	questionString.append(questions.getJSONObject(i).getString("text"));
		    	if (i!=questions.length()-1){
		    	questionString.append("*");
		    	}
		    }					
			cookiesManager.createCookie("questions",questionString.toString(), url);
						
			Intent dashboard = new Intent(act, MainActivity.class);
			act.startActivity(dashboard);
			act.finish();
			
			
			}
		
		} catch (JSONException e) {
			act.onFailure("Internal error in the application pep"+ user.toString());
		}

		
	}

	public void onFailure(HttpResponseException e, String response){
		int code = e.getStatusCode();
		if(code == 404 || code == 400 || code == 503){
			act.onFailure(response);
		}
		else{
			act.onFailure("Error accessing the server");
		}
	}
}