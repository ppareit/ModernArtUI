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

import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout mRootLinearLayout;
    private Random random = new Random();

    private ArrayList<LinearLayout> mLeafLinearLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLinearLayout = (LinearLayout) findViewById(R.id.root_linearlayout);

        newArt();

    }

    private void newArt() {
        mLeafLinearLayouts = new ArrayList<>();

        mRootLinearLayout.removeAllViews();
        fillLinearLayout(mRootLinearLayout, 2);
        Log.d(TAG, "Leafs: " + mLeafLinearLayouts.size());

        enforceConstraints();

    }

    private void enforceConstraints() {
        if (mLeafLinearLayouts.size() < 2) {
            Log.e(TAG, "Unable to guarantee constraints");
            return;
        }
        // we need one grey and one colored rectangle
        // so we take two leafs and color them correctly
        int greyIndex = random.nextInt(mLeafLinearLayouts.size());
        int colorIndex = random.nextInt(mLeafLinearLayouts.size());
        if (greyIndex == colorIndex) { // retry
            enforceConstraints();
            return;
        }
        // we generate a light grey color
        int greyCode = nextRandomIntBetween(200, 255);
        int greyColor = Color.rgb(greyCode, greyCode, greyCode);
        mLeafLinearLayouts.get(greyIndex).setBackgroundColor(greyColor);
        // we generate a sharp color
        int[] colorCodes = new int[]{nextRandomIntBetween(200, 255), nextRandomIntBetween(200, 255), nextRandomIntBetween(200, 255)};
        colorCodes[random.nextInt(3)] = 0;
        int colorColor = Color.rgb(colorCodes[0],colorCodes[1],colorCodes[2]);
        mLeafLinearLayouts.get(colorIndex).setBackgroundColor(colorColor);
    }

    private void fillLinearLayout(LinearLayout parent, int depth) {
        for (int i = 0; i < nextRandomIntBetween(depth, depth + 1); ++i) {
            LinearLayout linLay = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = nextRandomIntBetween(1, 2);
            if (depth == 1) {
                lp.setMargins(2, 2, 2, 2);
                int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                linLay.setBackgroundColor(color);
                mLeafLinearLayouts.add(linLay);
                linLay.setLayoutParams(lp);
                parent.addView(linLay);
            } else {
                linLay.setLayoutParams(lp);
                linLay.setOrientation(depth % 2 == 0 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
                parent.addView(linLay);
                fillLinearLayout(linLay, depth - 1);
            }
        }
    }

    /**
     * @return a random number in [min,max] (inclusive!)
     */
    private int nextRandomIntBetween(int min, int max) {
        return min + random.nextInt(max + 1);
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
        if (id == R.id.action_new) {
            newArt();
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
