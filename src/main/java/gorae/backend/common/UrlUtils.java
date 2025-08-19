package gorae.backend.common;

import java.util.UUID;

public class UrlUtils {
    private UrlUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String STATIC_URL = "https://static.goraekorean.site";
    private static final String TEXTBOOK_IMAGE_PATH = "/image/textbook";

    public static String getTextbookThumbnailUrl(UUID textbookId) {
        return STATIC_URL + TEXTBOOK_IMAGE_PATH + "/" + textbookId + "/thumbnail.png";
    }

    public static String getExpressionImageUrl(UUID textbookId, String fileName) {
        return STATIC_URL + TEXTBOOK_IMAGE_PATH + "/" + textbookId + "/expression/" + fileName + ".png";
    }

    public static String getCultureTipImageUrl(UUID textbookId, String fileName) {
        if (fileName == null) {
            return null;
        }
        return STATIC_URL + TEXTBOOK_IMAGE_PATH + "/" + textbookId + "/culture/" + fileName + ".png";
    }
}
