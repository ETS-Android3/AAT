package ch.bailu.aat_lib.service.render;

import org.mapsforge.core.model.Tile;

import javax.annotation.Nonnull;

import ch.bailu.aat_lib.preferences.OnPreferencesChanged;
import ch.bailu.aat_lib.preferences.StorageInterface;
import ch.bailu.aat_lib.preferences.map.SolidMapsForgeDirectory;
import ch.bailu.aat_lib.preferences.map.SolidRenderTheme;
import ch.bailu.aat_lib.preferences.map.SolidRendererThreads;
import ch.bailu.aat_lib.service.VirtualService;
import ch.bailu.aat_lib.service.cache.ObjTileMapsForge;
import ch.bailu.foc.FocFactory;

public final class RenderService  extends VirtualService
        implements OnPreferencesChanged, RenderServiceInterface {


    private final SolidMapsForgeDirectory sdirectory;
    private final SolidRenderTheme stheme;

    private final Configuration configuration = new Configuration();
    private final Caches caches= new Caches();


    public RenderService(FocFactory focFactory, SolidMapsForgeDirectory sdirectory) {

        this.sdirectory = sdirectory;
        this.stheme = new SolidRenderTheme(sdirectory, focFactory);

        sdirectory.getStorage().register(this);
        reconfigureRenderer();
    }


    private void reconfigureRenderer() {
        configuration.destroy();
        SolidRendererThreads.set();
        configuration.configure(
                sdirectory.getValueAsFile(),
                caches,
                stheme.getValueAsRenderTheme(),
                stheme.getValueAsThemeID());
    }


      public void lockToRenderer(ObjTileMapsForge o) {
        caches.lockToRenderer(o);
        configuration.lockToRenderer(o);
    }


    public void freeFromRenderer(ObjTileMapsForge o) {
        configuration.freeFromRenderer(o);
        caches.freeFromRenderer(o);
    }

    public void close() {
        sdirectory.getStorage().unregister(this);
        configuration.destroy();
    }


    @Override
    public void onPreferencesChanged(@Nonnull StorageInterface s, @Nonnull String key) {
        if (sdirectory.hasKey(key) || stheme.hasKey(key)) {
            reconfigureRenderer();
        }
    }

    public boolean supportsTile(Tile t) {
        return configuration.supportsTile(t);
    }
}
