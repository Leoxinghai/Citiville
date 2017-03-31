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

import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;

    public class NeighborNavigator extends Decoration
    {
        private  String NEIGHBORNAVIGATOR ="neighborNavigator";
        protected Function m_playActionCallback ;
        private boolean m_containsNeighbor ;
        private Bitmap m_neighborBillboard ;
        private Bitmap m_neighborPic ;
        private Player m_currentTopNeighbor ;

        public  NeighborNavigator (String param1)
        {
            super(param1);
            setState(STATE_STATIC);
            m_typeName = this.NEIGHBORNAVIGATOR;
            this.m_neighborBillboard = null;
            this.m_neighborPic = null;
            return;
        }//end

        public void  setPlayActionCallback (Function param1 )
        {
            this.m_playActionCallback = param1;
            return;
        }//end

        public void  containsNeighbor (boolean param1 )
        {
            this.m_containsNeighbor = param1;
            return;
        }//end

        public boolean  containsNeighbor ()
        {
            return this.m_containsNeighbor;
        }//end

        public void  neighborBillboard (Bitmap param1 )
        {
            this.m_neighborBillboard = param1;
            return;
        }//end

        public Bitmap  neighborBillboard ()
        {
            return this.m_neighborBillboard;
        }//end

        public void  neighborPic (Bitmap param1 )
        {
            this.m_neighborPic = param1;
            return;
        }//end

        public Bitmap  neighborPic ()
        {
            return this.m_neighborPic;
        }//end

        public void  currentTopNeighbor (Player param1 )
        {
            this.m_currentTopNeighbor = param1;
            return;
        }//end

        public Player  currentTopNeighbor ()
        {
            return this.m_currentTopNeighbor;
        }//end

        public void  displayFriendPicOnBillboard ()
        {
            this.m_neighborPic.x = this.m_neighborBillboard.x + 5.5;
            this.m_neighborPic.y = this.m_neighborBillboard.y + 0.5;
            double _loc_1 =0.2;
            this.m_neighborPic.scaleY = 0.2;
            this.m_neighborPic.scaleX = _loc_1;
            getDisplayObject().parent.addChild(this.m_neighborPic);
            return;
        }//end

        public void  loadTopFriendPicture ()
        {
            String url ;
            Loader icon ;
            DisplayObject dispObj ;
            if (this.m_currentTopNeighbor && this.m_currentTopNeighbor.snUser)
            {
                url = this.m_currentTopNeighbor.snUser.picture;
            }
            if (url)
            {
                icon =LoadingManager .load (url ,void  (Event event )
            {
                _loc_2 = undefined;
                if (icon && icon.content)
                {
                    _loc_2 = icon.content;
                    _loc_2.width = 50;
                    _loc_2.height = 50;
                    m_neighborPic = _loc_2;
                    displayFriendPicOnBillboard();
                }
                return;
            }//end
            );
            }
            else
            {
                dispObj = new EmbeddedArt.hud_no_profile_pic();
                dispObj.width = 50;
                dispObj.height = 50;
                this.m_neighborPic =(Bitmap) dispObj;
                this.displayFriendPicOnBillboard();
            }
            return;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public void  onPlayAction ()
        {
            if (!Global.isVisiting())
            {
                super.onPlayAction();
                if (this.m_playActionCallback != null)
                {
                    this.m_playActionCallback();
                }
            }
            return;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            if (!Global.world.isEditMode && !Global.isVisiting())
            {
                super.setHighlighted(param1);
            }
            return;
        }//end

         public void  showObjectBusy ()
        {
            return;
        }//end

    }



