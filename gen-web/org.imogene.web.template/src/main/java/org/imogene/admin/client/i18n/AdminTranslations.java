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

	String imogActor_field_regionAppliUserFilterField();

	String imogActor_field_s_login();

	/* ImogRole texts */
	String imogRole_name();

	String imogRole_name_plur();

	String imogRole_create_title();

	String imogRole_select_title();

	String imogRole_table_text();

	String imogRole_excel_title();

	/* ImogRole field group texts */
	String imogRole_group_identification();

	/* ImogRole fields texts */
	String imogRole_field_name();

	String imogRole_field_s_name();

	/* SynchronizableEntity texts */
	String synchronizableEntity_name();

	String synchronizableEntity_name_plur();

	String synchronizableEntity_create_title();

	String synchronizableEntity_select_title();

	String synchronizableEntity_table_text();

	String synchronizableEntity_excel_title();

	/* SynchronizableEntity field group texts */
	String synchronizableEntity_group_identification();

	/* SynchronizableEntity fields texts */
	String synchronizableEntity_field_name();

	String synchronizableEntity_field_s_name();

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

	String notification_field_roleRecipients();

	String notification_field_s_name();

	String notification_field_s_formType();

	String notification_field_s_method();

	String notification_field_s_operation();

}
