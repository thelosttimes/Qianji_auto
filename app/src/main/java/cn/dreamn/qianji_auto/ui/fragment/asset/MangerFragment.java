/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.dreamn.qianji_auto.ui.fragment.asset;

import android.os.Handler;
import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.popupwindow.bar.CookieBar;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import cn.dreamn.qianji_auto.R;
import cn.dreamn.qianji_auto.core.db.Table.Asset2;
import cn.dreamn.qianji_auto.core.db.Helper.Assets;
import cn.dreamn.qianji_auto.ui.adapter.AssetAdapter;
import cn.dreamn.qianji_auto.ui.fragment.StateFragment;

import static cn.dreamn.qianji_auto.ui.adapter.AssetAdapter.KEY_ID;
import static cn.dreamn.qianji_auto.ui.adapter.AssetAdapter.KEY_TITLE;


@Page(name = "钱迹资产")
public class MangerFragment extends StateFragment {

    @BindView(R.id.map_layout)
    SwipeRefreshLayout map_layout;
    @BindView(R.id.recycler_view)
    SwipeRecyclerView recyclerView;
    private AssetAdapter mAdapter;

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

        showLoading("加载中...");

        WidgetUtils.initRecyclerView(recyclerView);
        mAdapter = new AssetAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(item -> new MaterialDialog.Builder(getContext())
                .title(R.string.tip_options)
                .items(R.array.menu_values)
                .itemsCallback((dialog, itemView, position, text) -> {
                    int id = Integer.parseInt(Objects.requireNonNull(item.get(KEY_ID)));
                    if (position == 0) {
                        Assets.delAsset(id);
                        refresh();
                    } else {
                        showInputDialog(getString(R.string.asset_change), getString(R.string.asset_change_sub), item.get(KEY_TITLE), (str) -> {
                            Assets.updAsset(id, str);
                            SnackbarUtils.Long(getView(), getString(R.string.set_success)).info().show();
                            refresh();
                        });
                    }

                })
                .show());
        //下拉刷新
        map_layout.setOnRefreshListener(this::loadData);
        refresh(); //第一次进入触发自动刷新，演示效果
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();

        titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_add) {
            @Override
            public void performAction(View view) {

                showInputDialog("请输入资产名称", "钱迹中的资产名称", "", str -> {
                    Assets.addAsset(str);
                    refresh();
                });

            }
        });

        return titleBar;


    }


    private void loadData() {
        new Handler().postDelayed(() -> {
            //showLoading("正在加载资产");
            Asset2[] asset2s = Assets.getAllAccount();
            List<Map<String, String>> data = new ArrayList<>();
            for (Asset2 asset2 : asset2s) {
                Map<String, String> item = new HashMap<>();
                item.put(KEY_TITLE, asset2.name);
                item.put(KEY_ID, String.valueOf(asset2.id));
                data.add(item);
            }
            if (data.size() == 0) {
                showEmpty("没有资产信息");
                return;
            }

            mAdapter.refresh(data);
            if (map_layout != null) {
                map_layout.setRefreshing(false);
            }
            showContent();
        }, 1000);
    }

    @Override
    protected void initListeners() {

    }

    private void refresh() {
        map_layout.setRefreshing(true);
        loadData();
    }

    public void showInputDialog(String title, String tip, String def, CallBack callBack) {
        new MaterialDialog.Builder(getContext())
                .title(title)
                .content(tip)
                .input(
                        getString(R.string.input_tip),
                        def,
                        false,
                        ((dialog, input) -> {
                        })
                )
                .positiveText(getString(R.string.input_ok))
                .negativeText(getString(R.string.set_cancel))
                .onPositive((dialog, which) -> callBack.onResponse(dialog.getInputEditText().getText().toString()))
                .show();
    }

    // 回调接口
    public interface CallBack {
        void onResponse(String data);
    }

}
