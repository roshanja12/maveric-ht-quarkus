package org.maveric.quarkus.panache.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingAccountServices {

    //Log.info("Error : " + e.getMessage());

//   public Map<String, Object> getCaseDetailsBasedOnPage(Integer page, Integer size, String sortBy, String order, String findBy, String search) throws CaseDetailsNotFoundException {
//
//        Gson gson = new Gson();
//
//        try {
//
//            logger.info("CaseDetailController :: updateCaseDetails()   call stated ::  " + LocalDateTime.now());
//
//            Pageable paging;
//
//            if (order.equals(CaseDetailsConstant.ASC)) {
//                paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
//            } else {
//                paging = PageRequest.of(page, size, Sort.by(sortBy).descending());
//            }
//
//            Page<CaseDetails> pages;
//            if (findBy.equalsIgnoreCase(CaseDetailsConstant.CASEID)) {
//                pages = caseDetailsRepository.findByCaseIdContaining(search, paging);
//            } else {
//                pages = caseDetailsRepository.findAll(paging);
//            }
//
//
//            List<CaseDetails> caseDetailsList = pages.getContent();
//
//            if (caseDetailsList.size() == 0) {
//                logger.info("No Case Detail Found On Db");
//                throw new CaseDetailsNotFoundException("No Data Found");
//            }
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("caseDetails", caseDetailsList);
//            response.put("currentPage", pages.getNumber());
//            response.put("totalItems", pages.getTotalElements());
//            response.put("totalPages", pages.getTotalPages());
//
//            logger.info("CaseDetailController :: updateCaseDetails()   call stated :: " + LocalDateTime.now());
//            logger.info("Response data :: " + gson.toJson(response.toString()));
//
//            return response;
//
//        } catch (Exception e) {
//            logger.error("Error Occurred while in getting case details data from db  Exception :: " + e.getMessage());
//            throw e;
//        }
//
//    }


}
