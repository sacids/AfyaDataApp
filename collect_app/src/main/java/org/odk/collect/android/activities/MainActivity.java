package org.odk.collect.android.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.odk.collect.android.R;
import org.odk.collect.android.adapters.NavDrawerListAdapter;
import org.odk.collect.android.fragments.CampaignFragment;
import org.odk.collect.android.models.NavDrawerItem;
import org.odk.collect.android.preferences.PreferencesActivity;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //deal with Navigation drawer
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        //View header = getLayoutInflater().inflate(R.layout.header_layout, null);
        //mDrawerList.addHeaderView(header);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Fill blank form
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Edit saved Forms
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Send Finalized forms
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Delete saved forms
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // Download Forms
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        //Forms Feedback
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
        // Health Tips
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
        //Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }


    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_fill_form:
                //fill blank form
                Intent blankForms = new Intent(getApplicationContext(),
                        FormChooserList.class);
                startActivity(blankForms);
                return true;

            case R.id.action_edit_form:
                //Edit forms
                Intent editForms = new Intent(getApplicationContext(),
                        InstanceChooserList.class);
                startActivity(editForms);
                return true;

            case R.id.action_send_form:
                //send finalized Forms
                Intent sendForms = new Intent(getApplicationContext(),
                        InstanceUploaderList.class);
                startActivity(sendForms);
                return true;

            case R.id.action_delete_form:
                //delete saved forms
                Intent deleteForms = new Intent(getApplicationContext(),
                        FileManagerTabs.class);
                startActivity(deleteForms);
                return true;

            case R.id.action_download_form:
                //Download form from server
                Intent downloadForms = new Intent(getApplicationContext(),
                        FormDownloadList.class);
                startActivity(downloadForms);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main_menu content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //campaign fragment
                fragment = new CampaignFragment();
                break;
            case 1:
                //fill blank form
                Intent blankForms = new Intent(getApplicationContext(),
                        FormChooserList.class);
                startActivity(blankForms);
                break;
            case 2:
                //Edit forms
                Intent editForms = new Intent(getApplicationContext(),
                        InstanceChooserList.class);
                startActivity(editForms);
                break;
            case 3:
                //send finalized Forms
                Intent sendForms = new Intent(getApplicationContext(),
                        InstanceUploaderList.class);
                startActivity(sendForms);
                break;
            case 4:
                //delete saved forms
                Intent deleteForms = new Intent(getApplicationContext(),
                        FileManagerTabs.class);
                startActivity(deleteForms);
                break;
            case 5:
                //Download form from server
                Intent downloadForms = new Intent(getApplicationContext(),
                        FormDownloadList.class);
                startActivity(downloadForms);
                break;
            case 6:
                //Forms feedback
                //fragment = new FormsFragment();
                break;
            case 7:
                //Health Tips
                //Intent healthTips = new Intent(getApplicationContext(), HealthTipsActivity.class);
                //startActivity(healthTips);
                break;
            case 8:
                //General Settings
                Intent mySettings = new Intent(getApplicationContext(), PreferencesActivity.class);
                startActivity(mySettings);
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
