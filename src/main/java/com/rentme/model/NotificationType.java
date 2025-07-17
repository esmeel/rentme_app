package com.rentme.model;

public enum NotificationType {
  SYSTEM, // إشعار عام من النظام (صيانة، تحديث، إلخ)
  INFO, // معلومة عامة أو ترويجية
  RENTAL_REQUEST, // طلب استئجار جديد
  RENTAL_APPROVED, // تمت الموافقة على الطلب
  RENTAL_REJECTED, // تم رفض الطلب
  PAYMENT_SUCCESS, // تم الدفع بنجاح
  TOOL_RECEIVED, // تأكيد استلام الأداة
  CHAT_MESSAGE, OWNER_LOCATION_REQUEST, OWNER_TIME_REQUEST, OWNER_LOCATION_RESPONSE, OWNER_TIME_RESPONSE, LOCATION_SENT, SCHEDULE_REQUEST, SCHEDULE_PROPOSAL, SCHEDULE_CONFIRMED, FINAL_SCHEDULE_CONFIRMED, CONFIRMED_TOOL_RECEIVED, RETURN_CONFIRMATION_REQUEST, CoNFIRM_TOOL_RETURNED
}
