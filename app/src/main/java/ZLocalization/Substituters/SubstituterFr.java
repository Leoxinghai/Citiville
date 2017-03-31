package ZLocalization.Substituters;

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

    public class SubstituterFr extends SubstituterContractions
    {
public static  String VOWEL ="vowel";
public static  String CONSONANT ="consonant";
public static Object vowels ={a 1,à:1, á:1, â:1, ä:1, e:1, è:1, é:1, ê:1, ë:1, i:1, ì:1, í:1, î:1, ï:1, o:1, ò:1, ó:1, ô:1, ö:1, u:1, ù:1, ú:1, û:1, ü:1};
public static Object contractionMap ={le de {"du",à:"au"}, les:{de:"des", à:"aux"}, un:{de:"d\'un"}, une:{de:"d\'une"}, des:{de:{vowel:"d\'", consonant:"de"}}};

        public  SubstituterFr ()
        {
            return;
        }//end

         protected Object  getContractionMap ()
        {
            return SubstituterFr.contractionMap;
        }//end

         protected String  getContraction (Object param1 ,String param2 )
        {
            if (typeof(param1) == "string")
            {
                return String(param1);
            }
            _loc_3 = param2.match(newRegExp("\\S"));
            _loc_4 = _loc_3.get(0);
            if (SubstituterFr.vowels.hasOwnProperty(_loc_4))
            {
                return param1.get(SubstituterFr.VOWEL);
            }
            return param1.get(SubstituterFr.CONSONANT);
        }//end

    }



