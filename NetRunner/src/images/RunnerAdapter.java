package images;

import java.util.ArrayList;

import tan.shawn.jerold.netrunner.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class RunnerAdapter extends BaseAdapter 
{
    public RunnerAdapter(Context c, ArrayList<Integer> cardList) 
    {
        _context = c;
        _cardList = cardList;
    }
    
    
    public int getCount() 
    {
        return _cardList.size();
    }

    public Object getItem(int position) 
    {
        return null;
    }

    public long getItemId(int position) 
    {
    	// Return the drawableID of the image
    	if( 0 <= position && position < _cardList.size())
		{
			return _cardList.get(position);
		}
    	else
    	{
    		return -999;
    	}
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        ImageView imageView;
        if (convertView == null) 
        {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(_context);
          //imageView.setLayoutParams(new GridView.LayoutParams(150, 209));
    		imageView.setLayoutParams(new GridView.LayoutParams(108, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(6, 6, 6, 6);
        } 
        else 
        {
            imageView = (ImageView) convertView;
        }
        
		// Not Tapped 
		imageView.setImageResource(_cardList.get(position));
    	
    	
    	if(_cardList.get(position) == R.drawable.nothing)
        {
        	imageView.setVisibility(View.INVISIBLE);
        }
        else
        {
        	imageView.setVisibility(View.VISIBLE);
        }
    	        
        return imageView;
    }
    
    private Context _context;
    private ArrayList<Integer> _cardList;
}
