package com.hfc.sewing;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class UploadTask extends DefaultTask {
    public String uploadUrl;
    public String patchFilePath;
    static String uploadApkUrl;
    static String newFilePath;

    public UploadTask() {
        setGroup("jsDiffGroup");
    }
    @TaskAction
    public void doTaskAction() {
        System.out.println(">>>>> uploadUrl DiffTask <<<<<");
        if (uploadUrl==null||"".equals(uploadUrl)){
            System.out.println("patch upload url path is null please check it");
        }else {
            new UploadFileUtils().uploadFile2(uploadUrl,new File(patchFilePath));
        }

        if (uploadApkUrl==null||"".equals(uploadApkUrl)||newFilePath==null){
            System.out.println("patch upload apk url path is null please check it");
        }else {
            new UploadFileUtils().uploadFile2(uploadApkUrl,new File(newFilePath));
        }

    }
}
