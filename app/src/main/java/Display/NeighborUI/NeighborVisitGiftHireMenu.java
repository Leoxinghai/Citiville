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
import Display.aswingui.*;
import Events.*;
import Modules.matchmaking.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.event.*;

    public class NeighborVisitGiftHireMenu extends Sprite
    {
        private Array m_buttonConfig ;
        protected Friend m_friend ;
        public JWindow m_jwindow ;

        public  NeighborVisitGiftHireMenu (Friend param1 )
        {
boolean             this .m_buttonConfig =[ {"GetHelp"name ,FriendTradeEvent action .GET_HELP , condition ()
            {
                return MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid);
            }//end
boolean             , enabledCondition ()
            {
                return MatchmakingManager.instance.askForBuildableEnabled() || MatchmakingManager.instance.askForCrewEnabled();
            }//end
boolean             },{"Visit"name ,FriendTradeEvent action .VISIT ,null condition },{"Gift"name ,FriendTradeEvent action .GIFT , condition ()
            {
                return !this.m_friend.fake && !MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid);
            }//end
void             , postSetup (CustomButton param1 )
            {
                _loc_2 = param1.getFont ();
                param1.setFont(_loc_2.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_2.getSize(), 1, [{locale:"pt", ratio:0.8}])));
                return;
            }//end
boolean             },{"InstantGift"name ,FriendTradeEvent action .INSTANT_GIFT , condition ()
            {
                return MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid) && MatchmakingManager.instance.sendFreeGiftEnabled(this.m_friend.uid);
            }//end
boolean             },{"Friend"name ,FriendTradeEvent action .FRIEND , condition ()
            {
                return this.m_friend.isNonSNFriend && MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid);
            }//end
            , skin:"BlueSmallButtonUI"}];
            this.m_friend = param1;
            this.createBackground();
            return;
        }//end

        public Friend  friend ()
        {
            return this.m_friend;
        }//end

        public void  friend (Friend param1 )
        {
            this.m_friend = param1;
            return;
        }//end

        public double  anchorHeight ()
        {
            if (this.m_buttonConfig.length > 2)
            {
                return this.m_jwindow.height - (this.m_buttonConfig.length - 2) * 20 - 15;
            }
            return this.m_jwindow.height - 15;
        }//end

        private void  createBackground ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,3);
            addEventListener(MouseEvent.MOUSE_OVER, this.onMaskNeighborActions, false, 0, true);
            addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskNeighborActions, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OUT, this.onMaskNeighborActions, false, 0, true);
            _loc_2 = this.createButtonPanel ();
            DisplayObject _loc_3 =new EmbeddedArt.neighborActionsBG ()as DisplayObject ;
            ASwingHelper.setBackground(_loc_1, _loc_3);
            ASwingHelper.prepare(_loc_2);
            _loc_1.setPreferredWidth(Math.max(_loc_3.width, _loc_2.width));
            _loc_1.setPreferredHeight(Math.max(_loc_3.height, _loc_2.height));
            _loc_1.append(_loc_2);
            this.m_jwindow = new JWindow(this);
            this.m_jwindow.setContentPane(_loc_1);
            this.m_jwindow.show();
            ASwingHelper.prepare(this.m_jwindow);
            return;
        }//end

        private JPanel  createButtonPanel ()
        {
            Object _loc_3 =null ;
            String _loc_4 =null ;
            Function _loc_5 =null ;
            boolean _loc_6 =false ;
            String _loc_7 =null ;
            CustomButton _loc_8 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,3);
            ASwingHelper.setEasyBorder(_loc_1, 15, 20, 0, 11);
            int _loc_2 =0;
            while (_loc_2 < this.m_buttonConfig.length())
            {

                _loc_3 = this.m_buttonConfig.get(_loc_2);
                _loc_4 = _loc_3.get("name");
                _loc_5 = _loc_3.get("condition");
                _loc_6 = _loc_3.hasOwnProperty("enabledCondition") ? (_loc_3.get("enabledCondition")()) : (true);
                _loc_7 = _loc_3.hasOwnProperty("skin") ? (_loc_3.get("skin")) : ("GreenSmallButtonUI");
                if (_loc_5 == null || _loc_5.apply(this) == true)
                {
                    _loc_8 = new CustomDataButton(ZLoc.t("Dialogs", _loc_4), null, _loc_7, {action:this.m_buttonConfig.get(_loc_2).get("action")});
                    _loc_8.addActionListener(this.onButtonClick, 0, true);
                    _loc_8.setEnabled(_loc_6);
                    if ("postSetup" in this.m_buttonConfig.get(_loc_2))
                    {
                        (this.m_buttonConfig.get(_loc_2).get("postSetup") as Function).call(this, _loc_8);
                    }
                    _loc_1.append(_loc_8);
                }
                _loc_2++;
            }
            _loc_1.append(ASwingHelper.verticalStrut(15));
            return _loc_1;
        }//end

        public void  onMaskNeighborActions (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        private void  onButtonClick (AWEvent event )
        {
            _loc_2 =(CustomDataButton) event.target;
            if ("action" in _loc_2.data)
            {
                dispatchEvent(new FriendTradeEvent(_loc_2.data.get("action"), this.m_friend));
            }
            return;
        }//end

        private void  hireNeighbor (AWEvent event )
        {
            dispatchEvent(new FriendTradeEvent(FriendTradeEvent.HIRE, this.m_friend));
            return;
        }//end

    }



