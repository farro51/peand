package it.polito.dma.ponyexpressandroid.request;

import it.polito.dma.ponyexpressandroid.LoginActivity;
import it.polito.dma.ponyexpressandroid.R;
import it.polito.dma.ponyexpressandroid.library.AlertDialogManager;
import it.polito.dma.ponyexpressandroid.library.UserFunctions;
import it.polito.dma.ponyexpressandroid.response.LoginResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/*Contacts the server to send the login request and contact LoginResponseHandler
 * to manage the success or failure response from the server
 * */
public class LoginRequest {


	private LoginActivity act;
	private String url;
	private AlertDialogManager alert;

	public LoginRequest(LoginActivity c1){

		act = c1;
		url = act.getString(R.string.server_ip_address);
		alert = new AlertDialogManager();
	}

	public void login(String email, String password){
		StringEntity entity = null;
		//JSONObject login = new JSONObject();
		RequestParams login = new RequestParams();


		
			login.put("email", email);
			login.put("password",UserFunctions.getMd5Hash(password));
			//entity =  new StringEntity(login.toString());
		

		AsyncHttpClient client = new AsyncHttpClient();
		final ProgressDialog pd = ProgressDialog.show(act, "", "Logging in...", true, false);
		
			
			client.post(act, "http://"+url+"/ponyexpress/resources/agents/login.json",login ,new JsonHttpResponseHandler() {
			
			private LoginResponseHandler loginHandler;

			@Override
			public void onSuccess(JSONObject response) {
				pd.dismiss();
				loginHandler = new LoginResponseHandler( act);
				loginHandler.onSuccess(response);
			}

			@Override
			public void onFailure(Throwable e, String response) {
				pd.dismiss();
				
				//The device isn't connected to a network
				if(e.getClass() == HttpHostConnectException.class){
					alert.showAlertDialog(act, "Error", "Without Internet Connection", false);
					return;
				}

				//Server unreachable
				if(e.getClass() == ConnectException.class){
					alert.showAlertDialog(act, "Sorry!", "Server unreachable, please retry later", false);
					return;
				}

				loginHandler = new LoginResponseHandler(act);
				loginHandler.onFailure((HttpResponseException)e, response);

			}
		});
	}
}