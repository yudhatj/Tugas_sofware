package com.example.absensi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VolleyMultipartRequest
        extends Request<NetworkResponse> {

    private final Response.Listener<NetworkResponse> mListener;

    private final Map<String, String> params;

    private final Map<String, DataPart> byteData;

    public VolleyMultipartRequest(

            int method,
            String url,
            Map<String, String> params,
            Map<String, DataPart> byteData,
            Response.Listener<NetworkResponse> listener,
            Response.ErrorListener errorListener

    ) {

        super(method, url, errorListener);

        this.mListener = listener;

        this.params = params;

        this.byteData = byteData;
    }

    @Override
    protected Map<String, String> getParams(){

        return params;
    }

    @Override
    public String getBodyContentType(){

        return "multipart/form-data;boundary=apiclient";
    }

    @Override
    public byte[] getBody()
            throws AuthFailureError {

        ByteArrayOutputStream bos =
                new ByteArrayOutputStream();

        try {

            // PARAMS
            for(Map.Entry<String, String> entry
                    : params.entrySet()) {

                bos.write(("--apiclient\r\n").getBytes());

                bos.write(
                        ("Content-Disposition: form-data; name=\""
                                + entry.getKey()
                                + "\"\r\n\r\n")
                                .getBytes()
                );

                bos.write(entry.getValue().getBytes());

                bos.write("\r\n".getBytes());
            }

            // FILE
            for(Map.Entry<String, DataPart> entry
                    : byteData.entrySet()) {

                DataPart dataFile =
                        entry.getValue();

                bos.write(("--apiclient\r\n").getBytes());

                bos.write(
                        ("Content-Disposition: form-data; name=\""
                                + entry.getKey()
                                + "\"; filename=\""
                                + dataFile.fileName
                                + "\"\r\n")
                                .getBytes()
                );

                bos.write(
                        ("Content-Type: "
                                + dataFile.type
                                + "\r\n\r\n")
                                .getBytes()
                );

                bos.write(dataFile.content);

                bos.write("\r\n".getBytes());
            }

            bos.write("--apiclient--\r\n".getBytes());

        } catch (IOException e) {

            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(
            NetworkResponse response
    ) {

        return Response.success(
                response,
                HttpHeaderParser.parseCacheHeaders(response)
        );
    }

    @Override
    protected void deliverResponse(
            NetworkResponse response
    ) {

        mListener.onResponse(response);
    }

    // DATA PART
    public static class DataPart {

        private final String fileName;

        private final byte[] content;

        private final String type;

        public DataPart(
                String fileName,
                byte[] content,
                String type
        ) {

            this.fileName = fileName;

            this.content = content;

            this.type = type;
        }
    }
}