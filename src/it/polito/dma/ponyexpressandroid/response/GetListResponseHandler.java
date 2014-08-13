package it.polito.dma.ponyexpressandroid.response;


import it.polito.dma.ponyexpressandroid.library.AlertDialogManager;
import it.polito.dma.ponyexpressandroid.response.GetListResponseHandler;
import it.polito.dma.ponyexpressandroid.R;
import it.polito.dma.ponyexpressandroid.RequestListFragment;
import java.util.HashMap;
import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.widget.SimpleAdapter;

public class GetListResponseHandler {

	private RequestListFragment act;
	private AlertDialogManager alert;

	public GetListResponseHandler(Context c, RequestListFragment c1) {
		act = c1;
		alert = new AlertDialogManager();
	}

	public void onSuccess(JSONObject destinations) {
		try {

			JSONArray destinationslist = destinations.getJSONArray("destinations_list");
			HashMap<String, String> item;
			
			for (int i = 0; i < destinationslist.length(); i++) {
				item = new HashMap<String, String>();
				JSONObject destino;
				destino = destinationslist.getJSONObject(i);
				item.put("line1", destino.getString("address"));
				String typ= destino.getString("id_service");
				
				if (typ.contains("d")){
					item.put("line2","Delivery" );
				}else{
					item.put("line2","Pick Up" );
				}
	
				act.getList().add(item);
				act.getAddressList().add(destino.getString("address"));
				act.getServiceIdList().add(destino.getString("id_service"));
				act.getLatitudeList().add(Double.parseDouble(destino.getString("latitude")));
				act.getLongitudeList().add(Double.parseDouble(destino.getString("longitude")));
			}
		} catch (JSONException e) {
			HashMap<String, String> item = new HashMap<String, String>();
			JSONObject destino;
			
			try {
				destino = destinations.getJSONObject("destinations_list");
				item.put("line1", destino.getString("address"));
				String typ= destino.getString("id_service");
				
				if (typ.contains("d")){
					item.put("line2","Delivery" );
				}else{
					item.put("line2","Pick Up" );
				}
				act.getList().add(item);
				act.getAddressList().add(destino.getString("address"));
				act.getServiceIdList().add(destino.getString("id_service"));
				act.getLatitudeList().add(Double.parseDouble(destino.getString("latitude")));
				act.getLongitudeList().add(Double.parseDouble(destino.getString("longitude")));
			} catch (JSONException e1) {
				alert.showAlertDialog(act.getActivity(), "Error", "Internal error in the application", false);
				return;
			}
		}

		act.setSa(new SimpleAdapter(act.getActivity(), act.getList(),
				android.R.layout.two_line_list_item, new String[] { "line1",
		"line2" }, new int[] { android.R.id.text1,
			android.R.id.text2 }));
		act.setListAdapter(act.getSa());
	}

	public void onFailure(HttpResponseException e, String response) {
		int code = e.getStatusCode();

		if(code == 404 || code == 400 || code == 503){
			alert.showAlertDialog(act.getActivity(), "Error", response, true);
		}
		else{
			alert.showAlertDialog(act.getActivity(), "Error", "Service Unavailable", false);
		}

	}
}