package tan.shawn.jerold.netrunner;

import Images.ImageAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

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
		GridView gridview = (GridView) getActivity().findViewById(R.id.gridView1);
	   
		gridview.setAdapter(new ImageAdapter(getActivity()));

	    gridview.setOnItemClickListener(new OnItemClickListener() 
	    {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	        {
	            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    
	}
	
}
