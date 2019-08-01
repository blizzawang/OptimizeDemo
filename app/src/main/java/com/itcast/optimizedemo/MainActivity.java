package com.itcast.optimizedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.itcast.optimizedemo.overlayutil.DrivingRouteOverlay;
import com.itcast.optimizedemo.overlayutil.PoiOverlay;
import com.itcast.optimizedemo.overlayutil.TransitRouteOverlay;
import com.itcast.optimizedemo.overlayutil.WalkingRouteOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextureMapView mapView;
    private BaiduMap baiduMap;
    private PoiSearch mPoiSearch;
    private BusLineSearch mBusLineSearch;
    private RoutePlanSearch mSearch;
    private GeoCoder mGeoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();

        //获得地图控制器
        baiduMap = mapView.getMap();
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//卫星图层
//        baiduMap.setTrafficEnabled(true);//交通图层
//        baiduMap.setBaiduHeatMapEnabled(true);//热力图


//        LatLng point = new LatLng(39.963175, 116.400244);
//        BitmapDescriptor mit = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
//        OverlayOptions options = new MarkerOptions().position(point).icon(mit);
//        baiduMap.addOverlay(options);

//        LatLng pt1 = new LatLng(39.93923, 116.357428);
//        LatLng pt2 = new LatLng(39.91923, 116.327428);
//        LatLng pt3 = new LatLng(39.89923, 116.347428);
//        List<LatLng> pts = new ArrayList<>();
//        pts.add(pt1);
//        pts.add(pt2);
//        pts.add(pt3);
//        OverlayOptions polygonOption = new PolygonOptions().points(pts).stroke(new Stroke(3, 0xAA00FF00)).fillColor(0xAAFFFF00);
//        baiduMap.addOverlay(polygonOption);

//        LatLng llText = new LatLng(39.86923, 116.397428);
//        OverlayOptions textOption = new TextOptions()
//                .bgColor(0xAAFFFF00)
//                .fontSize(40)
//                .fontColor(0xFFFF00FF)
//                .text("百度地图SDK")
//                .rotate(-30)
//                .position(llText);
//        baiduMap.addOverlay(textOption);

//        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
//                OverlayOptions options = new MarkerOptions().position(latLng).icon(descriptor);
//                baiduMap.addOverlay(options);
//            }
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                return false;
//            }
//        });
//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Toast.makeText(getApplicationContext(), marker.getPosition().toString(), Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

        mPoiSearch = PoiSearch.newInstance();
        mBusLineSearch = BusLineSearch.newInstance();
        mSearch = RoutePlanSearch.newInstance();
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                Log.d("MainActivityClass", geoCodeResult.getLocation().toString());
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Log.d("MainActivityClass", reverseGeoCodeResult.getAddress());
            }
        });
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    TransitRouteOverlay overlay = new MyTransitRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(transitRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
        mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                if (busLineResult == null || busLineResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
                List<BusLineResult.BusStation> stations = busLineResult.getStations();
                for (BusLineResult.BusStation station : stations) {
                    Log.e("MainActivityClass", station.getTitle());
                }
            }
        });
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
//                List<PoiInfo> allPoi = poiResult.getAllPoi();
//                for (PoiInfo poiInfo : allPoi) {
////                    Log.d("MainActivityClass", poiInfo.name.toString());
//                    BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
//                    OverlayOptions options = new MarkerOptions().position(poiInfo.location).icon(descriptor);
//                    baiduMap.addOverlay(options);
//                }
//                baiduMap.clear();//清空地图
//                PoiOverlay overlay = new MyOverlay(baiduMap);
//                overlay.setData(poiResult);
//                overlay.addToMap();
//                baiduMap.setOnMarkerClickListener(overlay);

                if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }

                List<PoiInfo> poiInfos = poiResult.getAllPoi();
                mBusLineSearch.searchBusLine(new BusLineSearchOption()
                        .city("南昌")
                        .uid(poiInfos.get(0).getUid()));
//                for(PoiInfo info : poiInfos){
//                    Log.d("TAGGAT", info.getPoiDetailInfo().getTag());
//                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//                Log.d("MainActivityClass", poiDetailResult.getPrice() + "");
//                Log.d("MainActivityClass", poiDetailResult.getTelephone());
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //起点
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
        //终点
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");
        switch (item.getItemId()) {
            case R.id.city_search:
//                mPoiSearch.searchInCity(new PoiCitySearchOption()
//                        .city("北京")
//                        .keyword("餐厅")
//                        .pageNum(1)
//                        .pageCapacity(10));

                mPoiSearch.searchInCity(new PoiCitySearchOption()
                        .keyword("201")   //公交路线
                        .city("南昌")
                        .scope(2));
                break;
//            case R.id.nearby_search:
//                LatLng pt = new LatLng(39.93923, 116.357428);
//                mPoiSearch.searchNearby(new PoiNearbySearchOption()
//                        .radius(10000)
//                        .pageCapacity(10)
//                        .pageNum(1)
//                        .location(pt)
//                        .keyword("ATM"));
//                break;
//            default:
//                break;
            case R.id.bus:
                mSearch.transitSearch(new TransitRoutePlanOption()
                        .city("北京")
                        .from(stNode)
                        .to(enNode));
                break;
            case R.id.walking:
                mSearch.drivingSearch(new DrivingRoutePlanOption()
                        .from(stNode)
                        .to(enNode));
                break;
            case R.id.driver:
                mSearch.walkingSearch(new WalkingRoutePlanOption()
                        .from(stNode)
                        .to(enNode));
                break;

            case R.id.geo:
                mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(39.946758, 116.423134)));
                break;
            case R.id.reverse:
                mGeoCoder.geocode(new GeoCodeOption()
                        .address("百度园").city("北京市东城区交道口东大街4-8"));
                break;
        }
        return true;
    }

    private void initView() {
        mapView = (TextureMapView) findViewById(R.id.mapView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
//        mPoiSearch.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

//    class MyOverlay extends PoiOverlay {
//
//        /**
//         * 构造函数
//         *
//         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
//         */
//        public MyOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public boolean onPoiClick(int i) {
//            List<PoiInfo> allPoi = getPoiResult().getAllPoi();
//            PoiInfo info = allPoi.get(i);
//            if (info.hasCaterDetails) {//判断是否有详情
//                mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(info.getUid()));
//            }
//            return super.onPoiClick(i);
//        }
//    }

    class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onRouteNodeClick(int i) {
            return super.onRouteNodeClick(i);
        }
    }

    class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onRouteNodeClick(int i) {
            return super.onRouteNodeClick(i);
        }
    }

    class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onRouteNodeClick(int i) {
            return super.onRouteNodeClick(i);
        }
    }
}
