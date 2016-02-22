package cn.salesuite.mlogcat.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.salesuite.mlogcat.R;
import cn.salesuite.mlogcat.app.BaseActivity;
import cn.salesuite.mlogcat.helper.PackageHelper;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.log.L;


public class AboutActivity extends BaseActivity {
	
	@InjectView
	Button okButton;
	
	@InjectView
	View topPanel;
	
	@InjectView
	WebView aboutWebView;

	@InjectView
	ProgressBar progressBar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logcat_about);
		
		initViews();				
	}
	
	private void initViews() {
		topPanel.setVisibility(View.GONE);
		okButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		okButton.setVisibility(View.GONE);
		aboutWebView.setVisibility(View.GONE);
		aboutWebView.setBackgroundColor(0);
		aboutWebView.setWebViewClient(new AboutWebClient());
		initializeWebView();
	}


	public void initializeWebView() {
		
		String text = loadTextFile(R.raw.about_body);
		String version = PackageHelper.getVersionName(this);
		boolean isDonateVersion = PackageHelper.isCatlogDonateInstalled(getApplicationContext());
		if (isDonateVersion) {
			version += " (" + getString(R.string.donate_version_name) + ")";
		}
		String message = loadTextFile(isDonateVersion ? R.raw.donate_message : R.raw.free_message);
		String changelog = loadTextFile(R.raw.changelog);
		String css = loadTextFile(R.raw.about_css);
		String translations = loadTextFile(R.raw.translations);
		text = String.format(text, version, message, changelog, css, translations);
		
		
		WebSettings settings = aboutWebView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		
		aboutWebView.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);
	}

	private String loadTextFile(int resourceId) {
		
		InputStream is = getResources().openRawResource(resourceId);
		
		BufferedReader buff = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		
		try {
			while (buff.ready()) {
				sb.append(buff.readLine()).append("\n");
			}
		} catch (IOException e) {
			L.e("This should not happen");
		}
		
		return sb.toString();
	}
	
	private void loadExternalUrl(String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW"); 
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}
	
	private class AboutWebClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, final String url) {
			L.d("shouldOverrideUrlLoading");
			
			// XXX hack to make the webview go to an external url if the hyperlink is 
			// in my own HTML file - otherwise it says "Page not available" because I'm not calling
			// loadDataWithBaseURL.  But if I call loadDataWithBaseUrl using a fake URL, then
			// the links within the page itself don't work!!  Arggggh!!!
			
			if (url.startsWith("http") || url.startsWith("mailto") || url.startsWith("market")) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						loadExternalUrl(url);
					}
				});
				return true;
			}		
			return false;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// dismiss the loading bar when the page has finished loading
			mHandler.post(new Runnable(){

				@Override
				public void run() {
					progressBar.setVisibility(View.GONE);
					aboutWebView.setVisibility(View.VISIBLE);
					topPanel.setVisibility(View.VISIBLE);
					okButton.setVisibility(View.VISIBLE);
					
				}});
			super.onPageFinished(view, url);
		}
	}
}
