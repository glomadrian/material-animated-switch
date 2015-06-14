package com.github.glomadrian.switchinboxpinned;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.github.glomadrian.materialanimatedswitch.SwitchPinned;

public class MainActivity extends ActionBarActivity {

  private SwitchPinned switchPinned;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    switchPinned = (SwitchPinned) findViewById(R.id.pin);
    switchPinned.setOnCheckedChangeListener(new SwitchPinned.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(boolean isChecked) {
        Toast.makeText(MainActivity.this, isChecked + "", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
