package com.cremamobile.filemanager.treeview;

public interface TreeViewAdapterParent<T> {
	void updateChildList(T parent, String path);
}
