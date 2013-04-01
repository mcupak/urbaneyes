package edu.toronto.ece1778.urbaneyes;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
// import android.view.Menu;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import edu.toronto.ece1778.urbaneyes.model.*;

/**
 * Activity showing the map with the current position.
 * 
 * @author mcupak
 * @author rkalyani
 * 
 */
public class MapActivity extends AbstractMapActivity implements
		OnNavigationListener, OnInfoWindowClickListener, OnMarkerDragListener,
		LocationSource, LocationListener {

	public static final String TAG = "URBANEYES";
	private static final String STATE_NAV = "nav";
	private static final int[] MAP_TYPE_NAMES = { R.string.normal,
			R.string.hybrid, R.string.satellite, R.string.terrain };
	private static final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_NORMAL,
			GoogleMap.MAP_TYPE_HYBRID, GoogleMap.MAP_TYPE_SATELLITE,
			GoogleMap.MAP_TYPE_TERRAIN };
	private GoogleMap map = null;
	private static final LatLng DEFAULT_LOCATION = new LatLng(
			43.660964, -79.398461);
	private OnLocationChangedListener mapLocationListener = null;
	private LocationManager locMgr = null;
	private LatLng currentLocation = DEFAULT_LOCATION;
	private float currentAltitude = 0;
	private String currentProject = "Project 1";
	private SensorManager mSensorManager = null;

	boolean beginPath = false;
	boolean endPath = false;

	boolean beginPoly = false;
	boolean endPoly = false;
	
	private Criteria crit = new Criteria();
	private AltitudeProcessor altitudeProcessor = new AltitudeProcessor();
	private SensorEventListener mSensorListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// when accuracy changed, this method will be called.
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (Sensor.TYPE_PRESSURE == event.sensor.getType()) {
				currentAltitude = altitudeProcessor
						.getAltitude(event.values[0]);
			}
		}
	};

	// task downloading the reference pressure value by location
	class ComputePressureTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			altitudeProcessor.computeRefPressure(currentLocation.longitude,
					currentLocation.latitude);
			return null;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// prepare maps
		if (readyToGo()) {
			setContentView(R.layout.activity_map);

			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			initListNav();

			map = mapFrag.getMap();

			if (savedInstanceState == null) {
				CameraUpdate center = CameraUpdateFactory
						.newLatLng(DEFAULT_LOCATION);
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

				map.moveCamera(center);
				map.animateCamera(zoom);
			}

			map.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
			map.setOnInfoWindowClickListener(this);
			map.setOnMarkerDragListener(this);

			locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
			crit.setAccuracy(Criteria.ACCURACY_FINE);

			map.setMyLocationEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(false);

			// load params
			// TODO: check current survey type set
			int i = getIntent().getIntExtra("selectedProject", 0);
			currentProject = SurveyStateHolder.getSurveyNames().get(i);
			SurveyStateHolder.setCurrentSurveyType(currentProject);
			/*
			case 0:
				currentProject = "Food vendor";
				break;
			case 1:
				currentProject = "Subway entrance";
				break;
			default:
				currentProject = "Uncategorized";
				break;
			}
			*/
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		locMgr.requestLocationUpdates(0L, 0.0f, crit, this, null);
		map.setLocationSource(this);
	}

	@Override
	public void onPause() {
		map.setLocationSource(null);
		locMgr.removeUpdates(this);
		mSensorManager.unregisterListener(mSensorListener);

		super.onPause();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		map.setMapType(MAP_TYPES[itemPosition]);

		return (true);
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		// empty
	}

	@Override
	public void onMarkerDrag(Marker marker) {
		// empty
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		LatLng position = marker.getPosition();
		marker.setSnippet(formatSnippet(currentLocation, currentAltitude));
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
			clearResultsData();
			openSurvey();
			
			return (true);
		case R.id.beginPath:
			beginPath();
			return true;
		case R.id.endPath:
			endPath();
			return true;
		case R.id.beginPoly:
			beginPoly();
			return true;
		case R.id.endPoly:
			endPoly();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		openSurvey();
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

			// compute reference altitude when location is registered for the
			// first time
			// if (!altitudeProcessor.isComputed()) {     // COMMENTED BY RAVI
				new ComputePressureTask().execute(null, null, null);
			// }       // COMMENTED BY RAVI

			// center camera on location
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

	private void openSurvey() {
		Intent i = new Intent(this, StartSurveyActivity.class);
		startActivityForResult(i, 1);
		/*
		switch (getIntent().getIntExtra("selectedProject", 0)) {
		case 0:
			i = new Intent(this, FoodVendorSurveyActivity.class);
			startActivityForResult(i, 1);
			break;
		case 1:
			i = new Intent(this, SubwayEntranceSurvey.class);
			startActivityForResult(i, 1);
			break;
		default:
			i = new Intent(this, FoodVendorSurveyActivity.class);
			startActivityForResult(i, 1);
			break;
		}
		*/
	}

	private void addPoint(GoogleMap map, LatLng latLng, String title,
			String snippet) {
		map.addMarker(new MarkerOptions().position(latLng).title(title)
				.snippet(snippet).draggable(true));
		SurveyStateHolder.addSurveyPoint(latLng, currentAltitude);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				// String result=data.getStringExtra("result");
				if (SurveyStateHolder.getCurrentSurveyType().getKind() == SurveyKind.POINT) {
					addPoint(map, currentLocation, currentProject,
							formatSnippet(currentLocation, currentAltitude));
					sendPointSurveyResults();     // WORKS FOR POINT SURVEY
				}
			}
			if (resultCode == RESULT_CANCELED) {
				// clean up
			}
		}
	}

	private String formatSnippet(LatLng position, float altitude) {
		StringBuilder output = new StringBuilder();
		output.append("Lat: ");
		output.append(position.latitude);
		output.append("\n");
		output.append("Lon: ");
		output.append(position.longitude);
		output.append("\n");
		output.append("Alt: ");
		output.append(altitude);
		output.append("\n");

		return output.toString();
	}


	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear(); //Clear view of previous menu
        if(SurveyStateHolder.getCurrentSurveyType().getKind() == SurveyKind.POINT) {
        	getSupportMenuInflater().inflate(R.menu.map, menu);
        } else if(SurveyStateHolder.getCurrentSurveyType().getKind() == SurveyKind.PATH) {
        	if (beginPath == false) {
        		getSupportMenuInflater().inflate(R.menu.map_path, menu);
        	} else {
        		getSupportMenuInflater().inflate(R.menu.map_end_path, menu);
        	}
        } else if(SurveyStateHolder.getCurrentSurveyType().getKind() == SurveyKind.POLYGON) {
        	if (beginPoly == false) {
        		getSupportMenuInflater().inflate(R.menu.map_begin_poly, menu);
        	} else {
        		getSupportMenuInflater().inflate(R.menu.map_end_poly, menu);
        	}
        	
        } 

        return super.onPrepareOptionsMenu(menu);
    }	

	/*  PATH RELATED CODE */
	
	
	class TrackPointsOnPathTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			LatLng prev = new LatLng(currentLocation.latitude, currentLocation.longitude);
			final double RADIUS = 6731;
			final double THRESHOLD = 10;   // meters

			while (!endPath) {
				double lat1 = Math.toRadians(prev.latitude);
				double lat2 = Math.toRadians(currentLocation.latitude);
				
				double lon1 = Math.toRadians(prev.longitude);
				double lon2 = Math.toRadians(currentLocation.longitude);
				
				double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + 
		                  			 Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2-lon1)) * RADIUS * 1000;
				if (d >= THRESHOLD) {
					final LatLng fNewCurrent = new LatLng(currentLocation.latitude, currentLocation.longitude);
					final LatLng fprev = prev;

					MapActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							map.addPolyline(new PolylineOptions()
						     .add(fprev, fNewCurrent)
						     .width(5)
						     .color(Color.RED));
						}
					});
					prev = fNewCurrent;
				}
				try {
					Thread.sleep(5000);  // sleep for 5 seconds
				} catch (InterruptedException e) {
					
				}
			}
			return null;
		}

	}

	class SimulatePointsOnPathTask extends AsyncTask<Void, Void, Void> {

		
		@Override
		protected Void doInBackground(Void... params) {
			
			LatLng savedCurrentLocation = currentLocation;
			
			ArrayList<LatLng> points = new ArrayList<LatLng>();
			// points.add(new LatLng(43.659614, -79.396851));
			points.add(new LatLng(43.659715, -79.396883));
			points.add(new LatLng(43.659825, -79.396883));
			points.add(new LatLng(43.660072, -79.396969));
			points.add(new LatLng(43.660180, -79.397001));
			points.add(new LatLng(43.660180, -79.397001));
			points.add(new LatLng(43.660242, -79.397034));
			points.add(new LatLng(43.660421, -79.397120));
			points.add(new LatLng(43.660374, -79.397441));
			points.add(new LatLng(43.660297, -79.397774));
			points.add(new LatLng(43.660436, -79.397838));
			points.add(new LatLng(43.660553, -79.397903));
			points.add(new LatLng(43.660677, -79.397967));
			points.add(new LatLng(43.660755, -79.398042));
			points.add(new LatLng(43.660700, -79.398235));
			points.add(new LatLng(43.660762, -79.398321));
			points.add(new LatLng(43.660964, -79.398461));
			
			LatLng prev = new LatLng(43.659614, -79.396851);
			
			for (LatLng loc : points) {
				final LatLng fprev = prev;
				final LatLng floc = loc;
				MapActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						map.addPolyline(new PolylineOptions()
					     .add(fprev, floc)
					     .width(5)
					     .color(Color.RED));
					}
				});

				prev = loc;
				try {
					Thread.sleep(500);  // sleep for half seconds
				} catch (InterruptedException e) {
					
				}
			}
			currentLocation = savedCurrentLocation;
			return null;
		}

	}

	public void beginPath() {
		beginPath = true;
		endPath = false;
		if (SurveyStateHolder.getCurrentSurveyType().getName().equals("Path Simulation")) {
			new SimulatePointsOnPathTask().execute(null, null, null);
		} else {
			new TrackPointsOnPathTask().execute(null, null, null);
		}
		invalidateOptionsMenu();
	}
	
	public void endPath() {
		beginPath = false;
		endPath = true;
		invalidateOptionsMenu();
		if (SurveyStateHolder.getCurrentSurveyType().getQuestions() != null &&
			SurveyStateHolder.getCurrentSurveyType().getQuestions().size() > 0) {
			openSurvey();
		}
	}

	/* POLYGON code */

	public void beginPoly() {
		beginPoly = true;
		endPoly = false;
		if (SurveyStateHolder.getCurrentSurveyType().getName().equals("Polygon Simulation : Building Survey")) {
			new SimulatePointsOnPolyTask().execute(null, null, null);
		} else {
			new TrackPointsOnPolyTask().execute(null, null, null);
		}
		invalidateOptionsMenu();
	}
	
	public void endPoly() {
		beginPoly = false;
		endPoly = true;
		invalidateOptionsMenu();
		if (SurveyStateHolder.getCurrentSurveyType().getQuestions() != null &&
			SurveyStateHolder.getCurrentSurveyType().getQuestions().size() > 0) {
			openSurvey();
		}
	}

	class TrackPointsOnPolyTask extends AsyncTask<Void, Void, Void> {

		PolygonOptions po = new PolygonOptions();

		@Override
		protected Void doInBackground(Void... params) {
			
			LatLng prev = new LatLng(currentLocation.latitude, currentLocation.longitude);
			final double RADIUS = 6731;
			final double THRESHOLD = 10;   // meters

			final LatLng first = prev;
			
			while (!endPoly) {
				double lat1 = Math.toRadians(prev.latitude);
				double lat2 = Math.toRadians(currentLocation.latitude);
				
				double lon1 = Math.toRadians(prev.longitude);
				double lon2 = Math.toRadians(currentLocation.longitude);
				
				double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + 
		                  			 Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2-lon1)) * RADIUS * 1000;
				if (d >= THRESHOLD) {
					final LatLng fNewCurrent = new LatLng(currentLocation.latitude, currentLocation.longitude);
					final LatLng fprev = prev;
					
					MapActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							po.add(currentLocation);
							map.addPolyline(new PolylineOptions()
						     .add(fprev, fNewCurrent)
						     .width(5)
						     .color(Color.RED));

						}
					});
					prev = fNewCurrent;
				}
				try {
					Thread.sleep(5000);  // sleep for 5 seconds
				} catch (InterruptedException e) {
					
				}
			}
			final LatLng fprev  = prev;
			MapActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					po.add(first);
					map.addPolyline(new PolylineOptions()
				     .add(fprev, first)
				     .width(5)
				     .color(Color.RED));
				}
			});

			MapActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Polygon p = map.addPolygon(po.strokeColor(Color.RED).fillColor(Color.BLUE));
				}
			});
			return null;
		}

	}

	class SimulatePointsOnPolyTask extends AsyncTask<Void, Void, Void> {

		PolygonOptions po;
		LatLng point;
		@Override
		protected Void doInBackground(Void... params) {
			
			ArrayList<LatLng> points = new ArrayList<LatLng>();
			// points.add(new LatLng(43.660002, -79.397036));
			points.add(new LatLng(43.659738, -79.3980002));
			points.add(new LatLng(43.659226, -79.397809));
			points.add(new LatLng(43.659427, -79.396768));
			final LatLng first = new LatLng(43.660002, -79.397036);
			LatLng prev = first;
			po = new PolygonOptions();
			for (LatLng loc : points) {
				point = loc;
				final LatLng fprev = prev;
				final LatLng floc = loc;
	
				MapActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						po.add(point);
						/*
						addPoint(map, point, currentProject,
								 formatSnippet(point, currentAltitude));
								 */
						map.addPolyline(new PolylineOptions()
					     .add(fprev, floc)
					     .width(5)
					     .color(Color.RED));
					}
				});
				prev = loc;
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					
				}
						
			}
			final LatLng fprev  = prev;
			MapActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					po.add(first);
					/*
					addPoint(map, point, currentProject,
							 formatSnippet(point, currentAltitude));
							 */
					map.addPolyline(new PolylineOptions()
				     .add(fprev, first)
				     .width(5)
				     .color(Color.RED));
				}
			});

			MapActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Polygon p = map.addPolygon(po.strokeColor(Color.RED).fillColor( Color.CYAN | Color.TRANSPARENT));
				}
			});

			return null;
		}

	}

	// SEND RESULTS
	
	private void clearResultsData() {
		SurveyStateHolder.clearResults();
	}
	
	
	private void sendPointSurveyResults() {
		new SendPointSurveyResultsTask().execute();
	}

	class SendPointSurveyResultsTask extends AsyncTask<Void, Void, Void> {

		protected Void doInBackground(Void... params) {
			
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://urbaneyes-mcupak.rhcloud.com/upload");

		    try {
		        // Add data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		        nameValuePairs.add(new BasicNameValuePair("user", String.valueOf(SurveyStateHolder.getUserId())));
		        // Answer a = SurveyStateHolder.getSurveyAnswers().get(0);
		        nameValuePairs.add(new BasicNameValuePair("sur", String.valueOf(SurveyStateHolder.getCurrentSurveyType().getId())));
		        SurveyPoint sp = SurveyStateHolder.getSurveyPoints().get(0);
		        nameValuePairs.add(new BasicNameValuePair("alt", String.valueOf(sp.alt)));
		        nameValuePairs.add(new BasicNameValuePair("lat", String.valueOf(sp.latLng.latitude)));
		        nameValuePairs.add(new BasicNameValuePair("lon", String.valueOf(sp.latLng.longitude)));
		        nameValuePairs.add(new BasicNameValuePair("add", getAddress(sp)));

		        for (Answer ans : SurveyStateHolder.getSurveyAnswers()) {
		        	nameValuePairs.add(new BasicNameValuePair("q" + ans.questionId, ans.answer));
		        }
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        
		    } catch (ClientProtocolException e) {
		        
		    } catch (IOException e) {
		        
		    }
		    return null;
		}
		
		private String getAddress(SurveyPoint sp) {
			try {
				Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
				addresses = geocoder.getFromLocation(sp.latLng.latitude, sp.latLng.longitude, 1);
	
				String address = addresses.get(0).getAddressLine(0);
				String city = addresses.get(0).getAddressLine(1);
				String country = addresses.get(0).getAddressLine(2);
				return address + ", " + city + ", " + country;
			
			} catch (Exception e) {
				return "";
			}
		}
	}

}
