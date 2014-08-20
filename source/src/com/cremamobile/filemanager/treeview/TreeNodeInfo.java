package com.cremamobile.filemanager.treeview;

/**
 * Information about the node.
 * 
 * @param <T>
 *            type of the id for the tree
 */
public class TreeNodeInfo<T> {
    private final T id;
    private final String absolutePath;
    private final String name;
    private final int level;
    private final boolean isRoot;
    private boolean withChildren;
    private boolean visible;
    private boolean expanded;
    private boolean needSearchChild;

    /**
     * Creates the node information.
     * 
     * @param id
     *            id of the node
     * @param level
     *            level of the node
     * @param withChildren
     *            whether the node has children.
     * @param visible
     *            whether the tree node is visible.
     * @param expanded
     *            whether the tree node is expanded
     * 
     */
    public TreeNodeInfo(final T id, final String path, final String name, final boolean isRoot, final int level,
            boolean withChildren, boolean visible,
            boolean expanded, boolean needSearchChild) {
        super();
        this.id = id;
        this.absolutePath = path;
        this.name = name;
        this.isRoot = isRoot;
        this.level = level;
        this.withChildren = withChildren;
        this.visible = visible;
        this.expanded = expanded;
    	this.needSearchChild = needSearchChild;
    }

    public T getId() {
        return id;
    }

    public String getName() {
    	return name;
    }
    
    public String getPaht() {
    	return absolutePath;
    }
    
    public boolean isWithChildren() {
        return withChildren;
    }
    
    public boolean isNeedSearchChild() {
    	return needSearchChild;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public int getLevel() {
        return level;
    }

    public boolean isRoot() {
    	return isRoot;
    }
    
    @Override
    public String toString() {
        return "TreeNodeInfo [id=" + id
        		+ ", absolutePath=" + absolutePath
        		+ ", name="+name
        		+ ", isRoot="+isRoot
        		+ ", level=" + level
                + ", withChildren=" + withChildren
                + ", needSearchChildren=" + needSearchChild
                + ", visible=" + visible
                + ", expanded=" + expanded + "]";
    }

}