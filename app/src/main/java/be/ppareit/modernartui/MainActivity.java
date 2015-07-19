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
import android.widget.SeekBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout mRootLinearLayout;
    private SeekBar mColorSeekBar;

    private Random random = new Random();

    private ArrayList<LinearLayout> mLeafLinearLayouts;
    private ArrayList<LinearLayout> mColoredLeafLinearLayouts;
    private int nColor = 0;
    private int nOrientation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLinearLayout = (LinearLayout) findViewById(R.id.root_linearlayout);

        mColorSeekBar = (SeekBar) findViewById(R.id.color_seekbar);
        mColorSeekBar.setOnSeekBarChangeListener(this);

        newArt();

    }

    private void newArt() {
        mLeafLinearLayouts = new ArrayList<>();
        mColoredLeafLinearLayouts = new ArrayList<>();

        mRootLinearLayout.removeAllViews();
        nColor = random.nextInt();
        nOrientation = random.nextInt();

        fillLinearLayout(mRootLinearLayout, 3);

    }

    private int getGreyColor() {
        int greyCode = nextRandomIntBetween(200, 255);
        return Color.rgb(greyCode, greyCode, greyCode);
    }

    private int getColorColor() {
        int[] colorCodes = new int[]{nextRandomIntBetween(200, 255), nextRandomIntBetween(200, 255), nextRandomIntBetween(200, 255)};
        colorCodes[random.nextInt(3)] = 0;
        return Color.argb(255, colorCodes[0], colorCodes[1], colorCodes[2]);
    }

    private int getNextColor() {
        nColor++;
        return (nColor % 2 == 0) ? getGreyColor() : getColorColor();
    }

    private void fillLinearLayout(LinearLayout parent, int depth) {
        for (int i = 0; i < nextRandomIntBetween(depth, depth + 1); ++i) {
            LinearLayout linLay = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = nextRandomIntBetween(1, 2);
            if (depth == 1) {
                lp.setMargins(2, 2, 2, 2);
                if (nColor++ % 2 == 0) {
                    linLay.setBackgroundColor(getColorColor());
                    mColoredLeafLinearLayouts.add(linLay);
                } else {
                    linLay.setBackgroundColor(getGreyColor());
                }
                mLeafLinearLayouts.add(linLay);
                linLay.setLayoutParams(lp);
                parent.addView(linLay);
            } else {
                linLay.setLayoutParams(lp);
                linLay.setOrientation(nOrientation++ % 2 == 0 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
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
        if (id == R.id.action_moreinfo) {
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to get the information from the MoMa")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "Display site");
                        }
                    })
                    .create();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        for (LinearLayout ll : mColoredLeafLinearLayouts) {
            ll.setAlpha(1.f - progress/100.f);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
