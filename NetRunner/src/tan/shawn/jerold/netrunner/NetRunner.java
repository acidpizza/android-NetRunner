package tan.shawn.jerold.netrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

public class NetRunner extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_runner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.net_runner, menu);
		return true;
	}

    public void startGame(View view) 
    {
    	
    	Intent intentFrag = new Intent(this, GameScreen.class);
    	String side = "";
    	ToggleButton toggle = (ToggleButton)findViewById(R.id.toggleSide);

    	if(toggle.isChecked())
    	{
    		side = "Runner";
    	}
    	else
    	{
    		side = "Corporation";
    	}
    	
    	intentFrag.putExtra("SIDE", side);
    	startActivity(intentFrag);
    	
    	
    	/*
    	ToggleButton toggle = (ToggleButton)findViewById(R.id.toggleSide);

    	if(toggle.isChecked())
    	{
    		//side = "Runner";		
			Intent intent = new Intent(this, Runner.class);
	    	startActivity(intent);
    	}
    	else
    	{
    		//side = "Corporation";
			Intent intent = new Intent(this, Corp.class);
	    	startActivity(intent);
    	}
    	*/
    }
	
}
