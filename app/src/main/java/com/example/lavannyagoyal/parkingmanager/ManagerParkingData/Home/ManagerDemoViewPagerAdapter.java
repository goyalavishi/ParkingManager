package com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Calendar.CalenderFragment;
import com.example.lavannyagoyal.parkingmanager.ManagerParkingData.Today.TodayFragment;

import java.util.ArrayList;

public class ManagerDemoViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments = new ArrayList<>();
	private Fragment currentFragment;

	public ManagerDemoViewPagerAdapter(FragmentManager fm) {
		super(fm);

		fragments.clear();

		fragments.add(TodayFragment.newInstance(0));
		fragments.add(CalenderFragment.newInstance(1));
		//fragments.add(CameraFragment.newInstance(2));
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		if (getCurrentFragment() != object) {
			currentFragment = new TodayFragment();
		}
		super.setPrimaryItem(container, position, object);
	}

	public Fragment getCurrentFragment() {
		return currentFragment;
	}
}