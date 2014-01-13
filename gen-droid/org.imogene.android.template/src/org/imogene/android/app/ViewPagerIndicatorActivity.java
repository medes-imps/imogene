package org.imogene.android.app;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;

import org.imogene.android.template.R;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ViewPagerIndicatorActivity extends GDActivity {

	private ViewPager  mViewPager;
	
	public ViewPagerIndicatorActivity() {
		super();
	}
	
	public ViewPagerIndicatorActivity(ActionBar.Type type) {
		super(type);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the indicator. We need some information here:
        // * What page do we start on.
        // * How many pages are there in total
        // * A callback to get page titles
		setActionBarContentView(R.layout.ig_view_pager);

		// Retrieve our viewpager
		mViewPager = (ViewPager)findViewById(R.id.ig_pager);
    }
    
    public ViewPager getViewPager() {
    	return mViewPager;
    }
    
    public void changePageIfNeeded() {
    	int current = mViewPager.getCurrentItem();
    	int count = mViewPager.getAdapter().getCount();
    	if (current >= count) {
    		mViewPager.setCurrentItem(count - 1, true);
    	}
    }
    
}