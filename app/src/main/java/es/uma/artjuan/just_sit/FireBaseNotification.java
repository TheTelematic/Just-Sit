package es.uma.artjuan.just_sit;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Juanca on 29/05/2017.
 */

public class FireBaseNotification extends FirebaseInstanceIdService {
    private static final String LOGTAG = "android-fcm";

    @Override
    public void onTokenRefresh() {
        //Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(LOGTAG, "Token actualizado: " + refreshedToken);
    }
}
