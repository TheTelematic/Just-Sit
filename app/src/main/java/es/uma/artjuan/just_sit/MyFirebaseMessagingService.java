package es.uma.artjuan.just_sit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Juanca on 29/05/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String LOGTAG = "Just-sit";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*
        if (remoteMessage.getNotification() != null) {

            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();

            Log.d(LOGTAG, "NOTIFICACION RECIBIDA");
            Log.d(LOGTAG, "Título: " + titulo);
            Log.d(LOGTAG, "Texto: " + texto);

            //Opcional: mostramos la notificación en la barra de estado
            showNotification(titulo, texto);

        }*/
        if(remoteMessage.getData().size() > 0) {
            Map<String, String> payload = remoteMessage.getData();
            showNotification(payload);
            /*Log.d(LOGTAG, "DATOS RECIBIDOS");
            Log.d(LOGTAG, "Usuario: " + remoteMessage.getData().get("usuario"));
            Log.d(LOGTAG, "Estado: " + remoteMessage.getData().get("estado"));
            */
        }
    }

    private void showNotification(Map<String, String> payload) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle(payload.get("username"));//mesa
        builder.setContentText(payload.get("email"));

        Intent resultIntent = new Intent(this, BarPedidoActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

}
