package cc.echarger.echarger.ui.animation;

import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

public class RotateAnimator extends Animation {
    private int mCenterX,mCenterY;
    private Camera mCamera;
    private float mFromDegrees,mToDegrees;
    private AnimationUpdateListener updateListener;
    private int mWidth;

    public AnimationUpdateListener getUpdateListener() {
        return updateListener;
    }

    public void setUpdateListener(AnimationUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public RotateAnimator(float mFromDegrees, float mToDegrees) {
        this.mFromDegrees = mFromDegrees;
        this.mToDegrees = mToDegrees;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mWidth = width;
        mCenterX = width / 2;
        mCenterY = height / 2;
        // 设置默认时长
//        setDuration(500);
//        // 动画结束后保留状态
//        setFillAfter(true);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = mFromDegrees + (interpolatedTime *  (mToDegrees - mFromDegrees));
        Log.e("TAG", "applyTransformation: "+degrees );
        if(updateListener != null){
            updateListener.onProgressUpdate(interpolatedTime,degrees);
        }
        Matrix matrix = t.getMatrix();
        mCamera.save();

        //让旋转90度的时候不显的太大
//        if(interpolatedTime >= 0.5){
//            mCamera.translate(0,0,(Math.abs(interpolatedTime - 1) / 0.5f) * mWidth / 6);
//        }else {
//            mCamera.translate(0,0,(interpolatedTime / 0.5f) * mWidth / 6);
//        }

        mCamera.translate(0,0,interpolatedTime  * mWidth / 6);


        //图形绕Y轴旋转
        mCamera.rotateY(degrees);

        mCamera.getMatrix(matrix);
        //将原点移动到中心处
        matrix.preTranslate(-mCenterX,-mCenterY);
        matrix.postTranslate(mCenterX,mCenterY);
        mCamera.restore();

        super.applyTransformation(interpolatedTime, t);
    }

    /**
     * 动画更新的回调
     */
    public interface AnimationUpdateListener{
        /**
         * 进度回调
         */
        void onProgressUpdate(float progress,float value);
    }
}
