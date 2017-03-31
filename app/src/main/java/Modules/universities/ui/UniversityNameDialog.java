package Modules.universities.ui;

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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class UniversityNameDialog extends FlyFishDialog
    {
        private MechanicMapResource m_spawner ;
        private Object m_data ;
        private JTextField m_universityInputTextField ;

        public  UniversityNameDialog (MechanicMapResource param1 ,Object param2 )
        {
            this.m_spawner = param1;
            this.m_data = param2;
            super("assets/flyfish/UniversityName.xml");
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.UNIVERSITY_ASSETS, DelayedAssetLoader.ATTRACTIONS_ASSETS, DelayedAssetLoader.NEW_QUEST_ASSETS, DelayedAssetLoader.MARKET_ASSETS);
        }//end

         protected void  performDialogActions ()
        {
            titleText = (JLabel)pane.getComponent("Title_Text")
            titleText.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Congratulations")));
            ASwingHelper.setProperFont(titleText, EmbeddedArt.titleFont);
            samText = (MultilineLabel)pane.getComponent("sam_text")
            samText.setText(ZLoc.t("Dialogs", "University_complete"));
            ASwingHelper.setProperFont(samText, EmbeddedArt.defaultFontNameBold);
            samText.setForeground(new ASColor(7553852));
            samText.setColumns(15);
            samText.setRows(5);
            samText.setEnabled(false);
            universityName = (JLabel)pane.getComponent("universityName")
            universityName.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "University_iconName")));
            ASwingHelper.setProperFont(universityName, EmbeddedArt.defaultFontNameBold);
            universityNamePrompt = (JLabel)pane.getComponent("nameYourUniversity")
            universityNamePrompt.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "University_nameYourUni")));
            ASwingHelper.setProperFont(universityNamePrompt, EmbeddedArt.titleFont);
            this.m_universityInputTextField =(JTextField) pane.getComponent("universityInputTextField");
            this.m_universityInputTextField.setText(ZLoc.t("Dialogs", "University_default", {city:Global.player.cityName}));
            ASwingHelper.setProperFont(this.m_universityInputTextField, EmbeddedArt.defaultFontNameBold);
            this.m_universityInputTextField.setForeground(new ASColor(7553852));
            LoadingManager .load (this .m_spawner .getItem ().icon ,void  (Event event )
            {
                (pane.getComponent("universityAsset") as AssetPane).setAsset(event.target.content);
                return;
            }//end
            );
            closebtn = pane.getComponent("close_btn");
            closebtn.addActionListener(this.closeDialog, 0, true);
            acceptbtn = pane.getComponent("saveBtn");
            dialogViewData.put("acceptText",  ZLoc.t("Dialogs", "Save"));
            acceptbtn.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Save")));
            acceptbtn.setFont(new ASFont(EmbeddedArt.titleFont, 24, false, false, false, EmbeddedArt.advancedFontProps));
            acceptbtn.addActionListener(this.acceptDialog, 0, true);
            this.addEventListener(Event.ADDED_TO_STAGE, this.setFocus, false, 0, true);
            return;
        }//end

        protected void  setFocus (Event event )
        {
            this.removeEventListener(Event.ADDED_TO_STAGE, this.setFocus);
            _loc_2 = this.m_universityInputTextField.getTextField ();
            _loc_2.maxChars = 25;
            Global.stage.focus = _loc_2;
            setTimeout(_loc_2.setSelection, 250, 0, _loc_2.text.length());
            return;
        }//end

        private void  closeDialog (AWEvent event )
        {
            this.close();
            return;
        }//end

        private void  acceptDialog (AWEvent event )
        {
            _loc_2 = (SubLicensedPropertyMechanic)MechanicManager.getInstance().getMechanicInstance(this.m_spawner,"universityLogo",MechanicManager.ALL)
            _loc_2.setProperty("logo_CVU");
            processSelection(new GenericPopupEvent("selected", GenericDialogView.YES, true));
            this.close();
            _loc_3 = this.m_universityInputTextField.getTextField ();
            if (_loc_3 == null)
            {
                return;
            }
            _loc_4 =(Function) this.m_data.get( "submitNameCallback");
            if (this.m_data.get("submitNameCallback") as Function != null)
            {
                _loc_4(_loc_3.text);
            }
            return;
        }//end

         protected void  onHideComplete ()
        {
            super.onHideComplete();
            MechanicManager.getInstance().handleAction(this.m_spawner, MechanicManager.PLAY);
            return;
        }//end

    }



