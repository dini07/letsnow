package kr.letsnow.crema.file;

import java.util.Comparator;

import android.content.Context;

public class FileSorter implements Comparator<FileListEntry> {
	public static final int SORT_BY_NAME_ASC = 0;
	public static final int SORT_BY_NAME_DES = 1;
	
	public static final int SORT_BY_SIZE_ASC = 2;
	public static final int SORT_BY_SIZE_DES = 3;
	
	public static final int SORT_BY_DATE_ASC = 4;
	public static final int SORT_BY_DATE_DES = 5;
	
	public static final int SORT_BY_EXT_ASC = 6;
	public static final int SORT_BY_EXT_DES = 7;
	
	private Context context;
	private boolean dirOnTop = true;
	private int	sortField;
	private int dir;
		
	public FileSorter(Context context){
		this.context = context;
	}
	
	public void setDirOnTop(boolean isTop) {
		this.dirOnTop = isTop;
	}
	
	public void setSortField(int sortField) {
		this.sortField = sortField;
	}
	
	@Override
	public int compare(FileListEntry file1, FileListEntry file2) {
//TODO. TEST!!!
		
		if(dirOnTop)
		{
			if(file1.isDir() && !file2.isDir()) {
				return -1;
			} else if(file2.isDir() && !file1.isDir()) {
				return 1;
			}
		}
		
		switch (sortField) {
		case SORT_BY_NAME_ASC:
			return dir * file1.getName().compareToIgnoreCase(file2.getName());
		
		case SORT_BY_NAME_DES:
			return dir * file2.getName().compareToIgnoreCase(file1.getName());
			
		case SORT_BY_DATE_ASC:
			return dir * file1.getLastModified().compareTo(file2.getLastModified());

		case SORT_BY_DATE_DES:
			return dir * file2.getLastModified().compareTo(file1.getLastModified());
			
		case SORT_BY_SIZE_ASC:
			return dir * Long.valueOf(file1.getSize()).compareTo(file2.getSize());
			
		case SORT_BY_SIZE_DES:
			return dir * Long.valueOf(file2.getSize()).compareTo(file1.getSize());
			
		default:
			break;
		}
		
		return 0;
	}
}
