package Modules.franchise.data;

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

//import flash.utils.*;

    public class FranchiseDataModel
    {
        private String m_ownerUid ;
        private String m_ownerFirstName ;
        private FranchiseData m_franchiseData ;

        public  FranchiseDataModel (String param1 ,String param2 ,FranchiseData param3 )
        {
            this.m_ownerUid = param1;
            this.m_ownerFirstName = param2;
            this.m_franchiseData = param3;
            return;
        }//end

        public String  ownerUid ()
        {
            return this.m_ownerUid;
        }//end

        public String  getFranchiseName (String param1 )
        {
            _loc_2 = this.getOwnedFranchiseName(param1 );
            return _loc_2;
        }//end

        public String  getPendingName (String param1 )
        {
            _loc_2 = this.getOwnedFranchisePendingName(param1 );
            return _loc_2;
        }//end

        public void  setFranchiseName (String param1 ,String param2 )
        {
            _loc_3 = this.getOwnedFranchise(param2 );
            if (!_loc_3)
            {
                _loc_3 = new OwnedFranchiseData(param2, param1, null, new Dictionary(), 0);
                this.addOwnedFranchise(_loc_3);
            }
            _loc_3.franchiseName = param1;
            return;
        }//end

        public void  setPendingName (String param1 ,String param2 )
        {
            _loc_3 = this.getOwnedFranchise(param2 );
            if (_loc_3)
            {
                _loc_3.pendingName = param1;
            }
            return;
        }//end

        public String Vector  getFranchiseTypes ().<>
        {
            OwnedFranchiseData _loc_2 =null ;
            Vector<String> _loc_1 =new Vector<String>();
            for(int i0 = 0; i0 < this.m_franchiseData.ownedFranchises.size(); i0++)
            {
            		_loc_2 = this.m_franchiseData.ownedFranchises.get(i0);

                _loc_1.push(_loc_2.franchiseType);
            }
            return _loc_1;
        }//end

        public FranchiseExpansionData  getFranchise (String param1 ,String param2 )
        {
            _loc_3 = this.getFranchiseMapByType(param1 );
            return _loc_3.get(param2);
        }//end

        public double  getFranchiseCount ()
        {
            OwnedFranchiseData _loc_2 =null ;
            FranchiseExpansionData _loc_3 =null ;
            double _loc_1 =0;
            for(int i0 = 0; i0 < this.m_franchiseData.ownedFranchises.size(); i0++)
            {
            		_loc_2 = this.m_franchiseData.ownedFranchises.get(i0);

                for(int i0 = 0; i0 < _loc_2.locations.size(); i0++)
                {
                		_loc_3 = _loc_2.locations.get(i0);

                    _loc_1 = _loc_1 + 1;
                }
            }
            return _loc_1;
        }//end

        public double  getBusinessCount ()
        {
            OwnedFranchiseData _loc_2 =null ;
            double _loc_1 =0;
            for(int i0 = 0; i0 < this.m_franchiseData.ownedFranchises.size(); i0++)
            {
            		_loc_2 = this.m_franchiseData.ownedFranchises.get(i0);

                _loc_1 = _loc_1 + 1;
            }
            return _loc_1;
        }//end

        public double  getFranchiseCountByType (String param1 )
        {
            return this.getFranchisesByType(param1).length;
        }//end

        public double  getFranchiseCountByLocation (String param1 )
        {
            return this.getFranchisesByLocation(param1).length;
        }//end

        public FranchiseExpansionData Vector  getFranchisesByType (String param1 ).<>
        {
            FranchiseExpansionData _loc_4 =null ;
            Vector<FranchiseExpansionData> _loc_2 =new Vector<FranchiseExpansionData>();
            _loc_3 = this.getFranchiseMapByType(param1 );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.push(_loc_4);
            }
            return _loc_2;
        }//end

        public FranchiseExpansionData Vector  getFranchisesByLocation (String param1 ).<>
        {
            OwnedFranchiseData _loc_3 =null ;
            FranchiseExpansionData _loc_4 =null ;
            Vector<FranchiseExpansionData> _loc_2 =new Vector<FranchiseExpansionData>();
            for(int i0 = 0; i0 < this.m_franchiseData.ownedFranchises.size(); i0++)
            {
            		_loc_3 = this.m_franchiseData.ownedFranchises.get(i0);

                _loc_4 = _loc_3.locations.get(param1);
                if (_loc_4)
                {
                    _loc_2.push(_loc_4);
                }
            }
            return _loc_2;
        }//end

        public double  getFriendFranchiseCount ()
        {
            Dictionary _loc_2 =null ;
            FriendFranchiseData _loc_3 =null ;
            double _loc_1 =0;
            for(int i0 = 0; i0 < this.m_franchiseData.friendFranchises.size(); i0++)
            {
            		_loc_2 = this.m_franchiseData.friendFranchises.get(i0);

                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_1 = _loc_1 + 1;
                }
            }
            return _loc_1;
        }//end

        public double  getFriendFranchiseCountByUid (String param1 )
        {
            return this.getFriendFranchisesByUid(param1).length;
        }//end

        public FriendFranchiseData Vector  getFriendFranchisesByUid (String param1 ).<>
        {
            String _loc_3 =null ;
            Dictionary _loc_4 =null ;
            FriendFranchiseData _loc_5 =null ;
            Vector<FriendFranchiseData> _loc_2 =new Vector<FriendFranchiseData>();
            for(int i0 = 0; i0 < this.m_franchiseData.friendFranchises.size(); i0++)
            {
            		_loc_3 = this.m_franchiseData.friendFranchises.get(i0);

                if (_loc_3 == param1)
                {
                    _loc_4 = this.m_franchiseData.friendFranchises.get(param1);
                    for(int i0 = 0; i0 < _loc_4.size(); i0++)
                    {
                    		_loc_5 = _loc_4.get(i0);

                        _loc_2.push(_loc_5);
                    }
                    break;
                }
            }
            return _loc_2;
        }//end

        public void  addFranchise (FranchiseExpansionData param1 ,String param2 )
        {
            _loc_3 = this.getFranchiseMapByType(param1.franchiseType );
            if (_loc_3 && !_loc_3.get(param2))
            {
                _loc_3.put(param2,  param1);
            }
            return;
        }//end

        public FranchiseExpansionData  removeFranchise (String param1 ,String param2 )
        {
            FranchiseExpansionData _loc_3 =null ;
            _loc_4 = this.getFranchiseMapByType(param1 );
            if (this.getFranchiseMapByType(param1))
            {
                _loc_3 = _loc_4.get(param2);
                delete _loc_4.get(param2);
            }
            return _loc_3;
        }//end

        public String  getHeadquartersType (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.headquartersName;
            }
            return _loc_2;
        }//end

        public boolean  getOwnsFranchise (String param1 )
        {
            _loc_2 = this.getOwnedFranchise(param1 );
            if (_loc_2)
            {
                return true;
            }
            return false;
        }//end

        private String  getOwnedFranchiseName (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.getOwnedFranchise(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.franchiseName;
            }
            if (!_loc_2)
            {
                _loc_2 = this.getDefaultFranchiseName(param1, this.m_ownerUid);
            }
            return _loc_2;
        }//end

        private String  getOwnedFranchisePendingName (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.getOwnedFranchise(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.pendingName;
            }
            return _loc_2;
        }//end

        public OwnedFranchiseData  getOwnedFranchise (String param1 )
        {
            OwnedFranchiseData _loc_3 =null ;
            OwnedFranchiseData _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_franchiseData.ownedFranchises.size(); i0++)
            {
            		_loc_3 = this.m_franchiseData.ownedFranchises.get(i0);

                if (_loc_3.franchiseType == param1)
                {
                    _loc_2 = _loc_3;
                    break;
                }
            }
            return _loc_2;
        }//end

        public OwnedFranchiseData Vector  getOwnedFranchises ().<>
        {
            return this.m_franchiseData.ownedFranchises;
        }//end

        public String  getDefaultFranchiseName (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            if (param2 == Global.player.uid)
            {
                _loc_3 = Global.player.firstName;
            }
            else
            {
                _loc_3 = Global.player.getFriendFirstName(param2);
            }
            if (!_loc_3)
            {
                _loc_3 = ZLoc.t("Dialogs", "DefaultFriendName");
            }
            _loc_4 =Global.player.snUser.gender =="M"? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
            return ZLoc.t("Dialogs", "rename_business_inputField", {name:ZLoc.tn(_loc_3, _loc_4), item:ZLoc.t("Items", param1 + "_friendlyName")});
        }//end

        private Dictionary  getFranchiseMapByType (String param1 )
        {
            OwnedFranchiseData _loc_3 =null ;
            Dictionary _loc_2 =new Dictionary ();
            for(int i0 = 0; i0 < this.m_franchiseData.ownedFranchises.size(); i0++)
            {
            		_loc_3 = this.m_franchiseData.ownedFranchises.get(i0);

                if (_loc_3.franchiseType == param1)
                {
                    _loc_2 = _loc_3.locations;
                    break;
                }
            }
            return _loc_2;
        }//end

        public void  addOwnedFranchise (OwnedFranchiseData param1 )
        {
            OwnedFranchiseData _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = this.getOwnedFranchise(param1.franchiseType);
                if (!_loc_2)
                {
                    this.m_franchiseData.ownedFranchises.push(param1);
                }
            }
            return;
        }//end

    }



