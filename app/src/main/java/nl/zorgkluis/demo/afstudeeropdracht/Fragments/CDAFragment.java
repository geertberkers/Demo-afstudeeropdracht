package nl.zorgkluis.demo.afstudeeropdracht.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nl.zorgkluis.demo.afstudeeropdracht.R;

/**
 * Created by Zorgkluis (geert).
 */
public class CDAFragment extends Fragment {

    private static final String TAG = "CDAFragment";

    private String title;
    private WebView webView;

    public CDAFragment() {
        title = "CDA Document";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cda, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);

        showCDADocument();

        return rootView;
    }

    private void showCDADocument() {
        // Now your raw files are accessible here.
        Source xml = getResource(R.raw.cda_document);
        Source xsl = getResource(R.raw.style_document);

        // Transform ...
        String html = transformXMLandXSLtoHTML(xml, xsl);

        // Loading the above transformed XSLT into WebView...
        if (html != null) {
            webView.loadData(html, "text/html", null);
        }
    }

    /**
     * Read resources file to string
     *
     * @param resourceId resource file id
     * @return resource file
     */
    private Source getResource(int resourceId) {
        return new StreamSource(getResources().openRawResource(resourceId));
    }

    /**
     * Transform XML and XSL to HTML
     *
     * @param xml xml source
     * @param xsl xsl source
     * @return HTML code
     */
    private static String transformXMLandXSLtoHTML(Source xml, Source xsl) {
        String html = null;

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsl);

            OutputStream outputStream = new ByteArrayOutputStream();
            Result result = new StreamResult(outputStream);
            transformer.transform(xml, result);

            html = outputStream.toString();
        } catch (TransformerException ex) {
            Log.e(TAG, ex.getMessage());
            ex.printStackTrace();
        }

        return html;
    }

    public String getTitle() {
        return title;
    }
}
