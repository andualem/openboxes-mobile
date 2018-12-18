/*
 * Copyright (C) 2011 University of Washington
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.health.supplychain.utility;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.preference.PreferenceManager;
import java.io.File;

public class FileManager extends Application{

    public static final String EVOUCHER_ROOT = Environment.getExternalStorageDirectory()
            + File.separator + "EVOUCHER";

    public static final String PHOTO_PATH = EVOUCHER_ROOT + File.separator + "photo";
    public static final String BACKUP_PATH = EVOUCHER_ROOT + File.separator + "backup";
    public static final String CARD_PATH = EVOUCHER_ROOT + File.separator + "card";
    public static final String UPLOADS_PATH = EVOUCHER_ROOT + File.separator + "uploads";
    public static final String DOWNLOADS_PATH = EVOUCHER_ROOT + File.separator + "downloads";
    
    public static final String DEFAULT_FONTSIZE = "21";

    private static FileManager fileManager = null;

    public static FileManager getInstance() {
        return fileManager;
    }

    public static void createDirectories() throws RuntimeException {
        String cardstatus = Environment.getExternalStorageState();
        if (!cardstatus.equals(Environment.MEDIA_MOUNTED)) {
            RuntimeException e =
                    new RuntimeException("Reports :: SDCard error: "
                            + Environment.getExternalStorageState());
            throw e;
        }

        String[] dirs = {
                EVOUCHER_ROOT, PHOTO_PATH, BACKUP_PATH, CARD_PATH, UPLOADS_PATH, DOWNLOADS_PATH
        };

        for (String dirName : dirs) {
            File dir = new File(dirName);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    RuntimeException e =
                            new RuntimeException("Reports :: Cannot create directory: "
                                    + dirName);
                    throw e;
                }
            } else {
                if (!dir.isDirectory()) {
                    RuntimeException e =
                            new RuntimeException("Reports :: " + dirName
                                    + " exists, but is not a directory");
                    throw e;
                }
            }
        }
    }

}
