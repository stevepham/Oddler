#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ht117_keylib_NativeLib_getAuthKey(
        JNIEnv* env,
        jobject /* this */) {
    std::string auth = "C1OTTkcusvooatf7nxS2a6AD8ISLUW0gjcBRC4v9jVXN2pFRHHmZjWVnn4eKEllY";
    return env->NewStringUTF(auth.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ht117_keylib_NativeLib_getAccountID(JNIEnv *env, jobject thiz) {
    std::string accountID = "d91eaf24-8548-47e6-ab56-908de75660ce";
    return env->NewStringUTF(accountID.c_str());
}
