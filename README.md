# MobFox Demo App

This is a demo Android app for demand partners to test their ads creative.


## MobFox Banner
* Select ad size - 320x50/300x50/300x250.
* Select server (optional).
* Enter Floor price value (optional).
* Enter your own Inventory hash/Use the QR scanner button/Leave the default testing hash.
* Tap the "Load Inventory" button.

## MobFox Interstitial
* Enter Floor price value (optional).
* Select server (optional)
* Enter your own Inventory hash/Use the QR scanner button/Leave the default testing hash.
* Tap the "Load Inventory" button.

## MobFox Native
* Select The "Native Ad" testing inventory hash from the top dropdown menu or manually enter your own.
* Select a request parameter from the list, enter the desired value and tap "Add" (optional)
* Tap the "Load" button

## URL Ads
You can also display an ad from a specific URL.

**Banner:**
* Select size - 320x50/300x50/300x250.
* Enter the desired creative URL address or use the QR scanner.
* Tap the "Load URL Banner".

**Interstitial:**
* Enter the desired creative URL address or use the QR scanner.
* Tap the "Load URL Interstitial".

**Vast(video):**
* Enter the desired creative URL address or use the QR scanner.
* Tap the "Load URL Interstitial".
## Possible Errors And Solutions:
- "no fill" - Meaning that there were no demand for that request. 
could be caused by a low fill rate at your geo location, too high floor price, or other wrongly entered request params.
- "timeout" - Meaning the connection was interrupted during the ad loading or that the ad takes too long to load due to slow internet connection.
- {"e3":{corssDoman":true ...} -Meaning that the request did not hit our servers at all. Check your internet connection/VPN/proxy.

## Demo video
A video demonstrating how to use the app: 
https://youtu.be/p6tX6HeKjyI
