

package org.imogene.web.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface ImogConstants extends Constants {

	/* ------------------------------------------------------------------- */
	/*                         COMMON TEXTS                                */
	/* ------------------------------------------------------------------- */
	
	String connexion_error();
	String warning_form_in_edition();

	/* Bottom links */
	String bottom_link_legal();
	String bottom_link_copyrights();
	String bottom_link_contact();
	
	/* Boolean texts */
	String boolean_true();
	String boolean_false();
	String boolean_unknown();
	
	/* Enumeration texts */
	String enumeration_unknown();
	
	/* QRCode text */
	String qrcode();	
	
	/* Date text */
	String format_date();
	String format_time();
	String format_validation_date();
	String format_validation_time();
	
	/* Error messages */
	String field_mandatory();
	String field_correct_email();
	String field_correct_integer();
	String field_correct_float();
	String thema_default();
	
	/* form messages */
	String form_saving();
	String form_uploading();
	String form_saved();
	String form_not_validated();
	String form_loading();
	String form_metadata_title();
	
	/* form buttons */
	String button_save();
	String button_edit();
	String button_cancel();
	String button_close();
	String button_print();
	String button_delete();
	String button_create();
	String button_ok();
	String button_assign();
	String button_add();
	String button_remove();
	String button_view();
	String button_search();
	String button_export();
	String button_download();
	String button_list();
	String button_home();
	String button_filter();
	String label_filter();
	String label_filtered();
	String button_plus();
	String button_configure();
	String button_generate();
	
	/* login panel */
	String login_login();
	String login_password();
	String login_button();
	String login_description();
	String login_identify();
	String login_wrong_id();
	String login_expired();
	
	/* top panel */
	String disconnect();
	
	/* list */
	String entity_card_number_txt();
	
	/* binary field */
	String binary_nofile();
	String binary_select();
	
	/* confirmation texts */
	String confirmation_delete();
	String confirmation_delete_several1();
	String confirmation_delete_several2();
	String confirmation_save();
	
	/* search operators */
	String search_operator_string_equal();
	String search_operator_startwith();
	String search_operator_contains();
	String search_operator_date_equal();
	String search_operator_date_between();
	String search_date_label_and();
	String search_operator_before();
	String search_operator_after();
	String search_operator_numeric_equal();
	String search_operator_inferior();
	String search_operator_superior();
	String search_operator_isnull();
	String search_not_searched();
	String search_operator_contains_all();
	String search_operator_contains_one();
	
	/* search panel */
	String search_group();
	String field_creationDate();
	String field_lastModificationDate();
	String field_id();
	
	/* gps field */
	String gps_longiture();
	String gps_latitude();
	
	/* pager */
	String pager_of();
}
