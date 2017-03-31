package Classes.util;

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

import Display.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Managers.*;

    public class GameFacebookUtil extends FacebookUtil
    {
        private static  int NUM_QUEUED_ACTIONS_REDIRET =5;

        public  GameFacebookUtil (String param1 ,String param2 )
        {
            super(param1, param2);
            return;
        }//end

         public void  redirect (String param1 ,Object param2 ,String param3 ="_parent",boolean param4 =true )
        {
            if (param4)
            {
                param1 = Config.SN_APP_URL + param1;
            }
            Utilities.launchURL(param1, param3, param2);
            return;
        }//end

         public Array  getDefaultFeedImages ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            Array _loc_1 =new Array();
            switch(_loc_2)
            {
                case 1:
                case 8:
                {
                    break;
                }
                case 2:
                {
                    break;
                }
                case 3:
                {
                    break;
                }
                case 4:
                {
                    break;
                }
                case 5:
                {
                    break;
                }
                case 6:
                {
                    break;
                }
                case 7:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_3 <= 1)
            {
            }
            else
            {
                switch(_loc_4)
                {
                    case 0:
                    {
                        break;
                    }
                    case 1:
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

         public void  onFeedClosed ()
        {
            super.onFeedClosed();
            UI.thawScreen();
            return;
        }//end

         public void  onPermissionDialogClosed (boolean param1 )
        {
            super.onPermissionDialogClosed(param1);
            if (param1 !=null)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "Permissions_stream_accept"));
                StatsManager.count("streamPermissions", "accept");
            }
            else
            {
                StatsManager.count("streamPermissions", "cancel");
            }
            Global.player.checkSeenExtendedPermissions(Global.player.SEEN_EXTENDED_PERMISSIONS_ANY);
            return;
        }//end

         public void  streamPublish (Object param1 ,Object param2 ,String param3 ,String param4 =null ,Function param5 =null ,boolean param6 =false ,String param7 ="")
        {
            boolean _loc_8 =false ;
            super.streamPublish(param1, param2, param3, param4, param5);
            if (this.userHasStreamPermissions())
            {
                _loc_8 = Global.player.checkSeenExtendedPermissions(Global.player.SEEN_EXTENDED_PERMISSIONS_ANY, false);
                if (!_loc_8 && !Global.player.getSeenFlag("autoPubDlg"))
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "Permissions_stream_accept_no_prompt"));
                    Global.player.setSeenFlag("autoPubDlg");
                }
            }
            return;
        }//end

         public void  publishFeedStory (String param1 ,Object param2 ,Array param3 ,boolean param4 ,Function param5 =null ,int param6 =0,boolean param7 =false ,boolean param8 =false ,String param9 ="")
        {
            if (!Global.autoPublishEnabled)
            {
                param7 = false;
            }
            super.publishFeedStory(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

         public boolean  userHasStreamPermissions ()
        {
            _loc_1 = m_hasPublishPermissions;
            if (!Global.autoPublishEnabled)
            {
                _loc_1 = false;
            }
            return _loc_1;
        }//end

    }



