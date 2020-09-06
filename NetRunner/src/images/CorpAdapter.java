package images;

import java.util.ArrayList;

import tan.shawn.jerold.netrunner.R;
import android.content.Context;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class CorpAdapter extends BaseAdapter 
{
    public CorpAdapter(Context c, ArrayList<Integer> cardList, int iceTracker) 
    {
        _context = c;
        _cardList = cardList;
        _iceTracker = iceTracker;
        
        _cardWidth = 108;
        _cardHeight = 150;
        
        mat.postRotate(90);
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
    	FrameLayout frame; // Use framelayout to hold imageview so that imageview can be rotated properly
        ImageView imageView;
        
        if (convertView == null) 
        {  // if it's not recycled, initialize some attributes

        	imageView = new ImageView(_context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(6, 6, 6, 6);
            
			imageView.setPivotX(0f);
			imageView.setPivotY(0f);
			imageView.setTag("IMAGE");

			frame = new FrameLayout(_context);
			frame.addView(imageView);
			
			//imageView.setBackgroundColor(Color.argb(255,255,0,0));
			//frame.setBackgroundColor(Color.argb(255,0,255,0));
        } 
        else 
        {
			frame = (FrameLayout) convertView;
			imageView = (ImageView) frame.findViewWithTag("IMAGE");
        }
        
        imageView.setImageResource(_cardList.get(position));
        
    	if(position < _iceTracker)
    	{
    		// Tapped
    		
    		/*
    		// Difficult to fit image to layout due to ScaleType.MATRIX instead of CENTER_CROP
    		Matrix matrix=new Matrix();
            imageView.setScaleType(ScaleType.MATRIX);   //required
            matrix.postScale( 108f / imageView.getDrawable().getIntrinsicWidth(), 150f / imageView.getDrawable().getIntrinsicHeight());
            matrix.postRotate( 90f, 54, 75);
            imageView.setImageMatrix(matrix);
 			*/              		
    		
    		/*
    		// Works but performs poorly
    		Bitmap bMap = BitmapFactory.decodeResource(_context.getResources(), _cardList.get(position));
    		Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,bMap.getWidth(),bMap.getHeight(), mat, true);
    		imageView.setImageBitmap(bMapRotate);
    		imageView.setLayoutParams(new GridView.LayoutParams(_cardHeight, _cardWidth));
    		 */
    		
			imageView.setRotation(-90f);
			imageView.setTranslationY(_cardWidth);
			imageView.setLayoutParams(new FrameLayout.LayoutParams(_cardWidth, _cardHeight));
			
			frame.setLayoutParams(new GridView.LayoutParams(_cardHeight, _cardWidth));
    	}
    	else
    	{
    		// Not Tapped 
			imageView.setRotation(0f);
			imageView.setTranslationY(0);
			imageView.setLayoutParams(new FrameLayout.LayoutParams(_cardWidth, _cardHeight));
			
			frame.setLayoutParams(new GridView.LayoutParams(_cardWidth, _cardHeight));
    	}
    	
    	if(_cardList.get(position) == R.drawable.nothing)
        {
        	frame.setVisibility(View.INVISIBLE);
    		//imageView.setVisibility(View.INVISIBLE);
        }
        else
        {
        	frame.setVisibility(View.VISIBLE);
        	//imageView.setVisibility(View.VISIBLE);
        }
    	        
    	return frame;
    }
    
    public void SetIceTracker(int iceTracker)
    {
    	_iceTracker = iceTracker;
    }
    
    
    Matrix mat = new Matrix();
    private Context _context;
    private ArrayList<Integer> _cardList;
    private int _iceTracker = 0;
    private int _cardWidth = 300;
    private int _cardHeight = 418;
}
