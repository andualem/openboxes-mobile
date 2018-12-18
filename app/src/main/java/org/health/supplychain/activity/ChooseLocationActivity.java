package org.health.supplychain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.health.supplychain.R;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IFacilityService;
import org.health.supplychain.tasks.ChooseLocationUploadTask;
import org.health.supplychain.tasks.LocationDownloadTask;
import org.health.supplychain.webservice.service.APIServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseLocationActivity extends AppCompatActivity {

    private Spinner spLocationList;
    private Button btnSaveChoice;
    private ArrayAdapter<String> dataAdapter;

    private IFacilityService facilityService;
    private List<Facility> facilityList;
    private List<String> facilityNameList;
    private Facility selectedFacility;
    private LocationDownloadTask locationDownloadTask;
    private ChooseLocationUploadTask chooseLocationUploadTask;
    private UserSessionService userSessionService;
    private UserSession userSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselocation_activity_layout);

        spLocationList = findViewById(R.id.spLocationList);
        btnSaveChoice = findViewById(R.id.btnSaveChoice);

        getFacilityList();
        if(facilityNameList != null) {
            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, R.id.text, facilityNameList);
            spLocationList.setAdapter(dataAdapter);
        }

        btnSaveChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFacility = facilityList.get(spLocationList.getSelectedItemPosition());
                userSessionService = new UserSessionService();
                userSession = userSessionService.getUserSessionDetail(ChooseLocationActivity.this);
                String sessionId = userSession.getSessionId();

                if(selectedFacility != null && sessionId != null){
                    chooseLocationUploadTask = new ChooseLocationUploadTask(sessionId, selectedFacility.getId());
                    chooseLocationUploadTask.execute();
                    userSessionService.updateLocationId(ChooseLocationActivity.this,
                            String.valueOf(selectedFacility.getId()));

                    //open Main menu
                    Intent intent = new Intent(getApplicationContext(), MenuGridActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void getFacilityList(){
        facilityService = new FacilityService();
        facilityList = facilityService.getAllFacilities();
        if(facilityList == null || facilityList.isEmpty()){
            //TODO: it is not showing freshly downloaded list of locations
            downloadLocation();
            facilityList = facilityService.getAllFacilities();
        }

        if(facilityList != null && !facilityList.isEmpty()){
            facilityNameList = new ArrayList<>();
            for(Facility facility: facilityList){
                facilityNameList.add(facility.getName());
            }

        }
    }

    private void downloadLocation(){
        userSessionService = new UserSessionService();
        userSession = userSessionService.getUserSessionDetail(ChooseLocationActivity.this);

        locationDownloadTask = new LocationDownloadTask(ChooseLocationActivity.this, null, null,
                userSession.getSessionId(), null);
        locationDownloadTask.execute();
    }

}
