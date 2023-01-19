package cam.alperez.samples.onlineonlyapi.ui.common;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Function;

import cam.alperez.samples.onlineonlyapi.R;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;

public class ErrorUiMessageMapper implements Function<ApiResponse<?>, ErrorUiMessage> {

    private final Resources resources;

    public ErrorUiMessageMapper(Context context) {
        resources = context.getResources();
    }

    @Override
    public ErrorUiMessage apply(ApiResponse<?> apiResponse) {
        final String displayText;
        final String description;
        if (apiResponse.isSuccessful()) {
            displayText = resources.getString(R.string.result_success);
            description = String.format(Locale.UK, "%d - %s", apiResponse.httpCode(), apiResponse.httpMessage());
        } else if (apiResponse.getLocalError() == null) {
            displayText = resources.getString(R.string.result_server_error);
            description = String.format(Locale.UK, "%d - %s", apiResponse.httpCode(), apiResponse.httpMessage());
        } else if (apiResponse.getLocalError() instanceof MalformedJsonException) {
            displayText = resources.getString(R.string.result_server_bad_data);
            description = apiResponse.getLocalError().getMessage();
        } else if (apiResponse.getLocalError() instanceof IOException) {
            displayText = resources.getString(R.string.result_connection_error);
            description = apiResponse.getLocalError().getClass().getSimpleName()+": "+apiResponse.getLocalError().getMessage();
        } else {
            displayText = resources.getString(R.string.result_critical_app_error);
            description = apiResponse.getLocalError().getClass().getSimpleName()+": "+apiResponse.getLocalError().getMessage();
        }
        return new ErrorUiMessage(displayText, description);
    }
}
