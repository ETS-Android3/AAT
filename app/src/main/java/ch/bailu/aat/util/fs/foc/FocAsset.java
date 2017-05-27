package ch.bailu.aat.util.fs.foc;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import ch.bailu.simpleio.foc.Foc;

public class FocAsset extends Foc {

    private final String asset;
    private final AssetManager manager;


    public FocAsset(AssetManager m, String a) {
        manager = m;
        asset = a;
    }


    @Override
    public long lastModified() {
        return System.currentTimeMillis();
    }


    public static ArrayList<String> listAssets(AssetManager am, String path) {
        ArrayList<String> r = new ArrayList();

        try {
            String [] files = am.list(path);
            for (String f: files) {
                r.add(path+ "/" + f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return r;
    }

    @Override
    public boolean remove() {
        return false;
    }

    @Override
    public boolean mkdir() {
        return false;
    }

    @Override
    public Foc parent() {
        return null;
    }


    @Override
    public Foc child(String name) {
        return null;
    }

    @Override
    public String getName() {
        return asset;
    }

    @Override
    public long length() {
        return 0;
    }

    @Override
    public void foreach(Execute e) {

    }

    @Override
    public void foreachFile(Execute e) {

    }

    @Override
    public void foreachDir(Execute e) {

    }

    @Override
    public boolean isDir() {
        return false;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public boolean canRead() {
        return false;
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public InputStream openR() throws IOException {
        return manager.open(asset);
    }

    @Override
    public OutputStream openW() throws IOException {
        throw new IOException();
    }
}
