package com.jei.widgettest;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jei.widgettest.view.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private ViewPager mVpTabs;
    private SlidingTabLayout mStlTabTitle;

    private Toolbar mTbtitle;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mAbDrawerToggle;

    private SearchView mSearchView;

    private MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mTbtitle = (Toolbar) findViewById(R.id.tb_title);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(mTbtitle);

        mAbDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mTbtitle, R.string.open_drawer, R.string.close_drawer);

        mDrawerLayout.setDrawerListener(mAbDrawerToggle);

        mVpTabs = (ViewPager) findViewById(R.id.vp_pager);
        DemoCollectionPagerAdapter adapter
                = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mVpTabs.setAdapter(adapter);

        mStlTabTitle = (SlidingTabLayout) findViewById(R.id.stl_title_strip);
        mStlTabTitle.setViewPager(mVpTabs);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAbDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager
                = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setIconified(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            /* return true일 때 키보드 유지, false 내려감. */
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit() s : " + s);
                if (s != null && !s.isEmpty()) {
                    Toast.makeText(mContext, "Query : " + s, Toast.LENGTH_SHORT).show();
//                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange() s : " + s);
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d(TAG, "onClose()");

                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        // Search 닫기.
        if (!mSearchView.isIconified()) {
            Log.d(TAG, "onBackPressed(), t");
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new EduDataFragment();
                    break;
                case 2:
                    fragment = new RecommDataFragment();
                    break;
                case 3:
                    fragment = new PopularDataFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return EduDataFragment.VIEW_TAG;
                case 2:
                    return RecommDataFragment.VIEW_TAG;
                case 3:
                    return PopularDataFragment.VIEW_TAG;
                default:
                    return HomeFragment.VIEW_TAG;

            }
        }
    }
}
