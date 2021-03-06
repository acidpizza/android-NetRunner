package tan.shawn.jerold.netrunner;

import images.CorpAdapter;
import images.TouchImageView;

import java.util.ArrayList;

import tan.shawn.jerold.netrunner.CorpFragment.CorpInterface.swipeDirection;
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

public class CorpFragment extends Fragment
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
	            	_corpListener.CorpPageChange(swipeDirection.Left);
	                return true;
	            }  
	            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
	            {
	            	// Right Swipe
	            	_corpListener.CorpPageChange(swipeDirection.Right);
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
	
	
	private CorpInterface _corpListener;
	
	public interface CorpInterface 
	{
		public enum swipeDirection
		{
			Left, Right
		}

	    public void CorpPageChange(swipeDirection direction);
	}
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		if (activity instanceof CorpInterface) 
		{
			_corpListener = (CorpInterface) activity;
		} 
		else 
		{
			throw new ClassCastException(activity.toString() + " must implement CorpFragment.CorpPageChangerInterface");
		}
	}

	@Override
	public void onDetach() 
	{
		super.onDetach();
		_corpListener = null;
	}
	
	// ----------------------------------------------------------------------------------------------
    
    private CorpAdapter _corpAdapter;
    
	private ArrayList<Integer> _cardList = new ArrayList<Integer>();
	private int _iceTracker = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
	    View view = inflater.inflate(R.layout.fragment_corp, container, false);
	    
	    /* 
	    //view.findViewById(R.id.imageView1).setOnTouchListener(new TouchListener_DragAtTouch());
	    //view.findViewById(R.id.Header).setOnDragListener(new MyDragListener());
	     
	    view.findViewById(R.id.imageView1).setOnClickListener(new View.OnClickListener() 
	    {
	        @Override
	        public void onClick(View v) 
	        {
	          listener.onItemSelected("Selected!");
	        }
	      });
	    */
	 
	    return view;		
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	    
		getSettings(savedInstanceState);
		
		final GridView gridviewCorp = (GridView) getActivity().findViewById(R.id.gridViewCorp);
		_corpAdapter = new CorpAdapter(getActivity(), _cardList, _iceTracker);
		gridviewCorp.setAdapter(_corpAdapter);
		
		// Allow viewing of card in dialog on click
	    gridviewCorp.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            // custom dialog	        	
	        	Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	dialog.setContentView(R.layout.card_viewer);
		
				TouchImageView view = (TouchImageView) dialog.findViewById(R.id.cardView);
	            view.setImageResource((int) gridviewCorp.getItemIdAtPosition(position));
				
				dialog.show();
				dialog.getWindow().setLayout(390, 544);	
	        }
	    });	 
	    
		// Swipe Gestures to change page
		gestureDetector = new GestureDetector(getActivity(), new SwipeOnGestureListener());
		
		gridviewCorp.setOnTouchListener(new View.OnTouchListener() 
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
	  savedInstanceState.putInt("ICETRACKER", _iceTracker);
	}

	
	private boolean getSettings(Bundle savedInstanceState)
	{	
		if(savedInstanceState!=null)
		{
			// Restart of activity
			_cardList = savedInstanceState.getIntegerArrayList("CARDLIST");
			_iceTracker = savedInstanceState.getInt("ICETRACKER");
		}
	
		return true;
	}
	
	// Update UI from main activity
	public void updateUI(GameState gameState)
	{	
		// Update _cardList and _iceTracker from _gameState
		UpdateCardList(gameState);

		_corpAdapter.SetIceTracker(_iceTracker);
		
		// Update the GridView
		_corpAdapter.notifyDataSetChanged();
	}	
	
	//TODO: put this function inside GameState as GameState.UpdateCardList(_cardList), return _iceTracker
	// Update _cardList and _iceTracker only from _gameState.
	// Do this for restarting due to rotation when fragment has not been created yet.
	public void UpdateCardList(GameState gameState)
	{
		int page = gameState._corpState._page;
		_cardList.clear();
		
		int maxIce = 0;
		int maxInstalls = 0;
		
		// Ice List
		maxIce = gameState._corpState.GetMaxIce(page);

		for(int i=( maxIce - 1 ); i>=0; i--) // for each ice row
		{
			for(int j=0; j<3; j++) // for each server in the page
			{
				if(page*3 + j >= gameState._corpState._servers.size())
				{
					// Server does not exist
					_cardList.add(R.drawable.nothing);
				}
				else
				{
					if(i < gameState._corpState._servers.get(page*3 + j)._ice.size()) // server exists and ice exists
					{
						_cardList.add(gameState._corpState._servers.get(page*3 + j)._ice.get(i)._drawableID);
					}
					else // sever exists but ice does not exist
					{
						_cardList.add(R.drawable.nothing);
					}
				}
			}
		}
		_iceTracker = _cardList.size(); // point to the index of first non-ice
	
		if(page == 0)
		{
			_cardList.add(R.drawable.nothing); 		// archives
			_cardList.add(R.drawable.corp_back);	// R&D
			_cardList.add(R.drawable.wey_hq1);		// HQ
		}
		
		// Installs List
		maxInstalls = gameState._corpState.GetMaxInstalls(page);
		
		for(int i = 0; i < maxInstalls; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(page*3 + j >= gameState._corpState._servers.size())
				{
					// Server does not exist
					_cardList.add(R.drawable.nothing);
				}
				else
				{
					if(i < gameState._corpState._servers.get(page*3 + j)._installs.size()) // server and install exists
					{
						_cardList.add(gameState._corpState._servers.get(page*3 + j)._installs.get(i)._drawableID);
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
