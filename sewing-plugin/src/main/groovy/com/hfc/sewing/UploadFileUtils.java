package com.hfc.sewing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class UploadFileUtils {
    //gradlew testmakepatch
    //这个方法无法使用
    public void uploadFile(final String uploadUrl,final File file) {
                    try {
                        if (file==null||uploadUrl==null||uploadUrl.equals("")){
                            return;
                        }
                        URL url = new URL(uploadUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
//
//                        try (OutputStream outputStream = connection.getOutputStream()) {
//                            // 将文件内容写入输出流
//                            Files.copy(file.toPath(), outputStream);
//                            outputStream.flush(); // 刷新输出流，确保数据被发送到服务器
//                            System.out.println("File content sent to server.");
//                        }
                        try (OutputStream outputStream = connection.getOutputStream();
                             FileInputStream fileInputStream = new FileInputStream(file)) {
                            // 设置缓冲区大小
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            // 从文件输入流读取数据，并写入输出流
                            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }

                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            System.out.println("File uploaded successfully!");
                        } else {
                            System.err.println("File upload failed. Response Code: " + responseCode);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
    }

    /* 上传文件至Server的方法 */
    public static void uploadFile2(String actionUrl, File file) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String newName = file.getName();
        String uploadFile   = file.getPath();

        try {
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            /* 设置传送的method=POST */
            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            /* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);
            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
            /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            fStream.close();
            ds.flush();
//            /* 取得Response内容 */
//            InputStream is = con.getInputStream();
//            int ch;
//            StringBuffer b = new StringBuffer();
//            while ((ch = is.read()) != -1) {
//                b.append((char) ch);
//            }
//            /* 将Response显示于Dialog */
//            System.out.println("上传成功" + b.toString().trim());
//            /* 关闭DataOutputStream */
//            ds.close();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("File uploaded successfully!");
            } else {
                System.err.println("File upload failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("上传失败" + e);
        }
    }
}
