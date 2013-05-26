package dragAndDrop;

import android.content.ClipData;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;

public final class TouchListener_DragAtTouch implements OnTouchListener 
{
	public boolean onTouch(View view, MotionEvent motionEvent) 
	{
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) 
		{
			view.setVisibility(View.INVISIBLE);
			
		    //setup drag
			ClipData data = ClipData.newPlainText(String.valueOf(view.getTag()), "");
			
			Point offset = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
			DragShadowBuilder shadowBuilder = new DragShadowBuilderAtTouch(view, offset);
			//start dragging the item touched
			view.startDrag(data, shadowBuilder, view, 0);
		    return true;
		}
		else 
		{
		    return false;
		}
	}
}
