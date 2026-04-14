#include <jni.h>
#include <windows.h>
#include "windivert.h"
#include "firewall_NativeBridge.h"

JNIEXPORT void JNICALL Java_firewall_NativeBridge_startFirewall
  (JNIEnv *env, jobject obj) {

    HANDLE handle;
    WINDIVERT_ADDRESS addr;
    char packet[65535];
    UINT packetLen;

    handle = WinDivertOpen("true", WINDIVERT_LAYER_NETWORK, 0, 0);

	if (handle == INVALID_HANDLE_VALUE) {
	    DWORD err = GetLastError();
	    printf("WinDivertOpen failed: %lu\n", err);
	    return;
	}

    jclass cls = (*env)->FindClass(env, "firewall/NativeBridge");
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