package com.cremamobile.filemanager.file;

import com.cremamobile.filemanager.file.Exception.ExistSameFileNameException;

public interface FileInterface {
	public static final int PASTE_MODE_COPY = 0;
	public static final int PASTE_MODE_MOVE = 1;

	public static final int	FILE_TYPE_ROOT = 0;
	public static final int	FILE_TYPE_EXTEND_DEVICE = 1;
	public static final int	FILE_TYPE_DIR = 2;
	public static final int FILE_TYPE_NORMAL = 3;
	
	public static final int	FILE_TYPE_AUDIO = 100;
	public static final int	FILE_TYPE_VIDEO = 101;
	public static final int	FILE_TYPE_IMAGE = 102;
	public static final int FILE_TYPE_APK = 103;
	public static final int	FILE_TYPE_ZIP = 104;
	
	public static final int	FILE_TYPE_OFFICE = 200;
	public static final int	FILE_TYPE_WORD = 201;
	public static final int	FILE_TYPE_POWERPOINT = 202;
	public static final int	FILE_TYPE_EXCEL = 203;

	public void filesAll();
	public void filesForDrive(String driveName);
	public void filesForDirectory(String uri);
	public void copyFiles();
	public void copyFile();
	public void cutFiles();
	public void cutFile();
	public void pauseFiles() throws ExistSameFileNameException;
	public void pauseFile() throws ExistSameFileNameException;
	public void refreshFilesAll();
	public void refreshFilesForDrive(String uri);
	public void refreshFilesForDirectory(String uri);
	public boolean changeName(String uri) throws ExistSameFileNameException;
}
