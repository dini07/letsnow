package kr.letsnow.crema.file;

import java.io.File;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import kr.letsnow.crema.file.Exception.ExistSameFileNameException;

public class FileUtils {

	public static long getSize(File file) {
		if (file ==  null || file.exists())
			return 0;
		return file.length();
	}
	
	public static String name(File file) {
		if (file ==  null || file.exists())
			return null;
		return file.getName();
	}
	
	public static String getExt(File file) {
		if (file ==  null || file.exists())
			return null;
		
		String name = file.getName();
		int pos = name.lastIndexOf( "." );
		if (pos > 0) {
			return name.substring( pos + 1 );
		}
		return null;
	}
	
	public static String getExt(String name) {
		if (name ==  null || name.length() <= 0)
			return null;
		int pos = name.lastIndexOf( "." );
		if (pos > 0) {
			return name.substring( pos + 1 );
		}
		return null;
	}
	
	public static long getLastModified(File file) {
		if (file ==  null || file.exists())
			return 0;
		return file.lastModified();
	}
	
	public static boolean isDir(File file) {
		return file.isDirectory();
	}
	
	public static boolean delete(File file) {
		if (file == null || file.exists())
			return false;
		
		file.delete();
		return true;
	}
	
	public static boolean renam(File file, File new_file) throws ExistSameFileNameException {
		if (file == null || file.exists() || new_file == null)
			return false;
		
		if (new_file.exists())
			throw new ExistSameFileNameException(new_file);
		file.renameTo(new_file);
		return true;		
	}
	
	public static String[] getList(File dir){
        if(dir!=null&&dir.exists())
            return dir.list();
        return null;
    }
	
	public static Bitmap getThumbnail(File file) {
		//TODO.
		return null;
	}
}
