package com.alpha.springmvc.core.messageConvert;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CustomMessageConverter<T> implements HttpMessageConverter<T> {

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return "application".equals(mediaType.getType()) && "my-format".equals(mediaType.getSubtype());
    }


    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }
        return "application".equals(mediaType.getType()) && "my-format".equals(mediaType.getSubtype());
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        MediaType mediaType = new MediaType("application", "my-format");
        ArrayList<MediaType> list = new ArrayList<>();
        list.add(mediaType);
        return list;
    }

    @Override
    public T read(Class<? extends T> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream body = httpInputMessage.getBody();
        JsonReader reader = new JsonReader(new InputStreamReader(body));
        Gson gson = new Gson();
        T t = gson.fromJson(reader, aClass);
        return t;
    }

    @Override
    public void write(T t, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getHeaders().setContentType(mediaType);
        Gson gson = new Gson();
        String json = gson.toJson(t);
        httpOutputMessage.getBody().write(json.getBytes());
    }
}
