package org.imogene.admin.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface AdminTranslations extends Constants {

	/* ------------------------------------------------------------------- */
	/* ADMIN TEXTS */
	/* ------------------------------------------------------------------- */

	/* Languages texts */
	String locale();

	/* Enumeration texts */
	String enumeration_unknown();

	/* Thema texts */
	String thema_users();

	String thema_administration();
	
	String thema_model();

	/* Password message */
	String password_confirm_error();

	String login_without_password_error();

	/* Binary texts */
	String binary_name();

	/* Actor texts */
	String imogActor_name();

	String imogActor_name_plur();

	String imogActor_create_title();

	String imogActor_select_title();

	String imogActor_table_text();

	String imogActor_excel_title();

	/* Actor field group texts */
	String imogActor_group_administration();

	String imogActor_group_synchronization();

	String imogActor_group_filterFields();

	/* Actor fields texts */
	String imogActor_field_login();

	String imogActor_field_password();

	String imogActor_field_passwordConfirm();

	String imogActor_field_roleList();

	String imogActor_field_idFile();

	String imogActor_field_idFile_text();

	String imogActor_field_synchronizableList();

	String imogActor_field_s_login();

	/* SynchronizableEntity texts */
	String cardEntity_name();

	String cardEntity_name_plur();

	String cardEntity_create_title();

	String cardEntity_select_title();

	String cardEntity_table_text();

	String cardEntity_excel_title();

	/* SynchronizableEntity field group texts */
	String cardEntity_group_identification();

	/* SynchronizableEntity fields texts */
	String cardEntity_field_name();

	String cardEntity_field_s_name();

	/* Profile texts */
	String profile_name();

	String profile_name_plur();

	String profile_create_title();

	String profile_select_title();

	String profile_table_text();

	String profile_excel_title();

	/* Profile field group texts */
	String profile_group_description();

	/* Profile fields texts */
	String profile_field_name();

	String profile_field_entityProfiles();

	String profile_field_fieldGroupProfiles();

	String profile_field_s_name();

	/* EntityProfile texts */
	String entityProfile_name();

	String entityProfile_name_plur();

	String entityProfile_create_title();

	String entityProfile_select_title();

	String entityProfile_table_text();

	String entityProfile_excel_title();

	/* EntityProfile field group texts */
	String entityProfile_group_description();

	/* EntityProfile fields texts */
	String entityProfile_field_profile();

	String entityProfile_field_entity();

	String entityProfile_field_create();

	String entityProfile_field_directAccess();

	String entityProfile_field_delete();

	String entityProfile_field_export();

	String entityProfile_field_s_profile();

	String entityProfile_field_s_entity();

	String entityProfile_field_s_directAccess();

	String entityProfile_field_s_create();

	String entityProfile_field_s_delete();

	String entityProfile_field_s_export();

	/* FieldGroupProfile texts */
	String fieldGroupProfile_name();

	String fieldGroupProfile_name_plur();

	String fieldGroupProfile_create_title();

	String fieldGroupProfile_select_title();

	String fieldGroupProfile_table_text();

	String fieldGroupProfile_excel_title();

	/* FieldGroupProfile field group texts */
	String fieldGroupProfile_group_description();

	/* FieldGroupProfile fields texts */
	String fieldGroupProfile_field_profile();

	String fieldGroupProfile_field_cardEntity();

	String fieldGroupProfile_field_fieldGroup();

	String fieldGroupProfile_field_read();

	String fieldGroupProfile_field_write();

	String fieldGroupProfile_field_export();

	String fieldGroupProfile_field_s_profile();

	String fieldGroupProfile_field_s_fieldGroup();

	String fieldGroupProfile_field_s_read();

	String fieldGroupProfile_field_s_write();

	String fieldGroupProfile_field_s_export();

	/* FieldGroup texts */
	String fieldGroup_name();

	String fieldGroup_name_plur();

	String fieldGroup_create_title();

	String fieldGroup_select_title();

	String fieldGroup_table_text();

	String fieldGroup_excel_title();

	/* FieldGroup field group texts */
	String fieldGroup_group_description();

	/* FieldGroup fields texts */
	String fieldGroup_field_name();

	String fieldGroup_field_entity();

	String fieldGroup_field_s_name();

	String fieldGroup_field_s_entity();

	/* Notification texts */
	String notification_name();

	String notification_name_plur();

	String notification_create_title();

	String notification_select_title();

	String notification_table_text();

	String notification_excel_title();

	/* Notification field group texts */
	String notification_group_description();

	String notification_group_recipients();

	/* Notification fields texts */
	String notification_field_name();

	String notification_field_method();

	String notification_method_mail_option();

	String notification_method_sMS_option();

	String notification_field_title();

	String notification_field_formType();

	String notification_field_operation();

	String notification_operation_create_option();

	String notification_operation_update_option();

	String notification_operation_delete_option();

	String notification_field_message();

	String notification_field_actorRecipients();

	String notification_field_s_name();

	String notification_field_s_formType();

	String notification_field_s_method();

	String notification_field_s_operation();

}
