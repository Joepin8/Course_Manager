package ikpmd.westgeestoonk.course_manager.GSON;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends Request<Object> {

    private final Gson gson = new Gson();
    private final Type mClazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Type mType;
    private final String TAG = "GsonRequest<T>";

    public GsonRequest(String url, Type clazz, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.mClazz = clazz;
        this.mType = null;
        this.headers = headers;
        this.listener = listener;
    }

    public GsonRequest(String url, Type type, Map<String, String> headers, Response.Listener<T> listener) {
        super(Method.GET, url, null);
        this.mClazz = null;
        this.mType = type;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.wtf(TAG, "RESPONSE = " + json);
            if (mClazz != null) {
                return Response.success(gson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return (Response<Object>) Response.success(gson.fromJson(json, mType), HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        listener.onResponse((T) response);
    }

}