package com.depromeet.articlereminder.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Constants {
    public static final String CURRENT_VER = SupportedVersions.V1_0;

    public static class SupportedVersions {
        public static final String V1_0 = "/v1";
        private static Set<String> versions = new HashSet<>();

        public static List<String> getAllVersions() {
            versions.add(V1_0);
            return new ArrayList<>(versions);
        }
    }
}
