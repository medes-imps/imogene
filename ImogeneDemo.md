

# Introduction #

> The Information System that is presented here is a demonstration Information System that has been generated thanks to the _**imogene**_ development studio.
> This generated system deals with the collection of epidemiological data. But it could have been any other kind of activity type since _**imogene**_ modeling capabilities are generic.
> The presented data model does not correspond to a real use case. The forms and fields have been defined to illustrate the different features that can be modeled thanks to _**imogene**_.


# Demonstration Information System #

> ## Architecture ##

> http://imogene.googlecode.com/svn/wiki/demo/demo_archi.PNG

> The demonstration IS illustrates the collection of data through Android mobile terminals and the bidirectional data synchronization between the mobile terminals and a central database.
> It includes:
    * **A Web application** to collect, view and analyze the data from anywhere as far as an Internet connection is available.
    * **An Android Mobile application** to collect and consult data in mobility situations. It works both in connected and disconnected mode.
    * **A Synchronization server** to synchronize the data between the mobile application and the Information System central database.

> ## Data Model ##

> The data model that has been defined to generate the demonstration IS can be downloaded [here](http://imogene.googlecode.com/svn/wiki/demo/ImogeneDemoModel.zip).
> The published applications directly result from the _**imogene**_ application generation process. No additional programming work has been done. This shows the set of features that are available as a basis within _**imogene**_.
> Of course some customization can be done on the applications. _**imogene**_ makes it easy to display a specific logo instead of the _**imogene**_ one. Colors and icons can be configured in the data model too. Finally, the generation process provides the source code of the applications, and from there, they can be customized.

> ## Access ##

> ### Web application ###

> The Web application is accessible at the URL: [http://demo.i-mogene.org](http://demo.i-mogene.org)

> ### Android application ###

> The Android application can be downloaded and installed from the terminal.
> In addition to the generated demonstration application, an additional mapping module can be installed on the terminal to be able to display the collected data on a map on the terminal.

> These applications are available at the following links:
| **Demo Application** | **Map module** | **Map provider** |
|:---------------------|:---------------|:-----------------|
| [ImogeneDemoAndroid](http://imogene.googlecode.com/svn/wiki/demo/ImogeneDemoAndroid.apk) | [org.imogene.map.apk](http://imogene.googlecode.com/svn/wiki/demo/org.imogene.map.apk) | [org.imogene.map.provider.osm.apk](http://imogene.googlecode.com/svn/wiki/demo/org.imogene.map.provider.osm.apk) |
| ![http://imogene.googlecode.com/svn/wiki/demo/ImogeneDemoAndroid.apk.png](http://imogene.googlecode.com/svn/wiki/demo/ImogeneDemoAndroid.apk.png) | ![http://imogene.googlecode.com/svn/wiki/demo/org.imogene.map.apk.png](http://imogene.googlecode.com/svn/wiki/demo/org.imogene.map.apk.png) | ![http://imogene.googlecode.com/svn/wiki/demo/org.imogene.map.provider.osm.apk.png](http://imogene.googlecode.com/svn/wiki/demo/org.imogene.map.provider.osm.apk.png) |

> ### Login/Password ###

> 3 different users with different user privileges have been defined in the system.

> #### System Administrator ####
    * login: sysadmin
    * password: sysadmin

> This type of user would be the system administrator.
> He would use only the web application for data management.
> He has access to all the data in read and write mode. He can build the lookup tables that the other users will use while collecting data.

> #### Investigator ####
    * login: investigator
    * password: investigator

> This type of user would be the staff that goes on the field to collect data in mobility situation.
> He would use the Android application when he is on the field of operation. He could use also the web application to check or complete data.
> He has access to all the data in read and write mode except for the lookup tables.

> #### Public User ####
    * login: readonly
    * password: readonly

> This type of user would be the persons who need to access the data to be able to perform analysis but who should not modify it.
> He would use mainly the web application for data access. He could also use the Android application to access the data in mobility situation.
> He has access to the data only in read mode and some confidential data are hidden to him.


# Demonstrated features #

> ### Field types ###

> The demonstration IS illustrates most of the field types that can be defined in the data model.
> Each field type has its specific default validation rules and present a specific adapted widget in the application user interfaces.
    * '**Text**' field: Enables to enter textual data (Example: 'Name' field of the 'Patient' form).
    * '**Integer**' field: Enables to enter integer numeric values (Example: 'Age' field of the 'Patient' form). If the entered value is not an integer, an error message is displayed. (A non illustrated Float field enables to manage float values)
    * '**Date**' field: Enables to enter dates (Example: 'Report Date' field of the 'Case Declaration' form). If the entered value is not a date, an error message is displayed. (A non illustrated Time field enables to manage time values)
    * '**List**' field with **one choice** only: Enables to select one value among a fixed list of values  (Example: 'Gender' field of the 'Patient' form).
    * '**List**' field with **multiple choices**: Enables to select one or several values among a fixed list of values  (Example: 'Symptoms' field of the 'Case Declaration' form).
    * '**Yes/No**' field: Enables to select a value between 'yes' or 'no' (Example: 'Treatment given' field of the 'Case Declaration' form).
    * '**Photo**' field: Enables to attach a photo to a form (Example: 'Photo' field of the 'Patient' form). Using the Android application, this type of field calls the terminal camera so that the user can take a picture that will be automatically attached to the form.
    * '**Video**' field: Enables to attach a video to a form (Example: 'Video' field of the 'Case Declaration' form). Using the Android application, this type of field calls the terminal camera so that the user can make a video that will be automatically attached to the form.
    * '**Sound**' field: Enables to attach an audio file to a form (Example: 'Audio comment' field of the 'Case Declaration' form). Using the Android application, this type of field calls the terminal sound recorder so that the user can record a sound that will be automatically attached to the form.
    * '**Geographic coordinates**' field: Enables to affect geographical coordinates to a form (Example: 'Geographic coordinates' field of the 'Case Declaration' form). Using the Android application, this type of field calls the terminal GPS receiver that will automatically capture and affect the terminal GPS position to the field.
    * '**Relation**' field: Enables to affect one or more entry of one form to another form (see the Sub form part).

> Are also illustrated an 'Address' field, a 'Phone' field and an 'Email' field.

> ### Sub forms ###

> Called 'Relation' fields in the modeling, they enable to affect one or more entry of one form to another form. This feature enables to define sophisticated and meaningful data models.
> The modeling process enables to define if the user can affect one entry of a sub form, or if he can affect several entries.
> _**imogene**_ applications enable an easy affectation of form entries and an easy navigation between the forms thanks to these 'Relation' fields.

> This kind of feature can be used to define different types of forms that are related to each other.
> In the demo application, this is illustrated by the 'Patient' and 'Case Declaration' forms.
> The 'Case Declaration' form references a patient though the 'Patient' field.
> The 'Patient' form lists all the 'Case Declaration' forms that have been created for a given patient through the 'Case Declarations' field.

> It can also be used to create lookup tables/dynamic lists.
> In the demo application, this is illustrated by the 'Country', 'Region' and 'Province' forms.
> This enables to avoid multiple entries of data that can be a source of error. Having a 'Province' form, for instance, enables that several forms references the same Province entry instead of typing several times the Province name.

> ### Standard and custom field validation ###

> Field validation can be managed at different levels during the modeling process.

> Choosing a field type is a first level of validation. An 'Integer' field for instance will force the user to enter an integer value into the field.
> Additional validation parameters can be entered for some field types. Minimum and maximum values can be defined for numeric and date fields for instance. This is illustrated by the 'Age' field of the 'Patient' form. The entered value must be between 0 and 20.

> A field can be defined as 'required'. In that case, the user will be able to save the form only if the required fields are filled in. Required fields are defined for all forms. Trying to save an empty form will generate an error message and highlight the fields that are required.

> Finally, very customized validation rules can be defined for 'Text' fields. This is illustrated by the 'Unique ID' field of the 'Patient' form whose value must be made of 2 letters and 2 digits.

> ### Default values ###

> Default values can be defined for some field types. This is illustrated by the 'Report date' field of the 'Case Declaration' form whose whose default value is the current date.

> ### Conditional field access ###

> The access to a field can be conditioned by the value of another field. This feature makes the forms easier to read and facilitates the navigation inside the form since it enables to hide the fields that are not relevant for a given entry.

> This feature is illustrated by the 'Multimedia data' field of the 'Case Declaration' form. If its value is set to 'No', no additional field is displayed. If set to 'Yes', 3 additional fields become visible in the form.
> It is also illustrated by the 'Symptoms' field of the 'Case Declaration' form. If the 'Other' value is selected, an additional text field is displayed so that the user can precise the name of the other symptom.

> ### Hierarchical lists ###

> Using 'Relation' fields as dynamic lists, it is possible to define that the entries available for selection of one list will depend on the selected value of another list.
> This feature is illustrated by the 'Country', 'Region' and 'Province' fields of the 'Case Declaration' form. The entries available for selection of the 'Region' list depend on the value that is selected in the 'Country' list. And the entries available for selection of the 'Province' list depend on the value that is selected in the 'Region' list.

> ### User privilege management ###

> Each user that will connect an application has its own login/password and its own affected privileges. These privileges define what forms he can create and access and what form fields he can read and write.

> This is illustrated by the 3 users types that can connect the demo applications:
    * The 'System administrator' user type has access to all the data in read and write mode. He can build the lookup tables (Country, Region, Province) that the other users will use while collecting data.
    * The 'Investigator' user type has access to all the data in read and write mode except for the lookup tables (Country, Region, Province). He can affect the entries of these look up tables to other forms but he can not create or update the entries.
    * The 'Public User' user type has access to the data only in read mode and some confidential data are hidden to him. This confidential data is all the data that concern the Patient. Thus, he has no access to the patient entries and within the 'Case Declaration' form, the 'Patient' field is not displayed.

> ### Internationalization ###

> The applications are internationalized. By default, the application displays the texts of the locale that is defined for the Web browser or the Android terminal. For the Web application, the locale can be changed in the login page. For the Android application, it can be changed in the terminal preferences.

> All the user interface texts are available in the languages that have been defined during the modeling process.
> In the modeling, the text fields can be set as translatable. This will enable the user to enter text in several languages. This is illustrated by the 'Name' field of the 'Country' form. The country names can be entered in the languages that have been defined during the modeling process.

> ### Photo/Video/Sound acquisition ###

  * 'Photo' field of the 'Patient' form. Using the Android application, this type of field calls the terminal camera so that the user can take a picture that will be automatically attached to the form.
  * 'Video' field of the 'Case Declaration' form. Using the Android application, this type of field calls the terminal camera so that the user can make a video that will be automatically attached to the form.
  * 'Audio comment' field of the 'Case Declaration' form. Using the Android application, this type of field calls the terminal sound recorder so that the user can record a sound that will be automatically attached to the form.

> ### Geographical coordinates acquisition ###

  * 'Geographic coordinates' field of the 'Case Declaration' form. Using the Android application, this type of field calls the terminal GPS receiver that will automatically capture and affect the terminal GPS position to the field.
> It also enables to display the georeferences entries on a map.

> ### Bidirectional data synchronization ###

> Using the demonstration Android application, the user can download entries from the central server to the mobile terminal. He can also create or update and entry on the terminal and upload it to the central server


# Not demonstrated features #

> ### Data filtering ###

> Imogene enables to define 2 types of data filtering:
    * One is defined by the system administrator. He will define what entry groups a given user will have access to or not (for instance, he could define that a given user has only access to the case declarations that are part of Province A and B).
    * One is defined by the user himself from the mobile terminal. Among the entries that he is allowed to access, he can choose to download on the terminal only a subset of these entries (He has acess to the case declarations of Province A and B, but he will define that he will only download on his mobile terminal the case declarations of Province A).


# Android application User guide #

> A document explaining how to use the Android application and how to synchronize data between the mobile terminal and the central server can be downloaded [here](http://code.google.com/p/imogene/downloads/detail?name=ImogeneAndroidApplicationUserGuide.pdf&can=2&q=#makechanges).

