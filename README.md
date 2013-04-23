NFC Unlocker
===========

NFC Unlocker is the Android application that makes it possible to unlock the secured lock screen using NFC tags.

Android API does not support unlock of secured lock screen. I have made some workaround using `input` shell command that automatically enters password instead of a user.



Supported lock screen security options
--------------------------------------
* PIN
* Password



Firmware requirements
---------------------

* **Root** to run shell commands.
* **Modified NfcNci.apk** that can poll while the screen is locked. You can obtain it [here][1] for Google Nexus 4.



Developed By
------------

* Paul Annekov (https://github.com/PaulAnnekov)



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





 [1]: http://forum.xda-developers.com/showthread.php?t=2026439
