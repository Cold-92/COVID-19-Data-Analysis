package org.cold92.handler;

import com.google.gson.Gson;
import org.cold92.bean.DataBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataHandler {

    public static List<DataBean> analysisJsonData() throws Exception {
        // 读取数据文件流
        FileReader reader = new FileReader(new File("originalData.txt"));
        char[] temp = new char[512];
        int length = 0;
        StringBuffer dataStr = new StringBuffer();
        Gson gson = new Gson();
        List<DataBean> beanList = new ArrayList<>();
        while ((length = reader.read(temp)) != -1) {
            dataStr.append(new String(temp, 0, length));
        }
        Map dataMap = gson.fromJson(dataStr.toString(), Map.class);
        ArrayList areaTree = (ArrayList) dataMap.get("areaTree");
        Map areaTreeElement = (Map) areaTree.get(0);
        ArrayList children = (ArrayList) areaTreeElement.get("children");
        for (int i = 0; i < children.size(); i++) {
            Map childrenMap = (Map) children.get(i);
            String name = (String) childrenMap.get("name");
            Map total = (Map) childrenMap.get("total");
            double nowConfirm = (Double) total.get("nowConfirm");
            double confirm = (Double) total.get("confirm");
            double suspect = (Double) total.get("suspect");
            double dead = (Double) total.get("dead");
            double heal = (Double) total.get("heal");
            DataBean bean = new DataBean();
            bean.setName(name);
            bean.setNowConfirm((int) nowConfirm);
            bean.setConfirm((int) confirm);
            bean.setSuspect((int) suspect);
            bean.setDead((int) dead);
            bean.setHeal((int) heal);
            beanList.add(bean);
        }
        return beanList;
    }
}
