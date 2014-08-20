package com.cremamobile.filemanager.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cremamobile.filemanager.utils.CLog;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

public class FileLister {
	public static List<FileListEntry> getSDCardDirectoryLists(int childDepth) {
		File sd = Environment.getExternalStorageDirectory();
		String state = Environment.getExternalStorageState();
		List<FileListEntry> list = new ArrayList<FileListEntry>();
		
		if (Environment.MEDIA_MOUNTED.equals(state) && sd.isDirectory()) {
            File[] fileArr = sd.listFiles();
            for (File f : fileArr) {
            	if (f.isDirectory()) {
            		FileListEntry entry = new FileListEntry(f);
            		if (childDepth > 0) {
            			entry.setChildLists(getDirectoryLists(f, childDepth-1));
            		}
            		list.add(entry);
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
	
	public static List<FileListEntry> getDirectoryLists(File parent, int childDepth) {
		if (parent == null)
			return null;
		
		List<FileListEntry> list = new ArrayList<FileListEntry>();
		File[] files = parent.listFiles();
		if (files == null || files.length <= 0)
			return null;
		
		for(File f : files) {
			if (f.isDirectory()) {
				FileListEntry entry = new FileListEntry(f);
				if (childDepth > 0) {
					entry.setChildLists(getDirectoryLists(f, childDepth-1));
				}
        		list.add(entry);
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
	
	public static void getAllLists() {
		/*
		 * Scan the /system/etc/vold.fstab file and look for lines like this:
		 * dev_mount sdcard /mnt/sdcard 1
		 * /devices/platform/s3c-sdhci.0/mmc_host/mmc0
		 * 
		 * When one is found, split it into its elements and then pull out the
		 * path to the that mount point and add it to the arraylist
		 * 
		 * some devices are missing the vold file entirely so we add a path here
		 * to make sure the list always includes the path to the first sdcard,
		 * whether real or emulated.
		 */

		//sVold.add("/mnt/sdcard");
		

		try {
			Scanner scanner = new Scanner(new File("/system/etc/vold.fstab"));
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.startsWith("dev_mount")) {
					String[] lineElements = line.split(" ");
					String element = lineElements[2];

					if (element.contains(":"))
						element = element.substring(0, element.indexOf(":"));

					if (element.contains("usb"))
						continue;

					// don't add the default vold path
					// it's already in the list.
//					if (!sVold.contains(element))
//						sVold.add(element);
				}
			}
			scanner.close();
		} catch (Exception e) {
			// swallow - don't care
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public static String[] labels;
	public static String[] paths;
	public static int count = 0;

	private static Context sContext;
	private static ArrayList<String> sVold = new ArrayList<String>();

	public static void readVoldFile(Context context) {
		sContext = context;
		/*
		 * Scan the /system/etc/vold.fstab file and look for lines like this:
		 * dev_mount sdcard /mnt/sdcard 1
		 * /devices/platform/s3c-sdhci.0/mmc_host/mmc0
		 * 
		 * When one is found, split it into its elements and then pull out the
		 * path to the that mount point and add it to the arraylist
		 * 
		 * some devices are missing the vold file entirely so we add a path here
		 * to make sure the list always includes the path to the first sdcard,
		 * whether real or emulated.
		 */

		sVold.add("/mnt/sdcard");

		try {
			Scanner scanner = new Scanner(new File("/system/etc/vold.fstab"));
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.startsWith("dev_mount")) {
					String[] lineElements = line.split(" ");
					String element = lineElements[2];

					if (element.contains(":"))
						element = element.substring(0, element.indexOf(":"));

					if (element.contains("usb"))
						continue;

					// don't add the default vold path
					// it's already in the list.
					if (!sVold.contains(element))
						sVold.add(element);
				}
			}
		} catch (Exception e) {
			// swallow - don't care
			e.printStackTrace();
		}
	}

	public static void testAndCleanList() {
		/*
		 * Now that we have a cleaned list of mount paths, test each one to make
		 * sure it's a valid and available path. If it is not, remove it from
		 * the list.
		 */

		for (int i = 0; i < sVold.size(); i++) {
			String voldPath = sVold.get(i);
			File path = new File(voldPath);
			CLog.d("FileLister", "file:"+path.getName() + ", isDirectory:"+path.isDirectory() + ", canWrite:"+path.canWrite());
			if (!path.exists() || !path.isDirectory() || !path.canWrite())
				sVold.remove(i--);
		}
	}

	public static void setProperties() {
		/*
		 * At this point all the paths in the list should be valid. Build the
		 * public properties.
		 */

		ArrayList<String> labelList = new ArrayList<String>();

		int j = 0;
		if (sVold.size() > 0) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD)
				labelList.add("Auto");
			else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				if (Environment.isExternalStorageRemovable()) {
					labelList.add("external storage" + " 1");
					j = 1;
				} else
					labelList.add("internal storage");
			} else {
				if (!Environment.isExternalStorageRemovable()
						|| Environment.isExternalStorageEmulated())
					labelList.add("internal storage");
				else {
					labelList.add("external storage" + " 1");
					j = 1;
				}
			}

			if (sVold.size() > 1) {
				for (int i = 1; i < sVold.size(); i++) {
					labelList.add("external storage"
							+ " " + (i + j));
				}
			}
		}

		labels = new String[labelList.size()];
		labelList.toArray(labels);

		paths = new String[sVold.size()];
		sVold.toArray(paths);

		count = Math.min(labels.length, paths.length);

		/*
		 * don't need these anymore, clear the lists to reduce memory use and to
		 * prepare them for the next time they're needed.
		 */
		sVold.clear();
	}
}
