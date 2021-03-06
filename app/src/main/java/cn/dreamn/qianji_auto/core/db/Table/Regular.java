/*
 * Copyright (C) 2021 dreamn(dream@dreamn.cn)
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

package cn.dreamn.qianji_auto.core.db.Table;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Regular {
    //自动分类规则
    @PrimaryKey(autoGenerate = true)
    public int id;
    /**
     * 解析字段 商户title  备注信息 sub
     * if(title.contents("123"))//标题 contents 、not contents、indexof、endof、regular（匹配到）
     * if(sub.contents("123"))//副标题
     * if(time>200 && time <100)//时间 < 、>、=
     * return "okk"
     */
    public String regular;//翻译后的规则
    public String name;
    public String cate;
    public String tableList;
    public int use;//是否启用该规则
    public int sort = 0;//排序

}
