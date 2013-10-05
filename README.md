NFC Unlocker
===========

NFC Unlocker is the Android application that makes it possible to unlock the secured lock screen using NFC tags.

Android API doesn't provide simple way to permanently unlock secured lock screen, therefore NFC Unlocker can use
one of several workarounds (unlock methods).



Unlock methods
--------------

* **Flag**. Uses window flags that dismisses lock screen.
* **Input**. Enters password into field instead of user using `input` shell command. The most stable, but requires root
  and supports only PIN and Password lock (no patterns).
* **KeyguardLock**. It's pretty stable but uses deprecated features (still works on 4.3).



Firmware requirements
---------------------

* **Root** if using *Input* unlock method.
* **Modified NfcNci.apk** that can poll while the screen is locked. Here are some apk's for popular devices:
 * [Google Nexus 4][Nexus 4 apk]
 * [HTC One][HTC One apk]
 * [Samsung Galaxy S4][Galaxy S4 apk]
 * [Sony Xperia Z][Xperia Z apk]
 * [Samsung Galaxy Note ll][Galaxy Note ll apk]
 
If your device is not listed here then just try to search using such keywords: 
"*nfc while screen is locked yourdevicename*".



Developed By
------------

* Paul Annekov (https://github.com/PaulAnnekov)



Branching model
---------------

Similar to [this] (http://nvie.com/posts/a-successful-git-branching-model) branching model but with only **master**,
**develop** and **feature branches**.

Develop main functionality in **develop** branch.

For new experimental features create separate branch.

Merge to **develop** from feature branch when you complete it.

Merge to **master** from **develop** branch on release and tag it with version number.



License
-------

    Copyright 2013 Paul Annekov

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





 [Nexus 4 apk]: http://forum.xda-developers.com/showthread.php?t=2026439
 [HTC One apk]: http://forum.xda-developers.com/showthread.php?t=2325410
 [Galaxy S4 apk]: http://forum.xda-developers.com/showthread.php?t=2285978
 [Xperia Z apk]: http://forum.xda-developers.com/showthread.php?t=2330677
 [Galaxy Note ll apk]: http://forum.xda-developers.com/showthread.php?t=2293467