package tan.shawn.jerold.netrunner;

import images.ImageAdapter;
import images.TouchImageView;

import java.util.ArrayList;

import cards.Card;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CorpFragment extends Fragment
{	
	private OnItemSelectedListener listener;
	
	public interface OnItemSelectedListener 
	{
	    public void onItemSelected(String msg);
	}
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) 
		{
			listener = (OnItemSelectedListener) activity;
		} 
		else 
		{
			throw new ClassCastException(activity.toString() + " must implemenet CorpFragment.OnItemSelectedListener");
		}
	}

	@Override
	public void onDetach() 
	{
		super.onDetach();
		listener = null;
	}
	
	// ----------------------------------------------------------------------------------------------
    
    ImageAdapter imageAdapterServers;
    
    int _page = 0;
	ArrayList<Integer> _cardList = new ArrayList<Integer>();
	int _iceTracker = 0;
	GameState _gameState = new GameState();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
	    View view = inflater.inflate(R.layout.fragment_corp, container, false);
	    
	    //view.findViewById(R.id.imageView1).setOnTouchListener(new TouchListener_DragAtTouch());
	    //view.findViewById(R.id.Header).setOnDragListener(new MyDragListener());
	    
	    /*
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
	
	public void onActivityCreated (Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
	    getSettings(savedInstanceState);
	    setupBoard();   
	}
	
	private boolean getSettings(Bundle savedInstanceState)
	{
		if(savedInstanceState==null)
		{
			// First creation
			return true;
		}
		
		// Restart of activity
		// Fill up gameState with details from savedInstanceState
		
		return true;
	}
	
	private void setupBoard()
	{	
		Card.GetDeck(_gameState._corpState._server.get(0)._ice, 1);
		Card.GetDeck(_gameState._corpState._server.get(1)._ice, 3);
		Card.GetDeck(_gameState._corpState._server.get(2)._ice, 2);
		
		Card.GetDeck(_gameState._corpState._server.get(0)._installs, 2);
		Card.GetDeck(_gameState._corpState._server.get(1)._installs, 3);
		Card.GetDeck(_gameState._corpState._server.get(2)._installs, 4);
		
		
		updateCardList();
		
		final GridView gridviewServer = (GridView) getActivity().findViewById(R.id.gridViewServer);
		imageAdapterServers = new ImageAdapter(getActivity(), _cardList, _iceTracker);
		gridviewServer.setAdapter(imageAdapterServers);

		// Allow viewing of card in dialog on click
	    gridviewServer.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            // custom dialog	        	
	        	Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	dialog.setContentView(R.layout.card_viewer);
		
				TouchImageView view = (TouchImageView) dialog.findViewById(R.id.cardView);
	            view.setImageResource((int) gridviewServer.getItemIdAtPosition(position));
				
				dialog.show();
				dialog.getWindow().setLayout(390, 544);	
	        }
	    });	    
	}	
	
	void updateCardList()
	{
		_cardList.clear();
		
		int maxIce = 0;
		int maxInstalls = 0;
		
		// Ice List
		maxIce = GetMaxIce();

		for(int i=( maxIce - 1 ); i>=0; i--)
		{
			for(int j=0; j<3; j++)
			{
				if(i < _gameState._corpState._server.get(_page + j)._ice.size())
				{
					_cardList.add(_gameState._corpState._server.get(_page + j)._ice.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
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
		maxInstalls = GetMaxInstalls();
		
		for(int i = 0; i < maxInstalls; i++)
		{
			for(int j=0; j<3; j++)
			{
				// Installs for Archives
				if(i < _gameState._corpState._server.get(_page + j)._installs.size())
				{
					_cardList.add(_gameState._corpState._server.get(_page + j)._installs.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
			}			
		}
	}
	
    private int GetMaxIce()
    {
    	int maxIce = 0;
    	
		for(int i=0; i<3; i++)
		{
			if((_page*3 + i) < _gameState._corpState._server.size()) // if server exists
			{
				maxIce = Math.max(maxIce, _gameState._corpState._server.get(_page*3 + i)._ice.size());
			}
		}
		
        return maxIce;
    }
    
    private int GetMaxInstalls()
    {
    	int maxInstalls = 0;
    	
    	for(int i=0; i<3; i++)
		{
			if((_page*3 + i) < _gameState._corpState._server.size()) // if server exists
			{
				maxInstalls = Math.max(maxInstalls, _gameState._corpState._server.get(_page*3 + i)._installs.size());
			}
		}

		return maxInstalls;
    }
}
