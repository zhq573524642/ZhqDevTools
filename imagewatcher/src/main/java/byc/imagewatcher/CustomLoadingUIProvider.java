package byc.imagewatcher;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import byc.imagewatcher.ImageWatcher;
import byc.imagewatcher.view.imagewatcher.R;

public class CustomLoadingUIProvider implements ImageWatcher.LoadingUIProvider {
    private final FrameLayout.LayoutParams lpCenterInParent = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @Override
    public View initialView(Context context) {
        ImageView load = new ImageView(context);
        lpCenterInParent.gravity = Gravity.CENTER;
        load.setLayoutParams(lpCenterInParent);
        load.setImageResource(R.drawable.dice_action);
        return load;
    }

    @Override
    public void start(View loadView) {
        loadView.setVisibility(View.VISIBLE);
        ((AnimationDrawable) ((ImageView) loadView).getDrawable()).start();
    }

    @Override
    public void stop(View loadView) {
        ((AnimationDrawable) ((ImageView) loadView).getDrawable()).stop();
        loadView.setVisibility(View.GONE);
    }
}
