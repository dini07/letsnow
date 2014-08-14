package com.cremamobile.filemanager.file.Exception;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExistSameFileNameException extends Exception {
	int count;
	//같은 이름의 파일리스트.
	List<File> files;
	
	public ExistSameFileNameException(File file) {
		super();
		
		// TODO Auto-generated constructor stub
		this.count = 1;
		files = new ArrayList<File>();
		files.add(file);
	}
	
	public int getCount() {
		return count;
	}
	
	public List<File> getList() {
		return files;
	}
}
