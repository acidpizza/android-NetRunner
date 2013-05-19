package Images;

import tan.shawn.jerold.netrunner.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter 
{
    private Context mContext;

    public ImageAdapter(Context c) 
    {
        mContext = c;
    }

    public int getCount() 
    {
        return mThumbIds.length;
    }

    public Object getItem(int position) 
    {
        return null;
    }

    public long getItemId(int position) 
    {
    	if( 0 <= position && position < mThumbIds.length)
		{
			return mThumbIds[position];
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
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(300, 418));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } 
        else 
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = 
    	{
            R.drawable.neutral_agenda1, R.drawable.neutral_agenda2,
            R.drawable.neutral_asset1, R.drawable.neutral_asset2,
            R.drawable.neutral_ice1, R.drawable.neutral_ice2,
            R.drawable.neutral_ice3, R.drawable.neutral_operation1,
            R.drawable.wey_agenda1, R.drawable.wey_agenda2,
            R.drawable.wey_asset1, R.drawable.wey_hq1,
            R.drawable.wey_ice1, R.drawable.wey_ice2,
            R.drawable.wey_ice3, R.drawable.wey_ice4,
            R.drawable.wey_op1, R.drawable.wey_op2,
            R.drawable.wey_op3, R.drawable.wey_op4,
            R.drawable.wey_upgrade1 
    };
}
