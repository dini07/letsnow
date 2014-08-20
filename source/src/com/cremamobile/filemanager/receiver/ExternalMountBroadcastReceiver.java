package com.cremamobile.filemanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExternalMountBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
        if (action.equals("android.intent.action.MEDIA_MOUNTED")) {
            // react to event
        	//TODO!!!
        }
	}
	
};
