package be.ppareit.modernartui;

import android.graphics.Color;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout mRootLinearLayout;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLinearLayout = (LinearLayout) findViewById(R.id.root_linearlayout);
        fillLinearLayout(mRootLinearLayout, 4);
    }

    private void fillLinearLayout(LinearLayout parent, int depth) {
        if (depth == 0) {
            return;
        }
        for (int i = 0; i < depth + random.nextInt(2); ++i) {
            LinearLayout linLay = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1 + random.nextInt(2);
            if ( depth == 1) {
                lp.setMargins(2, 2, 2, 2);
                int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                linLay.setBackgroundColor(color);
            }
            linLay.setLayoutParams(lp);
            linLay.setOrientation(depth % 2 == 0 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
            parent.addView(linLay);
            fillLinearLayout(linLay, depth - 1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
