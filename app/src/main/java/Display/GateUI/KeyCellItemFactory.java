package Display.GateUI;

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

import Classes.*;

import org.aswing.ext.*;

    public class KeyCellItemFactory implements GridListCellFactory
    {
        private Array m_listData ;
        private int m_index ;
        private Vector<KeyCell> m_cells;

        public  KeyCellItemFactory (Array param1 )
        {
            this.m_cells = new Vector<KeyCell>();
            this.m_listData = param1;
            this.m_index = 0;
            return;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            _loc_1 = (Item)this.m_listData.get(this.m_index).item
            this.m_index++;
            int _loc_2 =0;
            if (Global.marketSaleManager.isItemOnSale(_loc_1))
            {
                _loc_2 = Global.marketSaleManager.getDiscountPercent(_loc_1);
            }
            KeyCell _loc_3 =new KeyCell(_loc_1.icon ,_loc_1.localizedName ,_loc_1.GetItemSalePrice (),Global.player.inventory.getItemCountByName(_loc_1.name ),_loc_2 );
            this.m_cells.push(_loc_3);
            return _loc_3;
        }//end

        public void  updateList ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.m_cells.length())
            {

                this.m_cells.get(_loc_1).performUpdate(Global.player.inventory.getItemCountByName(this.m_listData.get(_loc_1).item.name));
                _loc_1++;
            }
            return;
        }//end

    }




