package edu.toronto.ece1778.urbaneyes;

import java.util.ArrayList;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity showing the map with the current position.
 * 
 * @author mcupak
 * 
 */
public class MapActivity extends AbstractMapActivity implements
		OnNavigationListener, OnInfoWindowClickListener, LocationSource,
		LocationListener {

	private static final String STATE_NAV = "nav";
	private static final int[] MAP_TYPE_NAMES = { R.string.normal,
			R.string.hybrid, R.string.satellite, R.string.terrain };
	private static final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_NORMAL,
			GoogleMap.MAP_TYPE_HYBRID, GoogleMap.MAP_TYPE_SATELLITE,
			GoogleMap.MAP_TYPE_TERRAIN };
	private GoogleMap map = null;
	private OnLocationChangedListener mapLocationListener = null;
	private LocationManager locMgr = null;
	private LatLng currentLocation = new LatLng(0, 0);
	private String currentProject = "Project 1";

	private Criteria crit = new Criteria();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// prepare maps
		if (readyToGo()) {
			setContentView(R.layout.activity_map);

			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			initListNav();

			map = mapFrag.getMap();

			if (savedInstanceState == null) {
				CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(
						40.76793169992044, -73.98180484771729));
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

				map.moveCamera(center);
				map.animateCamera(zoom);
			}

			map.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
			map.setOnInfoWindowClickListener(this);

			locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
			crit.setAccuracy(Criteria.ACCURACY_FINE);

			map.setMyLocationEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(false);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		locMgr.requestLocationUpdates(0L, 0.0f, crit, this, null);
		map.setLocationSource(this);
	}

	@Override
	public void onPause() {
		map.setLocationSource(null);
		locMgr.removeUpdates(this);

		super.onPause();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		map.setMapType(MAP_TYPES[itemPosition]);

		return (true);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putInt(STATE_NAV, getSupportActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		getSupportActionBar().setSelectedNavigationItem(
				savedInstanceState.getInt(STATE_NAV));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addPoint:
			// create a point
			addPoint(map, currentLocation, currentProject,
					currentLocation.toString());
			return (true);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO: add call to create survey
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		this.mapLocationListener = listener;
	}

	@Override
	public void deactivate() {
		this.mapLocationListener = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (mapLocationListener != null) {
			mapLocationListener.onLocationChanged(location);

			currentLocation = new LatLng(location.getLatitude(),
					location.getLongitude());
			CameraUpdate cu = CameraUpdateFactory.newLatLng(currentLocation);

			map.animateCamera(cu);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// unused
	}

	@Override
	public void onProviderEnabled(String provider) {
		// unused
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// unused
	}

	private void initListNav() {
		ArrayList<String> items = new ArrayList<String>();
		ArrayAdapter<String> nav = null;
		ActionBar bar = getSupportActionBar();

		for (int type : MAP_TYPE_NAMES) {
			items.add(getString(type));
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			nav = new ArrayAdapter<String>(bar.getThemedContext(),
					android.R.layout.simple_spinner_item, items);
		} else {
			nav = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, items);
		}

		nav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setListNavigationCallbacks(nav, this);
	}

	private void addPoint(GoogleMap map, LatLng latLng, String title,
			String snippet) {
		map.addMarker(new MarkerOptions().position(latLng).title(title)
				.snippet(snippet));
	}
}
