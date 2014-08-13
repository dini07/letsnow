package kr.letsnow.crema;

import java.util.List;

import android.content.Context;

public class Preference {
	static Context	context;
	public static final Preference instance = new Preference();
	
	public static Preference getInstance(Context c) {
		if (c == null) {
			return null;
		}
		context = c;
		return instance;
	}
	
	public List<String> getFindHistory() {
		return null;
	}
	
	public boolean setFindHistory(List<String> list) {
		return true;
	}
	
	public int getSortField() {
		return -1;
	}
	
	public boolean setSortField(int sortField) {
		return true;
	}
	
	public boolean getDirOnTop() {
		return true;
	}
	
	public boolean setDirOnTop(boolean isTop) {
		return true;
	}
	
	public int getViewType() {
		return -1;
	}
	
	public boolean setViewType(int viewType) {
		return true;
	}
	
	public int getThemeNumber() {
		return -1;
	}
	
	public boolean setThemeNumber(int theme) {
		return true;
	}
	
	public boolean getLastViewContinue() {
		return true;
	}
	
	public boolean setLastViewContinue(boolean cont) {
		return true;
	}
	
	public String getLastViewName() {
		return null;
	}
	
	public boolean setLastViewName(String name) {
		return true;
	}
	
	
}
