package org.health.supplychain.service;


import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.SyncMetadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Aworkneh on 6/19/2018.
 */

public class SyncMetadataService implements ISyncMetadataService {
//    @Override
//    public SyncMetadata getSyncMetadataByEntityNameAndOperationType(String entityName, Integer syncOperationType) {
//        String whereClause = "entity_name = ?" +
//                " AND sync_operation_type = ?";
//        String[] whereArgs = {entityName, String.valueOf(syncOperationType)};
//
//        List<SyncMetadata> syncMetadataList = SyncMetadata.find(SyncMetadata.class,
//                whereClause, whereArgs, null, "id desc", null);
//        return (syncMetadataList != null && !syncMetadataList.isEmpty())?syncMetadataList.get(0): null;
//    }
//
//    @Override
//    public void hasUnuploadedRecord(String entityName) {
//        String whereClause = "entity_name = ?" +
//                " AND sync_operation_type = ?";
//        String[] whereArgs = {entityName, String.valueOf(Constants.SYNC_OPERATION_UPLOAD)};
//
//        List<SyncMetadata> syncMetadataList = SyncMetadata.find(SyncMetadata.class,
//                whereClause, whereArgs, null, "id desc", null);
//        if(syncMetadataList != null && !syncMetadataList.isEmpty()){
//            SyncMetadata metadata = syncMetadataList.get(0);
//            metadata.setHasUnuploadedRecord(Constants.HAS_UNSYNCED_RECORD);
//            metadata.setOperationStatus(Constants.SYNC_FAILURE);
//            metadata.setUpdatedDate((new Date()).getTime());
//            metadata.save();
//        } else {
//            SyncMetadata metadata = new SyncMetadata();
//            metadata.setEntityName(entityName);
//            metadata.setSyncOperationType(Constants.SYNC_OPERATION_UPLOAD);
//            metadata.setOperationStatus(Constants.SYNC_FAILURE);
//            metadata.setHasUnuploadedRecord(Constants.HAS_UNSYNCED_RECORD);
//            metadata.setCreatedDate((new Date()).getTime());
//            metadata.save();
//        }
//
//    }
//
//    @Override
//    public List<String> getOutdatedEntityNameList() {
//        List<String> outdatedEntityNameList = null;
//
//        List<SyncMetadata> syncMetadataList = SyncMetadata.find(SyncMetadata.class,
//                null, null, null, "id desc", null);
//
//        if(syncMetadataList != null && !syncMetadataList.isEmpty()){
//            long dateRange;
//            outdatedEntityNameList = new ArrayList<>();
//            for(SyncMetadata syncMetadata: syncMetadataList){
//                dateRange = (new Date()).getTime() - syncMetadata.getLastOperationDate();
//                if(dateRange > Constants.ONE_DAY_IN_MILISECOND * Constants.SYNC_TOLERANCE_DATE
//                        || syncMetadata.getOperationStatus() == Constants.OPERATION_FAILURE)
//                    outdatedEntityNameList.add(syncMetadata.getEntityName());
//            }
//        }
//
//        return outdatedEntityNameList;
//    }
//
//    public List<SyncMetadata> getAllSyncMetadata(){
//        List<SyncMetadata> syncMetadataList = SyncMetadata.find(SyncMetadata.class,
//                null, null, null, "id desc", null);
//        return syncMetadataList;
//    }
//
//
//    @Override
//    public boolean groupHasUnsyncedData(int groupId){
//        String whereClause = " sync_group_code = ?" +
//                " AND operation_status != ?";
//
//        String[] whereArgs = {String.valueOf(groupId), String.valueOf(Constants.SYNC_SUCCESS)};
//
//        List<SyncMetadata> syncMetadataList = SyncMetadata.find(SyncMetadata.class,
//                null, null, null, "id desc", null);
//
//        if(syncMetadataList != null && !syncMetadataList.isEmpty()){
//            boolean hasUnsyncedData = true;
//            long dateRange;
//            for(SyncMetadata syncMetadata: syncMetadataList){
//
//                if(syncMetadata.getSyncOperationType() == Constants.SYNC_OPERATION_DOWNLOAD) {
//                    dateRange = (new Date()).getTime() - syncMetadata.getLastOperationDate();
//                    if (dateRange > Constants.ONE_DAY_IN_MILISECOND * Constants.SYNC_TOLERANCE_DATE)
//                        hasUnsyncedData &= true;
//                } else if(syncMetadata.getSyncOperationType() == Constants.SYNC_OPERATION_UPLOAD
//                        && (syncMetadata.getHasUnuploadedRecord() == Constants.HAS_UNSYNCED_RECORD
//                        || syncMetadata.getNoDataToUpload() != Constants.NO_DATA_TO_UPLOAD)) {
//                    hasUnsyncedData &= true;
//                } else {
//                    hasUnsyncedData &= false;
//                }
//            }
//
//            return hasUnsyncedData;
//        }
//
//        return false;
//    }
}
