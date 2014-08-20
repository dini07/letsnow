package com.cremamobile.filemanager;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cremamobile.filemanager.file.FileListEntry;
import com.cremamobile.filemanager.file.FileLister;
import com.cremamobile.filemanager.file.FileUtils;
import com.cremamobile.filemanager.treeview.InMemoryTreeStateManager;
import com.cremamobile.filemanager.treeview.TreeBuilder;
import com.cremamobile.filemanager.treeview.TreeNodeInfo;
import com.cremamobile.filemanager.treeview.TreeStateManager;
import com.cremamobile.filemanager.treeview.TreeViewAdapter;
import com.cremamobile.filemanager.treeview.TreeViewAdapterParent;
import com.cremamobile.filemanager.treeview.TreeViewList;
import com.cremamobile.filemanager.utils.CLog;

import com.cremamobile.filemanager.R;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	//private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 0;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;

    private final Set<Long> selected = new HashSet<Long>();

    private View		containerView;
    private TreeViewList mDirectoryTreeView;
    private TextView mAvailableSizeTextView;
    private TextView mTotalSizeTextView;
    private ImageView mSizeProgressImageView;

    private TreeStateManager<Long> manager = null;
    private TreeViewAdapter treeViewAdapter;
    private boolean collapsible;
    private int	treeLevel = 1;

    TreeViewAdapterParent<Long> treeListParent = new TreeViewAdapterParent<Long>() {
		@Override
		public void updateChildList(Long parent, String path) {
			// TODO Auto-generated method stub
			updateTreeBuilder(parent, path);
		}    	
    };
    
    public void updateTreeBuilder(Long parent, String path) {
    	TreeBuilder<Long> treeBuilder = manager.getTreeBuilder();
    	
    	List<FileListEntry> files = FileLister.getDirectoryLists(new File(path), 2);
		if (files != null && files.size() > 0) {
			long i = treeBuilder.getLastAddedId();
			for (FileListEntry file : files) {
				CLog.d(this, "count:" + i + ", file["+file.toString()+"]");
				
				treeBuilder.addRelation(parent, ++i, file.getAbsolutePath(), file.getName(), false);
				
				long currentId = i;
				if (file.getChildFileNumber() > 0) {
					List<FileListEntry> childFiles = file.getChildLists();
					for (FileListEntry childFile : childFiles) {
						CLog.d(this, "  - child.. count:" + i + ", file["+file.toString()+"]");
						treeBuilder.addRelation(currentId, ++i, childFile.getAbsolutePath(), childFile.getName(), false);
					}
				}
				
				treeViewAdapter.collapse(currentId);
				
			}
			treeViewAdapter.collapse(parent);
		}
    }
    
	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);


		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		mDrawerListView = (ListView) inflater.inflate(
//				R.layout.fragment_navigation_drawer, container, false);
//		mDrawerListView
//				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,
//							int position, long id) {
//						selectItem(position);
//					}
//				});
//		mDrawerListView.setAdapter(new ArrayAdapter<String>(getActionBar()
//				.getThemedContext(), android.R.layout.simple_list_item_1,
//				android.R.id.text1, new String[] {
//						getString(R.string.title_section1),
//						getString(R.string.title_section2),
//						getString(R.string.title_section3), }));
//		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
//		return mDrawerListView;
		// TODO...
		boolean newCollapsible;
		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;

            manager = (TreeStateManager<Long>) savedInstanceState.getSerializable("treeManager");
            if (manager == null) {
                manager = new InMemoryTreeStateManager<Long>();
                manager.setTreeBuilder(new TreeBuilder<Long>(manager));
            }
            newCollapsible = savedInstanceState.getBoolean("collapsible");
            treeLevel = savedInstanceState.getInt("treeLevel");
		} else {
            manager = new InMemoryTreeStateManager<Long>();
            manager.setTreeBuilder(new TreeBuilder<Long>(manager));
            
    		setDirectoryListItem(manager.getTreeBuilder());
    		manager.collapseChildren(null);	//전부 닫는다.
    		
            CLog.d(this, manager.toString());
            newCollapsible = true;
		}

		View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

		mDirectoryTreeView = (TreeViewList) view.findViewById(R.id.directory_treeview);
		mSizeProgressImageView = (ImageView) view.findViewById(R.id.size_progress);

		mTotalSizeTextView = (TextView) view.findViewById(R.id.total_size);
		mAvailableSizeTextView = (TextView) view.findViewById(R.id.use_size);
		
        treeViewAdapter = new TreeViewAdapter(getActivity(), treeListParent, manager, treeLevel);
        mDirectoryTreeView.setAdapter(treeViewAdapter);
        setCollapsible(newCollapsible);
        registerForContextMenu(mDirectoryTreeView);

        setStorageSize();
		setSizeView();
		return view;
	}
	
	private void setStorageSize() {
		mTotalSizeTextView.setText(getString(R.string.total_size) + " " +FileUtils.getTotalExternalMemorySize());
		mAvailableSizeTextView.setText(getString(R.string.use_size) +  " " + FileUtils.getAvaiableExternalMemorySize());
	}
	
	private void setDirectoryListItem(TreeBuilder<Long> treeBuilder) {
		
//	    int[] DEMO_NODES = new int[] { 0, 0, 1, 1, 1, 2, 2, 1,
//            1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1, 2, 0, 1, 2, 0, 1 };
//	    int LEVEL_NUMBER = 4;

		FileLister.readVoldFile(getActivity().getApplicationContext());
		FileLister.testAndCleanList();
		FileLister.setProperties();
		
		List<FileListEntry> files = FileLister.getSDCardDirectoryLists(2);
		if (files != null && files.size() > 0) {
			long i = 0;
			for (FileListEntry file : files) {
				CLog.d(this, "count:" + i + ", file["+file.toString()+"]");
				treeBuilder.sequentiallyAddNextNode(i++, file.getAbsolutePath(), file.getName(), false, 0);
				if (file.getChildFileNumber() > 0) {
					List<FileListEntry> childFiles = file.getChildLists();
					for (FileListEntry childFile : childFiles) {
						CLog.d(this, "  - child.. count:" + i + ", file["+file.toString()+"]");
						treeBuilder.sequentiallyAddNextNode(i++, childFile.getAbsolutePath(), childFile.getName(), false, 1);
					}
				}
			}
			
			// addNodeToParentOneLevelDown
		}
		
		this.treeLevel = 1;
	}
	
	private void setSizeView() {
		
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.navigation_drawer_open, /*
										 * "open drawer" description for
										 * accessibility
										 */
		R.string.navigation_drawer_close /*
										 * "close drawer" description for
										 * accessibility
										 */
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}

				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to
					// prevent auto-showing
					// the navigation drawer automatically in the future.
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.apply();
				}

				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		//TODO.
//		if (mDrawerListView != null) {
//			mDrawerListView.setItemChecked(position, true);
//		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
		
        outState.putSerializable("treeManager", manager);
        outState.putBoolean("collapsible", this.collapsible);
        outState.putInt("treeLevel", this.treeLevel);
        
        super.onSaveInstanceState(outState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// If the drawer is open, show the global app actions in the action bar.
		// See also
		// showGlobalContextActionBar, which controls the top-left area of the
		// action bar.
		if (mDrawerLayout != null && isDrawerOpen()) {
			inflater.inflate(R.menu.global, menu);
			showGlobalContextActionBar();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		if (item.getItemId() == R.id.action_example) {
			Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT)
					.show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Per the navigation drawer design guidelines, updates the action bar to
	 * show the global app 'context', rather than just what's in the current
	 * screen.
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(R.string.app_name);
	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}
	
    protected final void setCollapsible(final boolean newCollapsible) {
        this.collapsible = newCollapsible;
        mDirectoryTreeView.setCollapsible(this.collapsible);
    }
}
