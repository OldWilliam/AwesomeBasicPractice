package me.jim.wx.awesomebasicpractice.jetpack;

import android.app.Activity;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Date: 2019/8/1
 * Name: wx
 * Description: ViewModel初使用
 */
public class HelloViewModel extends ViewModel {
    public final ObservableField<String> name = new ObservableField<>("");
    private final TextWatcherAdapter textWatcher;

    public HelloViewModel(String name) {
        this.name.set(name);
        textWatcher = new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (!TextUtils.equals(s.toString(), HelloViewModel.this.name.get())) {
                    HelloViewModel.this.name.set(s.toString());
                }
            }
        };
    }

    public void onClickRead(View view) {
        doReadFile((Activity) view.getContext());
    }

    public void doReadFile(final Activity context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityCompat.requestPermissions(context, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 0);
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File testFile = new File(externalStorageDirectory.getAbsolutePath().concat("/Ingkee").concat("/OKioTest.txt"));
                if (!testFile.exists()) {
                    try {
                        //noinspection unused
                        boolean success = testFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Source source = null;
                try {
                    source = Okio.source(testFile);
                    String utf8Line = Okio.buffer(source).readUtf8Line();
                    name.set(utf8Line);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (source != null) {
                            source.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


    public void onClickWrite(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 0);
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File testFile = new File(externalStorageDirectory.getAbsolutePath().concat("/Ingkee").concat("/OKioTest.txt"));
                if (!testFile.exists()) {
                    try {
                        //noinspection unused
                        boolean success = testFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Sink sink = null;
                try {
                    sink = Okio.sink(testFile);
                    BufferedSink buffer = Okio.buffer(sink);
                    buffer.writeString(name.get(), Charset.defaultCharset());
                    buffer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (sink != null) {
                        try {
                            sink.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    public TextWatcherAdapter getTextWatcher() {
        return textWatcher;
    }


    public class TextWatcherAdapter implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
