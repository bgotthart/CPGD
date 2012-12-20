package com.example.testjni3;

import com.example.testjni3.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
			
	private TextView text;
	private PlayerProxy playerProxy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.textView1);
		playerProxy = new PlayerProxy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onClick(View view) {
    	switch(view.getId()) {
    	case R.id.button1:
    		text.setText(playerProxy.getPosition());
    		break;
    	}
    }

}
