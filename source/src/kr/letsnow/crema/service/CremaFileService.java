package kr.letsnow.crema.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class CremaFileService extends Service {
	private static CremaFileService instance = null;
	
	private static final String TAG = "CremaService";
	private final IBinder fileBinder = new CremaFileBinder();

	/**
	 * Binder Class
	 */
	public class CremaFileBinder extends Binder {
		public CremaFileService	getService() {
			return CremaFileService.this;
		}
	}
	/************************************************/

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return fileBinder;
	}

	
	/*************************************************/
	// For Application Functions
	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	/*************************************************/

}
