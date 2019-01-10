package com.askdog.android.network.okhttp.request;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.ImageView;


import com.askdog.android.network.okhttp.OkHttpClientManager;
import com.askdog.android.network.okhttp.ResultCallback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 */
public abstract class OkHttpRequest {
    protected OkHttpClientManager mOkHttpClientManager;
    protected OkHttpClient mOkHttpClient;

    protected RequestBody requestBody;
    protected Request request;

    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Context mContext;

    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers, Context context) {
        this.mContext = context;
        mOkHttpClientManager = OkHttpClientManager.getInstance(context);
        mOkHttpClient = mOkHttpClientManager.getOkHttpClient();
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
    }

    protected abstract Request buildRequest();

    protected abstract RequestBody buildRequestBody();

    protected void prepareInvoked(ResultCallback callback) {
        requestBody = buildRequestBody();
        requestBody = wrapRequestBody(requestBody, callback);
        request = buildRequest();
    }


    public void invokeAsyn(ResultCallback callback) {
        prepareInvoked(callback);
        mOkHttpClientManager.execute(request, callback);
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody, final ResultCallback callback) {
        return requestBody;
    }


    public Response invoke() throws IOException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return mOkHttpClientManager.execute(request);
    }


    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public void cancel() {
//        if (!TextUtils.isEmpty(tag))
        if (tag != null)
            mOkHttpClientManager.cancelTag(tag);
    }


    public static class Builder {
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Pair<String, File>[] files;
        public OkHttpRequest request;
        private String destFileDir;
        private String destFileName;

        private ImageView imageView;
        private int errorResId = -1;

        //for post
        private String content;
        private byte[] bytes;
        private File file;
        private String content_type;
        private OkHttpUploadProgressRequest.ProgressListener listener;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder listener(OkHttpUploadProgressRequest.ProgressListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder addParams(String key, String val) {
            if (this.params == null) {
                params = new IdentityHashMap<>();
            }
            params.put(key, val);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String val) {
            if (this.headers == null) {
                headers = new IdentityHashMap<>();
            }
            headers.put(key, val);
            return this;
        }


        public Builder files(Pair<String, File>... files) {
            this.files = files;
            return this;
        }

        public Builder destFileName(String destFileName) {
            this.destFileName = destFileName;
            return this;
        }

        public Builder destFileDir(String destFileDir) {
            this.destFileDir = destFileDir;
            return this;
        }


        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder errResId(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder content_type(String type) {
            this.content_type = type;
            return this;
        }

        //        public Response get(OkHttpDemoActivity.MyResultCallback<User> myResultCallback) throws IOException {
//            OkHttpRequest request = new OkHttpGetRequest(url, tag, params, headers);
//            return request.invoke();
//        }
        public OkHttpRequest delete(Context context) throws IOException {
            request = new OkHttpDeleteRequest(url, tag, params, headers, context);
            return request;
        }

        public OkHttpRequest get(Context context) throws IOException {
            OkHttpRequest request = new OkHttpGetRequest(url, tag, params, headers, context);
            return request;
        }

        public OkHttpRequest get(ResultCallback callback, Context context) {
            OkHttpRequest request = new OkHttpGetRequest(url, tag, params, headers, context);
            request.invokeAsyn(callback);
            return request;
        }

        public OkHttpRequest post(Context context) throws IOException {
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params, headers, content, bytes, file, content_type, context);
            return request;
        }

        public OkHttpRequest put(Context context) throws IOException {
            OkHttpRequest request = new OkHttpPutRequest(url, tag, params, headers, content, bytes, file, content_type, context);
            return request;
        }

        public OkHttpRequest post(ResultCallback callback, Context context) {
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params, headers, content, bytes, file, content_type, context);
            request.invokeAsyn(callback);
            return request;
        }

        public OkHttpRequest upload(ResultCallback callback) {
            OkHttpRequest request = new OkHttpUploadRequest(url, tag, params, headers, files);
            request.invokeAsyn(callback);
            return request;
        }

        public OkHttpRequest upload() throws IOException {
            OkHttpRequest request = new OkHttpUploadRequest(url, tag, params, headers, files);
            return request;
        }

        public OkHttpRequest uploadProgress() throws IOException {
            OkHttpRequest request = new OkHttpUploadProgressRequest(url, tag, params, headers, file, listener);
            return request;
        }

        public OkHttpRequest uploadProgressRequest() throws IOException {
            OkHttpRequest request = new OkHttpUploadProgressRequest(url, tag, params, headers, file, listener);
            return request;
        }

        public OkHttpRequest download(ResultCallback callback, Context context) {
            OkHttpRequest request = new OkHttpDownloadRequest(url, tag, params, headers, destFileName, destFileDir, context);
            request.invokeAsyn(callback);
            return request;
        }

        public Response download(Context context) throws IOException {
            OkHttpRequest request = new OkHttpDownloadRequest(url, tag, params, headers, destFileName, destFileDir, context);
            return request.invoke();
        }

        public void displayImage(ResultCallback callback, Context context) {
            OkHttpRequest request = new OkHttpDisplayImgRequest(url, tag, params, headers, imageView, errorResId, context);
            request.invokeAsyn(callback);
        }


    }


}
