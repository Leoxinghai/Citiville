package Display.NeighborUI;

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
import Display.*;
import Engine.*;
import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;

    public class FriendBarSlot extends Sprite
    {
        public Bitmap m_background ;
        public int m_index ;
        public TextField m_label ;
        public Player m_friend ;
        protected Object m_friendMetaData =null ;
        public DisplayObject m_profilePic ;
        public int m_xp =0;
        public int m_level =0;
        public int m_rank =0;
        protected boolean m_isLoadingFriend =false ;
        public Object m_avatarData ;
        public boolean m_fakeFriend ;
        public boolean m_empty ;
        private SlotLadder m_slotLadder ;
        public double m_wishListElementCount ;
        private static  Point PIC_POSITION =new Point(5.5,24);

        public  FriendBarSlot (int param1 ,boolean param2 )
        {
            this.m_index = param1;
            this.m_background = new EmbeddedArt.neighborCard();
            this.m_background.y = 16;
            addChildAt(this.m_background, 0);
            this.m_label = new TextField();
            this.m_wishListElementCount = MathUtil.random(5);
            this.m_slotLadder = new SlotLadder(this.m_wishListElementCount);
            this.m_label.selectable = false;
            addChild(this.m_label);
            this.addEventListener(MouseEvent.ROLL_OVER, this.onRollOverFriend);
            this.addEventListener(MouseEvent.ROLL_OUT, this.onRollOutFriend);
            return;
        }//end

        public void  updateSlot (Player param1 ,int param2 ,Object param3 ,boolean param4 =false )
        {
            this.m_friendMetaData = param3;
            this.m_level = param3.level;
            this.m_xp = param3.xp;
            this.m_avatarData = param3.avatar;
            this.m_rank = param2;
            this.m_fakeFriend = param3.fake;
            this.m_empty = param3.empty;
            if (this.numChildren > 1)
            {
                this.removeChildAt(1);
            }
            this.m_friend = param1;
            this.m_isLoadingFriend = false;
            if (this.m_empty)
            {
                this.setupEmptySlot();
            }
            else if (param4)
            {
                this.setupLoadingOccupiedSpot();
                this.m_isLoadingFriend = true;
            }
            else
            {
                this.setupOccupiedSpot();
            }
            return;
        }//end

        private void  setupOccupiedSpot ()
        {
            DisplayObject _loc_1 =null ;
            DisplayObject _loc_6 =null ;
            DisplayObject _loc_7 =null ;
            DisplayObject _loc_12 =null ;
            DisplayObject _loc_13 =null ;
            String _loc_16 =null ;
            if (this.m_friend && this.m_friend.snUser)
            {
                _loc_16 = this.m_friend.snUser.picture;
                if (_loc_16)
                {
                    _loc_1 = LoadingManager.load(_loc_16, null, LoadingManager.PRIORITY_LOW);
                }
            }
            if (_loc_1 == null)
            {
                _loc_1 = new EmbeddedArt.emptyAvatar();
                _loc_1.width = 48;
                _loc_1.height = 48;
            }
            _loc_1.x = PIC_POSITION.x;
            _loc_1.y = PIC_POSITION.y;
            _loc_1.scaleX = 1;
            _loc_1.scaleY = 1;
            this.m_profilePic = addChildAt(_loc_1, 0);
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.font = EmbeddedArt.defaultFontNameBold;
            _loc_2.bold = true;
            _loc_2.size = 12;
            _loc_2.color = 16777215;
            this.m_label.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_label.defaultTextFormat = _loc_2;
            this.m_label.setTextFormat(_loc_2);
            this.m_label.selectable = false;
            this.m_label.autoSize = TextFieldAutoSize.LEFT;
            this.m_label.text = this.m_friend.snUser.firstName;
            this.m_label.width = this.width;
            this.m_label.wordWrap = false;
            this.m_label.y = 2;
            this.m_label.x = -4;
            addChild(this.m_label);
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.font = EmbeddedArt.defaultFontNameBold;
            _loc_3.bold = true;
            _loc_3.size = 16;
            _loc_3.color = 16775567;
            _loc_3.align = TextFormatAlign.CENTER;
            TextField _loc_4 =new TextField ();
            _loc_4.filters = .get(new GlowFilter(8211727, 1, 2, 2, 10));
            _loc_4.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_4.defaultTextFormat = _loc_3;
            _loc_4.setTextFormat(_loc_3);
            _loc_4.selectable = false;
            _loc_4.width = 20;
            _loc_4.text = this.m_level.toString();
            _loc_4.x = 45;
            _loc_4.y = 18;
            addChild(_loc_4);
            Sprite _loc_5 =new Sprite ();
            _loc_5.addChild(_loc_6);
            _loc_6.x = 5;
            _loc_5.addChild(_loc_7);
            _loc_7.x = -5;
            _loc_7.y = 0;
            TextFormat _loc_8 =new TextFormat ();
            _loc_8.font = EmbeddedArt.defaultFontNameBold;
            _loc_8.bold = true;
            _loc_8.size = 14;
            _loc_8.color = EmbeddedArt.brownTextColor;
            _loc_8.align = TextFormatAlign.CENTER;
            TextField _loc_9 =new TextField ();
            _loc_9.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_9.defaultTextFormat = _loc_8;
            _loc_9.setTextFormat(_loc_8);
            _loc_9.selectable = false;
            _loc_9.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_9.width = 30;
            _loc_9.text = ZLoc.t("Main", "Gift");
            _loc_9.x = 15;
            _loc_5.addChild(_loc_9);
            SimpleButton _loc_10 =new SimpleButton(_loc_5 ,_loc_5 ,_loc_5 ,_loc_5 );
            addChild(_loc_10);
            _loc_10.y = 71;
            Sprite _loc_11 =new Sprite ();
            _loc_11.addChild(_loc_12);
            _loc_12.x = 5;
            _loc_11.addChild(_loc_13);
            _loc_13.x = -5;
            _loc_13.y = 0;
            TextField _loc_14 =new TextField ();
            _loc_14.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_14.defaultTextFormat = _loc_8;
            _loc_14.setTextFormat(_loc_8);
            _loc_14.selectable = false;
            _loc_14.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_14.width = 39;
            _loc_14.text = ZLoc.t("Main", "Visit");
            _loc_14.x = 11;
            _loc_11.addChild(_loc_14);
            SimpleButton _loc_15 =new SimpleButton(_loc_11 ,_loc_11 ,_loc_11 ,_loc_11 );
            addChild(_loc_15);
            _loc_15.y = 89;
            return;
        }//end

        private void  setupEmptySlot ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.x = 35;
            _loc_1.y = 80;
            _loc_1.width = 40;
            _loc_1.height = 60;
            Array _loc_2 =.get(10249488) ;
            _loc_3 = this.m_rank % _loc_2.length;
            this.m_background.visible = false;
            _loc_4 = new EmbeddedArt.emptyAvatar ();
            _loc_4.x = (_loc_1.width - _loc_4.width) / 2 + 28;
            _loc_4.y = (_loc_1.height - _loc_4.height) / 2 + 65;
            addChild(_loc_4);
            _loc_5 = ZLoc.t("Main","InviteNeighbor");
            _loc_6 = this.createSlotStatusText(_loc_5 ,60,-1,28);
            addChild(_loc_1);
            return;
        }//end

        private void  setupLoadingOccupiedSpot ()
        {
            _loc_1 = ZLoc.t("Main","LoadingNeighborSlot");
            _loc_2 = this.createSlotStatusText(_loc_1 ,60,1,110);
            addChild(_loc_2);
            return;
        }//end

        private StrokeTextField  createSlotStatusText (String param1 ,double param2 ,double param3 ,double param4 )
        {
            Array _loc_5 =.get(1322345) ;
            _loc_6 = this.m_rank % _loc_5.length;
            TextFormat _loc_7 =new TextFormat(EmbeddedArt.defaultFontNameBold ,12,16777215,true ,null ,null ,null ,null ,TextFormatAlign.CENTER );
            StrokeTextField _loc_8 =new StrokeTextField(_loc_5.get(_loc_6) ,1);
            _loc_8.toolTipText = param1;
            _loc_8.setTextFormat(_loc_7);
            _loc_8.width = 60;
            _loc_8.wordWrap = true;
            _loc_8.multiline = true;
            _loc_8.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_8.x = param3;
            _loc_8.y = param4;
            return _loc_8;
        }//end

        private void  onRollOverFriend (MouseEvent event )
        {
            double _loc_2 =0;
            if (!this.m_empty)
            {
                addChild(this.m_slotLadder);
                this.m_slotLadder.x = -7;
                this.m_slotLadder.visible = true;
                this.m_slotLadder.alpha = 0;
                _loc_2 = (-this.m_slotLadder.m_itemsCount) * this.m_slotLadder.SLOT_LADDER_SPACING - 47;
                TweenLite.to(this.m_slotLadder, 0.2, {alpha:1, y:_loc_2});
            }
            return;
        }//end

        private void  onRollOutFriend (MouseEvent event )
        {
            e = event;
            if (!this.m_empty)
            {
void                 TweenLite .to (this .m_slotLadder ,0.2,{0alpha , onComplete ()
            {
                m_slotLadder.y = 0;
                m_slotLadder.visible = false;
                return;
            }//end
            });
            }
            return;
        }//end

        private boolean  isJack ()
        {
            if (this.m_friend)
            {
                if (this.m_friend.getId() && this.m_friend.getId() == Player.FAKE_USER_ID)
                {
                    return true;
                }
            }
            return false;
        }//end

        public String  uid ()
        {
            return this.m_friend ? (this.m_friend.uid) : (null);
        }//end

    }



