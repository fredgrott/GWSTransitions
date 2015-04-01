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
import android.os.Build.VERSION_CODES;
import android.view.View;

import java.lang.reflect.Method;


/**
 * Created by fgrott on 4/1/2015.
 */
@TargetApi(VERSION_CODES.KITKAT)
class ViewUtilsKitKat extends ViewUtils.ViewUtilsJellyBeanMR2 {

    private static final Method METHOD_getTransitionAlpha = ReflectionUtils.getMethod(View.class, "getTransitionAlpha");
    private static final Method METHOD_setTransitionAlpha = ReflectionUtils.getMethod(View.class, "setTransitionAlpha",
            float.class);

    @Override
    public float getTransitionAlpha(View v) {
        return (Float) ReflectionUtils.invoke(v, 1, METHOD_getTransitionAlpha);
    }

    @Override
    public boolean isLaidOut(View v, boolean defaultValue) {
        return v.isLaidOut();
    }

    @Override
    public void setTransitionAlpha(View v, float alpha) {
        ReflectionUtils.invoke(v, null, METHOD_setTransitionAlpha, alpha);
    }

    @Override
    public boolean isTransitionAlphaCompatMode() {
        return false;
    }

    @Override
    public String getAlphaProperty() {
        return "transitionAlpha";
    }

}
