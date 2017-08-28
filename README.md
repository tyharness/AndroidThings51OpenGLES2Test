Example opengles2 Android app for phone and Android Things Device Developer Preview 4.1 and 5.1 on the rpi3
--------------------------------------------------------------------------------------

Please note this app comes in 3 flavours: phone, Android Things 41 and Android Things 51.

At this stage the app is not running on 51.  Android 41 is not hardware accelerated and therefore unlikey to work but made
for completeness.  The phone flavour indicates the code is valid in a general sense.

Just playing about trying to learn opengles2 and Android Things on the Raspberry Pi.  Any help or input
is much appreciated.  It's a very simple app to display Maxwell's colour triangle.
 

Ty Harness August 2017



I've got my phone plugged into the USB and the debugging enabled.

To connect to Android Things:
adb connect 192.168.0.10

adb devices -l

List of devices attached

192.168.0.10:5555      device product:   iot_rpi3 model:iot_rpi3 device:rpi3

cfa60340               device usb:1-1




---------------------------------------------------------------------------------------
Build via Android SDK the CLI:

./gradlew assembleDebug

----------------------------------------------------------------------------------------
Installation and use:

Use your IP address and PhoneID not mine.


Android Phone Flavour:


adb -s cfa60340 install -r app/build/outputs/apk/app-phone-debug.apk


adb -s cfa60340 logcat | grep MainActivity


-------------------------------------------------------------

Android Things 51 Flavour:

adb -s 192.168.0.10 install -r app/build/outputs/apk/app-things51-debug.apk

adb -s 192.168.0.10 shell am start example.androidthings.myopengles2Test/.MainActivity

adb -s 192.168.0.10  logcat | grep MainActivity

--------------------------------------------------------------

Android Things 41 Flavour:

same as above

adb -s 192.168.0.10 install -r app/build/outputs/apk/app-things41-debug.apk


---------------------------------------------------------------
Useful Commands for Android Things:

See what OEM apps have been installed:

adb -s 192.168.0.10 shell pm list packages -3

How to unistall:
adb -s 192.168.0.10 uninstall example.androidthings.myopengles2Test










