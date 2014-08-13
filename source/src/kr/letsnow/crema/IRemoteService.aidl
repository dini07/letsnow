package kr.letsnow.crema;

import kr.letsnow.crema.IRemoteServiceCallback;

interface IRemoteService {
	int getPid();
	void registerCallback(IRemoteServiceCallback cb);
	void unregisterCallback(IRemoteServiceCallback cb);
	String onService(int msg);
}