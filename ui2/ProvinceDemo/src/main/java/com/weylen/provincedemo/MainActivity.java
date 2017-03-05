package com.weylen.provincedemo;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView provinceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provinceListView = (ExpandableListView) findViewById(R.id.provinceList);

        List<String> provinceData = new ArrayList<>();
        provinceData.add("四川");
        provinceData.add("北京");
        provinceData.add("重庆");
        provinceData.add("湖南");
        provinceData.add("山东");

        List<List<String>> cityData = new ArrayList<>();
        List<String> cityChildData = new ArrayList<>();
        cityChildData.add("成都");
        cityChildData.add("德阳");
        cityChildData.add("绵阳");
        cityChildData.add("乐山");
        cityChildData.add("宜宾");
        cityData.add(cityChildData);
        cityChildData = new ArrayList<>();
        cityChildData.add("北京");
        cityData.add(cityChildData);
        cityChildData = new ArrayList<>();
        cityChildData.add("重庆");
        cityData.add(cityChildData);
        cityChildData = new ArrayList<>();
        cityChildData.add("长沙");
        cityData.add(cityChildData);
        cityChildData = new ArrayList<>();
        cityChildData.add("济南");
        cityChildData.add("青岛");
        cityData.add(cityChildData);

        List<List<List<String>>> quData = new ArrayList<>();
        List<List<String>> quCityData = new ArrayList<>();
        List<String> quChildData = new ArrayList<>();
        quChildData.add("金牛区");
        quChildData.add("青羊区");
        quChildData.add("武侯区");
        quChildData.add("高新区");
        quChildData.add("成华区");
        quCityData.add(quChildData);
        quChildData = new ArrayList<>();
        quChildData.add("1区");
        quChildData.add("2区");
        quChildData.add("3区");
        quChildData.add("4区");
        quCityData.add(quChildData);
        quChildData = new ArrayList<>();
        quChildData.add("1区");
        quChildData.add("2区");
        quChildData.add("3区");
        quChildData.add("4区");
        quCityData.add(quChildData);
        quChildData = new ArrayList<>();
        quChildData.add("1区");
        quChildData.add("2区");
        quChildData.add("3区");
        quChildData.add("4区");
        quCityData.add(quChildData);
        quChildData = new ArrayList<>();
        quChildData.add("1区");
        quChildData.add("2区");
        quChildData.add("3区");
        quChildData.add("4区");
        quCityData.add(quChildData);
        quData.add(quCityData);

        quCityData = new ArrayList<>();
        quChildData = new ArrayList<>();
        quChildData.add("朝阳区");
        quChildData.add("东城区");
        quCityData.add(quChildData);
        quData.add(quCityData);

        quCityData = new ArrayList<>();
        quChildData = new ArrayList<>();
        quChildData.add("菜园坝");
        quChildData.add("重庆北站");
        quCityData.add(quChildData);
        quData.add(quCityData);

        quCityData = new ArrayList<>();
        quChildData = new ArrayList<>();
        quChildData.add("长沙1");
        quChildData.add("长沙2");
        quCityData.add(quChildData);
        quData.add(quCityData);

        quCityData = new ArrayList<>();
        quChildData = new ArrayList<>();
        quChildData.add("济南1");
        quChildData.add("济南2");
        quChildData.add("济南3");
        quCityData.add(quChildData);

        quChildData = new ArrayList<>();
        quChildData.add("青岛1");
        quChildData.add("青岛2");
        quChildData.add("青岛3");
        quCityData.add(quChildData);
        quData.add(quCityData);

        ProvinceAdapter adapter = new ProvinceAdapter(this, provinceData, cityData, quData);
        provinceListView.setAdapter(adapter);
    }
}
