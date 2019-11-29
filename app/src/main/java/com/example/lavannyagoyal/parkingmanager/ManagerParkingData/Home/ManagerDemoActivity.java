package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.example.lavannyagoyal.parkinghero.R;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar.CalenderFragment;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today.TodayFragment;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import java.util.ArrayList;

public class ManagerDemoActivity extends AppCompatActivity implements TodayFragment.OnFragmentInteractionListenerToday, CalenderFragment.OnFragmentInteractionListenerCalendar {

	private Fragment currentFragment;
	private ManagerDemoViewPagerAdapter adapter;
	private AHBottomNavigationAdapter navigationAdapter;
	private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
	private boolean useMenuResource = true;
	private int[] tabColors;
	private Handler handler = new Handler();

//	private Fragment TodayFragment;

	TodayFragment todayFragment;

	// UI
	private AHBottomNavigationViewPager viewPager;
	private AHBottomNavigation bottomNavigation;
	//private FloatingActionButtoFn floatingActionButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//popup = new PopupWindow(this);
		boolean enabledTranslucentNavigation = getSharedPreferences("shared", Context.MODE_PRIVATE)
				.getBoolean("translucentNavigation", false);
		//setTheme(enabledTranslucentNavigation ? R.style.AppTheme_TranslucentNavigation : R.style.AppTheme);
		setContentView(R.layout.activity_home);
		initUI();

	//	todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentById(R.id.todayFragment);

	}






	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}

	private void initUI() {
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		}



		bottomNavigation = findViewById(R.id.bottom_navigation);
		viewPager = findViewById(R.id.view_pager);
		//floatingActionButton = findViewById(R.id.floating_action_button);

		if (useMenuResource) {
			tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
			navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_manager);
			navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
		}

		else {
			AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_money, R.color.themeColor);
			AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_calendar, R.color.themeColor);
			//AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3,R.drawable.ic_camera,R.color.themeColor);

			bottomNavigationItems.add(item1);
			bottomNavigationItems.add(item2);
		//	bottomNavigationItems.add(item3);
			bottomNavigation.addItems(bottomNavigationItems);
		}

		bottomNavigation.setAccentColor(Color.parseColor("#008080"));
		bottomNavigation.setTranslucentNavigationEnabled(true);
		bottomNavigation.setCurrentItem(0,true);
		bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
			@Override
			public boolean onTabSelected(int position, boolean wasSelected) {

				if (currentFragment == null) {
				}

				if (wasSelected) {
				}

				if (currentFragment != null) {
				}

				viewPager.setCurrentItem(position, false);
				currentFragment = adapter.getCurrentFragment();
                updateBottomNavigationItems(true);
				return true;
			}
		});

		viewPager.setOffscreenPageLimit(2);
		adapter = new ManagerDemoViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		currentFragment = adapter.getCurrentFragment();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Setting custom colors for notification
				AHNotification notification = new AHNotification.Builder()
						.setText(":)")
						.setBackgroundColor(ContextCompat.getColor(ManagerDemoActivity.this, R.color.color_notification_back))
						.setTextColor(ContextCompat.getColor(ManagerDemoActivity.this, R.color.color_notification_text))
						.build();
				bottomNavigation.setNotification(notification, 1);
				Snackbar.make(bottomNavigation, "Parking Hero welcomes you :)",
						Snackbar.LENGTH_SHORT).show();
			}
		}, 3000);
		
		//bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);
		bottomNavigation.setBehaviorTranslationEnabled(true);
	}

	/**
	 * Update the bottom navigation colored param
	 */
	public void updateBottomNavigationColor(boolean isColored) {
		bottomNavigation.setColored(isColored);
	}

	/**
	 * Return if the bottom navigation is colored
	 */
	public boolean isBottomNavigationColored() {
		return bottomNavigation.isColored();
	}

	/**
	 * Add or remove items of the bottom navigation
	 */
	public void updateBottomNavigationItems(boolean addItems) {

		if (useMenuResource) {
			if (addItems) {
				navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_manager);
				navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
				bottomNavigation.setNotification("2", 1);
			} else {
				navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_3);
				navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
			}

		} else {
			if (addItems) {
//				AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.tab_1),
//						ContextCompat.getDrawable(this, R.drawable.ic_hamburger_menu),
//						ContextCompat.getColor(this, R.color.themeColor));
//				AHBottomNavigationItem item5 = new AHBottomNavigationItem(getString(R.string.tab_2),
//						ContextCompat.getDrawable(this, R.drawable.ic_history),
//						ContextCompat.getColor(this, R.color.themeColor));
//
//				bottomNavigation.addItem(item4);
//				bottomNavigation.addItem(item5);
//				bottomNavigation.setNotification("2", 1);
			} else {
				bottomNavigation.removeAllItems();
				bottomNavigation.addItems(bottomNavigationItems);
			}
		}
	}

	/**
	 * Show or hide the bottom navigation with animation
	 */
	public void showOrHideBottomNavigation(boolean show) {
		if (show) {
			bottomNavigation.restoreBottomNavigation(true);
		} else {
			bottomNavigation.hideBottomNavigation(true);
		}
	}

	/**
	 * Show or hide selected item background
	 */
	public void updateSelectedBackgroundVisibility(boolean isVisible) {
		bottomNavigation.setSelectedBackgroundVisible(isVisible);
	}

	/**
	 * Set title state for bottomNavigation
	 */
	public void setTitleState(AHBottomNavigation.TitleState titleState) {
		bottomNavigation.setTitleState(titleState);
	}

	/**
	 * Reload activity
	 */

	/**
	 * Return the number of items in the bottom navigation
	 */
	public int getBottomNavigationNbItems() {
		return bottomNavigation.getItemsCount();
	}



    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

	@Override
	public void sendData(String data) {

	}

	public void switchtotodayfragment(String todayDate)
	{
		bottomNavigation.setCurrentItem(0);
		this.setTitle(todayDate);
		//getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, TodayFragment.newInstance(0)).commit();
	}
}