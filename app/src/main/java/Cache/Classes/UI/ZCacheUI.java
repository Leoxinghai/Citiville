package Cache.Classes.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.shapes.Shape;
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

import Cache.Interfaces.*;
import Cache.Util.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class ZCacheUI
    {
        private Stage m_stage ;
        private String m_sandboxUrl ;
        private boolean m_drawGradient ;
        private Function m_completeCallback ;
        private Point m_offsets ;
        private DisplayObject m_sandbox ;
        private Loader m_sandboxLoader ;
        private Shape m_sandboxGradient ;
        private boolean m_promptOnSandboxLoad =false ;
        private IZCache m_zCache ;

        public  ZCacheUI (IZCache param1 ,Stage param2 ,String param3 =null ,boolean param4 =true ,boolean param5 =false )
        {
            this.m_sandboxGradient = new Shape();
            if (!param1)
            {
                throw new Error("ZCachePromptUtil requires a non-null zcache!");
            }
            if (!param2)
            {
                throw new Error("ZCachePromptUtil requires a non-null stage!");
            }
            this.m_zCache = param1;
            this.m_stage = param2;
            this.m_sandboxUrl = param3;
            this.m_drawGradient = param4;
            this.m_offsets = new Point(0, 0);
            if (param5)
            {
                this.preloadSandbox();
            }
            return;
        }//end

        public void  offsets (Point param1 )
        {
            if (param1 !=null)
            {
                this.m_offsets = param1;
            }
            else
            {
                this.m_offsets = new Point(0, 0);
            }
            return;
        }//end

        public void  preloadSandbox ()
        {
            this.loadSandbox();
            return;
        }//end

        private void  loadSandbox ()
        {
            if (!this.m_sandboxLoader)
            {
                this.m_sandboxLoader = LoadingManager.load(this.m_sandboxUrl, this.onSandboxLoadSuccess, LoadingManager.PRIORITY_HIGH, this.onSandboxLoadFault);
            }
            return;
        }//end

        private void  onSandboxLoadSuccess (Event event )
        {
            this.m_sandbox = this.m_sandboxLoader.content;
            if (this.m_promptOnSandboxLoad)
            {
                this.promptForPermission();
            }
            return;
        }//end

        private void  onSandboxLoadFault (Event event )
        {
            if (this.m_promptOnSandboxLoad)
            {
                this.promptForPermission();
            }
            return;
        }//end

        public void  showPermissionsPrompt (Function param1)
        {
            this.m_completeCallback = param1;
            if (this.m_sandbox)
            {
                this.promptForPermission();
            }
            else
            {
                this.m_promptOnSandboxLoad = true;
                this.loadSandbox();
            }
            return;
        }//end

        private void  promptForPermission ()
        {
            this.initUI();
            StatsManager.count("zcache", "flash_permission", "shown");
            this.m_zCache.promptForStorage(Util.bind(this.onFlashPromptDismissed, this, [true]), Util.bind(this.onFlashPromptDismissed, this, [false]));
            return;
        }//end

        private void  onFlashPromptDismissed (boolean param1 )
        {
            StatsManager.count("zcache", "flash_permission", param1 ? ("allowed") : ("denied"));
            StatsManager.count("zcache", "status", param1 ? ("enabled") : ("disabled"));
            this.destroy();
            if (this.m_completeCallback !== null)
            {
                this.m_completeCallback(param1);
            }
            return;
        }//end

        private void  initUI ()
        {
            if (this.m_drawGradient)
            {
                this.drawSandboxGradient();
                this.m_stage.addChild(this.m_sandboxGradient);
            }
            if (this.m_sandbox)
            {
                this.m_stage.addChild(this.m_sandbox);
                this.m_sandbox.x = (this.m_stage.stageWidth - this.m_sandbox.width >> 1) + this.m_offsets.x;
                this.m_sandbox.y = (this.m_stage.stageHeight - this.m_sandbox.height >> 1) + this.m_offsets.y;
            }
            return;
        }//end

        public void  destroy ()
        {
            if (this.m_sandboxGradient)
            {
                if (this.m_sandboxGradient.parent)
                {
                    this.m_sandboxGradient.parent.removeChild(this.m_sandboxGradient);
                }
                this.m_sandboxGradient = null;
            }
            if (this.m_sandbox)
            {
                if (this.m_sandbox.parent)
                {
                    this.m_sandbox.parent.removeChild(this.m_sandbox);
                }
                this.m_sandbox = null;
            }
            if (this.m_sandboxLoader)
            {
                this.m_sandboxLoader.unload();
                this.m_sandboxLoader = null;
            }
            this.m_zCache = null;
            this.m_stage = null;
            return;
        }//end

        private void  drawSandboxGradient ()
        {
            _loc_1 = this.m_stage.stageWidth;
            _loc_2 = this.m_stage.stageHeight;
            Matrix _loc_3 =new Matrix ();
            _loc_3.createGradientBox(_loc_1 * 2, _loc_2 * 2, 0, (-_loc_1) / 2, (-_loc_2) / 2);
            _loc_4 = this.m_sandboxGradient.graphics;
            this.m_sandboxGradient.graphics.clear();
            _loc_4.beginGradientFill(GradientType.RADIAL, [16777215, 3092271], [0, 1], [0, 255], _loc_3);
            _loc_4.drawRect(0, 0, _loc_1, _loc_2);
            _loc_4.endFill();
            this.m_sandboxGradient.width = _loc_1;
            this.m_sandboxGradient.height = _loc_2;
            return;
        }//end

    }




