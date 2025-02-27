package ch.bailu.aat_lib.preferences.map;

import ch.bailu.aat_lib.preferences.SolidCheckList;
import ch.bailu.aat_lib.preferences.StorageInterface;
import ch.bailu.aat_lib.resources.Res;
import ch.bailu.foc.FocFactory;

public class SolidOverlayFileList extends SolidCheckList {
    public static final int MAX_OVERLAYS=4;

    private final SolidOverlayFile[] list = new SolidOverlayFile[MAX_OVERLAYS];

    public SolidOverlayFileList(StorageInterface storage, FocFactory focFactory) {
        for (int i=0; i<list.length; i++)
            list[i] = new SolidOverlayFile(storage,focFactory, i);
    }


    public SolidOverlayFile get(int i) {
        i=Math.min(MAX_OVERLAYS, i);
        i=Math.max(0, i);

        return list[i];
    }

    @Override
    public String[] getStringArray() {
        String[] array = new String[list.length];
        for (int i=0; i < list.length; i++) {
            array[i] = list[i].getLabel();
        }
        return array;
    }


    @Override
    public boolean[] getEnabledArray() {
        boolean[] array = new boolean[MAX_OVERLAYS];
        for (int i=0; i<list.length; i++)
            array[i] = list[i].isEnabled();
        return array;
    }


    @Override
    public void setEnabled(int i, boolean isChecked) {
        get(i).setEnabled(isChecked);
    }


    @Override
    public String getKey() {
        return list[0].getKey();
    }



    @Override
    public StorageInterface getStorage() {
        return list[0].getStorage();
    }


    @Override
    public boolean hasKey(String s) {
        for (SolidOverlayFile aList : list) if (aList.hasKey(s)) return true;
        return false;
    }


    @Override
    public String getLabel() {
        return Res.str().file_overlay();
    }

    @Override
    public String getIconResource() {return "view_paged_inverse";}

}
