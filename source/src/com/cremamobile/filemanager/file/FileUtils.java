package com.cremamobile.filemanager.file;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.cremamobile.filemanager.file.Exception.ExistSameFileNameException;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.os.StatFs;


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
	
	public static String getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();

        return formatSize(totalBlocks * blockSize);
	}
	
	public static String getAvaiableInternalMemorySize() {
		File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();

        return formatSize(availableBlocks * blockSize);

	}
	
	public static String getTotalExternalMemorySize() {
		if (isStorage(true) == true) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();

            return formatSize(totalBlocks * blockSize);
       } else {
            return formatSize(-1);
       }
	}
	
	public static String getAvaiableExternalMemorySize() {
		if (isStorage(true) == true) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return formatSize(-1);
        }
	}
	
	/** 보기 좋게 MB,KB 단위로 축소시킨다 */
    public static String formatSize(long size) {
         String suffix = null;
 
         if (size >= 1024) {
             suffix = "KB";
             size /= 1024;
             if (size >= 1024) {
                  suffix = "MB";
                  size /= 1024;
             }
             if (size >= 1024) {
                 suffix = "GB";
                 size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
 
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
             resultBuffer.insert(commaOffset, ',');
             commaOffset -= 3;
        }
 
         if (suffix != null) {
              resultBuffer.append(suffix);
         }
 
         return resultBuffer.toString();
    }
    
    /** 외장메모리 sdcard 사용가능한지에 대한 여부 판단 */
    public static boolean isStorage(boolean requireWriteAccess) {
         String state = Environment.getExternalStorageState();
 
          if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
          } else if (!requireWriteAccess &&
               Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
               return true;
          }
          return false;
    }
}
