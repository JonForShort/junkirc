package com.github.thejunkjon.junkirc.utils;

import java.io.InputStream;
import java.net.URL;

public class ResourceUtils {

    private ResourceUtils() {

    }

    public static InputStream getResourceAsStream(final String resourcePath) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    public static URL getResource(final String resourcePath) {
        return ResourceUtils.class.getClassLoader().getResource(resourcePath);
    }
}
