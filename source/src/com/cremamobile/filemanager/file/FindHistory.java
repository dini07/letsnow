package com.cremamobile.filemanager.file;

// testing.. done. 
import java.util.ArrayList;
import java.util.List;

public class FindHistory {
	private static final FindHistory instance = new FindHistory(10);
	
	private int front = 0;
	private int insertSize = 0;
	private int size;
	private String[]	array;

	private FindHistory(int size) {
		this.size = size;	// 실제보다 하나를 크게 지정한다. 
		this.array = new String[size];
	}
	
	public static FindHistory getInstance() {
		return instance;
	}
	
	public boolean empty() {
		return (insertSize == 0); 
	}

	public boolean full() {
		if (insertSize >= size)
			return true;
		return false;
	}
	
	public void insert(String item) {
		if (full()) {
			int currentItem = (front+insertSize)%size;
			array[currentItem] = String.valueOf(item);
			
			front = (front+1)%size;
		} else {
			array[insertSize++] = String.valueOf(item);
		}
	}
	
	public List<String> getAll() {
		if (empty())
			return null;
		
		List<String> retList = new ArrayList<String>();
		
		int i=0;
		while(i<=insertSize) {
			retList.add( array[(front+i)%size] );
		}
		
		return retList;
	}
	
	public void putAll(List<String> list) {
		if (list == null || list.size() == 0) {
			front = 0;
			insertSize = 0;
			array = new String[size];
		} else {
			front = 0;
			insertSize = list.size();
			for (int i=0; i< size; i++) {
				array[i] = list.get(i);
			}
		}
	}	
}
