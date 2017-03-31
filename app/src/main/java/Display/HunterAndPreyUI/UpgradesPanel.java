package Display.HunterAndPreyUI;

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

import Classes.sim.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class UpgradesPanel extends JPanel
    {
        private Array m_upgrades ;
        private JPanel m_bodyPanel ;
        private JPanel m_infoPanel ;
        private UpgradesScrollingList m_upgradesShelf ;
        public static  String PREPARE ="prepare";
        public static  int UPS_PER_PAGE =4;

        public  UpgradesPanel ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.init();
            return;
        }//end

        protected void  init ()
        {
            this.m_upgrades = UpgradeDefinition.getUpgradeChain(Global.gameSettings().getHubName(HunterDialog.groupId, 1));
            while (this.m_upgrades.length % UPS_PER_PAGE != 0)
            {

                this.m_upgrades.push(new UpgradeDefinition(null, null));
            }
            this.makeBackground();
            this.m_bodyPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_infoPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.setPreferredWidth(671);
            this.setPreferredHeight(390);
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.makeBodyPanel());
            ASwingHelper.prepare(this);
            dispatchEvent(new Event(UpgradesPanel.PREPARE, true));
            return;
        }//end

        protected void  makeBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "inner_undertab");
            ASwingHelper.setBackground(this, _loc_1);
            return;
        }//end

        protected JPanel  makeBodyPanel ()
        {
            this.m_upgradesShelf = new UpgradesScrollingList(this.m_upgrades, UpgradesCellFactory, 0, UPS_PER_PAGE, 1);
            this.m_upgradesShelf.create();
            ASwingHelper.prepare(this.m_upgradesShelf);
            this.m_bodyPanel.append(this.m_upgradesShelf);
            return this.m_bodyPanel;
        }//end

    }



