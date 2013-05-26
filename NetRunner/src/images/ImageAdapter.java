package images;

import java.util.ArrayList;

import tan.shawn.jerold.netrunner.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter 
{
    public ImageAdapter(Context c, ArrayList<Integer> cardList, int iceTracker) 
    {
        _context = c;
        _cardList = cardList;
        _iceTracker = iceTracker;
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
                        
            //imageView.setLayoutParams(new GridView.LayoutParams(150, 108));
            //imageView.setLayoutParams(new GridView.LayoutParams(150, 209));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
            //imageView.setPadding(0, 0, 0, 0);
        } 
        else 
        {
            imageView = (ImageView) convertView;
        }
        
        imageView.setImageResource(_cardList.get(position));
        
     	
    	
    	if(position < _iceTracker)
    	{
    		// Tapped
    		
    		/*
    		Matrix matrix=new Matrix();
            imageView.setScaleType(ScaleType.MATRIX);   //required
            matrix.postScale(107.5f/225, 150f/314);
            matrix.postRotate( 90f, 53.75f, 75f);
            imageView.setImageMatrix(matrix);
            */
    		
    		Matrix mat = new Matrix();
    		Bitmap bMap = BitmapFactory.decodeResource(_context.getResources(), _cardList.get(position));
    		mat.postRotate(90);
    		Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,bMap.getWidth(),bMap.getHeight(), mat, true);
    		imageView.setImageBitmap(bMapRotate);
    		
    		
    		
    		//imageView.setRotation(90f);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 108));
    		//imageView.setLayoutParams(new GridView.LayoutParams(108, 150));
    		//imageView.setLayoutParams(new GridView.LayoutParams(75, 104));
    		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	}
    	else
    	{
    		// Not Tapped 
    		imageView.setRotation(0f);
    		//imageView.setLayoutParams(new GridView.LayoutParams(150, 209));
    		imageView.setLayoutParams(new GridView.LayoutParams(108, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    	}
    	
    	if(_cardList.get(position) == R.drawable.nothing)
        {
        	imageView.setVisibility(View.INVISIBLE);
        	//imageView.setClickable(false);
        	//imageView.setEnabled(false);
        }
        else
        {
        	imageView.setVisibility(View.VISIBLE);
        }
    	        
        return imageView;
    }
    
    private Context _context;
    private ArrayList<Integer> _cardList;
    private int _iceTracker = 0;
}
