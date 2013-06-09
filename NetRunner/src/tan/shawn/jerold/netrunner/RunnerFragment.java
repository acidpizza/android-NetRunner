package tan.shawn.jerold.netrunner;

import images.RunnerAdapter;
import images.TouchImageView;

import java.util.ArrayList;

import tan.shawn.jerold.netrunner.RunnerFragment.RunnerInterface.swipeDirection;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class RunnerFragment extends Fragment
{
	// ----------------------------------------------------------------------------------
	
    private GestureDetector gestureDetector;
	
	class SwipeOnGestureListener extends SimpleOnGestureListener 
	{
		private final int SWIPE_MIN_DISTANCE = 120;
	    private final int SWIPE_MAX_OFF_PATH = 250;
	    private final int SWIPE_THRESHOLD_VELOCITY = 400;
	    
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	    {	
	        try 
	        {
	            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	            {
	                return false;
	            }
	            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
	            {
	            	// Left Swipe
	            	_runnerListener.RunnerPageChange(swipeDirection.Left);
	                return true;
	            }  
	            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
	            {
	            	// Right Swipe
	            	_runnerListener.RunnerPageChange(swipeDirection.Right);
	                return true;
	            }
	        } 
	        catch (Exception e) 
	        {
	            // nothing
	        }
	        return false;
	    }
	
	}	
	
	// ----------------------------------------------------------------------------------
	
	private RunnerInterface _runnerListener;
	
	public interface RunnerInterface 
	{
		public enum swipeDirection
		{
			Left, Right
		}

	    public void RunnerPageChange(swipeDirection direction);
	}
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		if (activity instanceof RunnerInterface) 
		{
			_runnerListener = (RunnerInterface) activity;
		} 
		else 
		{
			throw new ClassCastException(activity.toString() + " must implement RunnerFragment.RunnerPageChangerInterface");
		}
	}

	@Override
	public void onDetach() 
	{
		super.onDetach();
		_runnerListener = null;
	}
	
	// ----------------------------------------------------------------------------------------------

	
	ArrayList<Integer> _cardList = new ArrayList<Integer>();
	RunnerAdapter _runnerAdapter; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_runner, container, false);
		return view;
	}

	public void onActivityCreated (Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		getSettings(savedInstanceState);
		
		final GridView gridviewRunner = (GridView) getActivity().findViewById(R.id.gridViewRunner);
		_runnerAdapter = new RunnerAdapter(getActivity(), _cardList);
		gridviewRunner.setAdapter(_runnerAdapter);
		
		// Allow viewing of card in dialog on click
	    gridviewRunner.setOnItemClickListener(new OnItemClickListener()
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            // custom dialog	        	
	        	Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	dialog.setContentView(R.layout.card_viewer);
		
				TouchImageView view = (TouchImageView) dialog.findViewById(R.id.cardView);
	            view.setImageResource((int) gridviewRunner.getItemIdAtPosition(position));
				
				dialog.show();
				dialog.getWindow().setLayout(390, 544);	
	        }
	    });	 
		
		// Swipe Gestures to change page
		gestureDetector = new GestureDetector(getActivity(), new SwipeOnGestureListener());
		
		gridviewRunner.setOnTouchListener(new View.OnTouchListener() 
		{
		    public boolean onTouch(View v, MotionEvent event) 
		    {
		    	return gestureDetector.onTouchEvent(event);
		    }
		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
	  super.onSaveInstanceState(savedInstanceState);
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	  
	  // On restart, save the existing _cardList and _iceTracker 
	  savedInstanceState.putIntegerArrayList("CARDLIST", _cardList);
	}

	private boolean getSettings(Bundle savedInstanceState)
	{	
		if(savedInstanceState!=null)
		{
			// Restart of activity
			_cardList = savedInstanceState.getIntegerArrayList("CARDLIST");
		}
	
		return true;
	}
	
	// Update UI from main activity
	public void updateUI(GameState gameState)
	{	
		// Update _cardList and _iceTracker from _gameState
		UpdateCardList(gameState);
		
		// Update the GridView
		_runnerAdapter.notifyDataSetChanged();
	}	
	
	// Update _cardList and _iceTracker only from _gameState.
	// Do this for restarting due to rotation when fragment has not been created yet.
	public void UpdateCardList(GameState gameState)
	{
		_cardList.clear();

		int maxRig = gameState._runnerState.GetMaxRig();
		
		for(int i = 0; i < maxRig; i++)
		{
			if(i < gameState._runnerState._rigResources.size()) // resource card exists
			{
				_cardList.add(gameState._runnerState._rigResources.get(i)._drawableID);
			}
			else // resource card does not exist
			{
				_cardList.add(R.drawable.nothing);
			}
			
			if(i < gameState._runnerState._rigHardware.size()) // hardware card exists
			{
				_cardList.add(gameState._runnerState._rigHardware.get(i)._drawableID);
			}
			else // hardware card does not exist
			{
				_cardList.add(R.drawable.nothing);
			}
			
			if(i < gameState._runnerState._rigPrograms.size()) // program card exists
			{
				_cardList.add(gameState._runnerState._rigPrograms.get(i)._drawableID);
			}
			else // program card does not exist
			{
				_cardList.add(R.drawable.nothing);
			}
		}
	}	
}
