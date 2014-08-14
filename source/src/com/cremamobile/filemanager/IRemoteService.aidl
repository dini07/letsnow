package com.cremamobile.filemanager;

import com.cremamobile.filemanager.IRemoteServiceCallback;

interface IRemoteService {
	int getPid();
	void registerCallback(IRemoteServiceCallback cb);
	void unregisterCallback(IRemoteServiceCallback cb);
	String onService(int msg);
}