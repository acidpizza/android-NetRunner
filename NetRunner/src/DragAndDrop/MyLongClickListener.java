package DragAndDrop;

import android.content.ClipData;
import android.view.View;
import android.view.View.OnLongClickListener;

public final class MyLongClickListener implements OnLongClickListener
{
	// Defines the one method for the interface, which is called when the View is long-clicked
	@Override
	public boolean onLongClick(View v) 
	{
		v.setVisibility(View.INVISIBLE);
		
		ClipData dragData = ClipData.newPlainText(String.valueOf(v.getTag()), "");

		View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

		v.startDrag(dragData, myShadow, v, 0);
		
		return true;
	}
};
