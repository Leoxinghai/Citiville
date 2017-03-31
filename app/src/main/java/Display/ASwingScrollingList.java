package Display;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import org.aswing.*;
import org.aswing.ext.*;

    public class ASwingScrollingList extends JPanel
    {
        private GridList m_dataList ;
        private JScrollPane m_scrollPane ;
        private GridListCellFactory m_cellFactory ;
        private JWindow m_frame ;
        public static  int SCROLL_VERTICAL =0;
        public static  int SCROLL_HORIZONTAL =1;

        public  ASwingScrollingList (Array param1 ,GridListCellFactory param2 ,int param3 ,int param4 =0,int param5 =2,int param6 =100,int param7 =100)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 1, SoftBoxLayout.CENTER));
            this.m_cellFactory = param2;
            VectorListModel _loc_8 =new VectorListModel ();
            int _loc_9 =0;
            while (_loc_9 < param1.length())
            {

                _loc_8.append(param1.get(_loc_9));
                _loc_9++;
            }
            this.m_dataList = new GridList(_loc_8, this.m_cellFactory, param4, param5);
            this.m_dataList.setHGap(0);
            this.m_dataList.setVGap(0);
            this.m_dataList.setTileWidth((param6 - 30) / param4);
            this.m_dataList.setTileHeight(param7 / param5);
            this.m_scrollPane = new JScrollPane(this.m_dataList);
            this.m_scrollPane.setPreferredHeight(param7);
            this.m_scrollPane.setPreferredWidth(param6);
            this.append(this.m_scrollPane);
            return;
        }//end

        public GridList  dataList ()
        {
            return this.m_dataList;
        }//end

        public void  scrollHPolicy (int param1 )
        {
            this.m_scrollPane.setHorizontalScrollBarPolicy(param1);
            return;
        }//end

        public void  scrollVPolicy (int param1 )
        {
            this.m_scrollPane.setVerticalScrollBarPolicy(param1);
            return;
        }//end

    }


