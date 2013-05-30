package tan.shawn.jerold.netrunner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import cards.Card;

public class GameScreen extends Activity implements CorpFragment.OnItemSelectedListener 
{
	//@Override
	public void onItemSelected(String msg) 
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); 
	}
	
	// ----------------------------------------------------------------------------------
	
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 400;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
	
    class MyGestureDetector extends SimpleOnGestureListener 
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }	
	
	// ----------------------------------------------------------------------------------
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
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
	CorpFragment _corpFragment;
	RunnerFragment _runnerFragment = new RunnerFragment();
	
	int _page = 0;
	ArrayList<Integer> _cardList = new ArrayList<Integer>();
	int _iceTracker = 0;

	// Save current state into savedInstanceState when activity stops
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
	  super.onSaveInstanceState(savedInstanceState);
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	  
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
		
		
        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() 
        {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        //FrameLayout frame = (FrameLayout) findViewById(R.id.SideFragmentHolder);
        Button frame = (Button) findViewById(R.id.button1);
        frame.setOnTouchListener(gestureListener);
		
		
		setupGame(savedInstanceState);
	}
	
	// Initialise settings
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
			// Get parameters from savedInstanceState
			_side = savedInstanceState.getString("SIDE");
		}
		
		// Implement side-specific settings
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
	
	// Initialise Runner-specific settings 
	private boolean setupRunner(Bundle savedInstanceState)
	{
		if(savedInstanceState == null)
		{
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.SideFragmentHolder, _runnerFragment);
			ft.commit();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Not recreating fragment", Toast.LENGTH_SHORT).show();
		}
		
		return true;
	}
	
	// Initialise Corp-specific settings
	private boolean setupCorp(Bundle savedInstanceState)
	{	
		// update _cardList and _iceTracker
		InitialiseBoard();
		UpdateCardList();
		
		if(savedInstanceState == null)
		{	
			// New game. Start from scratch.
			_corpFragment = new CorpFragment();
			
			// Send _cardList and iceTracker to _corpFragment as an argument
			Bundle b = new Bundle();
			b.putIntegerArrayList("CARDLIST", _cardList);
			b.putInt("ICETRACKER", _iceTracker);
			_corpFragment.setArguments(b);
			
			// Start the fragment 
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.SideFragmentHolder, _corpFragment);
			ft.commit();
		}
		else
		{
			// Resuming from stop.
			// Recover the handle to _corpFragment
			_corpFragment = (CorpFragment) getFragmentManager().findFragmentById(R.id.SideFragmentHolder);
			
			Toast.makeText(getApplicationContext(), "Not recreating fragment", Toast.LENGTH_SHORT).show();	
		}
		
		return true;
	}
	
	// Switch between Corp and Runner views
	public void SwitchLayout(View view)
	{
		Log.e("1","Switch");
		if(_side.equals("Runner"))
		{
			_page = 0;
			UpdateCardList();
			_corpFragment._cardList = _cardList;
			_corpFragment._iceTracker = _iceTracker;
			_corpFragment.updateUI();
			_side="Corporation";
			
			/*
			_side="Corporation";
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.SideFragmentHolder, _corpFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			*/
		}
		else if(_side.equals("Corporation"))
		{
			_page = 1;
			UpdateCardList();
			_corpFragment._cardList = _cardList;
			_corpFragment._iceTracker = _iceTracker;
			_corpFragment.updateUI();
			_side="Runner";
			
			/*
			_side="Runner";
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.SideFragmentHolder, _runnerFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			*/
		}
	}

	// Add the starting cards to _gameState and updateCardList.
	private void InitialiseBoard()
	{
		_gameState.reset();
		
		Card.GetDeck(_gameState._corpState._server.get(0)._ice, 1);
		Card.GetDeck(_gameState._corpState._server.get(1)._ice, 3);
		Card.GetDeck(_gameState._corpState._server.get(2)._ice, 2);
		Card.GetDeck(_gameState._corpState._server.get(3)._ice, 2);
		
		Card.GetDeck(_gameState._corpState._server.get(0)._installs, 2);
		Card.GetDeck(_gameState._corpState._server.get(1)._installs, 3);
		Card.GetDeck(_gameState._corpState._server.get(2)._installs, 4);
		Card.GetDeck(_gameState._corpState._server.get(3)._installs, 3);
	}
	
	// Obtain _cardList and _iceTracker from _gameState and _page.
	private void UpdateCardList()
	{
		_cardList.clear();
		
		int maxIce = 0;
		int maxInstalls = 0;
		
		// Ice List
		maxIce = _gameState._corpState.GetMaxIce(_page);

		for(int i=( maxIce - 1 ); i>=0; i--) // for each ice row
		{
			for(int j=0; j<3; j++) // for each server in the page
			{
				if(_page*3 + j >= _gameState._corpState._server.size())
				{
					// Server does not exist
					_cardList.add(R.drawable.nothing);
				}
				else
				{
					if(i < _gameState._corpState._server.get(_page*3 + j)._ice.size()) // server exists and ice exists
					{
						_cardList.add(_gameState._corpState._server.get(_page*3 + j)._ice.get(i)._drawableID);
					}
					else // sever exists but ice does not exist
					{
						_cardList.add(R.drawable.nothing);
					}
				}
			}
		}
		_iceTracker = _cardList.size(); // point to the index of first non-ice
	
		if(_page == 0)
		{
			_cardList.add(R.drawable.nothing); 		// archives
			_cardList.add(R.drawable.corp_back);	// R&D
			_cardList.add(R.drawable.wey_hq1);		// HQ
		}
		
		// Installs List
		maxInstalls = _gameState._corpState.GetMaxInstalls(_page);
		
		for(int i = 0; i < maxInstalls; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(_page*3 + j >= _gameState._corpState._server.size())
				{
					// Server does not exist
					_cardList.add(R.drawable.nothing);
				}
				else
				{
					if(i < _gameState._corpState._server.get(_page*3 + j)._installs.size()) // server and install exists
					{
						_cardList.add(_gameState._corpState._server.get(_page*3 + j)._installs.get(i)._drawableID);
					}
					else // server exists but install does not exist
					{
						_cardList.add(R.drawable.nothing);
					}
				}
			}			
		}
	}
	
}
