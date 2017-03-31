package GameMode;

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
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import com.adobe.images.*;
import com.facebook.*;
import com.facebook.commands.photos.*;
import com.facebook.commands.users.*;
import com.facebook.data.*;
import com.facebook.data.auth.*;
import com.facebook.data.photos.*;
import com.facebook.events.*;
import com.facebook.net.*;
//import flash.display.*;
//import flash.events.*;
//import flash.external.*;
//import flash.geom.*;
//import flash.utils.*;

    public class GMSnapShot extends GMDefault
    {
        private Sprite m_image ;
        private Sprite m_snapShotObject ;
        private Vector2 m_snapshotPos ;
        private Vector2 m_snapshotSize ;
        private boolean m_hasPermissions =false ;
        private ByteArray m_snapshot ;
        private AlbumData m_album ;
        private Facebook m_fbook ;
        private String m_aid ;
        private MovieClip m_photoDialog ;
        private Loader m_cancelButton ;
        private String m_message ;
        private static  int OUTLINE_WIDTH =3;
        private static  int OUTLINE_BORDER_WIDTH =2;
        private static boolean m_soundLoaded =false ;

        public  GMSnapShot ()
        {
            this.m_image = new Sprite();
            this.m_snapshotPos = new Vector2();
            this.m_snapshotSize = new Vector2();
            this.m_fbook = (GlobalEngine.socialNetwork as FacebookUtil).facebook;
            if (!m_soundLoaded)
            {
                m_soundLoaded = true;
                SoundManager.addSound("camera", Global.getAssetURL("assets/sounds/actions/sfx_camera.mp3"), false);
            }
            _loc_1 =Global.getVisiting ();
            setVisit(_loc_1);
            Global.ui.visible = false;
            this.getAlbums();
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            _loc_1 = GlobalEngine(.socialNetwork as FacebookUtil ).facebook.post(new HasAppPermission(ExtendedPermissionValues.PHOTO_UPLOAD ,(GlobalEngine.socialNetwork as FacebookUtil ).facebook.uid ));
            _loc_1.addEventListener(FacebookEvent.COMPLETE, this.onExtendedPermissionsCheck);
            StatsManager.count("take_photo", "start");
            return;
        }//end

         public void  disableMode ()
        {
            if (this.m_image.parent != null)
            {
                Global.stage.removeChild(this.m_image);
                this.m_image = null;
            }
            Global.ui.visible = true;
            super.disableMode();
            return;
        }//end

         protected void  updateOverlay ()
        {
            if (this.m_snapShotObject != null)
            {
                this.m_snapShotObject.graphics.clear();
                this.m_snapShotObject.graphics.lineStyle(2 * 2, 16711680);
                this.m_snapShotObject.graphics.moveTo(this.m_snapshotPos.x, this.m_snapshotPos.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x + this.m_snapshotSize.x, this.m_snapshotPos.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x + this.m_snapshotSize.x, this.m_snapshotPos.y + this.m_snapshotSize.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x, this.m_snapshotPos.y + this.m_snapshotSize.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x, this.m_snapshotPos.y);
                this.m_snapShotObject.graphics.lineStyle(2, 16711680);
                this.m_snapShotObject.graphics.moveTo(this.m_snapshotPos.x, this.m_snapshotPos.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x + this.m_snapshotSize.x, this.m_snapshotPos.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x + this.m_snapshotSize.x, this.m_snapshotPos.y + this.m_snapshotSize.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x, this.m_snapshotPos.y + this.m_snapshotSize.y);
                this.m_snapShotObject.graphics.lineTo(this.m_snapshotPos.x, this.m_snapshotPos.y);
            }
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            _loc_2 = getMouseStagePos();
            Vector2 _loc_3 =new Vector2(_loc_2.x -this.m_snapshotSize.x /2,_loc_2.y -this.m_snapshotSize.y /2);
            _loc_3.x = Utilities.clamp(_loc_3.x, 0, Global.ui.screenWidth - this.m_snapshotSize.x);
            _loc_3.y = Utilities.clamp(_loc_3.y, 0, Global.ui.screenHeight - this.m_snapshotSize.y);
            this.m_snapshotPos = _loc_3;
            this.updateOverlay();
            super.onMouseMove(event);
            return true;
        }//end

         protected int  getSelectableTypes ()
        {
            return 0;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            this.m_image.visible = false;
            if (this.m_cancelButton != null && this.m_cancelButton.parent != null)
            {
                Global.stage.removeChild(this.m_cancelButton);
            }
            BitmapData _loc_2 =new BitmapData(this.m_snapshotSize.x ,this.m_snapshotSize.y ,true ,0);
            Matrix _loc_3 =new Matrix ();
            _loc_3.translate(-this.m_snapshotPos.x, -this.m_snapshotPos.y);
            _loc_2.draw(Global.stage, _loc_3, null, null, null, true);
            JPGEncoder _loc_4 =new JPGEncoder(85);
            ByteArray _loc_5 =_loc_4.encode(_loc_2 );
            this.m_snapshot = _loc_5;
            this.m_photoDialog = new TakePhotoPopup(_loc_2);
            this.m_photoDialog.addEventListener(TakePhotoEvent.ACCEPT, this.onDialogAccept);
            this.m_photoDialog.addEventListener(TakePhotoEvent.CANCEL, this.onDialogCancel);
            UI.displayPopup(this.m_photoDialog);
            return;
        }//end

        private void  onDialogAccept (TakePhotoEvent event )
        {
            this.m_message = event.data;
            this.uploadPhoto();
            if (!this.m_hasPermissions)
            {
            }
            else
            {
                this.onPermissionDialogClosed();
            }
            return;
        }//end

        private void  onDialogCancel (TakePhotoEvent event )
        {
            if (Global.isVisiting())
            {
                Global.world.addGameMode(new GMVisit(Global.getVisiting()), true);
            }
            else
            {
                Global.world.addGameMode(new GMDefault(), true);
            }
            Global.ui.visible = true;
            return;
        }//end

        private void  onPermissionDialogClosed ()
        {
            return;
        }//end

        private void  uploadPhoto ()
        {
            String _loc_1 =null ;
            if (Global.isVisiting())
            {
                _loc_1 = "?ref=takePhoto&visitId=" + Global.getVisiting();
            }
            else
            {
                _loc_1 = "?ref=takePhoto";
            }
            UploadPhoto _loc_2 =new UploadPhoto(this.m_snapshot ,this.m_aid ,this.m_message );
            _loc_2.addEventListener(FacebookEvent.COMPLETE, this.onPhotoUploadComplete, false, 0, true);
            this.m_fbook.post(_loc_2);
            if (Global.isVisiting())
            {
                Global.world.addGameMode(new GMVisit(Global.getVisiting()), true);
            }
            else
            {
                Global.world.addGameMode(new GMDefault(), true);
            }
            Global.ui.visible = true;
            StatsManager.count("take_photo", "upload");
            StatsManager.sendStats(true);
            return;
        }//end

        private void  onPhotoUploadComplete (FacebookEvent event )
        {
            if (event.success)
            {
            }
            StatsManager.count("take_photo", "upload_complete");
            StatsManager.sendStats(true);
            return;
        }//end

        private void  getAlbums ()
        {
            _loc_1 = this.m_fbook.post(new GetAlbums(this.m_fbook.uid ));
            _loc_1.addEventListener(FacebookEvent.COMPLETE, this.onGetAlbums);
            return;
        }//end

        private void  onGetAlbums (FacebookEvent event )
        {
            AlbumData _loc_4 =null ;
            FacebookCall _loc_6 =null ;
            boolean _loc_2 =false ;
            _loc_3 =(GetAlbumsData) event.data;
            int _loc_5 =0;
            while (_loc_5 < _loc_3.albumCollection.length())
            {

                _loc_4 =(AlbumData) _loc_3.albumCollection.getItemAt(_loc_5);
                if (_loc_4.name == "FarmVille Photos")
                {
                    this.m_aid = _loc_4.aid;
                    _loc_2 = true;
                }
                _loc_5++;
            }
            if (!_loc_2)
            {
                _loc_6 = this.m_fbook.post(new CreateAlbum("FarmVille Photos"));
                _loc_6.addEventListener(FacebookEvent.COMPLETE, this.onCreateAlbum);
            }
            return;
        }//end

        private void  onCreateAlbum (FacebookEvent event )
        {
            this.getAlbums();
            return;
        }//end

        private void  handleGetPhotosResponse (FacebookEvent event )
        {
            String _loc_3 =null ;
            Array _loc_4 =null ;
            _loc_2 =(GetPhotosData) event.data;
            if (_loc_2.photoCollection.length > 0)
            {
                _loc_3 = _loc_2.photoCollection.getItemAt((_loc_2.photoCollection.length - 1)).get("src");
                _loc_4 = new Array();
                _loc_4.push({src:_loc_3, href:"{SN_APP_URL}{creative:10}index.php"});
                GlobalEngine.socialNetwork.publishFeedStory("takePhoto", {images:_loc_4}, [], false);
            }
            return;
        }//end

        private void  onExtendedPermissionsCheck (FacebookEvent event )
        {
            if (event.success && (event.data as BooleanResultData).value)
            {
                this.m_hasPermissions = true;
                this.createPhotoOutline();
            }
            else
            {
                this.m_hasPermissions = false;
                UI.displayMessage(ZLoc.t("Main", "Snapshot_dialog"), 1, this.onNewMessage);
            }
            return;
        }//end

        private void  createPhotoOutline ()
        {
            this.m_snapShotObject = new Sprite();
            this.m_image = this.m_snapShotObject;
            this.updateOverlay();
            Global.stage.addChild(this.m_image);
            this.m_snapshotSize.x = 400;
            this.m_snapshotSize.y = 400;
            if (Global.stage.displayState == StageDisplayState.FULL_SCREEN)
            {
                Global.stage.displayState = StageDisplayState.NORMAL;
            }
            this.m_cancelButton = LoadingManager.load(Global.getAssetURL("assets/dialogs/Cancel_Bt.swf"), this.onCancelButtonAdded, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

        private void  onPhotoCancel (Event event )
        {
            Global.stage.removeChild(this.m_cancelButton);
            this.m_cancelButton.removeEventListener(MouseEvent.CLICK, this.onPhotoCancel);
            Global.world.removeGameMode(this);
            this.onDialogCancel(null);
            return;
        }//end

        private void  onCancelButtonAdded (Event event )
        {
            Point _loc_2 =null ;
            Global.stage.addChild(this.m_cancelButton);
            _loc_2 = Global.ui.getBottomRight();
            this.m_cancelButton.x = _loc_2.x - this.m_cancelButton.width - 10;
            this.m_cancelButton.y = _loc_2.y - this.m_cancelButton.height - 10;
            this.m_cancelButton.addEventListener(MouseEvent.CLICK, this.onPhotoCancel);
            return;
        }//end

        private void  onNewMessage (GenericPopupEvent event )
        {
            if (!event.button)
            {
                Global.world.removeGameMode(this);
                this.onDialogCancel(null);
            }
            else
            {
                this.createPhotoOutline();
                ExternalInterface.addCallback("onPermissionDialogClosed", this.onPermissionDialogClosed);
                GlobalEngine.socialNetwork.showStreamPermissions();
                StatsManager.count("take_photo", "prompt_permission");
            }
            return;
        }//end

    }



