package com.hfc.sewing;

import org.apache.commons.compress.compressors.CompressorException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.sigpipe.jbsdiff.Diff;
import io.sigpipe.jbsdiff.InvalidHeaderException;
import io.sigpipe.jbsdiff.Patch;

public class DiffTask extends DefaultTask {
    static String newFilePath;
    static String oldFilePath;
    static String patchFilePath;
    static String uploadUrl;
    static String uploadApkUrl;
    public DiffTask() {
        setGroup("jsDiffGroup");
    }
    @TaskAction
    public void doTaskAction() {
        System.out.println(">>>>> starting DiffTask <<<<<");
        diffFile();
        if (uploadUrl==null||"".equals(uploadUrl)){
            System.out.println("patch upload url path is null please check it");
        }else {
            new UploadFileUtils().uploadFile2(uploadUrl,new File(patchFilePath));
        }

        if (uploadApkUrl==null||"".equals(uploadApkUrl)){
            System.out.println("patch upload apk url path is null please check it");
            return;
        }else {
            new UploadFileUtils().uploadFile2(uploadApkUrl,new File(newFilePath));
        }
    }
    public static void diffFile(){
        System.out.println("newFilePath:"+newFilePath);
        if (newFilePath==null||"".equals(newFilePath)||oldFilePath==null||"".equals(oldFilePath)){
            System.out.println("old or new file path is null");
            return;
        }
        if (!newFilePath.endsWith(".apk")||!oldFilePath.endsWith(".apk")){
            System.out.println("old or new file path is not a apk file");
            return;
        }
        File newFile = new File(newFilePath);
        File oldFile = new File(oldFilePath);
        if (!newFile.exists()||!oldFile.exists()){
            System.out.println("old or new file not exit");
            return;
        }

        try {
            OutputStream outputStream = new FileOutputStream(patchFilePath);
            Diff.diff( convertFileToByteArray(oldFile), convertFileToByteArray(newFile),outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("patch make success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CompressorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidHeaderException e) {
            e.printStackTrace();
        }
    }
    public static byte[] convertFileToByteArray(File file) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            long length = file.length();

            if (length > Integer.MAX_VALUE) {
                throw new IOException("File is too large to read into a byte array");
            }

            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead;

            while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }

            return bytes;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
