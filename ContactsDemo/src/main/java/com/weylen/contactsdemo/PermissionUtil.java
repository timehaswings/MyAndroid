/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.weylen.contactsdemo;

import android.content.pm.PackageManager;

public abstract class PermissionUtil {

    /**
     * 验证权限是否被允许
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // 先检查结果集的长度大于1
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        // 验证权限
        for (int result : grantResults) {
            // PackageManager.PERMISSION_GRANTED // 允许
            // PackageManager.PERMISSION_DENIED // 拒绝
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
