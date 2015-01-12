# README #

Pour build Droidcon-UiAutomator : 

```
#!shell

<android-sdk>/tools/android create uitest-project -n Droidcon -t 1 -p <path>
cd <path> && ant build
```
Pour executer le test en ligne de commande:

```
#!shell

adb push bin/Droidcon-UiAutomator.jar /data/local/tmp/ && adb shell uiautomator runtest Droidcon-UiAutomator.jar -c Droidcon

```
Pour specifier un devices a adb

```
#!shell

-s <serial>

```
Pour donnes un arguments a l'execution:

```
#!shell

-e argKey key
```