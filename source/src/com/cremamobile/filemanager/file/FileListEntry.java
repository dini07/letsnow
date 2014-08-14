package com.cremamobile.filemanager.file;

import java.io.File;
import java.util.Date;
import java.util.List;

public class FileListEntry {

	private boolean isDir = false;
	private File path;
	private String name;
	private String ext;
	private long size = 0;
	private Date lastModified;

	private int childFileNumber;
	private List<FileListEntry> child;
	
	public FileListEntry(String fqpath) {
		this.path = new File(fqpath);
		this.isDir = FileUtils.isDir(this.path);
		this.name = path.getName();
		int pos = name.lastIndexOf( "." );
		if (pos > 0) {
			this.ext = name.substring( pos + 1 );
		}
	}
	
	public FileListEntry(File file) {
		this.path = file;
		this.isDir = FileUtils.isDir(this.path);
		this.name = path.getName();
		int pos = name.lastIndexOf( "." );
		if (pos > 0) {
			this.ext = name.substring( pos + 1 );
		}
	}

	public FileListEntry() {}
	
	public File getPath() {
		return path;
	}
	public void setPath(File path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}

	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}

	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public boolean isDir() {
		return isDir;
	}
	public void isDir(boolean up) {
		this.isDir = up;
	}
	
	public int getChildFileNumber() {
		return childFileNumber;
	}
	public List<FileListEntry> getChildLists() {
		return this.child;
	}
	public void setChildLists(List<FileListEntry> list) {
		this.child = list;
		this.childFileNumber = this.child != null ? this.child.size() : 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileListEntry other = (FileListEntry) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FileListEntry[file:"+this.path
				+", name:"+this.name
				+", ext:"+this.ext
				+", isDir:"+this.isDir
				+", size:"+this.size
				+", lastModified:"+this.lastModified
				+", childFileNumber:"+this.childFileNumber;
	}
}