package com.cremamobile.filemanager;

import com.cremamobile.filemanager.IRemoteService;
import com.cremamobile.filemanager.IRemoteServiceCallback;
import android.app.Service;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class CremaRemoteService extends Service {

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    super.handleMessage(msg);
            }
        }
    }
    
	final RemoteCallbackList<IRemoteServiceCallback>	mCallbacks = new RemoteCallbackList<IRemoteServiceCallback>();
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(this.getClass().getName(), "onBind called.. " + IRemoteService.class.getName());
		Log.d(this.getClass().getName(), "onBind called.. " + intent.getAction());
		
		if (IRemoteService.class.getName().equals(intent.getAction())) {
			return mBinder;
		}
		return null;
	}

	private final IRemoteService.Stub	mBinder = new IRemoteService.Stub() {
		public int getPid() {
			return android.os.Process.myPid();
		}
		
		public void registerCallback(IRemoteServiceCallback cb) {
			if (cb != null) {
				mCallbacks.register(cb);
			}
		}
		
		public void unregisterCallback(IRemoteServiceCallback cb) {
			if (cb != null) {
				mCallbacks.unregister(cb);
			}
		}

		@Override
		public String onService(int msg) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	private void sendMessageCallback(int msg) {
		final int N = mCallbacks.beginBroadcast();
		for (int i=0; i<N ; i++) {
			try {
				mCallbacks.getBroadcastItem(i).MessageCallback(msg);
			} catch(RemoteException e) {
				//TODO...
			}
		}
		mCallbacks.finishBroadcast();
	}
	
	@Override
    public int onStartCommand(Intent aIntent, int aFlags, int aStartId)
    {
    	return super.onStartCommand(aIntent, aFlags, aStartId);
    }
    
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	
}
