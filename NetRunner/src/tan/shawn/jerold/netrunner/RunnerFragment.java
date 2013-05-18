package tan.shawn.jerold.netrunner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RunnerFragment extends Fragment
{
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	  {
	    View view = inflater.inflate(R.layout.fragment_runner, container, false);
	    return view;
	  }
}
