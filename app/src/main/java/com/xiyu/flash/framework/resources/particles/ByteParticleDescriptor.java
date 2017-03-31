package com.xiyu.flash.framework.resources.particles;

import com.xiyu.flash.framework.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
//import flash.utils.*;

    public class ByteParticleDescriptor implements ParticleDescriptor
    {
        private byte[] mBytes0 ;
        private DataInputStream mBytes ;

        public  ByteParticleDescriptor (byte[] param1 )
        {
            this.mBytes0 = param1;
            return;
        }//end

        public ParticleDefinition  createData (AppBase param1 )
        {

            ParticleEmitterDefinition _loc_5 ;
            int _loc_6 ;
            int _loc_7 ;
            int _loc_8 ;
            ParticleField _loc_9 ;
            ParticleField _loc_10 ;
            ParticleDefinition _loc_2 = new ParticleDefinition ();
            try {

            	this.mBytes = new DataInputStream(new ByteArrayInputStream(mBytes0));
	            //this.mBytes.reset();//.position = 0;
	            int _loc_3 = this.mBytes.readInt();
	            int _loc_4 =0;
	            System.out.println("ByteParticle.def.length." + _loc_3);
	            while (_loc_4 < _loc_3)
	            {

	                _loc_5 = new ParticleEmitterDefinition();
	                _loc_5.mName = this.mBytes.readUTF();
	                _loc_5.mImageID = this.mBytes.readUTF();
	                if (_loc_5.mImageID.length() > 0)
	                {
	                    _loc_5.mImage = param1.imageManager().getImageInst(_loc_5.mImageID);
	                }
	                _loc_5.mImageCol = this.mBytes.readInt();
	                _loc_5.mImageRow = this.mBytes.readInt();
	                _loc_5.mImageFrames = this.mBytes.readInt();
	                _loc_5.mAnimated = this.mBytes.readBoolean();
	                _loc_5.mParticleFlags.fromUInt(this.mBytes.readInt());
	                _loc_5.mEmitterType = ParticleEmitterType.fromUInt(this.mBytes.readInt());
	                this.readParam(this.mBytes, _loc_5.mSystemDuration);
	                this.readParam(this.mBytes, _loc_5.mCrossFadeDuration);
	                this.readParam(this.mBytes, _loc_5.mSpawnRate);
	                this.readParam(this.mBytes, _loc_5.mSpawnMinActive);
	                this.readParam(this.mBytes, _loc_5.mSpawnMaxActive);
	                this.readParam(this.mBytes, _loc_5.mSpawnMaxLaunched);
	                this.readParam(this.mBytes, _loc_5.mEmitterRadius);
	                this.readParam(this.mBytes, _loc_5.mEmitterOffsetX);
	                this.readParam(this.mBytes, _loc_5.mEmitterOffsetY);
	                this.readParam(this.mBytes, _loc_5.mEmitterBoxX);
	                this.readParam(this.mBytes, _loc_5.mEmitterBoxY);
	                this.readParam(this.mBytes, _loc_5.mEmitterSkewX);
	                this.readParam(this.mBytes, _loc_5.mEmitterSkewY);
	                this.readParam(this.mBytes, _loc_5.mEmitterPath);
	                this.readParam(this.mBytes, _loc_5.mParticleDuration);
	                this.readParam(this.mBytes, _loc_5.mLaunchSpeed);
	                this.readParam(this.mBytes, _loc_5.mLaunchAngle);
	                this.readParam(this.mBytes, _loc_5.mSystemRed);
	                this.readParam(this.mBytes, _loc_5.mSystemGreen);
	                this.readParam(this.mBytes, _loc_5.mSystemBlue);
	                this.readParam(this.mBytes, _loc_5.mSystemAlpha);
	                this.readParam(this.mBytes, _loc_5.mSystemBrightness);
	                this.readParam(this.mBytes, _loc_5.mParticleRed);
	                this.readParam(this.mBytes, _loc_5.mParticleGreen);
	                this.readParam(this.mBytes, _loc_5.mParticleBlue);
	                this.readParam(this.mBytes, _loc_5.mParticleAlpha);
	                this.readParam(this.mBytes, _loc_5.mParticleBrightness);
	                this.readParam(this.mBytes, _loc_5.mParticleSpinAngle);
	                this.readParam(this.mBytes, _loc_5.mParticleSpinSpeed);
	                this.readParam(this.mBytes, _loc_5.mParticleScale);
	                this.readParam(this.mBytes, _loc_5.mParticleStretch);
	                this.readParam(this.mBytes, _loc_5.mCollisionReflect);
	                this.readParam(this.mBytes, _loc_5.mCollisionSpin);
	                this.readParam(this.mBytes, _loc_5.mClipTop);
	                this.readParam(this.mBytes, _loc_5.mClipBottom);
	                this.readParam(this.mBytes, _loc_5.mClipLeft);
	                this.readParam(this.mBytes, _loc_5.mClipRight);
	                this.readParam(this.mBytes, _loc_5.mAnimationRate);
	                _loc_6 = 0;
	                _loc_7 = this.mBytes.readInt();
	                _loc_6 = 0;
	                while (_loc_6 < _loc_7)
	                {

	                    _loc_9 = new ParticleField();
	                    _loc_9.mFieldType = ParticleFieldType.fromUInt(this.mBytes.readInt());
	                    this.readParam(this.mBytes, _loc_9.mX);
	                    this.readParam(this.mBytes, _loc_9.mY);
	                    _loc_5.mParticleFields.add(_loc_6,_loc_9);
	                    _loc_6++;
	                }
	                _loc_8 = this.mBytes.readInt();
	                _loc_6 = 0;
	                while (_loc_6 < _loc_8)
	                {

	                    _loc_10 = new ParticleField();
	                    _loc_10.mFieldType = ParticleFieldType.fromUInt(this.mBytes.readInt());
	                    this.readParam(this.mBytes, _loc_10.mX);
	                    this.readParam(this.mBytes, _loc_10.mY);
	                    _loc_5.mSystemFields.add(_loc_6,_loc_10);
	                    _loc_6++;
	                }
	                _loc_2.mEmitterDefs.add(_loc_4,_loc_5);
	                _loc_4++;
	            }

            } catch(IOException iex) {
            	iex.printStackTrace();
            }

            return _loc_2;

	}//end

        private void  readParam (DataInputStream param1 ,FloatParameterTrack param2 ) throws IOException
        {

            FloatParameterTrackNode _loc_5 ;
            int _loc_3 = param1.readInt ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = new FloatParameterTrackNode();
                _loc_5.mCurveType = CurveType.fromUInt(param1.readInt());
                _loc_5.mDistribution = CurveType.fromUInt(param1.readInt());
                _loc_5.mHighValue = param1.readDouble();
                _loc_5.mLowValue = param1.readDouble();
                _loc_5.mTime = param1.readFloat();
                param2.mNodes.add(_loc_4, _loc_5);
                _loc_4++;
            }

            return;
        }//end

    }
