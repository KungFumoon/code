# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
######################云 ocr 混淆规则 ocr-BEGIN###########################
-keepattributes InnerClasses
-keep public class com.webank.mbank.ocr.WbCloudOcrSDK{
    public <methods>;
    public static final *;
}
-keep public class com.webank.mbank.ocr.WbCloudOcrSDK$*{
    *;
}

-keep public class com.webank.mbank.ocr.tools.ErrorCode{
    *;
}

-keep public class com.webank.mbank.ocr.net.*$*{
    *;
}
-keep public class com.webank.mbank.ocr.net.*{
    *;
}
#######################云 ocr 混淆规则 ocr-END#############################


#######################webank normal混淆规则-BEGIN#############################
#不混淆内部类
-keepattributes InnerClasses
-keepattributes *Annotation*
-keepattributes Signature

-keep, allowobfuscation @interface com.webank.normal.xview.Inflater
-keep, allowobfuscation @interface com.webank.normal.xview.Find
-keep, allowobfuscation @interface com.webank.normal.xview.BindClick

-keep @com.webank.normal.xview.Inflater class *
-keepclassmembers class * {
    @com.webank.normal.Find *;
    @com.webank.normal.BindClick *;
}

-keep public class com.webank.normal.net.*$*{
    *;
}
-keep public class com.webank.normal.net.*{
    *;
}
-keep public class com.webank.normal.thread.*{
   *;
}
-keep public class com.webank.normal.thread.*$*{
   *;
}
-keep public class com.webank.normal.tools.WLogger{
    *;
}

#webank normal 包含的第三方库 bugly
-keep class com.tencent.bugly.webank.**{
    *;
}

#wehttp 混淆规则
-dontwarn com.webank.mbank.okio.**

-keep class com.webank.mbank.wehttp.**{
    public <methods>;
}
-keep interface com.webank.mbank.wehttp.**{
    public <methods>;
}
-keep public class com.webank.mbank.wehttp.WeLog$Level{
    *;
}
-keep class com.webank.mbank.wejson.WeJson{
    public <methods>;
}


#######################webank normal混淆规则-END#############################
###############云刷脸混淆规则 faceverify-BEGIN##################
#不混淆内部类
-keepattributes InnerClasses

-keep public class com.webank.facelight.tools.WbCloudFaceVerifySdk{
    public <methods>;
    public static final *;
}
-keep public class com.webank. facelight.tools.WbCloudFaceVerifySdk$*{
    *;
}
-keep public class com.webank.record.**{
    public <methods>;
    public static final *;
}
-keep public class com.webank. facelight.ui.FaceVerifyStatus{

}
-keep public class com.webank. facelight.ui.FaceVerifyStatus$Mode{
    *;
}
-keep public class com.webank. facelight.tools.IdentifyCardValidate{
    public <methods>;
}
-keep public class com.tencent.youtulivecheck.**{
    *;
}
-keep public class com.webank. facelight.contants.**{
    *;
}
-keep public class com.webank. facelight.listerners.**{
    *;
}
-keep public class com.webank. facelight.Request.*$*{
    *;
}
-keep public class com.webank. facelight.Request.*{
    *;
}
-keep public class com.webank. facelight.config.FaceVerifyConfig {
    public <methods>;
}

-keep class com.tencent.youtuface.**{
    *;
}
-keep class com.tencent.youtulivecheck.**{
    *;
}
-keep class com.tencent.youtufacetrack.**{
    *;
}
-keep class com.tencent.youtufacelive.model.**{
    *;
}
-keep class com.tencent.youtufacelive.tools.FileUtils{
public <methods>;
}
-keep class com.tencent.youtufacelive.tools.YTUtils{
    public <methods>;
}
-keep class com.tencent.youtufacelive.tools.YTFaceLiveLogger{
    public <methods>;
}
-keep class com.tencent.youtufacelive.tools.YTFaceLiveLogger$IFaceLiveLogger{
     *;
}
-keep class com.tencent.youtufacelive.IYTMaskStateListener{
    *;
}
-keep class com.tencent.youtufacelive.YTPreviewHandlerThread{
    public static *;
    public <methods>;
}
-keep class com.tencent.youtufacelive.YTPreviewHandlerThread$IUploadListener{
     *;
}
-keep class com.tencent.youtufacelive.YTPreviewHandlerThread$ISetCameraParameterListener{
     *;
}
-keep class com.tencent.youtufacelive.YTPreviewMask{
    public <methods>;
}
-keep class com.tencent.youtufacelive.YTPreviewMask$TickCallback{
    *;
}
-keeppackagenames com.webank.mbank.permission_request
# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(***);
    *** get* ();
}
################云刷脸混淆规则 faceverify-END########################
#############webank normal混淆规则-BEGIN###################
#不混淆内部类
-keepattributes InnerClasses
-keepattributes *Annotation*
-keepattributes Signature

-keep, allowobfuscation @interface com.webank.normal.xview.Inflater
-keep, allowobfuscation @interface com.webank.normal.xview.Find
-keep, allowobfuscation @interface com.webank.normal.xview.BindClick

-keep @com.webank.normal.xview.Inflater class *
-keepclassmembers class * {
    @com.webank.normal.Find *;
    @com.webank.normal.BindClick *;
}

-keep public class com.webank.normal.net.*$*{
    *;
}
-keep public class com.webank.normal.net.*{
    *;
}
-keep public class com.webank.normal.thread.*$*{
   *;
}
-keep public class com.webank.normal.thread. *{
   *;
}
-keep public class com.webank.normal.tools.WLogger{
    *;
}
-keep public class com.webank.normal.tools.*{
*;
}

#wehttp混淆规则
-dontwarn com.webank.mbank.okio.**

-keep class com.webank.mbank.wehttp.**{
    public <methods>;
}
-keep interface com.webank.mbank.wehttp.**{
    public <methods>;
}
-keep public class com.webank.mbank.wehttp.WeLog$Level{
    *;
}
-keep class com.webank.mbank.wejson.WeJson{
    public <methods>;
}
-keep public class com.webank.mbank.wehttp.WeReq$ErrType{
     *;
}
#webank normal包含的第三方库bugly
-keep class com.tencent.bugly.webank.**{
    *;
}
###########webank normal混淆规则-END#######################
########云产品依赖的第三方库 混淆规则-BEGIN############

## support:appcompat-v7
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

#########云产品依赖的第三方库 混淆规则-END#############