package APIUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class APICaller {

	public static String cUrl(URL url) {
		
		InputStream is = null;
		String result = "";
		String line = null;
		try {
			is = url.openConnection().getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static URL getUrlFromString(String url) {

		URL url_object = null;
		try {

			url_object = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("ERREUR URL" + url);
		}
		return url_object;

	}

	public static String urlEncode(String str) {
		String res = "";
		try {
			res = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return res;

	}

}
