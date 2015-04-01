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

/**
 * Created by fgrott on 4/1/2015.
 */
public class PropertyCompatObject<T, F> {

    private T mObject;

    public PropertyCompatObject() {
    }

    public PropertyCompatObject(T object) {
        mObject = object;
    }

    public T getObject() {
        return mObject;
    }

    public String getProperty() {
        return "value";
    }

    public void setValue(F value) {
        // do nothing
    }

    public F getValue() {
        return null;
    }

}
