package com.cremamobile.filemanager;

import java.util.Locale;

import com.cremamobile.filemanager.file.FindHistory;
import com.cremamobile.filemanager.receiver.ExternalMountBroadcastReceiver;
import com.cremamobile.filemanager.service.*;
import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cremamobile.filemanager.IRemoteService;
import com.cremamobile.filemanager.IRemoteServiceCallback;
import com.cremamobile.filemanager.R;

public class CremaActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	/**
	 * mount sdcard ..etc..
	 */
	ExternalMountBroadcastReceiver mReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		getActionBar().setIcon(R.drawable.folder_01);
		
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		
		// Start Service
		Intent intent = new Intent("com.cremamobile.filemanager.IRemoteService");
//		bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
		
//		Intent intent = new Intent("kr.letsnow.crema.service.IRemoteService");
		startService(intent);
		
		RegisterUpdateReceiver();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
	  
	@Override
	protected void onDestroy() {
		// Stop Service
//		if (mBound)
//			unbindService(mServiceConnection);
		
		Intent intent = new Intent("com.cremamobile.filemanager.IRemoteService");
		stopService(intent);
		
		//
		super.onDestroy();
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		mTitle = getString(R.string.app_name);
//		switch (number) {
//		case 1:
//			mTitle = getString(R.string.title_section1);
//			break;
//		case 2:
//			mTitle = getString(R.string.title_section2);
//			break;
//		case 3:
//			mTitle = getString(R.string.title_section3);
//			break;
//		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.crema, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((CremaActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	

	/*************************************************************************************************/
	// 공통 Handler
    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    super.handleMessage(msg);
            }
        }

    };
    
	/*************************************************************************************************/
	// Service 
//	CremaFileService mService;
//	boolean			mBound;
//	
//	private ServiceConnection	mServiceConnection = new ServiceConnection() {
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			// TODO Auto-generated method stub
//			CremaFileBinder binder = (CremaFileBinder) service;
//			mService = binder.getService();
//			mBound = true;
//			
//			//test!!!!!!!!!
//			mService.showToast("bind success!!!");
//		}
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			// TODO Auto-generated method stub
//			mBound = false;
//			
//		}
//		
//	};

	// Remote Service
	private IRemoteService	mService = null;
	boolean	mBound = false;
	private IRemoteServiceCallback mServiceCallback = new IRemoteServiceCallback.Stub() {
		public void MessageCallback(int msg) {
			mHandler.sendEmptyMessage(msg);
		}
	};
	
	private ServiceConnection	mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mService = IRemoteService.Stub.asInterface(service);
			mBound = true;
			try {
				mService.registerCallback(mServiceCallback);
			} catch(RemoteException e) {
				// TODO... 
				
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mService = null;
			mBound = false;
		}
		
	};
	
	/*************************************************************************************************/
	
	public static void updateLanguage(Context ctx, String lang)
	{
		Configuration cfg = new Configuration();
		if (!TextUtils.isEmpty(lang))
			cfg.locale = new Locale(lang);
		else
			cfg.locale = Locale.getDefault();
		
		ctx.getResources().updateConfiguration(cfg, null);
	}
	
	private void RegisterUpdateReceiver()
	{
	    IntentFilter intentFilter = new IntentFilter();
	    intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
	    intentFilter.addDataScheme("file");
	    mReceiver = new ExternalMountBroadcastReceiver();
	    this.registerReceiver(mReceiver, intentFilter);
	    
	    //<data android:scheme="file" />
	}
}
