package DragAndDrop;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;

public final class MyDragListener implements OnDragListener 
{
	@Override
	public boolean onDrag(View v, DragEvent event) 
	{
	    //handle drag events
		switch (event.getAction()) 
		{
		case DragEvent.ACTION_DRAG_STARTED:
			//no action necessary
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			//no action necessary
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			//no action necessary
			break;
		case DragEvent.ACTION_DROP:
			//no action necessary
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			//no action necessary
			if(!event.getResult())
			{
				final ImageView view = (ImageView) event.getLocalState();

				view.post(new Runnable() 
				{
					public void run() 
					{
						view.setVisibility(View.VISIBLE);
					}
				});
			}
			
			break;
		default:
			break;
		}
		
	    return true;
	}
}
