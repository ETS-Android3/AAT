package ch.bailu.aat.util.fs.foc;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import ch.bailu.aat.util.ui.AppLog;
import ch.bailu.simpleio.foc.Foc;

public class FocContent extends Foc {
    final Uri uri;
    final ContentResolver resolver;

    public FocContent(ContentResolver r, Uri u) {
        uri = u;
        resolver = r;
    }


    @Override
    public boolean remove() {
        return resolver.delete(uri,null,null) > 0;
    }

    @Override
    public boolean mkdir() {
        return false;
    }

    @Override
    public Foc parent() {
        URI parent;

        try {
            URI uri = new URI(this.uri.getPath());
            if (uri.getPath().endsWith("/"))
                parent = uri.resolve("..");
            else {
                parent = uri.resolve(".");
            }

            AppLog.d(this, uri.toString());
            AppLog.d(this, parent.toString());
            if (parent == null || uri.equals(parent)) {
                return null;
            } else {
                return new FocContent(resolver, Uri.parse(parent.getPath()));
            }
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Override
    public Foc child(String name) {
        return new FocContent(resolver, Uri.parse(this.toString() + "/" + name));
    }

    @Override
    public String getName() {
        return uri.getLastPathSegment();
    }

    @Override
    public String toString() {
        return uri.toString();
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
    public long lastModified() {
        return 0;
    }

    @Override
    public InputStream openR() throws IOException {
        return resolver.openInputStream(uri);
    }

    @Override
    public OutputStream openW() throws IOException {
        return resolver.openOutputStream(uri);
    }
}
