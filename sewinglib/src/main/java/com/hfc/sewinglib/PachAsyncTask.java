package com.hfc.sewinglib;

import android.os.AsyncTask;

public class PachAsyncTask extends AsyncTask<Void, Void, String> {
    private String newFilePath2;//the file created after patched
    private String oldFilePath;//the old file need to patched
    private String patchFilePath;//the patch file created before
    private PatchTask patchTask;

    private PatchTaskLisener patchTaskLisener ;

    public PatchTaskLisener getPatchTaskLisener() {
        return patchTaskLisener;
    }

    public void setPatchTaskLisener(PatchTaskLisener patchTaskLisener) {
        this.patchTaskLisener = patchTaskLisener;
    }

    public PachAsyncTask(String newFilePath2, String oldFilePath, String patchFilePath) {
        this.newFilePath2 = newFilePath2;
        this.oldFilePath = oldFilePath;
        this.patchFilePath = patchFilePath;
        this.patchTask = new PatchTask();
        patchTask.newFilePath2 = newFilePath2;
        patchTask.oldFilePath = oldFilePath;
        patchTask.patchFilePath = patchFilePath;
    }

    // 在后台执行耗时操作之前调用，通常用于初始化
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // 这里可以执行一些初始化操作，例如显示进度条
        if (patchTaskLisener!=null)
            patchTaskLisener.onStart();
    }

    // 在后台线程中执行耗时操作，不可以更新 UI
    @Override
    protected String doInBackground(Void... voids) {
        // 这里执行耗时操作，例如网络请求、数据库查询等
        if (patchTaskLisener!=null)
            patchTaskLisener.onPatching();

        patchTask.doTaskAction();
        return "Hello, AsyncTask!";
    }

    // 在后台执行耗时操作后调用，用于处理操作结果，并在主线程更新 UI
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // 这里可以更新 UI，例如显示请求结果
        if (patchTaskLisener!=null)
            patchTaskLisener.onComplete();
        System.out.println(result);
    }
}
