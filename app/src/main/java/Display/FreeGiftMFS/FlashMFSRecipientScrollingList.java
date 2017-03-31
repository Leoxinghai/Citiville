package Display.FreeGiftMFS;

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

import Display.aswingui.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class FlashMFSRecipientScrollingList extends JPanel
    {
        private GridList m_dataList ;
        private JScrollPane m_scrollPane ;
        private GridListCellFactory m_cellFactory ;
        protected int m_columns ;
        protected int m_rows ;

        public  FlashMFSRecipientScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            this.m_cellFactory = new param2();
            this.m_columns = param4;
            this.m_rows = param5;
            VectorListModel _loc_6 =new VectorListModel ();
            int _loc_7 =0;
            while (_loc_7 < param1.length())
            {
                
                _loc_6.append(param1.get(_loc_7));
                _loc_7++;
            }
            this.m_dataList = new GridList(_loc_6, this.m_cellFactory, param4, param5);
            this.m_dataList.setHGap(30);
            this.m_dataList.setVGap(2);
            this.m_scrollPane = new JScrollPane(this.m_dataList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            ASwingHelper.setForcedSize(this.m_scrollPane, new IntDimension(663, 300));
            this.append(this.m_scrollPane);
            return;
        }//end  

    }



