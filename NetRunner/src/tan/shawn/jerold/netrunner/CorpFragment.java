package tan.shawn.jerold.netrunner;

import images.ImageAdapter;
import images.TouchImageView;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cards.Card;

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
    
	ArrayList<Integer> _cardList = new ArrayList<Integer>();
	int _iceTracker = 0;
	
	
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
	    updateUI();   
	}
	
	private boolean getSettings(Bundle savedInstanceState)
	{
		if(savedInstanceState==null)
		{
			// First creation
			Bundle b = getArguments(); 
			_cardList = b.getIntegerArrayList("CARDLIST");
			_iceTracker = b.getInt("ICETRACKER");
			return true;
		}
		
		// Restart of activity
		// Fill up gameState with details from savedInstanceState
		Bundle b = getArguments(); 
		_cardList = b.getIntegerArrayList("CARDLIST");
		_iceTracker = b.getInt("ICETRACKER");
		
		
		return true;
	}
	
	public void updateUI()
	{	
		//TODO: use notify adapter instead of creating new adapter for every updateUI
		
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
}
