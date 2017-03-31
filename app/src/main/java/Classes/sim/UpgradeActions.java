package Classes.sim;

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
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import com.adobe.utils.*;

    public class UpgradeActions
    {
        protected MapResource m_resource ;
        protected Array m_cachedFriendHelpers =null ;
        protected int m_cachedFriendHelperActions =0;
        public static  String MECHANIC_NAME ="upgradeHelpers";

        public  UpgradeActions (MapResource param1 )
        {
            this.m_resource = param1;
            return;
        }//end

        protected void  lazyInitializeFriendHelpers ()
        {
            if (this.m_cachedFriendHelpers != null)
            {
                return;
            }
            this.m_resource.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            this.refreshHelperData();
            return;
        }//end

        private void  refreshHelperData ()
        {
            String _loc_6 =null ;
            this.m_cachedFriendHelpers = new Array();
            _loc_1 =(IMechanicUser) this.m_resource;
            if (_loc_1 == null)
            {
                return;
            }
            _loc_2 = (IDictionaryDataMechanic)MechanicManager.getInstance().getMechanicInstance(_loc_1,MECHANIC_NAME,MechanicManager.ALL)
            if (_loc_2 == null)
            {
                return;
            }
            _loc_3 = this.getFriendHelperDefinition ();
            if (_loc_3 == null)
            {
                return;
            }
            _loc_4 = _loc_2.getKeys ();
            int _loc_5 =0;
            for(int i0 = 0; i0 < _loc_4.size(); i0++) 
            {
            	_loc_6 = _loc_4.get(i0);

                this.m_cachedFriendHelpers.push(GameUtil.formatServerUid(_loc_6));
                _loc_5++;
                if (_loc_5 >= _loc_3.max)
                {
                    break;
                }
            }
            this.m_cachedFriendHelperActions = this.m_cachedFriendHelpers.length * _loc_3.actionValue;
            return;
        }//end

        protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (event.obj == MECHANIC_NAME)
            {
                this.refreshHelperData();
            }
            return;
        }//end

        public int  getTotal ()
        {
            return this.getResourceActions() + this.getHelperActions();
        }//end

        public int  getResourceActions ()
        {
            return this.m_resource.upgradeActionCount;
        }//end

        public int  getHelperActions ()
        {
            this.lazyInitializeFriendHelpers();
            return this.m_cachedFriendHelperActions;
        }//end

        public Array  getFriendHelperIds ()
        {
            this.lazyInitializeFriendHelpers();
            return ArrayUtil.copyArray(this.m_cachedFriendHelpers);
        }//end

        public UpgradeHelperDefinition  getFriendHelperDefinition ()
        {
            UpgradeHelperDefinition _loc_2 =null ;
            _loc_1 = this.m_resource.getItem ();
            if (_loc_1.upgrade && _loc_1.upgrade.helpers)
            {
                for(int i0 = 0; i0 < _loc_1.upgrade.helpers.size(); i0++) 
                {
                	_loc_2 = _loc_1.upgrade.helpers.get(i0);

                    if (_loc_2.type == UpgradeHelperDefinition.TYPE_FRIEND)
                    {
                        return _loc_2;
                    }
                }
            }
            return null;
        }//end

    }



