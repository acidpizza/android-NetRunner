package tan.shawn.jerold.netrunner;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GameScreen extends Activity {

	private String _side;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		// Show the Up button in the action bar.
		setupActionBar();
		
		setupGame(savedInstanceState);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
	  super.onSaveInstanceState(savedInstanceState);
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	  savedInstanceState.putString("SIDE", _side);
	}
	
	private boolean setupGame(Bundle savedInstanceState)
	{
		if(savedInstanceState == null)
		{
			// New game. Start from scratch
			// Get parameters passed from main menu
			Bundle extra = getIntent().getExtras();
			if(extra == null)
			{
				Log.e("SetupGame","Extra is null");
				return false;
			}
			
			// Populate relevant parameters
			_side = extra.getString("SIDE");
		}
		else
		{
			// Resuming from previous state
			_side = savedInstanceState.getString("SIDE");
		}
		
			
		if(_side.equals("Runner"))
		{   
		    return setupRunner(savedInstanceState);
		}
		else if(_side.equals("Corporation"))
		{
			return setupCorp(savedInstanceState);
		}
		else
		{
			Log.e("SetupGame","Cannot determine side: " + _side);
			return false;
		}
	
	}
	
	private boolean setupRunner(Bundle savedInstanceState)
	{
		if(savedInstanceState == null)
		{
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.SideFragmentHolder, new RunnerFragment());
			ft.commit();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Not recreating fragment", Toast.LENGTH_SHORT).show();
		}
		
		return true;
	}
	
	private boolean setupCorp(Bundle savedInstanceState)
	{	
		if(savedInstanceState == null)
		{
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.SideFragmentHolder, new CorpFragment());
			ft.commit();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Not recreating fragment", Toast.LENGTH_SHORT).show();
		}
		
		return true;
	}
	
}
