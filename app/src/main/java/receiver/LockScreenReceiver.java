package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            Intent intent11 = new Intent();
            intent11.setAction("com.example.sgajraj.androidchathead.ChatHeadService");
            context.startService(intent11);
            //intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent11);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            Intent intent11 = new Intent();
            intent11.setAction("com.example.sgajraj.androidchathead.ChatHeadService");
            context.startService(intent11);
            //Intent intent11 = new Intent(context,MainActivity.class);
            //intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        } else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Intent intent11 = new Intent();
            intent11.setAction("com.example.sgajraj.androidchathead.ChatHeadService");
            context.startService(intent11);
            //Intent intent11 = new Intent(context, MainActivity.class);
            //intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent11);

        }
    }
}
