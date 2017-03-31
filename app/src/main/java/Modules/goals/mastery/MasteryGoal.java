package Modules.goals.mastery;

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
import Display.*;
import Modules.goals.*;
import Modules.goals.mastery.ui.*;
//import flash.utils.*;

    public class MasteryGoal extends Goal
    {
        protected Dictionary m_mastery ;
        public static  String PUBLISH_STREAM_FEATURE_NAME ="masteryGoal";

        public  MasteryGoal (Object param1)
        {
            this.m_mastery = new Dictionary();
            super(param1);
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            if (param1 !=null)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_2 = param1.get(i0);

                    this.m_mastery.put(_loc_2,  new MasteryState(param1.get(_loc_2).count, param1.get(_loc_2).level));
                }
            }
            return;
        }//end

        public int  getLevel (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = this.m_mastery.get(param1) ;
            if (_loc_3)
            {
                _loc_2 = _loc_3.level;
            }
            return _loc_2;
        }//end

        public int  getCount (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = this.m_mastery.get(param1) ;
            if (_loc_3)
            {
                _loc_2 = _loc_3.count;
            }
            return _loc_2;
        }//end

        public double  getBonusMultiplier (String param1 )
        {
            double _loc_2 =0;
            _loc_3 = this.getLevelDefinition(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.bonusMultiplier;
            }
            return _loc_2;
        }//end

        public double  getNextBonusMultiplier (String param1 )
        {
            double _loc_2 =0;
            _loc_3 = this.getNextLevelDefinition(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.bonusMultiplier;
            }
            return _loc_2;
        }//end

        public MasteryDefinition  getLevelDefinition (String param1 )
        {
            MasteryDefinition _loc_2 =null ;
            _loc_3 = this.getLevel(param1 );
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1) && _loc_4.masteryLevels.length > _loc_3)
            {
                _loc_2 = _loc_4.masteryLevels.get(_loc_3);
            }
            return _loc_2;
        }//end

        public MasteryDefinition  getNextLevelDefinition (String param1 )
        {
            int _loc_3 =0;
            Item _loc_4 =null ;
            MasteryDefinition _loc_2 =null ;
            if (!this.isMaxLevel(param1))
            {
                _loc_3 = this.getLevel(param1) + 1;
                _loc_4 = Global.gameSettings().getItemByName(param1);
                if (_loc_4 && _loc_4.masteryLevels.length > _loc_3)
                {
                    _loc_2 = _loc_4.masteryLevels.get(_loc_3);
                }
            }
            return _loc_2;
        }//end

        public boolean  isMaxLevel (String param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = this.m_mastery.get(param1) ;
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (!Global.gameSettings().getItemByName(param1) || _loc_4.masteryLevels.length == 0)
            {
                _loc_2 = true;
            }
            if (_loc_3)
            {
                _loc_2 = (_loc_3.level + 1) > this.getMaxLevel(param1);
            }
            return _loc_2;
        }//end

        public int  getMaxLevel (String param1 )
        {
            return Math.max((this.getNumMasteryLevels(param1) - 1), 0);
        }//end

        public int  getNumMasteryLevels (String param1 )
        {
            int _loc_2 =0;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.masteryLevels.length;
            }
            return _loc_2;
        }//end

        public String  getFeedImage (String param1 )
        {
            String _loc_2 ="";
            _loc_3 = this.getLevelDefinition(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.feedImage;
            }
            return _loc_2;
        }//end

        public boolean  isUsingMastery (String param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                _loc_2 = Global.player.isEligibleForMastery(_loc_3) && this.getNumMasteryLevels(param1) > 0;
            }
            return _loc_2;
        }//end

        public int  getRequiredForNextLevel (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = this.m_mastery.get(param1) ;
            _loc_4 = _loc_3? (_loc_3.level) : (0);
            if (!this.isMaxLevel(param1))
            {
                _loc_2 = this.getRequiredForLevel(param1, (_loc_4 + 1));
            }
            return _loc_2;
        }//end

        public int  getCurrentCount (String param1 )
        {
            _loc_2 = this.m_mastery.get(param1) ;
            _loc_3 = _loc_2? (_loc_2.count) : (0);
            return _loc_3;
        }//end

        public int  getRequiredForLevel (String param1 ,int param2 )
        {
            _loc_3 = this.m_mastery.get(param1) ;
            _loc_4 = _loc_3? (_loc_3.count) : (0);
            return this.getTotalRequiredForLevel(param1, param2) - _loc_4;
        }//end

        public int  getTotalRequiredForNextLevel (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = this.m_mastery.get(param1) ;
            _loc_4 = _loc_3? (_loc_3.level) : (0);
            if (!this.isMaxLevel(param1))
            {
                _loc_2 = this.getTotalRequiredForLevel(param1, (_loc_4 + 1));
            }
            return _loc_2;
        }//end

        public int  getTotalRequiredForLevel (String param1 ,int param2 )
        {
            MasteryDefinition _loc_5 =null ;
            int _loc_3 =0;
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1))
            {
                _loc_5 = _loc_4.masteryLevels.length > param2 ? (_loc_4.masteryLevels.get(param2)) : (null);
                if (_loc_5)
                {
                    _loc_3 = _loc_5.requiredCount;
                }
            }
            return _loc_3;
        }//end

        public int  getProgressTowardNextLevel (String param1 )
        {
            int _loc_2 =0;
            _loc_3 = this.getLevelDefinition(param1 );
            if (_loc_3)
            {
                _loc_2 = this.getCount(param1) - _loc_3.requiredCount;
            }
            return _loc_2;
        }//end

        public int  getNextLevelDiff (String param1 )
        {
            MasteryDefinition _loc_4 =null ;
            MasteryDefinition _loc_5 =null ;
            _loc_2 = this.getLevel(param1 );
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3 && !this.isMaxLevel(param1))
            {
                _loc_4 = _loc_3.masteryLevels.length > _loc_2 ? (_loc_3.masteryLevels.get(_loc_2)) : (null);
                _loc_5 = _loc_3.masteryLevels.length > (_loc_2 + 1) ? (_loc_3.masteryLevels.get((_loc_2 + 1))) : (null);
                if (_loc_4 && _loc_5)
                {
                    return _loc_5.requiredCount - _loc_4.requiredCount;
                }
            }
            return 0;
        }//end

        public void  updateMastery (String param1 ,int param2 ,boolean param3 =true )
        {
            if (this.m_mastery.get(param1))
            {
                this.m_mastery.get(param1).count = this.m_mastery.get(param1).count + param2;
            }
            else
            {
                this.m_mastery.put(param1,  new MasteryState(param2, 0));
            }
            _loc_4 = this.getLevel(param1 );
            this.recomputeLevel(param1);
            if (this.getLevel(param1) > _loc_4)
            {
                this.onLevelUp(param1, param3);
            }
            return;
        }//end

        protected void  recomputeLevel (String param1 )
        {
            MasteryDefinition _loc_5 =null ;
            int _loc_2 =0;
            _loc_3 = this.m_mastery.get(param1) ;
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                if (_loc_4 && _loc_4.masteryLevels)
                {
                    for(int i0 = 0; i0 < _loc_4.masteryLevels.size(); i0++)
                    {
                    		_loc_5 = _loc_4.masteryLevels.get(i0);

                        if (_loc_3.count >= _loc_5.requiredCount)
                        {
                            _loc_2 = _loc_5.level;
                        }
                    }
                }
            }
            _loc_3.level = _loc_2;
            return;
        }//end

        protected void  onLevelUp (String param1 ,boolean param2 )
        {
            this.giveRewards(param1);
            if (param2)
            {
                UI.displayPopup(new MasteryLevelUpDialog(param1));
            }
            return;
        }//end

        protected void  giveRewards (String param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            _loc_3 = this.getLevelDefinition(param1 );
            if (_loc_2 && _loc_3 && _loc_3.rewards)
            {
                Global.player.giveRewards(_loc_3.rewards);
            }
            return;
        }//end

    }
class MasteryState
    public int level ;
    public int count ;

    void  MasteryState (int param1 ,int param2 )
    {
        this.level = param2;
        this.count = param1;
        return;
    }//end





