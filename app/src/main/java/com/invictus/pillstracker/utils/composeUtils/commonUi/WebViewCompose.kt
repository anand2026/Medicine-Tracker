package com.invictus.pillstracker.utils.composeUtils.commonUi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.MailTo
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.invictus.pillstracker.R
import com.invictus.pillstracker.utils.displayUnitConverter.UnitConverter.DP
import timber.log.Timber


@Composable
fun WebViewCommonCompose(loadUrl: String,title: String,back:()->Unit) {

    BackHandler {
        back()
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = colorResource(id = R.color.inner_page_status_bar))

    val activity = (LocalContext.current as Activity)
    val isLoading = remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(40.DP))

                Box {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AndroidView(
                            factory = {
                                WebView(it).apply {

                                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                                    val userAgentFake =
                                        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"

                                    run {
                                        // WebView
                                        this.webChromeClient = WebChromeClient()
                                        this.webViewClient = MyWebViewClient(activity,this,isLoading)
                                        /*this.webViewClient = object : WebViewClient() {
                                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                                super.onPageStarted(view, url, favicon)
                                                isLoading.value = false
                                            }

                                            override fun onPageFinished(view: WebView?, url: String?) {
                                                super.onPageFinished(view, url)
                                                isLoading.value = false
                                            }

                                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                                Timber.d("==>DealingWithUrgesUrl  ${request?.url}" )
                                                return if((request?.url.toString()) == "https://neave.com/"){
                                                    Timber.d("==>Url  ${request?.url} success")
                                                    true
                                                }else {
                                                    super.shouldOverrideUrlLoading(view, request)
                                                }
                                            }
                                        }*/
                                        val settings = this.settings
                                        settings.setSupportZoom(true)
                                        settings.mediaPlaybackRequiresUserGesture = true
                                        settings.builtInZoomControls = true
                                        settings.displayZoomControls = false
                                        settings.allowFileAccess = true
                                        settings.allowContentAccess = true
                                        settings.loadWithOverviewMode = true
                                        settings.saveFormData = true
                                        settings.useWideViewPort = false
                                        settings.setSupportMultipleWindows(false)
                                        settings.loadsImagesAutomatically = true
                                        settings.blockNetworkImage = false
                                        settings.blockNetworkLoads = false
                                        settings.javaScriptEnabled = true
                                        settings.allowUniversalAccessFromFileURLs = true
                                        settings.allowFileAccessFromFileURLs = true
                                        settings.databaseEnabled = true
                                        settings.domStorageEnabled = true
                                        settings.setGeolocationEnabled(true)
                                        settings.javaScriptCanOpenWindowsAutomatically = true
                                        //settings.userAgentString = userAgentFake
                                        settings.setNeedInitialFocus(true)
                                        settings.offscreenPreRaster = true
                                        if (loadUrl.isNotEmpty()) {
                                            this.loadUrl(loadUrl)
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    PillsTrackerLoadingView(context = activity, isVisible = isLoading.value)
                }

            }

//            Box(modifier = Modifier
//                .background(color = colorResource(id = R.color.colorPrimary))
//                .padding(16.DP, 0.DP)
//                .height(40.DP)
//            ) {
//
//                Image(
//                    painter = painterResource(id = R.drawable.ic_back_with_bg),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(24.DP)
//                        .align(alignment = Alignment.CenterStart)
//                        .noRippleClickable {
//                            back()
//                        }
//                )
//
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.h5.copy(
//                        textAlign = TextAlign.Center,
//                        fontSize = 24.SP,
//                        fontWeight = FontWeight.Medium,
//                        color = Color.White
//                    ),
//                    modifier = Modifier
//                        .align(alignment = Alignment.Center)
//                        .fillMaxWidth()
//                )
//            }
            PageTitleBar(pageTitle = title) {
                back()
            }
        }
    }
}

class MyWebViewClient(
    val context: Context,
    val webView: WebView,
    val isLoading: MutableState<Boolean>
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        isLoading.value = true
        url?.let {
            if (!url.contains("docs.google.com") && url.endsWith(".pdf")) {
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=$url")
            }
            Timber.d("url1==>>$url")
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        isLoading.value = false
        try {
            view?.loadUrl(
                "javascript:$(document).ajaxStart(function (event, request, settings) { " +
                        "ajaxHandler.ajaxBegin(); " +  // Event called when an AJAX call begins
                        "});"
            )
            view?.loadUrl(
                "javascript:$(document).ajaxComplete(function (event, request, settings) { " +
                        "ajaxHandler.ajaxDone(); " +  // Event called when an AJAX call ends
                        "});"
            )
        } catch (e: Exception) {
            Timber.d(e)
        }
        //view?.loadUrl( "javascript: { document.getElementsByClassName('header').remove(); }");
//        try {
//            url?.let {
//                if (it.contains("blockerx.net")) {
//                    view?.loadUrl("javascript:var footer = document.getElementById(\"colophon\"); footer.parentNode.removeChild(footer); var header = document.getElementById(\"masthead\"); header.parentNode.removeChild(header);")
//                }
//                if (it.contains(BuildConfig.SERVER_URL)) {
//                    view?.loadUrl("javascript:if (typeof(document.getElementsByClassName('outerHeader')[0]) != 'undefined' && document.getElementsByClassName('outerHeader')[0] != null){document.getElementsByClassName('outerHeader')[0].style.display = 'none';} void 0")
//                }
//            }
//        } catch (e: Exception) {
//            Timber.d(e)
//        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (!url.isNullOrEmpty()) {
            return if (url.endsWith(".mp4")) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(url), "video/*")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view?.context?.startActivity(intent)
                // If we return true, onPageStarted, onPageFinished won't be called.
                true
            } else if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("smsto:") || url
                    .startsWith("mms:") || url.startsWith("mmsto:")
            ) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view?.context?.startActivity(intent)
                true // If we return true, onPageStarted, onPageFinished won't be called.
            } else if (url.startsWith("mailto:")) {
                val mt = MailTo.parse(url)
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/html"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mt.to))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, mt.subject)
                emailIntent.putExtra(Intent.EXTRA_CC, mt.cc)
                emailIntent.putExtra(Intent.EXTRA_TEXT, mt.body)
                context.startActivity(emailIntent)
                true
            } else if (url.contains("mastodon.social")) {
                true
            } else {
                Timber.d("url==>>${url}")
                if (url.contains("facebook.com") ||
                    url.contains("instagram.com") ||
                    url.contains("youtube.com/channel") ||
                    url.contains("twitter.com") ||
                    url.contains("reddit.com") ||
                    url.contains("linkedin.com") ||
                    url.contains("pinterest.com") ||
                    // url.contains("mastodon.social") ||
                    url.contains("whatsapp.com")
                ) {
                    try {
                        view?.context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        true
                    } catch (e: Exception) {
                        Timber.d(e)
//                            false
                        true
                    }
                }/* else if (mOpenPurposeType == OPEN_PURPOSE.SHOW_BLOCKERX_SUPPORT && !url.contains("blockerx")) {
                        true
                    }*/ else {
                    super.shouldOverrideUrlLoading(view, url)
                }
            }
        } else {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    override fun onLoadResource(view: WebView?, url: String?) {
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        Timber.e("onReceivedError of Add_User request: $request")
        Timber.e("onReceivedError of Add_User error: $error")
    }
}
