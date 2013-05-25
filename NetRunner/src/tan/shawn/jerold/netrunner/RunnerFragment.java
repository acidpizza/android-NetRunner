package tan.shawn.jerold.netrunner;

import java.util.ArrayList;

import Images.ImageAdapter;
import Images.TouchImageView;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class RunnerFragment extends Fragment
{
	ArrayList<Integer> mCardList = new ArrayList<Integer>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_runner, container, false);
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
				R.drawable.rneutral_event1,R.drawable.rneutral_event2,
				R.drawable.rneutral_program1, R.drawable.rneutral_resource1,
				R.drawable.rneutral_resource2, R.drawable.sha_id1,
				R.drawable.sha_event1, R.drawable.sha_event2,
				R.drawable.sha_event3, R.drawable.sha_event4,
				R.drawable.sha_hardware1, R.drawable.sha_hardware2,
				R.drawable.sha_hardware3, R.drawable.sha_hardware4,
				R.drawable.sha_program1, R.drawable.sha_program2,
				R.drawable.sha_program3, R.drawable.sha_program4,
				R.drawable.sha_program5, R.drawable.sha_resource1,
				R.drawable.sha_resource2, R.drawable.runner_back
		};

		for(int i=0; i<cards.length; i++)
		{
			mCardList.add(cards[i]);
		}
		
		gridview.setAdapter(new ImageAdapter(getActivity(), mCardList));

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
