package ch.bailu.aat_lib.app;

import ch.bailu.aat_lib.dispatcher.Broadcaster;
import ch.bailu.aat_lib.factory.FocFactory;
import ch.bailu.aat_lib.gpx.GpxInformation;
import ch.bailu.aat_lib.map.tile.MapTileInterface;
import ch.bailu.aat_lib.preferences.StorageInterface;
import ch.bailu.aat_lib.preferences.map.SolidDem3Directory;
import ch.bailu.aat_lib.preferences.map.SolidMapsForgeDirectory;
import ch.bailu.aat_lib.preferences.map.SolidTileCacheDirectory;
import ch.bailu.aat_lib.preferences.system.SolidDataDirectory;
import ch.bailu.aat_lib.resources.AssetsInterface;
import ch.bailu.aat_lib.service.ServicesInterface;
import ch.bailu.aat_lib.service.background.DownloadConfig;
import ch.bailu.aat_lib.service.directory.MapPreviewInterface;
import ch.bailu.aat_lib.service.directory.SummaryConfig;
import ch.bailu.aat_lib.util.sql.Database;
import ch.bailu.foc.Foc;

public interface AppContext extends FocFactory{
    Broadcaster getBroadcaster();

    ServicesInterface getServices();

    StorageInterface getStorage();

    SummaryConfig getSummaryConfig();

    Database createDataBase();

    MapPreviewInterface createMapPreview(GpxInformation info, Foc previewImageFile);

    MapTileInterface createMapTile();

    SolidDem3Directory getDem3Directory();

    DownloadConfig getDownloadConfig();

    SolidDataDirectory getDataDirectory();

    AssetsInterface getAssets();

    SolidMapsForgeDirectory getMapDirectory();

    SolidTileCacheDirectory getTileCacheDirectory();
}
