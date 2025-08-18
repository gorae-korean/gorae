package gorae.backend.common;

import java.util.UUID;

public class UrlUtils {
    private UrlUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String STATIC_URL = "https://static.goraekorean.site";

    public static String getTextbookThumbnailUrl(UUID textbookId) {
        return STATIC_URL + "/image/textbook/" + textbookId + "/thumbnail.png";
    }

    public static String getExpressionImageUrl(UUID textbookId, String fileName) {
        return STATIC_URL + "/image/textbook/" + textbookId + "/" + fileName + ".png";
    }
}
