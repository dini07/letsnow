package com.cremamobile.filemanager.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class FileLister {
	public static List<FileListEntry> getSDCardDirectoryLists() {
		File sd = Environment.getExternalStorageDirectory();
		String state = Environment.getExternalStorageState();
		List<FileListEntry> list = new ArrayList<FileListEntry>();
		
		if (Environment.MEDIA_MOUNTED.equals(state) && sd.isDirectory()) {
            File[] fileArr = sd.listFiles();
            for (File f : fileArr) {
            	if (f.isDirectory()) {
            		list.add(new FileListEntry(f));
            	}
            }
		}
		return list;
	}
	
	public static List<FileListEntry> getDirectoryLists(File parent) {
		if (parent == null)
			return null;
		
		List<FileListEntry> list = new ArrayList<FileListEntry>();
		File[] files = parent.listFiles();
		if (files == null || files.length <= 0)
			return null;
		
		for(File f : files) {
			if (f.isDirectory()) {
        		list.add(new FileListEntry(f));
			}
		}
		return list;
	}
	
	public static List<FileListEntry> getSDCardAllDirectoryLists() {
		File sd = Environment.getExternalStorageDirectory();
		String state = Environment.getExternalStorageState();
		List<FileListEntry> list = new ArrayList<FileListEntry>();
		
		if (Environment.MEDIA_MOUNTED.equals(state) && sd.isDirectory()) {
            File[] fileArr = sd.listFiles();
            FileListEntry entry;
            for (File f : fileArr) {
            	if (f.isDirectory()) {
            		entry = new FileListEntry(f);
            		entry.setChildLists(getDirectoryAllLists(f));
            		list.add(entry);
            		
            	}
            }
		}
		return list;
	}
	
	public static List<FileListEntry> getDirectoryAllLists(File parent) {
		if (parent == null)
			return null;
		
		List<FileListEntry> list = new ArrayList<FileListEntry>();
		File[] files = parent.listFiles();
		if (files == null || files.length <= 0)
			return null;
		
		FileListEntry entry;
		for(File f : files) {
			if (f.isDirectory()) {
				entry = new FileListEntry(f);
        		entry.setChildLists(getDirectoryAllLists(f));
        		list.add(entry);
			}
		}
		return list;
	}
}
