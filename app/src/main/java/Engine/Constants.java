package Engine;

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

    public class Constants
    {
        public static final   int INDEX_NONE =-1;
        public static final   int DRAG_CLICK_EPSILON =4;
        public static final   double POSITION_OFFSET_EPSILON =0.001;
        public static final   String ASSET_NONE ="none";
        public static final   String ASSET_TILE ="tile";
        public static final   int ROTATION_0 =0;
        public static final   int ROTATION_90 =1;
        public static final   int ROTATION_180 =2;
        public static final   int ROTATION_270 =3;
        public static final   int ROTATION_MAX =4;
        public static final   int DIRECTION_SW =0;
        public static final   int DIRECTION_SE =1;
        public static final   int DIRECTION_NE =2;
        public static final   int DIRECTION_NW =3;
        public static final   int DIRECTION_MAX =4;
        public static final   int OBJECT_NONE =0;
        public static final   int WORLDOBJECT_NONE =0;
        public static final   int WORLDOBJECT_UNKNOWN =1;
        public static final   int WORLDOBJECT_ITEM =2;
        public static final   int WORLDOBJECT_AVATAR =4;
        public static final   int WORLDOBJECT_MESSAGE_SIGN =8;
        public static final   int WORLDOBJECT_ALL_EXCEPT_AVATAR =~WORLDOBJECT_AVATAR;
        public static final   int WORLDOBJECT_ALL =16777215;
        public static final   int OBJECTFLAG_NONE =0;
        public static final   int OBJECTFLAG_INTERACTIVE =1;
        public static final   int OBJECTFLAG_COLLIDABLE =2;
        public static final   int COLLISION_NONE =0;
        public static final   int COLLISION_AVATAR =1;
        public static final   int COLLISION_ALL_EXCEPT_AVATAR =2;
        public static final   int COLLISION_OVERLAP =4;
        public static final   int COLLISION_SAME_ONLY =8;
        public static final   int COLLISION_PATHS =16;
        public static final   int COLLISION_ALL =32;
        public static final   int COLOR_HIGHLIGHT =16755200;
        public static final   int COLOR_HIGHLIGHT_BLUE =65535;
        public static final   int COLOR_LOT_OUTLINE =16776960;
        public static final   double ALPHA_LOT_OUTLINE =0.8;
        public static final   int COLOR_LOT_OUTLINE_BORDER =0;
        public static final   double ALPHA_LOT_OUTLINE_BORDER =0.8;
        public static final   int COLOR_VALID_PLACEMENT =4521796;
        public static final   int COLOR_OVERLAP =16777028;
        public static final   int COLOR_NOT_VALID_PLACEMENT =16729156;
        public static final   double ALPHA_VALID_PLACEMENT =1;
        public static final   double ALPHA_NOT_VALID_PLACEMENT =0.5;
        public static final  int TILE_WIDTH =8;
        public static final  int TILE_HEIGHT =4;
        public static final  double TILE_SCALE =1;
        public static final   int D1DROP_STATS_MAX_TIMESTAMP =90;
        public static final   double NO_NETWORK =1;
        public static final   double FACEBOOK_NETWORK =1;
        public static final   double ZLIVE_NETWORK =18;

        public  Constants ()
        {
            return;
        }//end

    }



