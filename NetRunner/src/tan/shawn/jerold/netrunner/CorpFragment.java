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
    Integer[] cards = {
    		R.drawable.wey_hq1, R.drawable.corp_back,  
    		
    		R.drawable.cneutral_agenda1, R.drawable.cneutral_agenda2,
            R.drawable.cneutral_asset1, R.drawable.cneutral_asset2,
            R.drawable.wey_agenda1, R.drawable.wey_agenda2, 
            R.drawable.wey_asset1, R.drawable.wey_upgrade1, 
            
            R.drawable.cneutral_ice1, R.drawable.cneutral_ice2,
            R.drawable.cneutral_ice3,             
            R.drawable.wey_ice1, R.drawable.wey_ice2,
            R.drawable.wey_ice3, R.drawable.wey_ice4,
            
            R.drawable.cneutral_op1,
            R.drawable.wey_op1, R.drawable.wey_op2,
            R.drawable.wey_op3, R.drawable.wey_op4
    };
    ImageAdapter imageAdapterServers;
    ImageAdapter imageAdapterIce;
    
    int _page = 0;
	ArrayList<Integer> _cardList = new ArrayList<Integer>();
	int _iceTracker = 0;
	GameState _gameState;
	
	
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
			_gameState = new GameState();
			return true;
		}
		
		// Restart of activity
		// Fill up gameState with details from savedInstanceState
		
		return true;
	}
	
	private void setupBoard()
	{	
		Card.GetDeck(_gameState._corpState._serverArchive._ice, 1);
		Card.GetDeck(_gameState._corpState._serverResearch._ice, 3);
		Card.GetDeck(_gameState._corpState._serverHQ._ice, 2);
		
		Card.GetDeck(_gameState._corpState._serverArchive._installs, 2);
		Card.GetDeck(_gameState._corpState._serverResearch._installs, 3);
		Card.GetDeck(_gameState._corpState._serverHQ._installs, 4);
		
		
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
		
		if(_page == 0)
		{
			// Ice List
			maxIce = GetMaxIce();

			for(int i=( maxIce - 1 ); i>=0; i--)
			{
				// Ice for Archives
				if(i < _gameState._corpState._serverArchive._ice.size())
				{
					_cardList.add(_gameState._corpState._serverArchive._ice.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
				
				// Ice for R&D
				if(i < _gameState._corpState._serverResearch._ice.size())
				{
					_cardList.add(_gameState._corpState._serverResearch._ice.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
				
				// Ice for HQ
				if(i < _gameState._corpState._serverHQ._ice.size())
				{
					_cardList.add(_gameState._corpState._serverHQ._ice.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
			}
			
			_iceTracker = _cardList.size(); // point to the index of first non-ice
			
			_cardList.add(R.drawable.nothing); 		// archives
			_cardList.add(R.drawable.corp_back);	// R&D
			_cardList.add(R.drawable.wey_hq1);		// HQ
			
			// Installs List
			maxInstalls = GetMaxInstalls();
			
			for(int i = 0; i < maxInstalls; i++)
			{
				// Installs for Archives
				if(i < _gameState._corpState._serverArchive._installs.size())
				{
					_cardList.add(_gameState._corpState._serverArchive._installs.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
				
				// Installs for R&D
				if(i < _gameState._corpState._serverResearch._installs.size())
				{
					_cardList.add(_gameState._corpState._serverResearch._installs.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
				
				// Installs for HQ
				if(i < _gameState._corpState._serverHQ._installs.size())
				{
					_cardList.add(_gameState._corpState._serverHQ._installs.get(i)._drawableID);
				}
				else
				{
					_cardList.add(R.drawable.nothing);
				}
				
			}
		}
		else
		{
			//TODO: UpdateCardList code for other pages
		}
	}
	
    private int GetMaxIce()
    {
    	int maxIce = 0;
    	
    	if(_page == 0)
    	{
        	maxIce = Math.max(maxIce, _gameState._corpState._serverArchive._ice.size());
        	maxIce = Math.max(maxIce, _gameState._corpState._serverResearch._ice.size());
        	maxIce = Math.max(maxIce, _gameState._corpState._serverHQ._ice.size());
    	}
    	else if (_page > 0)
    	{
    		for(int i=0; i<3; i++)
    		{
    			if(((_page-1)*3 + i) < _gameState._corpState._serverRemote.size())
    			{
    				maxIce = Math.max(maxIce, _gameState._corpState._serverRemote.get((_page-1)*3 + i)._ice.size());
    			}
    		}
    	}
    	else
    	{
    		return -1;
    	}
    	
        return maxIce;
    }
    
    private int GetMaxInstalls()
    {
    	int maxInstalls = 0;
    	
    	if(_page == 0)
    	{	
        	maxInstalls = Math.max(maxInstalls, _gameState._corpState._serverArchive._installs.size());
        	maxInstalls = Math.max(maxInstalls, _gameState._corpState._serverResearch._installs.size());
        	maxInstalls = Math.max(maxInstalls, _gameState._corpState._serverHQ._installs.size());
    	}
    	else if (_page > 0)
    	{
    		for(int i=0; i<3; i++)
    		{
    			if(((_page-1)*3 + i) < _gameState._corpState._serverRemote.size())
    			{
    				maxInstalls = Math.max(maxInstalls, _gameState._corpState._serverRemote.get((_page-1)*3 + i)._installs.size());
    			}
    		}
    	}
    	else 
    	{
    		return -1;
    	}
    	
    	return maxInstalls;
    }
}
