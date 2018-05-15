package projetmobile.esiea.quiz;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Toolbox {
    public static double ScHgt(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics display = context.getResources().getDisplayMetrics();

        int height = display.heightPixels;
        return height;
    }
}
