/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Modifications Copyright(C) 2015 Fred Grott(GrottworkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.grottworkshop.gwstransitionslib.utils;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import android.view.View;
import android.view.ViewGroup;

import com.grottworkshop.gwstransitionslib.R;


/**
 * Created by fgrott on 4/1/2015.
 */
@TargetApi(VERSION_CODES.HONEYCOMB)
public class ViewUtils {
    interface ViewUtilsImpl {
        float getTransitionAlpha(View v);

        boolean isLaidOut(View v, boolean defaultValue);

        void setClipBounds(View v, Rect clipBounds);

        Rect getClipBounds(View v);

        void setTransitionName(View v, String name);

        String getTransitionName(View v);

        void setTransitionAlpha(View v, float alpha);

        boolean isTransitionAlphaCompatMode();

        String getAlphaProperty();

        void setTranslationZ(View view, float z);

        float getTranslationZ(View view);

        View addGhostView(View view, ViewGroup viewGroup, Matrix matrix);

        void removeGhostView(View view);

        void transformMatrixToGlobal(View view, Matrix matrix);

        void transformMatrixToLocal(View view, Matrix matrix);

        void setAnimationMatrix(View view, Matrix matrix);

        Object getWindowId(View view);

        boolean isRtl(View view);

        void setHasTransientState(View view, boolean hasTransientState);

        boolean hasTransientState(View view);
    }

    static class BaseViewUtilsImpl implements ViewUtilsImpl {
        @Override
        public float getTransitionAlpha(View v) {
            return v.getAlpha();
        }

        @Override
        public boolean isLaidOut(View v, boolean defaultValue) {
            return defaultValue;
        }

        @Override
        public void setClipBounds(View v, Rect clipBounds) {
            // TODO: Implement support behavior
        }

        @Override
        public Rect getClipBounds(View v) {
            // TODO: Implement support behavior
            return null;
        }

        @Override
        public void setTransitionName(View v, String name) {
            v.setTag(R.id.transitionName, name);
        }

        @Override
        public String getTransitionName(View v) {
            return (String) v.getTag(R.id.transitionName);
        }

        @Override
        public void setTransitionAlpha(View v, float alpha) {
            v.setAlpha(alpha);
        }

        @Override
        public boolean isTransitionAlphaCompatMode() {
            return true;
        }

        @Override
        public String getAlphaProperty() {
            return "alpha";
        }

        @Override
        public void setTranslationZ(View view, float z) {
            // do nothing
        }

        @Override
        public float getTranslationZ(View view) {
            return 0;
        }

        @Override
        public View addGhostView(View view, ViewGroup viewGroup, Matrix matrix) {
            return null;
        }

        @Override
        public void removeGhostView(View view) {
            // do nothing
        }

        @Override
        public void transformMatrixToGlobal(View view, Matrix matrix) {
            // TODO: Implement support behavior
        }

        @Override
        public void transformMatrixToLocal(View v, Matrix matrix) {
            // TODO: Implement support behavior
        }

        @Override
        public void setAnimationMatrix(View view, Matrix matrix) {
            // TODO: Implement support behavior
        }

        @Override
        public Object getWindowId(View view) {
            return null;
        }

        @Override
        public boolean isRtl(View view) {
            return false;
        }

        @Override
        public void setHasTransientState(View view, boolean hasTransientState) {
            // do nothing; API doesn't exist
        }

        @Override
        public boolean hasTransientState(View view) {
            return false;
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN)
    static class ViewUtilsJellyBean extends BaseViewUtilsImpl {
        @Override
        public void setHasTransientState(View view, boolean hasTransientState) {
            view.setHasTransientState(hasTransientState);
        }

        @Override
        public boolean hasTransientState(View view) {
            return view.hasTransientState();
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN_MR1)
    static class ViewUtilsJellyBeanMR1 extends ViewUtilsJellyBean {
        @Override
        public boolean isRtl(View view) {
            return view != null && view.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN_MR2)
    static class ViewUtilsJellyBeanMR2 extends ViewUtilsJellyBeanMR1 {
        @Override
        public void setClipBounds(View v, Rect clipBounds) {
            v.setClipBounds(clipBounds);
        }

        @Override
        public Rect getClipBounds(View v) {
            return v.getClipBounds();
        }

        @Override
        public Object getWindowId(View view) {
            return view.getWindowId();
        }
    }

    private static final ViewUtilsImpl IMPL;

    static {
        final int version = VERSION.SDK_INT;
        if (version >= VERSION_CODES.LOLLIPOP) {
            IMPL = new ViewUtilsLollipop();
        } else if (version >= VERSION_CODES.KITKAT) {
            IMPL = new ViewUtilsKitKat();
        } else if (version >= VERSION_CODES.JELLY_BEAN_MR2) {
            IMPL = new ViewUtilsJellyBeanMR2();
        } else if (version >= VERSION_CODES.JELLY_BEAN_MR1) {
            IMPL = new ViewUtilsJellyBeanMR1();
        } else if (version >= VERSION_CODES.JELLY_BEAN) {
            IMPL = new ViewUtilsJellyBean();
        } else {
            IMPL = new BaseViewUtilsImpl();
        }
    }

    public static float getTransitionAlpha(View v) {
        return IMPL.getTransitionAlpha(v);
    }

    public static boolean isLaidOut(View v, boolean defaultValue) {
        return IMPL.isLaidOut(v, defaultValue);
    }

    public static void setClipBounds(View v, Rect clipBounds) {
        IMPL.setClipBounds(v, clipBounds);
    }

    public static Rect getClipBounds(View v) {
        return IMPL.getClipBounds(v);
    }

    public static void setTransitionAlpha(View v, float alpha) {
        IMPL.setTransitionAlpha(v, alpha);
    }

    public static boolean isTransitionAlphaCompatMode() {
        return IMPL.isTransitionAlphaCompatMode();
    }

    public static String getAlphaProperty() {
        return IMPL.getAlphaProperty();
    }

    public static void setTransitionName(View v, String name) {
        IMPL.setTransitionName(v, name);
    }

    public static String getTransitionName(View v) {
        return IMPL.getTransitionName(v);
    }

    public static float getTranslationZ(View view) {
        return IMPL.getTranslationZ(view);
    }

    public static void setTranslationZ(View view, float z) {
        IMPL.setTranslationZ(view, z);
    }

    public static void transformMatrixToGlobal(View view, Matrix matrix) {
        IMPL.transformMatrixToGlobal(view, matrix);
    }

    public static void transformMatrixToLocal(View view, Matrix matrix) {
        IMPL.transformMatrixToLocal(view, matrix);
    }

    public static void setAnimationMatrix(View view, Matrix matrix) {
        IMPL.setAnimationMatrix(view, matrix);
    }

    public static View addGhostView(View view, ViewGroup viewGroup, Matrix matrix) {
        return IMPL.addGhostView(view, viewGroup, matrix);
    }

    public static void removeGhostView(View view) {
        IMPL.removeGhostView(view);
    }

    public static Object getWindowId(View view) {
        return IMPL.getWindowId(view);
    }

    public static boolean isRtl(View view) {
        return IMPL.isRtl(view);
    }

    public static boolean hasTransientState(View view) {
        return IMPL.hasTransientState(view);
    }

    public static void setHasTransientState(View view, boolean hasTransientState) {
        IMPL.setHasTransientState(view, hasTransientState);
    }
}
