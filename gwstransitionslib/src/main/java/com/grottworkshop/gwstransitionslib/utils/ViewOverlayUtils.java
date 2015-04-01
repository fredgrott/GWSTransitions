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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewGroup;


/**
 * Created by fgrott on 4/1/2015.
 */
public class ViewOverlayUtils {

    interface ViewOverlayUtilsImpl {
        void addOverlay(ViewGroup sceneRoot, Drawable drawable);

        void removeOverlay(ViewGroup sceneRoot, Drawable drawable);
    }

    static class BaseViewOverlayUtilsImpl implements ViewOverlayUtilsImpl {
        @Override
        public void addOverlay(ViewGroup sceneRoot, Drawable drawable) {
            ViewOverlayPreJellybean viewOverlay = ViewOverlayPreJellybean.getOverlay(sceneRoot);
            viewOverlay.addDrawable(drawable);
        }

        @Override
        public void removeOverlay(ViewGroup sceneRoot, Drawable drawable) {
            ViewOverlayPreJellybean viewOverlay = ViewOverlayPreJellybean.getOverlay(sceneRoot);
            viewOverlay.removeDrawable(drawable);
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    static class JellyBeanMR2ViewUtilsImpl implements ViewOverlayUtilsImpl {
        @Override
        public void addOverlay(ViewGroup sceneRoot, Drawable drawable) {
            sceneRoot.getOverlay().add(drawable);
        }

        @Override
        public void removeOverlay(ViewGroup sceneRoot, Drawable drawable) {
            sceneRoot.getOverlay().remove(drawable);
        }
    }

    private static final ViewOverlayUtilsImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            IMPL = new JellyBeanMR2ViewUtilsImpl();
        } else {
            IMPL = new BaseViewOverlayUtilsImpl();
        }
    }

    public static void addOverlay(ViewGroup sceneRoot, Drawable drawable) {
        IMPL.addOverlay(sceneRoot, drawable);
    }

    public static void removeOverlay(ViewGroup sceneRoot, Drawable drawable) {
        IMPL.removeOverlay(sceneRoot, drawable);
    }
}
