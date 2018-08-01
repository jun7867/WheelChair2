package com.example.mjkim.wheelchair2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mjkim.wheelchair2.NaverSearch.NaverLocationList;
import com.example.mjkim.wheelchair2.navermap.NMapCalloutCustomOldOverlay;
import com.example.mjkim.wheelchair2.navermap.NMapCalloutCustomOverlayView;
import com.example.mjkim.wheelchair2.navermap.NMapPOIflagType;
import com.example.mjkim.wheelchair2.navermap.NMapViewerResourceProvider;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutCustomOverlay;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import java.util.ArrayList;

/*
 - 앱 띄우는 데에는 NMapActivity를 상속받아 쓰면 된다.
 - 좌표지정과 마커사용에는 샘플파일 NMapCalloutCustomOldOverlay(밑에거 사용하는 클래스),
   NMapPOIflagType(마커관리, NMapViewerResourceProvider(아이콘, 사진 등 관리)
   들이 이용된다.
 */

public class NaverMapActivity extends NMapActivity {

    //상단 버튼들
    ImageButton back_button;
    ImageButton menu_button;

    ArrayList<NaverLocationList> mNaverLocationList;

    private final String TAG = "지도검색";

    private ViewGroup mapLayout;

    //맵 컨트롤러
    private NMapController mMapController;

    //네이버 지도 객체
    private NMapView mMapView;

    private NMapResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mapOverlayManager;

    //현재위치
    private NMapOverlayManager mOverlayManager;
    private MapContainerView mMapContainerView;

    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;

    private NGeoPoint mGeoPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_map);

        back_button = (ImageButton)findViewById(R.id.back_b);
        menu_button = (ImageButton)findViewById(R.id.menu_b);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openMenuTab();
            }
        });

        mapLayout = findViewById(R.id.mapLayout);

        //네이버지도 객체 생성
        mMapView = new NMapView(this);
        //객체에 키값 지정
        mMapView.setClientId(getResources().getString(R.string.NAVER_CLIENT_ID)); // 클라이언트 아이디 값 설정
        //지도 터치가능
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.setAutoRotateEnabled(true, false);
        //안하면 줌 한 다음 축소 시켜도 작게보임
        mMapView.setScalingFactor(1.7f);
        mMapView.requestFocus();

        mMapView.setOnMapStateChangeListener(changeListener);
        mMapView.setOnMapViewTouchEventListener(mapListener);
        mapLayout.addView(mMapView);

        //지도 객체로부터 컨트롤러 추출
        mMapController = mMapView.getMapController();

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mapOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        //내 주변으로 검색
        findNearLocation(mMapController);

//        startMyLocation();
//        mySetMarker();
//        setMarker();

    }


    //설정메뉴로 가기
    public void openMenuTab(){
        Intent intent2 = new Intent(this, MenuScreen.class);
        startActivity(intent2);
    }

    //내 주변으로 검색 기능 메소드
    //1. 내 위치 찾는다 - 되지만 반복해서 내 위치로 돌아감, 갱신을 없애야 함
    //2. 내 위치 주변 리뷰 쓰인 곳을 데이터베이스에서 NaverLocationList 형태로 가져와 어레이리스트에 담는다. - 해야함
    //3. 어레이리스트 안에 있는 장소들을 모두 마커를 달고 띄운다. - 아마 될 듯
    private void findNearLocation(NMapController mMapController) {
        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        // register callout overlay listener to customize it.
        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
        // register callout overlay view listener to customize it.
        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener);

        // location manager
        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        //mGeoPoint.set(mMapLocationManager.getMyLocation());

        // compass manager
        mMapCompassManager = new NMapCompassManager(this);

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

        mMapContainerView = new MapContainerView(this);


        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }
            //gps 권한있으면 실행
            if (mMapLocationManager.isMyLocationEnabled()) {

                if (!mMapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);

                    mMapCompassManager.enableCompass();

                    mMapView.setAutoRotateEnabled(true, false);

                    mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }
                mMapView.postInvalidate();

            //권한없으면 환경설정 들어감
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(NaverMapActivity.this, "환경설정에서 위치 정보 권한을 앱에 부여해주세요.",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }


        //마커다는 파트
        int markerId = NMapPOIflagType.PIN;
//        ArrayList<NMapPOIdata> locations = new ArrayList<NMapPOIdata>();

        // 리뷰단 장소들의 NaverLocationList 어레이리스트로 사용, db에서 받아와야함
        mNaverLocationList = new ArrayList<NaverLocationList>();


//         어레이리스트에 있는 정보로 마커 띄우기
//        NMapPOIdata poiData = new NMapPOIdata(mNaverLocationList.size(), mMapViewerResourceProvider);
//        poiData.beginPOIdata(mNaverLocationList.size());
//        for (int i = 0; i < mNaverLocationList.size(); i++) {
//            poiData.addPOIitem(mNaverLocationList.get(i).getMapx(), mNaverLocationList.get(i).getMapy(), mNaverLocationList.get(i).getName(), markerId, 0);
//        }
//        poiData.endPOIdata();
//        // create POI data overlay
//        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
//        poiDataOverlay.showAllPOIdata(0);
//        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);  //좌표 클릭시 말풍선 리스너
//        mMapController.setMapCenter(mMapLocationManager.getMyLocation(), 12);

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(129.398522, 36.082113, "하나로클럽 포항점", markerId, 0);
        poiData.addPOIitem(129.397848, 36.081324, "바벤", markerId, 0);
        poiData.endPOIdata();

//        NGeoPoint nGeoPoint = new NGeoPoint((36.082113 + 36.081324) / 2, (129.398522 + 129.397848) / 2);

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);  //좌표 클릭시 말풍선 리스너
//        mMapController.setMapCenter(mMapLocationManager.getMyLocation(), 12);
//        mMapController.setMapCenter(129.398185,36.0817185, 12);
        mMapController.setZoomLevel(12);
    }


    //마커다는 기본예제
    private void setMarker(){

        int markerId = NMapPOIflagType.PIN;
        ArrayList<NMapPOIdata> locations = new ArrayList<NMapPOIdata>();


        // set current POI data
//        NMapPOIdata currentPoiData = new NMapPOIdata(1, mMapViewerResourceProvider);
//        currentPoiData.beginPOIdata(1);
        //currentPoiData.addPOIitem()

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(127.0630205, 37.5091300, "말풍선 클릭시 뿅", markerId, 0);
        poiData.addPOIitem(127.061, 37.51, "네이버맵 입니다", markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);  //좌표 클릭시 말풍선 리스너
    }


    //내 위치 기반으로 (150m, 구현해야함) 미만의 리뷰가 등록된 곳에 마커가 뜬다.
    private void mySetMarker(){
        int markerId = NMapPOIflagType.PIN;
//        ArrayList<NMapPOIdata> locations = new ArrayList<NMapPOIdata>();

        // NaverLocationList 어레이리스트로 사용, db에서 받아와야함
        mNaverLocationList = new ArrayList<NaverLocationList>();

        // set current POI data
//        NMapPOIdata currentPoiData = new NMapPOIdata(1, mMapViewerResourceProvider);
//        currentPoiData.beginPOIdata(1);
//        currentPoiData.addPOIitem()


//         어레이리스트에 있는 정보로 마커 띄우기
//        NMapPOIdata poiData = new NMapPOIdata(mNaverLocationList.size(), mMapViewerResourceProvider);
//        poiData.beginPOIdata(mNaverLocationList.size());
//        for (int i = 0; i < mNaverLocationList.size(); i++) {
//            poiData.addPOIitem(mNaverLocationList.get(i).getMapx(), mNaverLocationList.get(i).getMapy(), mNaverLocationList.get(i).getName(), markerId, 0);
//        }
//        poiData.endPOIdata();
//        // create POI data overlay
//        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
//        poiDataOverlay.showAllPOIdata(0);
//        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);  //좌표 클릭시 말풍선 리스너


        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(129.398522, 36.082113, "하나로클럽 포항점", markerId, 0);
        poiData.addPOIitem(129.397848, 36.081324, "바벤", markerId, 0);
        poiData.endPOIdata();

//        NGeoPoint nGeoPoint = new NGeoPoint((36.082113 + 36.081324) / 2, (129.398522 + 129.397848) / 2);

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);  //좌표 클릭시 말풍선 리스너
        mMapController.setMapCenter(129.398185,36.0817185, 12);
    }

    private NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {
        @Override
        public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

        }

        @Override
        public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
            if (nMapPOIitem != null) {
                Log.e(TAG, "onFocusChanged: " + nMapPOIitem.toString());
            } else {
                Log.e(TAG, "onFocusChanged: ");
            }
        }
    };


    private NMapView.OnMapStateChangeListener changeListener = new NMapView.OnMapStateChangeListener() {
        @Override
        public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
            Log.e(TAG, "OnMapStateChangeListener onMapInitHandler : ");
        }

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
            Log.e(TAG, "OnMapStateChangeListener onMapCenterChange : " + nGeoPoint.getLatitude() + " ㅡ  " + nGeoPoint.getLongitude());
        }

        @Override
        public void onMapCenterChangeFine(NMapView nMapView) {
            Log.e(TAG, "OnMapStateChangeListener onMapCenterChangeFine : ");
        }

        @Override
        public void onZoomLevelChange(NMapView nMapView, int i) {
            Log.e(TAG, "OnMapStateChangeListener onZoomLevelChange : " + i);
        }

        @Override
        public void onAnimationStateChange(NMapView nMapView, int i, int i1) {
            Log.e(TAG, "OnMapStateChangeListener onAnimationStateChange : ");
        }
    };

    private NMapView.OnMapViewTouchEventListener mapListener = new NMapView.OnMapViewTouchEventListener() {
        @Override
        public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {
            Log.e(TAG, "OnMapViewTouchEventListener onLongPress : ");
        }

        @Override
        public void onLongPressCanceled(NMapView nMapView) {
            Log.e(TAG, "OnMapViewTouchEventListener onLongPressCanceled : ");
        }

        @Override
        public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {
            Log.e(TAG, "OnMapViewTouchEventListener onTouchDown : ");
        }

        @Override
        public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {
            Log.e(TAG, "OnMapViewTouchEventListener onTouchUp : ");
        }

        @Override
        public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {
            Log.e(TAG, "OnMapViewTouchEventListener onScroll : ");
        }

        @Override
        public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {
            Log.e(TAG, "OnMapViewTouchEventListener onSingleTapUp : ");
        }
    };

    private void startMyLocation() {

        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        // register callout overlay listener to customize it.
        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
        // register callout overlay view listener to customize it.
        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener);

        // location manager
        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        // compass manager
        mMapCompassManager = new NMapCompassManager(this);

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

        mMapContainerView = new MapContainerView(this);


        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }
            //gps 권한있으면 실행
            if (mMapLocationManager.isMyLocationEnabled()) {

                if (!mMapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);

                    mMapCompassManager.enableCompass();

                    mMapView.setAutoRotateEnabled(true, false);

                    mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }

                mMapView.postInvalidate();
            //권한없으면 환경설정 들어감
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(NaverMapActivity.this, "환경설정에서 위치 정보 권한을 앱에 부여해주세요.",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }
    }

    private void stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();

            if (mMapView.isAutoRotateEnabled()) {
                mMyLocationOverlay.setCompassHeadingVisible(false);

                mMapCompassManager.disableCompass();

                mMapView.setAutoRotateEnabled(false, false);

                mMapContainerView.requestLayout();
            }
        }
    }


    //이미지버튼 누르면 현재위치로 이동하는 이벤트
    public void gpsClick(View v) {
//        if (mMapController != null) {
//            mMapController.animateTo(myLocation);
//        }


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startMyLocation();
//            }
//        }, 100);

        startMyLocation();


//        // create overlay manager
//        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
//        // register callout overlay listener to customize it.
//        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
//        // register callout overlay view listener to customize it.
//        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener);
//
//        // location manager
//        mMapLocationManager = new NMapLocationManager(this);
//        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
//
//        // get geo point of current position
//        mGeoPoint = new NGeoPoint();
//        mGeoPoint.set(mMapLocationManager.getMyLocation());
//
//        // compass manager
//        mMapCompassManager = new NMapCompassManager(this);
//
//        // create my location overlay
//        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);
//
//
//        if (mMyLocationOverlay != null) {
//            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
//                mOverlayManager.addOverlay(mMyLocationOverlay);
//            }
//
//            if (mMapLocationManager.isMyLocationEnabled()) {
//
//                if (!mMapView.isAutoRotateEnabled()) {
//                    mMyLocationOverlay.setCompassHeadingVisible(true);
//
//                    mMapCompassManager.enableCompass();
//
//                    mMapView.setAutoRotateEnabled(true, false);
//
//                    mMapContainerView.requestLayout();
//                } else {
//                    stopMyLocation();
//                }
//
//                mMapView.postInvalidate();
//            } else {
//                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
//                if (!isMyLocationEnabled) {
//                    Toast.makeText(NaverMapActivity.this, "환경설정에서 위치 정보 권한을 앱에 부여해주세요.",
//                            Toast.LENGTH_LONG).show();
//
//                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(goToSettings);
//
//                    return;
//                }
//            }
//        }

    }

    /**
     * Container view class to rotate map view.
     */
    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }

            if (changed) {
                mOverlayManager.onSizeChanged(width, height);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);

                if (view instanceof NMapView) {
                    if (mMapView.isAutoRotateEnabled()) {
                        int diag = (((int)(Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
                        sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    private final NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener = new NMapOverlayManager.OnCalloutOverlayListener() {

        @Override
        public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem,
                                                         Rect itemBounds) {

            // handle overlapped items
            if (itemOverlay instanceof NMapPOIdataOverlay) {
                NMapPOIdataOverlay poiDataOverlay = (NMapPOIdataOverlay)itemOverlay;

                // check if it is selected by touch event
                if (!poiDataOverlay.isFocusedBySelectItem()) {
                    int countOfOverlappedItems = 1;

                    NMapPOIdata poiData = poiDataOverlay.getPOIdata();
                    for (int i = 0; i < poiData.count(); i++) {
                        NMapPOIitem poiItem = poiData.getPOIitem(i);

                        // skip selected item
                        if (poiItem == overlayItem) {
                            continue;
                        }

                        // check if overlapped or not
                        if (Rect.intersects(poiItem.getBoundsInScreen(), overlayItem.getBoundsInScreen())) {
                            countOfOverlappedItems++;
                        }
                    }

                    if (countOfOverlappedItems > 1) {
                        String text = countOfOverlappedItems + " overlapped items for " + overlayItem.getTitle();
                        Toast.makeText(NaverMapActivity.this, text, Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }

            // use custom old callout overlay
            if (overlayItem instanceof NMapPOIitem) {
                NMapPOIitem poiItem = (NMapPOIitem)overlayItem;

                if (poiItem.showRightButton()) {
                    return new NMapCalloutCustomOldOverlay(itemOverlay, overlayItem, itemBounds,
                            (NMapCalloutCustomOldOverlay.ResourceProvider) mMapViewerResourceProvider);
                }
            }

            // use custom callout overlay
            return new NMapCalloutCustomOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider);

//            // set basic callout overlay
//            return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
        }

    };

    /* MyLocation Listener */
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            if (mMapController != null) {
                mMapController.animateTo(myLocation);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

             //stop location updating
//            			Runnable runnable = new Runnable() {
//            				public void run() {
//            					stopMyLocation();
//            				}
//            			};
//            			runnable.run();

            Toast.makeText(NaverMapActivity.this, "현재 위치를 일시적으로 확인할 수 없습니다.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

            Toast.makeText(NaverMapActivity.this, "현재 위치가 탐지되지 않는 지역입니다.", Toast.LENGTH_LONG).show();

            stopMyLocation();
        }

    };

    private final NMapOverlayManager.OnCalloutOverlayViewListener onCalloutOverlayViewListener = new NMapOverlayManager.OnCalloutOverlayViewListener() {

        @Override
        public View onCreateCalloutOverlayView(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {

            if (overlayItem != null) {
                // [TEST] 말풍선 오버레이를 뷰로 설정함
                String title = overlayItem.getTitle();
                if (title != null && title.length() > 5) {
                    return new NMapCalloutCustomOverlayView(NaverMapActivity.this, itemOverlay, overlayItem, itemBounds);
                }
            }

            // null을 반환하면 말풍선 오버레이를 표시하지 않음
            return null;
        }

    };
}
