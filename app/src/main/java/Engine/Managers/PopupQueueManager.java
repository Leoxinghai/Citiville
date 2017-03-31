package Engine.Managers;

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

import Engine.Init.*;
import Engine.Interfaces.*;

//import flash.events.*;
//import flash.utils.*;

    public class PopupQueueManager
    {
        private Vector<IDialog> m_popupQueue;
        private Vector<IDialog> m_popupStack;
        private static PopupQueueManager m_instance =null ;
        private static boolean m_canInstantiate =false ;

        public  PopupQueueManager ()
        {
            this.m_popupQueue = new Vector<IDialog>();
            this.m_popupStack = new Vector<IDialog>();
            if (!m_canInstantiate)
            {
                throw new Error("Cannot call PopupQueueManager constructor directly - use getInstance() instead.");
            }
            return;
        }//end

        public void  showDialog (IDialog param1 ,boolean param2 =true )
        {
            IDialog _loc_4 =null ;
            IDialog _loc_5 =null ;
            boolean _loc_3 =true ;
            if (param1.unique == true)
            {
                for(int i0 = 0; i0 < this.m_popupStack.size(); i0++)
                {
                	_loc_4 = this.m_popupStack.get(i0);

                    if (param1.dialogName == _loc_4.dialogName)
                    {
                        _loc_3 = false;
                        break;
                    }
                }
                for(int i0 = 0; i0 < this.m_popupQueue.size(); i0++)
                {
                	_loc_5 = this.m_popupQueue.get(i0);

                    if (param1.dialogName == _loc_5.dialogName)
                    {
                        _loc_3 = false;
                        break;
                    }
                }
            }
            if (_loc_3)
            {
                if (param2)
                {
                    this.m_popupQueue.push(param1);
                    this.pumpPopupQueue();
                }
                else if (FreezeManager.getInstance().isAnythingFrozen() == false)
                {
                    this.displayImmediately(param1);
                }
                else
                {
                    this.m_popupQueue.unshift(param1);
                }
            }
            return;
        }//end

        public void  closeShownDialogs ()
        {
            IDialog _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_popupStack.size(); i0++)
            {
            	_loc_1 = this.m_popupStack.get(i0);

                _loc_1.close();
            }
            this.m_popupStack = new Vector<IDialog>();
            return;
        }//end

        private void  displayImmediately (IDialog param1 )
        {
            IDialog _loc_2 =null ;
            if (this.m_popupStack.length > 0)
            {
                _loc_2 = this.m_popupStack.get((this.m_popupStack.length - 1));
                _loc_2.disable();
            }
            GlobalEngine.stage.addChild(param1.getDisplayObject());
            param1.getDisplayObject().addEventListener(Event.CLOSE, this.onPopupClosed, false, 0, true);
            this.m_popupStack.push(param1);
            param1.show();
            FreezeManager.getInstance().freezeRender();
            return;
        }//end

        private void  onPopupClosed (Event event )
        {
            IDialog _loc_2 =null ;
            event.currentTarget.removeEventListener(Event.CLOSE, this.onPopupClosed);
            this.m_popupStack.pop();
            if (this.m_popupStack.length > 0)
            {
                _loc_2 = this.m_popupStack.get((this.m_popupStack.length - 1));
                _loc_2.enable();
            }
            else
            {
                this.pumpPopupQueue();
            }
            FreezeManager.getInstance().thaw(true);
            return;
        }//end

        public void  pumpPopupQueue ()
        {
            IDialog _loc_1 =null ;
            if (this.m_popupQueue.length > 0 && this.m_popupStack.length == 0 && InitializationManager.getInstance().haveAllCompleted())
            {
                _loc_1 = this.m_popupQueue.shift();
                this.displayImmediately(_loc_1);
            }
            return;
        }//end

        public boolean  hasActiveDialog ()
        {
            return this.m_popupStack.length > 0;
        }//end

        public IDialog  getActiveDialog ()
        {
            IDialog _loc_1 =null ;
            if (this.hasActiveDialog())
            {
                _loc_1 = this.m_popupStack.get((this.m_popupStack.length - 1));
            }
            return _loc_1;
        }//end

        public String  getActiveDialogName ()
        {
            String _loc_1 ="";
            _loc_2 = this.getActiveDialog ();
            if (_loc_2 != null)
            {
                _loc_1 = getQualifiedClassName(_loc_2);
                if (_loc_1.length >= 39)
                {
                    _loc_1 = _loc_1.substr((_loc_1.lastIndexOf(".") + 1));
                    if (_loc_1.length >= 39)
                    {
                        _loc_1 = _loc_1.substr(_loc_1.indexOf("::") + 2);
                    }
                }
                _loc_1 = "popup:" + _loc_1;
            }
            else
            {
                _loc_1 = "unkownUI";
            }
            return _loc_1;
        }//end

        public static PopupQueueManager  getInstance ()
        {
            if (m_instance == null)
            {
                m_canInstantiate = true;
                m_instance = new PopupQueueManager;
                m_canInstantiate = false;
            }
            return m_instance;
        }//end

    }



