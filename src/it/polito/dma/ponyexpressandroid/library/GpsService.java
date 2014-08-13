package it.polito.dma.ponyexpressandroid.library;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
 
public class GpsService extends Service {
 
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servicio en Ejecucion", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }
 
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
    }
}