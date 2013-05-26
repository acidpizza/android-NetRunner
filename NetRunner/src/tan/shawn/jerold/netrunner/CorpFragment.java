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
    
	ArrayList<Integer> mCardListServer = new ArrayList<Integer>();
	ArrayList<Integer> mCardListIce = new ArrayList<Integer>();
	int indexServer = 2;
	int indexIce = 10;

	
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
	    GameState boardSettings = new GameState();
	    getSettings(savedInstanceState, boardSettings);
	    setupBoard(boardSettings);
	    
	    
	    View button = getActivity().findViewById(R.id.ButtonServer);
	    button.setOnClickListener(
	        new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                /* DO SOMETHING UPON THE CLICK */
	            	AddImageServer(v);
	            }
	        }
	    );

	    View button2 = getActivity().findViewById(R.id.ButtonIce);
	    button2.setOnClickListener(
	        new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                /* DO SOMETHING UPON THE CLICK */
	            	AddImageIce(v);
	            }
	        }
	    );
	    
	}
	
	private boolean getSettings(Bundle savedInstanceState, GameState boardSettings)
	{
		if(savedInstanceState==null)
		{
			return true;
		}
		
		return true;
	}
	
	private void setupBoard(GameState boardSettings)
	{
		
		
mCardListServer.add(R.drawable.nothing);
mCardListServer.add(R.drawable.corp_back);
mCardListServer.add(R.drawable.wey_hq1);
		
		final GridView gridviewServer = (GridView) getActivity().findViewById(R.id.gridViewServer);
		imageAdapterServers = new ImageAdapter(getActivity(), mCardListServer);
		gridviewServer.setAdapter(imageAdapterServers);

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
	    
	    final GridView gridviewIce = (GridView) getActivity().findViewById(R.id.gridViewIce);
	    imageAdapterIce = new ImageAdapter(getActivity(), mCardListIce);
		gridviewIce.setAdapter(imageAdapterIce);

	    gridviewIce.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            // custom dialog	        	
	        	Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	dialog.setContentView(R.layout.card_viewer);
		
				TouchImageView view = (TouchImageView) dialog.findViewById(R.id.cardView);
	            view.setImageResource((int) gridviewIce.getItemIdAtPosition(position));
				
				dialog.show();
				dialog.getWindow().setLayout(390, 544);	
	        }
	    });
	    
	}
	
	public void AddImageServer(View view)
	{
		mCardListServer.add(cards[indexServer]);
		indexServer++;
		if(indexServer > 9)
		{
			indexServer = 2;
		}
		imageAdapterServers.notifyDataSetChanged();
	}
	
	public void AddImageIce(View view)
	{
		mCardListIce.add(cards[indexIce]);
		indexIce++;
		if(indexIce> 16)
		{
			indexIce = 10;
		}
		imageAdapterIce.notifyDataSetChanged();
	}
	
}
