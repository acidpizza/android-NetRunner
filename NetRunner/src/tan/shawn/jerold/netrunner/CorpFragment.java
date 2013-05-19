package tan.shawn.jerold.netrunner;

import Images.ImageAdapter;
import Images.TouchImageView;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
		final GridView gridview = (GridView) getActivity().findViewById(R.id.gridView1);
	   
	    Integer[] cards = {
	            R.drawable.cneutral_agenda1, R.drawable.cneutral_agenda2,
	            R.drawable.cneutral_asset1, R.drawable.cneutral_asset2,
	            R.drawable.cneutral_ice1, R.drawable.cneutral_ice2,
	            R.drawable.cneutral_ice3, R.drawable.cneutral_operation1,
	            R.drawable.wey_hq1, R.drawable.wey_agenda1, 
	            R.drawable.wey_agenda2, R.drawable.wey_asset1,
	            R.drawable.wey_ice1, R.drawable.wey_ice2,
	            R.drawable.wey_ice3, R.drawable.wey_ice4,
	            R.drawable.wey_op1, R.drawable.wey_op2,
	            R.drawable.wey_op3, R.drawable.wey_op4,
	            R.drawable.wey_upgrade1 
	    };
		
		gridview.setAdapter(new ImageAdapter(getActivity(), cards));

	    gridview.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            // custom dialog	        	
	        	Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        	dialog.setContentView(R.layout.card_viewer);
		
				TouchImageView view = (TouchImageView) dialog.findViewById(R.id.cardView);
	            view.setImageResource((int) gridview.getItemIdAtPosition(position));
				
				dialog.show();
				dialog.getWindow().setLayout(390, 544);	
	        }
	    });
	    
	}
	
}
