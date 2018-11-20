
# Android Demo App

Created with advertisers and publishers in mind, the Mobfox Demo App lets you check how those banners, interstitials and videos will look on your screen in an in-app environment. Type in the hash or scan the QR code of any ad to display it in the Demo App and make sure it fits your expectations - in size, quality and style.

### How to use
***
* Swipe through the various tabs and select the ad type you wish to test.
* Use the default inventory hash to display MobFox demo ads or insert your own MobFox inventory hash/URL address either manually or using a QR code.
* Set the floor price (optional).
* Tap the load button and see your live ads.
* In case of ad load failure the error message will be displayed.

**Note:** In order to test MoPub/AdMob ads enter the AdUnit ID provided by that network.
### URL Ads
***
You can also display an ad from a specific URL.

**Banner:**
* Select size - 320x50/300x50/300x250.
* Enter the desired creative URL address or use the QR scanner.
* Tap the "Load" button.

**Interstitial:**
* Select size - 320x480.
* Enter the desired creative URL address or use the QR scanner.
* Tap the "Load" button.

**VAST(video):**
* Select size - 320x480.
* Enter the desired creative URL address or use the QR scanner.
* Tap the "Load" button.

### Possible Errors And Solutions:
***
- "**no fill**" - Meaning that there were no demand for that request. 
could be caused by a low fill rate at your geo location, too high floor price, or other wrongly entered request params.
- "**timeout**" - Meaning the connection was interrupted during the ad loading or that the ad takes too long to load due to slow internet connection.
- **{"e3":{corssDoman":true ...}** - Meaning that the request did not hit our servers at all. Check your internet connection/VPN/proxy.
