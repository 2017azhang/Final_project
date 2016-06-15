package com.example.andrew.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Bitmap blurred;
    private Bitmap bitmap;
    //public static Bitmap blurred;
    private ImageView harper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmap = BitmapFactory.decodeFile("C:\\Users\\2017azhang\\AndroidStudioProjects\\FinalProject\\app\\src\\main\\res\\drawable\\harper.jpg");

        harper = (ImageView) findViewById(R.id.harper);
        //harper.setImageResource(R.drawable.harper);
        harper.setImageBitmap(bitmap);
        //

        harper.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               Toast.makeText(getApplicationContext(), "Clicked Image", Toast.LENGTH_SHORT).show();
               blurred = createBlurredImage(bitmap);
               harper.setImageBitmap(blurred);

           }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    private Bitmap createBlurredImage(Bitmap bitmap){
        //Bitmap original = BitmapFactory.decodeFile("C:\\Users\\2017azhang\\AndroidStudioProjects\\FinalProject\\app\\src\\main\\res\\drawable\\harper.jpg");
        Bitmap blurred = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(this);
        /*Allocation input = Allocation.createFromBitmap(rs, original, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(rs, input.getType());*/
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createFromBitmap(rs, blurred);

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        script.setRadius(25.f);
        script.forEach(output);
        output.copyTo(blurred);
        bitmap.recycle();
        rs.destroy();

        return blurred;
    }

    /*private void BlurImageHandler(Object sender, SeekBar.stopTrackingTouchEvent e)
    private void displayBlurredImage(int radius){

    }*/


}
