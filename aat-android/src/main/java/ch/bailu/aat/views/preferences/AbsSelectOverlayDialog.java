package ch.bailu.aat.views.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import ch.bailu.aat.preferences.Storage;
import ch.bailu.aat_lib.preferences.map.SolidOverlayFileList;
import ch.bailu.foc.Foc;
import ch.bailu.foc_android.FocAndroidFactory;


public abstract class AbsSelectOverlayDialog extends AbsSolidDialog implements  DialogInterface.OnClickListener {

    private final SolidOverlayFileList slist;

    public AbsSelectOverlayDialog(Context context) {
        this(context, new SolidOverlayFileList(new Storage(context), new FocAndroidFactory(context)));
    }

    public AbsSelectOverlayDialog (Context context, SolidOverlayFileList l) {
        slist=l;

        final AlertDialog.Builder dialog = createDefaultDialog(context, l);
        dialog.setItems(slist.getStringArray(), this);
        dialog.create();
        dialog.show();
    }




    @Override
    public void onClick(DialogInterface dialog, int i) {
        Foc file = slist.get(i).getValueAsFile();
        onFileSelected(slist, i, file);
        dialog.dismiss();
    }


    protected abstract void onFileSelected(SolidOverlayFileList slist, int index, Foc file);
}
