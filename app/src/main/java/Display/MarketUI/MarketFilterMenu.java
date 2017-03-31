package Display.MarketUI;

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
import Display.RenameUI.*;
import Display.aswingui.*;
//import flash.display.*;
import org.aswing.*;

    public class MarketFilterMenu extends JPanel
    {
        protected Array m_items ;

        public  MarketFilterMenu (Array param1 )
        {
            items = param1;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this .m_items =items .filter (boolean  (Item param11 ,int param2 ,Array param3 )
            {
                if (param11.isRemodelSkin())
                {
                    return false;
                }
                return true;
            }//end
            );
            Array all =new Array ();
            all.push(ZLoc.t("Market", "all"));
            all = all.concat(this.m_items);
            ASwingHelper.setBackground(this, new Catalog.assetDict.get("market2_sortDropDownPress"));
            VectorListModel model =new VectorListModel(all );
            VerticalScrollingList list =new VerticalScrollingList(Catalog.assetDict ,model ,new RenameCellFactory(MarketFilterCell ,Catalog.assetDict ),1,8,200,200);
            ASwingHelper.setEasyBorder(list, 5, 5, 5, 5);
            this.append(list);
            ASwingHelper.prepare(this);
            return;
        }//end

    }



