package org.imogene.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckedLinearLayout extends LinearLayout implements Checkable{
	
    private boolean mChecked;
    private int mCheckMarkResource;
    private Drawable mCheckMarkDrawable;
    private int mBasePaddingRight;
    private int mCheckMarkWidth;

    private static final int[] CHECKED_STATE_SET = {
    	android.R.attr.state_checked
    };

    public CheckedLinearLayout(Context context) {
        this(context, null);
    }

    public CheckedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
        Drawable d = context.getResources().getDrawable(value.resourceId);
        if (d != null) {
            setCheckMarkDrawable(d);
        }
        
        setWillNotDraw(false);
    }

    @Override
	public void toggle() {
        setChecked(!mChecked);
    }
    
    @Override
	public boolean isChecked() {
        return mChecked;
    }

    /**
     * <p>Changes the checked state of this text view.</p>
     *
     * @param checked true to check the text, false to uncheck it
     */
    @Override
	public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }


    /**
     * Set the checkmark to a given Drawable, identified by its resourece id. This will be drawn
     * when {@link #isChecked()} is true.
     * 
     * @param resid The Drawable to use for the checkmark.
     */
    public void setCheckMarkDrawable(int resid) {
        if (resid != 0 && resid == mCheckMarkResource) {
            return;
        }

        mCheckMarkResource = resid;

        Drawable d = null;
        if (mCheckMarkResource != 0) {
            d = getResources().getDrawable(mCheckMarkResource);
        }
        setCheckMarkDrawable(d);
    }

    /**
     * Set the checkmark to a given Drawable. This will be drawn when {@link #isChecked()} is true.
     *
     * @param d The Drawable to use for the checkmark.
     */
    public void setCheckMarkDrawable(Drawable d) {
        if (d != null) {
            if (mCheckMarkDrawable != null) {
                mCheckMarkDrawable.setCallback(null);
                unscheduleDrawable(mCheckMarkDrawable);
            }
            d.setCallback(this);
            d.setVisible(getVisibility() == VISIBLE, false);
            d.setState(CHECKED_STATE_SET);
            setMinimumHeight(d.getIntrinsicHeight());
            
            mCheckMarkWidth = d.getIntrinsicWidth();
            setPadding(getPaddingLeft(), getPaddingTop(), mCheckMarkWidth + mBasePaddingRight, getPaddingBottom());
//            mPaddingRight = mCheckMarkWidth + mBasePaddingRight;
            d.setState(getDrawableState());
            mCheckMarkDrawable = d;
        } else {
        	setPadding(getPaddingLeft(), getPaddingTop(), mBasePaddingRight, getPaddingBottom());
//            mPaddingRight = mBasePaddingRight;
        }
        requestLayout();
    }
    
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        mBasePaddingRight = getPaddingRight();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final Drawable checkMarkDrawable = mCheckMarkDrawable;
        if (checkMarkDrawable != null) {
//            final int verticalGravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
            final int height = checkMarkDrawable.getIntrinsicHeight();

            int y = 0;

//            switch (verticalGravity) {
//                case Gravity.BOTTOM:
//                    y = getHeight() - height;
//                    break;
//                case Gravity.CENTER_VERTICAL:
//                    y = (getHeight() - height) / 2;
//                    break;
//            }
            
            y = (getHeight() - height) / 2;

            
            int right = getWidth();
            checkMarkDrawable.setBounds(
                    right - mCheckMarkWidth - mBasePaddingRight, 
                    y, 
                    right - mBasePaddingRight, 
                    y + height);
            checkMarkDrawable.draw(canvas);
        }
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        
        if (mCheckMarkDrawable != null) {
            int[] myDrawableState = getDrawableState();
            
            // Set the state of the Drawable
            mCheckMarkDrawable.setState(myDrawableState);
            
            invalidate();
        }
    }
}
