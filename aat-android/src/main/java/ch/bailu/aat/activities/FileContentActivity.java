package ch.bailu.aat.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import ch.bailu.aat.description.AscendDescription;
import ch.bailu.aat.description.AveragePaceDescription;
import ch.bailu.aat.description.AveragePaceDescriptionAP;
import ch.bailu.aat.description.AverageSpeedDescription;
import ch.bailu.aat.description.AverageSpeedDescriptionAP;
import ch.bailu.aat.description.CaloriesDescription;
import ch.bailu.aat.description.ContentDescriptions;
import ch.bailu.aat.description.DateDescription;
import ch.bailu.aat.description.DescendDescription;
import ch.bailu.aat.description.DistanceApDescription;
import ch.bailu.aat.description.DistanceDescription;
import ch.bailu.aat.description.EndDateDescription;
import ch.bailu.aat.description.IndexedAttributeDescription;
import ch.bailu.aat.description.MaximumSpeedDescription;
import ch.bailu.aat.description.NameDescription;
import ch.bailu.aat.description.PathDescription;
import ch.bailu.aat.description.PauseApDescription;
import ch.bailu.aat.description.PauseDescription;
import ch.bailu.aat.description.TimeApDescription;
import ch.bailu.aat.description.TrackSizeDescription;
import ch.bailu.aat.map.MapFactory;
import ch.bailu.aat.preferences.Storage;
import ch.bailu.aat.util.ui.AppLayout;
import ch.bailu.aat.util.ui.AppTheme;
import ch.bailu.aat.views.ContentView;
import ch.bailu.aat.views.PercentageLayout;
import ch.bailu.aat.views.bar.MainControlBar;
import ch.bailu.aat.views.description.mview.MultiView;
import ch.bailu.aat.views.graph.GraphViewFactory;
import ch.bailu.aat.views.preferences.VerticalScrollView;
import ch.bailu.aat_lib.description.ContentDescription;
import ch.bailu.aat_lib.description.TimeDescription;
import ch.bailu.aat_lib.gpx.InfoID;


public class FileContentActivity extends AbsFileContentActivity{

    private static final String SOLID_KEY="file_content";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewGroup createLayout(MainControlBar bar, ContentView contentView) {
        map = MapFactory.DEF(this, SOLID_KEY).content(editorSource);

        VerticalScrollView summary = new VerticalScrollView(this);
        summary.addAllContent(this, getSummaryData(this), AppTheme.trackContent,
                InfoID.FILEVIEW, InfoID.EDITOR_OVERLAY);


        summary.add(createAttributesView());


        View graph = GraphViewFactory.all(this,this, THEME,
                InfoID.FILEVIEW, InfoID.EDITOR_OVERLAY);


        if (AppLayout.isTablet(this)) {
            return createPercentageLayout(summary, graph);
        } else {
            return createMultiView(bar, summary, graph, contentView);
        }

    }


    public static ContentDescription[] getSummaryData(Context c) {
        Storage storage = new Storage(c);
        return new ContentDescription[] {
                new NameDescription(),
                new PathDescription(),
                new DateDescription(),
                new EndDateDescription(),

                new ContentDescriptions(
                        new TimeDescription(),
                        new TimeApDescription()),

                new ContentDescriptions(
                        new PauseDescription(),
                        new PauseApDescription()),

                new ContentDescriptions(
                    new DistanceDescription(storage),
                    new DistanceApDescription(storage)),

                new ContentDescriptions(
                        new AverageSpeedDescription(storage),
                        new AverageSpeedDescriptionAP(storage),
                        new MaximumSpeedDescription(storage)),


                new CaloriesDescription(storage),

                new ContentDescriptions(
                    new AveragePaceDescription(storage),
                    new AveragePaceDescriptionAP(storage)),


                new ContentDescriptions(
                    new AscendDescription(storage),
                    new DescendDescription(storage)),

                new ContentDescriptions(
                    new IndexedAttributeDescription.HeartRate(c),
                    new IndexedAttributeDescription.HeartBeats(c)),

                new ContentDescriptions(
                    new IndexedAttributeDescription.Cadence(),
                    new IndexedAttributeDescription.TotalCadence()),

                new TrackSizeDescription()
        };
    }

    protected ViewGroup createMultiView(MainControlBar bar,
                                   View summary, View graph, ContentView contentView) {

        MultiView mv = new MultiView(this, SOLID_KEY);
        mv.add(summary);
        mv.add(map.toView());
        mv.add(graph);

        bar.addMvNext(mv);

        contentView.addMvIndicator(mv);
        return mv;
    }


    private ViewGroup createPercentageLayout(
            View summary, View graph) {

        PercentageLayout a = new PercentageLayout(this);
        a.setOrientation(AppLayout.getOrientationAlongLargeSide(this));
        a.add(map.toView(), 60);
        a.add(summary, 40);

        PercentageLayout b = new PercentageLayout(this);
        b.add(a, 70);
        b.add(graph, 30);

        return b;
    }

}
