package com.example.duyanhtang.homework4_duytang;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Activity2 extends AppCompatActivity {
    int width,height;
    int statusBarHeight,actionBarHeight;
    int originalx=0,originaly=0,originalIVX=0,originalIVY=0; //variables used for keeping track of move event
    int[] picID={R.drawable.p0,R.drawable.p1,R.drawable.p2,R.drawable.p3,
            R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,
            R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15};
    int[][] blockCoordinate;
    boolean[] correctlyPlacedBlock;
    ImageView grid;
    List<ImageView> imgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        grid=(ImageView) findViewById(R.id.grid);
        imgList=new Vector<>();
        imgList.add((ImageView) findViewById(R.id.img0));
        imgList.add((ImageView) findViewById(R.id.img1));
        imgList.add((ImageView) findViewById(R.id.img2));
        imgList.add((ImageView) findViewById(R.id.img3));
        imgList.add((ImageView) findViewById(R.id.img4));
        imgList.add((ImageView) findViewById(R.id.img5));
        imgList.add((ImageView) findViewById(R.id.img6));
        imgList.add((ImageView) findViewById(R.id.img7));
        imgList.add((ImageView) findViewById(R.id.img8));
        imgList.add((ImageView) findViewById(R.id.img9));
        imgList.add((ImageView) findViewById(R.id.img10));
        imgList.add((ImageView) findViewById(R.id.img11));
        imgList.add((ImageView) findViewById(R.id.img12));
        imgList.add((ImageView) findViewById(R.id.img13));
        imgList.add((ImageView) findViewById(R.id.img14));
        imgList.add((ImageView) findViewById(R.id.img15));
        setup();
    }

    /**
     * Code reused from this post: https://gist.github.com/hamakn/8939eb68a920a6d7a498
     * @return the height of the status bar
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * Code reused from this post: https://gist.github.com/hamakn/8939eb68a920a6d7a498
     * @return the height of the navigation bar
     */
    public int getNavigationBarHeight(){
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * Code reused from this post: https://gist.github.com/hamakn/8939eb68a920a6d7a498
     * @return the height of the action bar
     */
    public int getActionBarHeight(){
        int actionBarHeight = 0;
        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarHeight;
    }
    /**
     *  Setup the components of the game
     */
    private void setup() {

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
        height=dm.heightPixels;
        statusBarHeight=getStatusBarHeight();
        actionBarHeight=getActionBarHeight();
        Log.d("Status bar height",statusBarHeight+"");
        Log.d("Navigation bar height",getNavigationBarHeight()+"");
        Log.d("Action bar height",""+getActionBarHeight());
        Log.i("Width and height:",width+", "+height);

        FrameLayout.LayoutParams gridlo=new FrameLayout.LayoutParams(width,width);
        gridlo.setMargins(0,0,0,0);
        grid.setLayoutParams(gridlo);
        int[][] coordinate={
                {0,0},        {width/4,0},        {width/2,0},        {3*width/4,0},
                {0,width/4},  {width/4,width/4},  {width/2,width/4},  {3*width/4,width/4},
                {0,width/2},  {width/4,width/2},  {width/2,width/2},  {3*width/4,width/2},
                {0,3*width/4},{width/4,3*width/4},{width/2,3*width/4},{3*width/4,3*width/4},
        };
        blockCoordinate=coordinate;
        correctlyPlacedBlock=new boolean[16];
        int bottomSpace=height-width-getNavigationBarHeight();
        Random r=new Random();
        //spread out the blocks to the block area
        for(int i=0;i<imgList.size();i++){
            imgList.get(i).setImageResource(picID[i]);
            FrameLayout.LayoutParams imglo=new FrameLayout.LayoutParams(width/4,width/4);
            imglo.setMargins(r.nextInt(width-width/4),width+r.nextInt(bottomSpace-width/4),0,0);
            imgList.get(i).setLayoutParams(imglo);
        }
        // add TouchListener to every ImageViews
        for(int i=0;i<imgList.size();i++){
            imgList.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView iv=(ImageView)v;
                    int x=0,y=0;
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            originalx=(int)event.getRawX();
                            originaly=(int)event.getRawY();
                            originalIVX=(int)iv.getX();
                            originalIVY=(int)iv.getY();
                            Log.d("Touch",originalx+","+originaly);
                            Log.d("Original position",originalIVX+","+originalIVY);
                            iv.bringToFront();
                            break;
                        case MotionEvent.ACTION_UP:
                            x=(int)event.getRawX();
                            y=(int)event.getRawY();
                            Log.d("Released",x+","+y);
                            Log.d("ImageView released pos",iv.getX()+", "+iv.getY());
                            int location=findInGrid(x,y-statusBarHeight-actionBarHeight);
                            //int location=findInGrid(iv.getLeft(),iv.getTop());
                            if (location!=-1){
                                Log.d("Location to be placed:",location+"");
                                FrameLayout.LayoutParams slo=new FrameLayout.LayoutParams(width/4,width/4);
                                slo.setMargins(blockCoordinate[location][0],blockCoordinate[location][1],0,0);
                                iv.setLayoutParams(slo);
                                if (location==imgList.indexOf(iv)){
                                    correctlyPlacedBlock[location]=true;
                                    iv.setOnTouchListener(null);
                                }
                            }
                            if (gameIsFinished())
                                Toast.makeText(Activity2.this,"CONGRATULATIONS! YOU FINISHED THE PUZZLE", Toast.LENGTH_LONG).show();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            x=(int)event.getRawX();
                            y=(int)event.getRawY();
                            FrameLayout.LayoutParams lo=new FrameLayout.LayoutParams(width/4,width/4);
                            lo.setMargins(originalIVX+x-originalx,originalIVY+y-originaly,0,0);
                            iv.setLayoutParams(lo);
                            Log.d("Move to:",x+","+y);
                            //Log.d("Move relative to view",event.getX()+", "+event.getY());
                            break;
                    }
                    return true;

                }
            });
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return the position to place the ImageView once released, -1 if the cursor is not in the grid
     */
    private int findInGrid(float x, float y) {
        for (int i=0;i<blockCoordinate.length;i++){
            if (x>=blockCoordinate[i][0]&&x<(blockCoordinate[i][0]+width/4)&&
                    y>=blockCoordinate[i][1]&&y<(blockCoordinate[i][1]+width/4)) return i;
        }
        return -1;
    }

    /**
     *
     * @return true if game is finished, otherwise false
     */
    private boolean gameIsFinished(){
        for (int i=0;i<correctlyPlacedBlock.length;i++){
            if (!correctlyPlacedBlock[i]) return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.reset){
            setup();
        }
        return true;
    }

}
