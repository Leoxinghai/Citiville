package Classes;

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

import Classes.orders.Hotel.*;
import Events.*;
import Modules.hotels.*;

    public interface ICheckInHandler
    {

//        public  ICheckInHandler ();

        boolean  isClosed ();

        boolean  isNPCFull ();

        boolean  isFriendFull ();

        boolean  isVIPRequested ();

        boolean  isVIP ();

        boolean  isCheckedIn ();

        boolean  isOpenForNPC ();

        boolean  isOpenForFriend ();

        String  closedFlyoutText ();

        String  fullFlyoutText ();

        Array  hotelVisitorOrders ();

        HotelOrder  getVisitorHotelOrder (String param1 ,String param2 );

        boolean  hasVisitorCheckedIntoDefaultFloor (String param1 ,String param2 );

        boolean  hasVisitorCheckedIntoHotel (String param1 ,String param2 );

        int  visitorCheckedIntoHotelFloor (String param1 ,String param2 );

        boolean  giveGuestRewards (int param1 );

        int  getGuestGiftIndex (String param1 ,String param2 );

        void  setGuestVIPStatus (String param1 ,String param2 ,int param3 );

        int  getGuestVIPStatus (String param1 ,String param2 );

        boolean  isRoomOnFloor (int param1 );

        int  getHotelVisitorCountForFloor (int param1 );

        Array  visitorOrdersForHotel ();

        int  defaultCheckInFloor ();

        int  getPeepMax ();

        int  getPeepCount ();

        void  setPeepCount (int param1 );

        void  updatePeepCount (int param1 );

        Array  getAllRewardDooberData ();

        void  onMechanicDataChanged (GenericObjectEvent event );

        Array  getAllRewardDooberDataForFloor (int param1 );

        HotelDooberData  getRewardDooberDataByFloorAndByIndex (int param1 ,int param2 );

        void  updateSparkleEffect ();

        String  getDefaultUGCCheckinMessage (int param1 );

        String  getFloorCaption (int param1 );

    }


