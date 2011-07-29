package com.WireHeadApps.TiamatRomUtility.apk;

import java.io.DataOutputStream;
import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TiamatRomUtilitiesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Define the toggle buttons
        ToggleButton toggleLED = (ToggleButton) findViewById(R.id.toggleLED);
        
        // Define the settings files and check to see if they exist
        File eclfile;
        eclfile = new File ("/data/tiamat_controls/enableChargingLight");
        if (eclfile.exists()) {
        	toggleLED.setChecked(true);
        }

        // Handle user input, creating or removing file based on input
       	toggleLED.setOnClickListener(new View.OnClickListener() {
       		@Override
       		public void onClick(View v) {
       			Process p;
       			
       			if(((ToggleButton) v).isChecked()) {
       				Toast.makeText(getBaseContext(), "LED ON - Changes require REBOOT", Toast.LENGTH_SHORT).show();
       				try {
       					p = Runtime.getRuntime().exec("su");
       					DataOutputStream os = new DataOutputStream(p.getOutputStream());
       					os.writeBytes("echo \"On\" >/data/tiamat_controls/enableChargingLight\n");
       					os.writeBytes("exit\n");
       					os.flush();
       				}
       				catch(Exception ex) {}
       				}       				
       			else{
       				Toast.makeText(getBaseContext(), "LED OFF - Changes require REBOOT", Toast.LENGTH_SHORT).show();
       				try {
       					p = Runtime.getRuntime().exec("su");
       					DataOutputStream os = new DataOutputStream(p.getOutputStream());
      					os.writeBytes("rm /data/tiamat_controls/enableChargingLight\n");
       					os.writeBytes("exit\n");
       					os.flush();
       				}
       				catch(Exception ex) {}
       				}
       			}
       		});
       	
        Button reboot = (Button) findViewById(R.id.reboot);
       	reboot.setOnClickListener(new View.OnClickListener()
    	{
    		@Override
    		public void onClick(View v) {
    			Process x;
    			try {
    				x = Runtime.getRuntime().exec("su");
    				DataOutputStream os = new DataOutputStream(x.getOutputStream());
   					os.writeBytes("reboot\n");
    			}
    			catch(Exception ex) {}
    		}
    	});

    }
}