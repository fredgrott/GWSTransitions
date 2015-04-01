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

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.ViewGroup;

import java.lang.reflect.Field;


/**
 * Created by fgrott on 4/1/2015.
 */
@TargetApi(VERSION_CODES.HONEYCOMB)
public class ViewGroupUtils {
    interface ViewGroupUtilsImpl {
        void suppressLayout(ViewGroup group, boolean suppress);
    }

    @TargetApi(VERSION_CODES.HONEYCOMB)
    static class BaseViewGroupUtilsImpl implements ViewGroupUtilsImpl {

        private static Field sFieldLayoutSuppressed;

        static LayoutTransition mLayoutTransition = new LayoutTransition() {
            @Override
            public boolean isChangingLayout() {
                return true;
            }
        };
        static {
            mLayoutTransition.setAnimator(LayoutTransition.APPEARING, null);
            mLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, null);
            mLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, null);
            mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, null);
            mLayoutTransition.setAnimator(LayoutTransition.CHANGING, null);
        }

        @Override
        public void suppressLayout(ViewGroup group, boolean suppress) {
            if (suppress) {
                group.setLayoutTransition(mLayoutTransition);
            } else {
                group.setLayoutTransition(null);
                if (sFieldLayoutSuppressed == null) {
                    sFieldLayoutSuppressed = ReflectionUtils.getPrivateField(ViewGroup.class,
                            "mLayoutSuppressed");
                }
                Boolean suppressed = (Boolean) ReflectionUtils.getFieldValue(group,
                        Boolean.FALSE, sFieldLayoutSuppressed);
                if (!Boolean.FALSE.equals(suppressed)) {
                    ReflectionUtils.setFieldValue(group, sFieldLayoutSuppressed, false);
                    group.requestLayout();
                }
            }
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN_MR2)
    static class JellyBeanMr2ViewGroupUtilsImpl extends BaseViewGroupUtilsImpl {
        @Override
        public void suppressLayout(ViewGroup group, boolean suppress) {
            ViewGroupUtilsJellyBeanMr2.suppressLayout(group, suppress);
        }
    }

    private static final ViewGroupUtilsImpl IMPL;

    static {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) {
            IMPL = new JellyBeanMr2ViewGroupUtilsImpl();
        } else {
            IMPL = new BaseViewGroupUtilsImpl();
        }
    }

    public static void suppressLayout(ViewGroup group, boolean suppress) {
        if (group != null) {
            IMPL.suppressLayout(group, suppress);
        }
    }
}
