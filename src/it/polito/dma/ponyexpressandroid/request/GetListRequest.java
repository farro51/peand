package it.polito.dma.ponyexpressandroid.request;

import java.net.ConnectException;



import it.polito.dma.ponyexpressandroid.library.AlertDialogManager;
import it.polito.dma.ponyexpressandroid.response.GetListResponseHandler;
import it.polito.dma.ponyexpressandroid.R;
import it.polito.dma.ponyexpressandroid.RequestListFragment;

import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class GetListRequest {
	private Context appContext;
	private RequestListFragment act;
	private String url;
	private AlertDialogManager alert;
	
	public GetListRequest(Context c, RequestListFragment c1){
		appContext = c;
		act = c1;
		url = "http://"+ act.getString(R.string.server_ip_address);
		alert = new AlertDialogManager();
	}
	
	public void getpickupList(String id_user){
		AsyncHttpClient client = new AsyncHttpClient();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(appContext);
		client.setCookieStore(myCookieStore);
		Uri.Builder b = Uri.parse(url).buildUpon();
		b.path("/ponyexpress/resources/agents/"+id_user+".json");
		String url = b.build().toString();
		final ProgressDialog pd = ProgressDialog.show(appContext, "", "Getting list", true, false);

		//client.get(appContext, url, new JsonHttpResponseHandler() {
		 client.get(url, new JsonHttpResponseHandler() {
			private GetListResponseHandler cprh;
			
			@Override
			public void onSuccess(JSONObject response) {
				pd.dismiss();
				cprh = new GetListResponseHandler(appContext, act);
				cprh.onSuccess(response);
			}
			
			@Override
			public void onFailure(Throwable e, String response) {
				pd.dismiss();
				//The device isn't connected to a network
				
				if(e.getClass() == HttpHostConnectException.class){
					alert.showAlertDialog(appContext, "Error", "Without Internet Connection", false);
					return;
				}
				
				//Server unreachable
				if(e.getClass() == ConnectException.class){
					alert.showAlertDialog(appContext, "Sorry!", "Server unreachable, please retry later", false);
					return;
				}
            	
				cprh = new GetListResponseHandler(appContext, act);
				cprh.onFailure((HttpResponseException) e, response);
			}
		});
	}
}