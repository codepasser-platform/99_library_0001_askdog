package com.askdog.android.network.okhttp.request;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 */
public class OkHttpUploadProgressRequest extends OkHttpPostRequest {
    private File file;

    ProgressListener listener;

    protected OkHttpUploadProgressRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, File file, ProgressListener listener) {
        super(url, tag, params, headers, null, null, null, "", null);
        this.file = file;
        this.listener = listener;
    }

    @Override
    protected void validParams() {
        if (params == null && file == null) {
            throw new IllegalArgumentException("params and files can't both null in upload request .");
        }
    }

    private void addParams(MultipartBuilder builder, Map<String, String> params) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be null .");
        }

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));

            }
        }
    }

    @Override
    public RequestBody buildRequestBody() {
        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);
        addParams(builder, params);

        builder.addFormDataPart("file", file.getName(), createCustomRequestBody(MultipartBuilder.FORM, file, listener));

        return builder.build();
    }

    public static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    int length = 2048;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    public interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }
}


