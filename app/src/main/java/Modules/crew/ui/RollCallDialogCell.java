package Modules.crew.ui;

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
import Classes.util.*;
import Classes.virals.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Modules.crew.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class RollCallDialogCell extends JPanel implements GridListCell
    {
        protected Dictionary m_assetDict ;
        protected Loader m_imageIconLoader ;
        protected JPanel m_imagePanel ;
        protected Object m_data ;
        protected JPanel m_statusHolder ;
        protected String m_state ="";
        protected RollCallDataMechanic m_mechData ;
        protected JTextField m_textField ;
        private JPanel m_crewImage ;
        private String m_rollCallName ;
        private double m_index ;
        private int m_skipCost ;
        private static  String CHECKED_IN ="checkedIn";
        private static  String NEEDS_REMINDER ="needsReminder";
        private static  String POST_READY ="postReady";
        private static  String YOU ="you";
        private static  String SAMFRIEND ="samFriend";
        private static  String MISSEDROLLCALL ="missedRollCall";

        public  RollCallDialogCell (Dictionary param1 ,LayoutManager param2 )
        {
            this.m_assetDict = param1;
            this.m_rollCallName = this.m_assetDict.get("rollCallName");
            _loc_3 = MechanicManager.getInstance ().getMechanicInstance(this.m_assetDict.get( "spawner") ,"rollCall",MechanicManager.ALL )as RollCallDataMechanic ;
            this.m_skipCost = _loc_3.skipCost;
            super(new BorderLayout());
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            this.m_index = param3;
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_data = param1;
            this.m_mechData =(RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_assetDict.get("spawner"), "rollCall", MechanicManager.ALL);
            if (Global.isVisiting())
            {
                if (this.m_data.get("checkedIn"))
                {
                    this.m_state = CHECKED_IN;
                }
                else if (this.m_data.zid == Global.player.uid)
                {
                    this.m_state = YOU;
                }
                else if (this.m_mechData.isRollCallComplete())
                {
                    this.m_state = MISSEDROLLCALL;
                }
                else if (this.m_data.get("feedSent"))
                {
                    this.m_state = NEEDS_REMINDER;
                }
            }
            else if (this.m_data.get("checkedIn"))
            {
                this.m_state = CHECKED_IN;
            }
            else if (this.m_mechData.isRollCallComplete())
            {
                this.m_state = MISSEDROLLCALL;
            }
            else if (this.m_data.get("zid").substr(0, 1) == "-")
            {
                this.m_state = SAMFRIEND;
            }
            else if (!this.m_data.get("feedSent"))
            {
                this.m_state = POST_READY;
            }
            else
            {
                this.m_state = NEEDS_REMINDER;
            }
            this.buildCell();
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_data;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  makeBackground ()
        {
            Sprite _loc_1 =null ;
            if (this.m_data.get("orderId") % 2 == 1)
            {
                _loc_1 = new Sprite();
                _loc_1.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
                _loc_1.graphics.drawRect(0, 0, 681, 95);
                _loc_1.graphics.endFill();
                ASwingHelper.setSizedBackground(this, _loc_1);
            }
            return;
        }//end

        protected void  buildCell ()
        {
            this.removeAll();
            this.makeBackground();
            this.append(this.makeLeftPanel(), BorderLayout.WEST);
            this.append(this.makeRightPanel(), BorderLayout.EAST);
            return;
        }//end

        protected void  onImageLoaded (Event event )
        {
            DisplayObject _loc_2 =null ;
            if (this.m_imageIconLoader && this.m_imageIconLoader.content)
            {
                _loc_2 = this.m_imageIconLoader.content;
                _loc_3 = ASwingHelper.scaleToFit(_loc_2 ,this.m_imagePanel );
                _loc_2.scaleY = ASwingHelper.scaleToFit(_loc_2, this.m_imagePanel);
                _loc_2.scaleX = _loc_3;
                if (_loc_2 instanceof Bitmap)
                {
                    ((Bitmap)_loc_2).smoothing = true;
                }
                this.m_imagePanel.append(new AssetPane(_loc_2));
            }
            return;
        }//end

        protected JPanel  makeRightPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,2);
            _loc_3 = new AssetPane(new this.m_assetDict.get( "payroll_checkin_confirmCheckmark") );
            _loc_4 = new AssetPane(new this.m_assetDict.get( "payroll_checkin_missingX") );
            AssetIcon _loc_5 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            CustomButton _loc_6 =new CustomButton(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_postRollCall"),null ,"GreenButtonUI");
            _loc_6.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            CustomButton _loc_7 =new CustomButton(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_remind"),null ,"OrangeButtonUI");
            _loc_7.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            if (!this.canPostRollCallViral(this.m_data.zid))
            {
                _loc_6.setEnabled(false);
                _loc_7.setEnabled(false);
            }
            _loc_8 = this.m_skipCost ;
            CustomButton _loc_9 =new CustomButton(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_skip",{amount String(_loc_8 )}),_loc_5 ,"CashButtonUI");
            _loc_9.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_9.setMargin(new Insets(0, 5, 0, 5));
            CustomButton _loc_10 =new CustomButton(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_checkinAndNotify"),null ,"GreenButtonUI");
            CustomButton _loc_11 =new CustomButton(ZLoc.t("Dialogs","Collect"),null ,"GreenButtonUI");
            CustomButton _loc_12 =new CustomButton(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_replaceCrew"),null ,"GreenButtonUI");
            _loc_12.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_12.addActionListener(this.replaceCrewMember, 0, true);
            _loc_6.addActionListener(this.postRollCallFeed, 0, true);
            _loc_7.addActionListener(this.sendReminder, 0, true);
            _loc_9.addActionListener(this.buyCheckin, 0, true);
            _loc_10.addActionListener(this.checkIn, 0, true);
            _loc_11.addActionListener(this.collect, 0, true);
            switch(this.m_state)
            {
                case MISSEDROLLCALL:
                {
                    _loc_2.appendAll(_loc_4);
                    break;
                }
                case SAMFRIEND:
                {
                    if (Global.isVisiting() == false)
                    {
                        _loc_2.appendAll(_loc_12, _loc_9);
                    }
                    break;
                }
                case CHECKED_IN:
                {
                    _loc_2.appendAll(_loc_3);
                    break;
                }
                case POST_READY:
                {
                    if (Global.isVisiting() == false)
                    {
                        _loc_2.appendAll(_loc_6, _loc_12, _loc_9);
                    }
                    break;
                }
                case NEEDS_REMINDER:
                {
                    if (Global.isVisiting() == false)
                    {
                        _loc_2.appendAll(_loc_7, _loc_12, _loc_9);
                    }
                    break;
                }
                case YOU:
                {
                    if (this.m_mechData.isRollCallComplete())
                    {
                        _loc_2.appendAll(_loc_11);
                    }
                    else
                    {
                        _loc_2.appendAll(_loc_10);
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            ASwingHelper.prepare(_loc_2);
            _loc_13 = _loc_2(95-.getHeight())/2;
            ASwingHelper.setEasyBorder(_loc_2, _loc_13);
            _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(10));
            return _loc_1;
        }//end

        private boolean  isGhostCrewMember (String param1 )
        {
            return Global.player.findFriendById(param1) == null;
        }//end

        private boolean  canPostRollCallViral (String param1 )
        {
            return Global.world.viralMgr.canPost(ViralType.ROLL_CALL_CHECKIN, param1) == true;
        }//end

        protected JPanel  makeLeftPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            _loc_1.append(this.makeUser());
            _loc_1.append(this.makeBorder());
            _loc_1.append(this.makeUserStatus());
            return _loc_1;
        }//end

        protected AssetPane  makeBorder ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.graphics.beginFill(EmbeddedArt.rollCallBlue);
            _loc_1.graphics.drawRoundRect(0, 5, 3, 80, 2, 2);
            _loc_1.graphics.endFill();
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            return _loc_2;
        }//end

        protected JPanel  makeUser ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            _loc_2 = ASwingHelper.makeLabel(this.m_data.get( "orderId") ,EmbeddedArt.titleFont ,36,EmbeddedArt.rollCallBlue );
            this.m_crewImage = ASwingHelper.makeFriendImageFromZid(this.m_data.get("zid"));
            _loc_1.appendAll(_loc_2, this.m_crewImage);
            return _loc_1;
        }//end

        protected JPanel  makeUserStatus ()
        {
            String _loc_3 =null ;
            Player _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            _loc_2 = ASwingHelper.makeLabel(this.m_data.get( "position") ,EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.blueTextColor );
            if (this.m_data.get("zid").substr(0, 1) == "-")
            {
                _loc_3 = ZLoc.t("Main", "FakeFriendName");
            }
            else if (this.m_data.get("zid") == Global.player.uid)
            {
                _loc_3 = Global.player.name;
            }
            else if (Global.player.isFriendIDInList(this.m_data.get("zid")))
            {
                _loc_5 = Global.player.findFriendById(this.m_data.get("zid"));
                _loc_3 = _loc_5.name;
                if (!_loc_3 || _loc_3.length == 0)
                {
                    _loc_3 = ZLoc.t("RollCall", "FriendOfFriend", {firstName:Global.player.firstName});
                }
            }
            _loc_4 = ASwingHelper.makeLabel(_loc_3 ,EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.brownTextColor );
            this.m_statusHolder = this.makeStatus();
            _loc_1.appendAll(ASwingHelper.leftAlignElement(_loc_2), ASwingHelper.leftAlignElement(_loc_4), ASwingHelper.verticalStrut(10), ASwingHelper.leftAlignElement(this.m_statusHolder));
            return _loc_1;
        }//end

        protected JTextField  createTextField ()
        {
            String _loc_1 =null ;
            if (this.m_data.get("zid").substr(0, 1) == "-")
            {
                _loc_1 = ZLoc.t("Main", "FakeFriendName");
            }
            else if (Global.player.isFriendIDInList(this.m_data.get("zid")))
            {
                _loc_1 = Global.player.findFriendById(this.m_data.get("zid")).name;
            }
            this.m_textField = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_defaultMessage", {friendName:_loc_1}), EmbeddedArt.defaultSerifFont, 12, EmbeddedArt.blueTextColor);
            this.m_textField.setEditable(true);
            this.m_textField.getTextField().wordWrap = true;
            this.m_textField.getTextField().multiline = true;
            this.m_textField.setFont(new ASFont("_sans", 12, false, false, false));
            this.m_textField.getTextField().selectable = true;
            this.m_textField.setMaxChars(200);
            this.m_textField.addEventListener(MouseEvent.MOUSE_UP, this.deleteDefaultText);
            this.m_textField.setBorder(new LineBorder(null, new ASColor(EmbeddedArt.blueTextColor), 2));
            this.m_textField.setPreferredWidth(280);
            this.m_textField.setPreferredHeight(47);
            this.m_textField.setBackground(ASwingHelper.getWhite());
            return this.m_textField;
        }//end

        protected JPanel  makeStatus ()
        {
            JLabel _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            switch(this.m_state)
            {
                case CHECKED_IN:
                {
                    _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_checkedIn"), EmbeddedArt.titleFont, 24, EmbeddedArt.rollCallStatusBlue);
                    _loc_1.append(_loc_2);
                    break;
                }
                case POST_READY:
                {
                    if (Global.world.viralMgr.canPost(ViralType.ROLL_CALL_CHECKIN, this.m_data.zid) == true && Global.player.findFriendById(this.m_data.get("zid")))
                    {
                        _loc_1.append(this.createTextField());
                    }
                    break;
                }
                case NEEDS_REMINDER:
                {
                    _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_missing"), EmbeddedArt.titleFont, 24, EmbeddedArt.rollCallStatusBlue);
                    _loc_1.append(_loc_2);
                    break;
                }
                case MISSEDROLLCALL:
                {
                    _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_missingFinal"), EmbeddedArt.titleFont, 24, EmbeddedArt.rollCallStatusBlue);
                    _loc_1.append(_loc_2);
                    break;
                }
                case YOU:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

        private void  postRollCallFeed (AWEvent event )
        {
            MechanicMapResource _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            _loc_2 = this.m_data.get( "zid") ;
            if (this.canPostRollCallViral(_loc_2))
            {
                _loc_3 = this.m_assetDict.get("spawner");
                _loc_4 = "";
                _loc_5 = this.deleteDefaultText(null);
                if (this.m_textField)
                {
                    _loc_4 = this.m_textField.getText();
                }
                StatsManager.sample(100, StatsCounterType.DIALOG, "rollcalldialog", "post_roll_call", _loc_5 ? ("not_customized") : ("customized"));
                this.postFeedHelper(_loc_2, _loc_3, _loc_4);
                this.m_mechData.feedSent(_loc_2);
                this.buildCell();
            }
            return;
        }//end

        private boolean  deleteDefaultText (MouseEvent event )
        {
            String _loc_5 =null ;
            boolean _loc_2 =false ;
            String _loc_3 ="";
            String _loc_4 ="";
            if (this.m_data.get("zid").substr(0, 1) == "-")
            {
                _loc_4 = ZLoc.t("Main", "FakeFriendName");
            }
            else if (Global.player.isFriendIDInList(this.m_data.get("zid")))
            {
                _loc_4 = Global.player.findFriendById(this.m_data.get("zid")).name;
            }
            if (this.m_textField == null)
            {
                _loc_5 = "";
            }
            else
            {
                _loc_5 = this.m_textField.getText();
            }
            _loc_6 = ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_defaultMessage",{friendName _loc_4 });
            if (this.m_textField && _loc_5 == _loc_6)
            {
                this.m_textField.setText("");
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        private void  sendReminder (AWEvent event )
        {
            MechanicMapResource _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            _loc_2 = this.m_data.get( "zid") ;
            if (this.canPostRollCallViral(_loc_2))
            {
                _loc_3 = this.m_assetDict.get("spawner");
                _loc_4 = "";
                _loc_5 = this.deleteDefaultText(null);
                if (this.m_textField)
                {
                    _loc_4 = this.m_textField.getText();
                }
                _loc_6 = true;
                StatsManager.sample(100, StatsCounterType.DIALOG, "rollcalldialog", "post_roll_call", _loc_5 ? ("not_customized") : ("customized"));
                this.postFeedHelper(_loc_2, _loc_3, _loc_4);
                if (_loc_6)
                {
                    this.m_state = NEEDS_REMINDER;
                }
                this.buildCell();
            }
            return;
        }//end

        private void  onFeedSent (int param1 )
        {
            dispatchEvent(new Event("onPostRollCall", true));
            _loc_2 =(MechanicMapResource) this.m_assetDict.get( "spawner");
            if (_loc_2)
            {
                StatsManager.social("roll_call", this.m_data.get("zid"), "ask_for_check_in", _loc_2.getItemName(), _loc_2.getCrewPositionName(this.m_data.get("zid")));
            }
            return;
        }//end

        private void  buyCheckin (AWEvent event =null )
        {
            dispatchEvent(new Event("onBuyCheckin", true));
            _loc_2 = this.m_skipCost ;
            if (Global.player.cash < _loc_2)
            {
                dispatchEvent(new Event("needMoreCash", true));
                return;
            }
            if (this.m_mechData.checkIn(this.m_data.get("zid"), true))
            {
                this.m_state = CHECKED_IN;
            }
            this.buildCell();
            dispatchEvent(new UIEvent(UIEvent.CHANGE_CREW_STATE, "", true));
            return;
        }//end

        private void  replaceCrewMember (AWEvent event )
        {
            Item _loc_4 =null ;
            XMLList _loc_5 =null ;
            _loc_2 = this"crew.php?mId="+.m_mechData.owner.getId ().toString ()+"&pos="+this.m_index ;
            _loc_3 =(MechanicMapResource) this.m_assetDict.get( "spawner");
            if (_loc_3)
            {
                _loc_4 = _loc_3.getItem();
                _loc_5 = _loc_4.getCurrentCrewGateXML(_loc_3);
                if (_loc_4 && _loc_5 && String(_loc_5.@name).length > 0)
                {
                    _loc_2 = _loc_2 + ("&gname=" + _loc_5.@name);
                }
            }
            FrameManager.navigateTo(_loc_2);
            return;
        }//end

        private void  collect (AWEvent event )
        {
            TieredDooberMechanic _loc_3 =null ;
            String _loc_4 =null ;
            dispatchEvent(new Event("onCollectBonus", true));
            Global.rollCallManager.collect(Global.player.uid, this.m_assetDict.get("spawner"));
            _loc_2 =(MechanicMapResource) this.m_assetDict.get( "spawner");
            if (_loc_2)
            {
                _loc_3 =(TieredDooberMechanic) MechanicManager.getInstance().getMechanicInstance(_loc_2, "rollCallTieredDooberValue", MechanicManager.ALL);
                if (_loc_3)
                {
                    _loc_4 = _loc_3.getRandomModifiersName();
                    StatsManager.social("roll_call", Global.world.ownerId, "collect_bonus", _loc_2.getItemName(), _loc_2.getCrewPositionName(Global.player.uid), _loc_4);
                }
            }
            return;
        }//end

        private void  checkIn (AWEvent event )
        {
            Object _loc_3 =null ;
            Object _loc_4 =null ;
            _loc_2 =(MechanicMapResource) this.m_assetDict.get( "spawner");
            this.m_mechData.checkIn(Global.player.uid);
            if (Global.isVisiting() && Global.world.viralMgr.canPost(ViralType.ROLL_CALL_NOTIFY_CHECKIN, Global.world.ownerId))
            {
                _loc_3 = {owner:Global.player.getFriendFirstName(Global.world.ownerId), cityname:Global.player.getFriendCityName(Global.world.ownerId), buildingName:_loc_2.getItemFriendlyName()};
                _loc_4 = {action:"notifyCheckIn", bid:_loc_2.getId(), ownerId:Global.world.ownerId};
                RollCallManager.postFeed(RollCallManager.VIRAL_CHECKIN_REMIND, _loc_2)(_loc_3, Global.world.ownerId, _loc_4);
            }
            if (_loc_2)
            {
                StatsManager.social("roll_call", Global.world.ownerId, "checked_in", _loc_2.getItemName(), _loc_2.getCrewPositionName(Global.player.uid));
            }
            this.m_state = CHECKED_IN;
            this.buildCell();
            return;
        }//end

        private void  postFeedHelper (String param1 ,MechanicMapResource param2 ,String param3 )
        {
            _loc_4 =Global.player.getFriendFirstName(param1 );
            if (!Global.player.getFriendFirstName(param1))
            {
                _loc_4 = " ";
            }
            Object _loc_5 ={cityname Global.player.cityName ,recipient ,buildingName.getItemFriendlyName ()};
            Object _loc_6 ={bid param2.getId ()};
            RollCallManager.postFeed(RollCallManager.VIRAL_CHECKIN, param2)(_loc_5, param1, _loc_6, param3, this.onFeedSent);
            return;
        }//end

    }



