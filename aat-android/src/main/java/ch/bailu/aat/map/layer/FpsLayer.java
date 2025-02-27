package ch.bailu.aat.map.layer;

import ch.bailu.aat_lib.map.MapContext;
import ch.bailu.aat_lib.util.Point;
import ch.bailu.aat_lib.map.layer.MapLayerInterface;
import ch.bailu.aat_lib.preferences.StorageInterface;

public final class FpsLayer implements MapLayerInterface {

    private long totalTime;
    private long frameCount;
    private long startTime;
    private long frameTime;


    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public void drawInside(MapContext mcontext) {

    }

    @Override
    public void drawForeground(MapContext mcontext) {
        if (frameCount > 0 ) {
            long averageTime = totalTime / frameCount;
            mcontext.draw().textTop(frameCount + " " + frameTime + "ms, " + averageTime + "ms", 3);
        }
    }

    @Override
    public boolean onTap(Point tapPos) {
        return false;
    }

    @Override
    public void onPreferencesChanged(StorageInterface s, String key) {}


        @Override
    public void onAttached() {}

    @Override
    public void onDetached() {}


    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        frameTime = System.currentTimeMillis() - startTime;
        totalTime += frameTime;
        frameCount++;
    }
}
