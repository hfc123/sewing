[English](README.md) | **简体中文**
# 导入
```
implementation 'com.github.hfc123.sewing:sewinglib:v1.0.3'
```
```
classpath "com.github.hfc123.sewing:sewing-plugin:v1.0.3"
```
# 功能
- android 拆分包升级的功能
# 使用
```
//配置gradle插件
plugins {
id 'com.android.application'
id 'sewing'
}
```
```
//设置参数
sewing{
enabled true
newFilePath "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
oldFilePath "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
patchFilePath "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
//uploadApkUrl
}
//生成拆分包
makepatch{
newFilePath="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
oldFilePath="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
patchFilePath="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
}
//合成拆分包
patchTask{
newFilePath2="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\new.apk"
oldFilePath ="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\old.apk"
patchFilePath ="D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
}
//上传拆分包
uploadTask{
uploadUrl = "uploadurl"
uploadApkUrl ="uploadApkUrl"
patchFilePath = "D:\\workspace2\\githubdownLoad\\Sewing2\\files\\patch.patch"
}
```
-在app中合成
            ```
            String newfilepath;
            String oldfilepath;
            String patchpath;
            PachAsyncTask pachAsyncTask =  new PachAsyncTask(newfilepath,oldfilepath,patchpath);
            pachAsyncTask.setPatchTaskLisener(new PatchTaskLisener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onPatching() {

            }

            @Override
            public void onComplete() {

            }
        });
        pachAsyncTask.execute();
        ```
