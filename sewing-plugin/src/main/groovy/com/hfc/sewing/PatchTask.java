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

import io.sigpipe.jbsdiff.InvalidHeaderException;
import io.sigpipe.jbsdiff.Patch;

public class PatchTask extends DefaultTask {
    static String newFilePath2;//the file created after patched
    static String oldFilePath;//the old file need to patched
    static String patchFilePath;//the patch file created before

    public PatchTask() {
        setGroup("jsDiffGroup");
    }
    @TaskAction
    public void doTaskAction() {
        System.out.println(">>>>> starting PatchTask <<<<<");
        patchFile();
    }
    public static void patchFile(){

        File oldFile = new File(oldFilePath);
        File patchFile = new File(patchFilePath);

        try {
            //输出newfile2然后比较 file1 和 file2是否一样
            OutputStream outputStream = new FileOutputStream(newFilePath2);
            Patch.patch( convertFileToByteArray(oldFile), convertFileToByteArray(patchFile),outputStream);
            outputStream.flush();
            outputStream.close();
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
