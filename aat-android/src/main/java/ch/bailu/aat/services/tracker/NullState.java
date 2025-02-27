package ch.bailu.aat.services.tracker;

import ch.bailu.aat.R;
import ch.bailu.aat_lib.gpx.StateID;
import ch.bailu.aat_lib.resources.Res;
import ch.bailu.aat_lib.service.tracker.State;

public final class NullState extends State {

    @Override
    public int getStateID() {
        return StateID.OFF;
    }



    @Override
    public void updateTrack() {}

    @Override
    public void onStartPauseResume() {}

    @Override
    public void onStartStop() {}

    @Override
    public void onPauseResume() {}


    @Override
    public String getStartStopText() {
        return Res.str().tracker_start();
    }

    @Override
    public String getPauseResumeText() {
        return Res.str().tracker_pause();
    }

    @Override
    public int getStartStopIconID() {
        return R.drawable.media_playback_start_inverse;
    }
}
