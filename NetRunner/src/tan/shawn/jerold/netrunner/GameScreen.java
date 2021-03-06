package tan.shawn.jerold.netrunner;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import cards.Card;

public class GameScreen extends Activity implements CorpFragment.CorpInterface, RunnerFragment.RunnerInterface 
{	
	@Override
	public void CorpPageChange(CorpFragment.CorpInterface.swipeDirection direction) 
	{	
		if(direction == CorpFragment.CorpInterface.swipeDirection.Left)
		{
			if( (_gameState._corpState._page + 1) *3 < _gameState._corpState._servers.size() )
			{
				++_gameState._corpState._page;
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Right-most page", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		else if(direction == CorpFragment.CorpInterface.swipeDirection.Right)
		{
			if(_gameState._corpState._page > 0)
			{
				--_gameState._corpState._page;
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Left-most page", Toast.LENGTH_SHORT).show();
				return;
			}
		}
			
		_corpFragment.updateUI(_gameState);	
	}

	@Override
	public void RunnerPageChange(RunnerFragment.RunnerInterface.swipeDirection direction) 
	{	
		if(direction == RunnerFragment.RunnerInterface.swipeDirection.Left)
		{
			Toast.makeText(getApplicationContext(), "Adding cards...", Toast.LENGTH_SHORT).show();
			
			Card.GetRunnerDeck(_gameState._runnerState._rigResources, 1);
			Card.GetRunnerDeck(_gameState._runnerState._rigHardware, 2);
			Card.GetRunnerDeck(_gameState._runnerState._rigPrograms, 3);
		}
		else if(direction == RunnerFragment.RunnerInterface.swipeDirection.Right)
		{
			Toast.makeText(getApplicationContext(), "Clear cards...", Toast.LENGTH_SHORT).show();
			
			_gameState._runnerState.resetRunner();
		}

		_runnerFragment.updateUI(_gameState);
	}

	
	// ----------------------------------------------------------------------------------
	
	private void setupActionBar() 
	{
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
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
	
	// ----------------------------------------------------------------------------------

	private String _side;
	GameState _gameState = new GameState();
	CorpFragment _corpFragment = new CorpFragment();
	RunnerFragment _runnerFragment = new RunnerFragment();

	// Save current state into savedInstanceState when activity stops
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
		super.onSaveInstanceState(savedInstanceState);
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.

		savedInstanceState.putSerializable("GAMESTATE", _gameState);
		savedInstanceState.putString("SIDE", _side);
	}

	// 1st Creation or Restoration of activity. Restoration will take state from savedInstanceState. 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		// Show the Up button in the action bar.
		setupActionBar();
		
        // Retrieve values from previous activity or from restored state
        if(GetValuesFromIntent(savedInstanceState))
		{
        	// Initialise GameState
        	SetupGame(savedInstanceState);
		}
	}
	
	// Retrieve values from previous activity or from restored state
	private boolean GetValuesFromIntent(Bundle savedInstanceState)
	{
		if(savedInstanceState == null)
		{
			// New game. Start from scratch
			// Get parameters passed from main menu
			Bundle extra = getIntent().getExtras();
			if(extra == null)
			{
				Log.e("GetValuesFromIntent","Extra is null");
				return false;
			}
			
			// Populate relevant parameters
			_side = extra.getString("SIDE");
		}
		else
		{
			// Resuming from previous state
			// Get parameters from savedInstanceState
			_side = savedInstanceState.getString("SIDE");
		}
		return true;
	}
	
	// Populate GameState and Fragments
	private boolean SetupGame(Bundle savedInstanceState)
	{			
		if(savedInstanceState == null)
		{	
			// New game. Start from scratch.

			// Setup _gameState with initial layout
			InitialiseBoard(); 
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Not recreating fragment", Toast.LENGTH_SHORT).show();
			
			// Resuming from stop. Get _gameState from savedInstanceState
			_gameState = (GameState)savedInstanceState.getSerializable("GAMESTATE");
		}
			
		// Update the cardList from _gameState
		_corpFragment.UpdateCardList(_gameState);
		_runnerFragment.UpdateCardList(_gameState); 
		
		// Start the fragment 
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		
		if(getFragmentManager().findFragmentById(R.id.SideFragmentHolder) == null)
		{
			// Fragment does not exist. Add it.
			if(_side.equals("Corporation"))
			{
				ft.add(R.id.SideFragmentHolder, _corpFragment);
			}
			else if(_side.equals("Runner"))
			{
				ft.add(R.id.SideFragmentHolder, _runnerFragment);
			}
			else
			{
				Log.e("SetupGame", "Cannot identify side :" + _side);
				return false;
			}
		}
		else
		{
			// Fragment currently exists. Replace it.
			if(_side.equals("Corporation"))
			{
				ft.replace(R.id.SideFragmentHolder, _corpFragment);
			}
			else if(_side.equals("Runner"))
			{
				ft.replace(R.id.SideFragmentHolder, _runnerFragment);
			}
			else
			{
				Log.e("SetupGame", "Cannot identify side :" + _side);
				return false;
			}
		}
		
		ft.commit();
		
		return true;
	}
	
	// Switch between Corp and Runner views
	public void SwitchLayout(View view)
	{
		Log.e("1","Switch");
		if(_side.equals("Runner"))
		{	
			_side="Corporation";
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.SideFragmentHolder, _corpFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
		else if(_side.equals("Corporation"))
		{
			_side="Runner";
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.SideFragmentHolder, _runnerFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			
		}
	}

	// Switch between Pages
	public void SwitchPage(View view)
	{
		Log.e("1","Page");
	}
	
	// Add the starting cards to _gameState and updateCardList.
	private void InitialiseBoard()
	{
		_gameState.reset();
		
		// Initialise Corp
		Card.GetCorpDeck(_gameState._corpState._servers.get(0)._ice, 1);
		Card.GetCorpDeck(_gameState._corpState._servers.get(1)._ice, 3);
		Card.GetCorpDeck(_gameState._corpState._servers.get(2)._ice, 2);
		Card.GetCorpDeck(_gameState._corpState._servers.get(3)._ice, 2);
		
		Card.GetCorpDeck(_gameState._corpState._servers.get(0)._installs, 2);
		Card.GetCorpDeck(_gameState._corpState._servers.get(1)._installs, 3);
		Card.GetCorpDeck(_gameState._corpState._servers.get(2)._installs, 4);
		Card.GetCorpDeck(_gameState._corpState._servers.get(3)._installs, 3);
		
		// Initialise Runner
		Card.GetRunnerDeck(_gameState._runnerState._rigResources, 1);
		Card.GetRunnerDeck(_gameState._runnerState._rigHardware, 2);
		Card.GetRunnerDeck(_gameState._runnerState._rigPrograms, 3);
	}
		
}
