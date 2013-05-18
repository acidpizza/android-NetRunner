package DragAndDrop;

import android.graphics.Point;
import android.view.View;

public final class DragShadowBuilderAtTouch extends View.DragShadowBuilder 
{
	int x, y;
	
	// Defines the constructor for myDragShadowBuilder
	public DragShadowBuilderAtTouch(View v, Point offset) 
	{
		// Stores the View parameter passed to myDragShadowBuilder.
		super(v);
		x = offset.x;
		y = offset.y;
	}

	// Defines a callback that sends the drag shadow dimensions and touch point back to the
	// system.
	@Override
	public void onProvideShadowMetrics (Point size, Point touch)
	{
		// Defines local variables
		int width;
		int height;

		// Sets the width of the shadow to half the width of the original View
		width = getView().getWidth();

		// Sets the height of the shadow to half the height of the original View
		height = getView().getHeight();

		// Sets the size parameter's width and height values. These get back to the system
		// through the size parameter.
		size.set(width, height);

		// Sets the touch point's position to be in the middle of the drag shadow
		touch.set(x, y);
	}
	
}