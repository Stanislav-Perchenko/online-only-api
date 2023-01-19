package cam.alperez.samples.onlineonlyapi.ui.common;

import java.util.Locale;

public class ErrorUiMessage {

    public final String displayText;
    public final String detailedDescription;


    public ErrorUiMessage(String displayText, String detailedDescription) {
        this.displayText = displayText;
        this.detailedDescription = detailedDescription;
    }

    @Override
    public String toString() {
        return String.format(Locale.UK,
                "ErrorUiMessage{displayText='%s', detailedDescription='%s'",
                displayText,
                detailedDescription);
    }
}
