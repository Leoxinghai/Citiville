package Classes.orders.Valentines2011;

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

import Modules.achievements.data.*;
    public class ValentineCard
    {
        private int m_leftID ;
        private int m_middleID ;
        private int m_rightID ;
        private String m_msg ;
        private boolean m_seen ;
        private String m_dataString ;
        private String m_id ;
        private String m_sender ;
        private ValentineOrder m_order ;

        public  ValentineCard (String param1 ,boolean param2 ,String param3 ,ValentineOrder param4 ,String param5 )
        {
            this.m_dataString = param1;
            _loc_6 = param1.split(",");
            this.m_leftID = int(_loc_6.get(0));
            this.m_middleID = int(_loc_6.get(1));
            this.m_rightID = int(_loc_6.get(2));
            this.m_msg = String(_loc_6.get(3));
            int _loc_7 =4;
            while (_loc_7 < _loc_6.length())
            {

                this.m_msg = this.m_msg + ("," + String(_loc_6.get(_loc_7)));
                _loc_7++;
            }
            this.m_seen = param2;
            this.m_sender = param3;
            this.m_order = param4;
            this.m_id = param5;
            return;
        }//end

        public String  dataString ()
        {
            return this.m_dataString;
        }//end

        public int  leftPart ()
        {
            return this.m_leftID;
        }//end

        public int  middlePart ()
        {
            return this.m_middleID;
        }//end

        public int  rightPart ()
        {
            return this.m_rightID;
        }//end

        public String  message ()
        {
            return this.m_msg;
        }//end

        public boolean  isNew ()
        {
            return !this.m_seen;
        }//end

        public String  sender ()
        {
            return this.m_sender;
        }//end

        public void  setSeen ()
        {
            if (this.m_order)
            {
                this.m_seen = true;
            }
            return;
        }//end

        public String  id ()
        {
            return this.m_id;
        }//end

        public void  id (String param1 )
        {
            this.m_id = param1;
            return;
        }//end

        public Array  findMatchingAchievementIcons ()
        {
            Achievement ach ;
            boolean validState ;
            XML test ;
            String fn ;
            String params ;
            achievements = Global.achievementsManager.getAchievementGroup("vday_2011").achievements;
            Array results ;
            int _loc_2 =0;
            _loc_3 = achievements;
            for(int i0 = 0; i0 < achievements.size(); i0++)
            {
            		ach = achievements.get(i0);


                validState = ach.state == Achievement.FINISHED || ach.state == Achievement.REWARDED;
                if (!validState)
                {
                    continue;
                }
                test = ach.tests.get("finish");
                if (test != null)
                {
                    fn = String(test.attribute("function"));
                    if (fn == "vdayCheckCardMatch")
                    {
                        params = String(test.attribute("params"));
                        if (this.m_dataString.search(params) >= 0)
                        {
                            results.push(ach);
                        }
                    }
                }
            }
            return results .map (String  (Achievement param1 ,...args )
            {
                return param1.iconUrl;
            }//end
            );
        }//end

    }



