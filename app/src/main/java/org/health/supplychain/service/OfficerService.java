package org.health.supplychain.service;


import java.util.Date;
import java.util.List;

/**
 * Created by Aworkneh on 5/9/2018.
 */

public class OfficerService implements IOfficerService {
//    @Override
//    public Officer getOfficerById(long systemId, long organizationId) {
//        String whereClause = "system_Id = ?" +
//                " and organization_Id = ?";
//        String[] whereArgs = {String.valueOf(systemId), String.valueOf(organizationId)};
//
//        List<Officer> officerList = Officer.find(Officer.class,
//                whereClause, whereArgs, null, "id desc", null);
//        if(officerList != null && !officerList.isEmpty())
//            return officerList.get(0);
//
//        return null;
//    }
//
//    @Override
//    public Officer getCashierDetail(long organizationId, String username, String encryptedPassword) {
//        String whereClause = "organization_Id = ?" +
//                " and Lower(username) = ?" +
//                " and encrypted_password = ?";
//        String[] whereArgs = {String.valueOf(organizationId),
//                username.toLowerCase(), String.valueOf(encryptedPassword)};
//
//        List<Officer> officerList = Officer.find(Officer.class,
//                whereClause, whereArgs, null, "id desc", null);
//
//        if(officerList != null && !officerList.isEmpty()){
//            for(Officer officer: officerList){
//                if(officer.getRoleList().contains(Constants.AUTHENTICATED_ROLE_CASHIER))
//                    return officer;
//                else
//                    continue;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean updateOfficer(Officer existing, Officer incoming) {
//        existing.setName(incoming.getName());
//        existing.setFatherName(incoming.getFatherName());
//        existing.setOrganizationId(incoming.getOrganizationId());
//        existing.setOrganizationName(incoming.getOrganizationName());
//        existing.setOrganizationType(incoming.getOrganizationType());
//        existing.setOrganizationCreditLogic(incoming.getOrganizationCreditLogic());
//        existing.setCashOnHandAccountId(incoming.getCashOnHandAccountId());
//        existing.setCashDifferenceAccountId(incoming.getCashDifferenceAccountId());
//        //TODO: updates on username and password needs to be managed with reset password
//        existing.setUsername(incoming.getUsername());
//        existing.setEncryptedPassword(incoming.getEncryptedPassword());
//        existing.setRoleList(incoming.getRoleList());
//        existing.setStatus(incoming.getStatus());
//        existing.setSyncStatus(Constants.SYNC_SUCCESS);
//        existing.setSyncDate(new Date().getTime());
//        existing.setCreatedDate(incoming.getCreatedDate());
//        existing.setUpdatedDate(incoming.getUpdatedDate());
//        existing.setDmlFlag(incoming.getDmlFlag());
//        return existing.save() > 0;
//    }
//
//    @Override
//    public boolean updatePassword(long systemId, String username, String newPassword) {
//        String whereClause = "system_Id = ?" +
//                " and LOWER(username) = ?";
//        String[] whereArgs = {String.valueOf(systemId), username.toLowerCase()};
//
//        List<Officer> officerList = Officer.find(Officer.class,
//                whereClause, whereArgs, null, "id desc", null);
//        if(officerList != null && !officerList.isEmpty()) {
//            Officer officer = officerList.get(0);
//            officer.setEncryptedPassword(newPassword);
//            return officer.save() > 0 ;
//        }
//
//
//        return false;
//    }
}
