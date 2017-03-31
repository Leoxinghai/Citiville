package Classes;

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

    public class Mission
    {
        protected String m_missionType ;
        protected XML m_xml ;
        private String m_visitorUserId ;
        private String m_missionHostId ;
        private boolean m_hitLimit ;
        private Player m_hostPlayer =null ;

        public  Mission (String param1 ,String param2 ,String param3 ,boolean param4 ,Player param5 =null )
        {
            this.m_missionType = param1;
            this.m_visitorUserId = param2;
            this.m_missionHostId = param3;
            this.m_hitLimit = param4;
            this.m_hostPlayer = param5;
            _loc_6 = Global.gameSettings().getMissionByType(param1);
            if (Global.gameSettings().getMissionByType(param1))
            {
                this.m_xml = _loc_6.get(0);
            }
            return;
        }//end

        public String  visitorUserId ()
        {
            return this.m_visitorUserId;
        }//end

        public String  missionHostId ()
        {
            return this.m_missionHostId;
        }//end

        public String  missionType ()
        {
            return this.m_missionType;
        }//end

        public boolean  hitLimit ()
        {
            return this.m_hitLimit;
        }//end

        public Player  hostPlayer ()
        {
            return this.m_hostPlayer;
        }//end

        public String  assetItemName ()
        {
            return this.m_xml.assetItem;
        }//end

        public String  soundURL ()
        {
            return Global.getAssetURL(this.m_xml.sfx.@url);
        }//end

        public String  cursorUrl ()
        {
            return this.m_xml.cursorImage.@url;
        }//end

        public String  toolTip ()
        {
            return ZLoc.t("Missions", this.m_missionType + "_Tooltip");
        }//end

        public String  actionText ()
        {
            return ZLoc.t("Missions", this.m_missionType + "_ActionText");
        }//end

        public int  numObjects ()
        {
            return this.m_xml.numObjects;
        }//end

        public String  inventoryItemName ()
        {
            return this.m_xml.itemType;
        }//end

    }



