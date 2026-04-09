#include <jni.h>
#include <windows.h>
#include "windivert.h"
#include "com_example_firewall_NativeBridge.h"

JNIEXPORT void JNICALL Java_com_example_firewall_NativeBridge_startFirewall
  (JNIEnv *env, jobject obj) {

    HANDLE handle;
    WINDIVERT_ADDRESS addr;
    char packet[65535];
    UINT packetLen;

    handle = WinDivertOpen("true", WINDIVERT_LAYER_NETWORK, 0, 0);

    if (handle == INVALID_HANDLE_VALUE) {
        return;
    }

    jclass cls = (*env)->FindClass(env, "com/example/firewall/NativeBridge");
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, "isMalicious", "([BI)Z");

    while (TRUE) {
        if (!WinDivertRecv(handle, packet, sizeof(packet), &packetLen, &addr)) {
            continue;
        }

        jbyteArray arr = (*env)->NewByteArray(env, packetLen);
        (*env)->SetByteArrayRegion(env, arr, 0, packetLen, (jbyte*)packet);

        jboolean block = (*env)->CallStaticBooleanMethod(env, cls, mid, arr, packetLen);

        if (!block) {
            WinDivertSend(handle, packet, packetLen, NULL, &addr);
        }
        // DROP = non reinviare
    }
}