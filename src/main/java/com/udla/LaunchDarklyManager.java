package com.udla;

import java.io.IOException;

import com.launchdarkly.sdk.*;
import com.launchdarkly.sdk.server.*;

public class LaunchDarklyManager {

    private static LDClient client;
    private static final String SDK_KEY = System.getenv("LD_SDK_KEY");

    public static void initialize() {
        if (SDK_KEY == null || SDK_KEY.isEmpty()) {
            throw new IllegalStateException("No se ha definido LD_SDK_KEY en variables de entorno.");
        }

        client = new LDClient(SDK_KEY);
    }

    public static boolean isEnabled(String feature, String userKey) {
        if (client == null) {
            initialize();
        }

        LDContext context = LDContext.create(userKey);

        return client.boolVariation(feature, context, false);
    }

    public static void shutdown() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace(); // o un logger si tienes uno
            }
        }
    }
}