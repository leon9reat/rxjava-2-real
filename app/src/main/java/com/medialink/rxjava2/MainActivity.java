package com.medialink.rxjava2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medialink.rxjava2.model.Pimpinan;
import com.medialink.rxjava2.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.tv_empty_list)
    TextView tvEmptyList;
    @BindView(R.id.fab_main)
    FloatingActionButton fabMain;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private PimpinanViewModel mModel;
    private Unbinder unbinder;
    private MainAdapter mAdapter;
    private ArrayList<Pimpinan> mListPimpinan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = ViewModelProviders.of(this).get(PimpinanViewModel.class);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        mAdapter = new MainAdapter(getApplicationContext());

        mModel.loadPimpinan();
        mModel.getPimpinan().observe(this, new Observer<List<Pimpinan>>() {
            @Override
            public void onChanged(List<Pimpinan> pimpinans) {
                if (pimpinans.size() > 0) {
                    tvEmptyList.setVisibility(View.GONE);
                } else {
                    tvEmptyList.setVisibility(View.VISIBLE);
                }
                mListPimpinan.clear();
                mListPimpinan.addAll(pimpinans);

                mAdapter.setData(mListPimpinan);
            }
        });

        toolbar.setTitle("Pimpinan");
        setSupportActionBar(toolbar);

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(false, null, -1);
            }
        });


        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rvMain.setHasFixedSize(true);
        rvMain.setAdapter(mAdapter);

        rvMain.addOnItemTouchListener(new RecyclerTouchListener(this, rvMain, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "click biasa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionDialog(position);
            }
        }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void showInputDialog(final boolean shouldUpdate, final Pimpinan data, final int position) {
        Context context = getApplicationContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pimpinan_dialog, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setView(view);

        final EditText edNmPimpinan = view.findViewById(R.id.ed_nm_pimpinan);
        final EditText edJabatan = view.findViewById(R.id.ed_jabatan);
        TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(!shouldUpdate ? "Pimpinan Baru" : "Edit Pimpinan");

        if (shouldUpdate && data != null) {
            edNmPimpinan.setText(data.getNmPimpinan());
            edJabatan.setText(data.getJabatan());
        }

        alertBuilder.setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNmPimpinan.setError(null);
                if (TextUtils.isEmpty(edNmPimpinan.getText().toString())) {
                    edNmPimpinan.setError("Nama Pimpinan tidak boleh kosong");
                    return;
                }
                edJabatan.setError(null);
                if (TextUtils.isEmpty(edJabatan.getText().toString())) {
                    edJabatan.setError("Jabatan tidak boleh kosong");
                    return;
                }
                alertDialog.dismiss();

                if (shouldUpdate && data != null) {
                    Log.d(TAG, "onClick: update pimpinan");
                } else {
                    Log.d(TAG, "onClick: tambah pimpinan");
                }
            }
        });
    }

    private void showActionDialog(final int position) {
        CharSequence pilihan[] = new CharSequence[]{"Edit", "Hapus"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilihan");
        builder.setItems(pilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showInputDialog(true, mListPimpinan.get(position), position);
                } else {
                    Log.d(TAG, "onClick: hapus data " + position);
                }
            }
        });
        builder.show();
    }
}
