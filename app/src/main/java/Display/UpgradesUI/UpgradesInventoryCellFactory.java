package Display.UpgradesUI;

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

//import flash.utils.*;
import org.aswing.ext.*;

    public class UpgradesInventoryCellFactory implements GridListCellFactory
    {
        private Array m_listData ;
        private int m_index ;
        private Vector<UpgradeInventoryCell> m_cells;
        private Dictionary m_assets ;

        public  UpgradesInventoryCellFactory (Array param1 ,Dictionary param2 )
        {
            this.m_cells = new Vector<UpgradeInventoryCell>();
            this.m_listData = param1;
            this.m_assets = param2;
            this.m_index = 0;
            return;
        }//end

        public GridListCell  createNewGridListCell ()
        {
            _loc_1 =(Item) this.m_listData.get(this.m_index).item;
            this.m_index++;
            UpgradeInventoryCell _loc_2 =new UpgradeInventoryCell(_loc_1.icon ,_loc_1.localizedName ,_loc_1.cash ,Global.player.inventory.getItemCountByName(_loc_1.name ),this.m_assets );
            this.m_cells.push(_loc_2);
            return _loc_2;
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


