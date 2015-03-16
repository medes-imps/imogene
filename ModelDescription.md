

# Project #
> _**Description**_
> The root of the Model is the Project.

> The Editor enables to add the following objects to the project:
    * A Description
    * A Card Entity
    * An Actor
    * A Card Entity UI Format
    * A Thema
    * A Role
    * A Language

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Name  | The project name. It will give its name to the data collection applications. |  The name entered in the imogene project creation wizard  |  Any string |  yes  |


# Card Entity #
> _**Description**_
> A Card Entity is a specific object of interest to a business area that the user wishes to store data about. In the imogene IS applications, it will represent a form for data collection/consultation. The Card Entity has fields (form fields) that can be grouped in field groups.
> Different types of fields can be added to a form like Text fields, Integer fields, Float fields, Boolean fields, Enumeration fields, Date fields, Binary fields (Photo, Video, Sound, etc) as well as Relation fields that enable to create a relation between 2 card entities.
> A Model must contain at least one Card Entity.

> The Editor enables to add the following objects to a Card Entity:
    * A Description
    * A Field Group

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required**  |
|:------------------|:------------|:------------------|:--------------------|:--------------|
| Actor Filter Fields |  See the Filter Fields chapter |   |    |  |
| Client Filter Fields |  Not used for the moment |   |    |  |
| Client Period Filterable |  Not used for the moment |   |  |  |
| Color |  A color that will be associated with the Card Entity in the application user interfaces |   |  Any color HTML color notation, exp: '0391ce' |  no |
| Column Fields |  The fields that it will be displayed in the table that lists the entity instances that have been entered. This concerns the web and desktop applications |   |  A list of fields belonging to the card entity |  yes |
| Creators  | A set of Roles which will have the privilege to create instances of the form. (If a user does not have this privilege, the form will not appear in the menu list (even if the property Top Level is set to true)  |  | A list of Roles selected among the Roles of the project. If no role is assigned to the creators, all actors with 'edit' access on some fieldgroups will be able to create instances of the form | no |
| Display QR Code  | If set to yes, the applications will display in the card entity instance unique ID as a QR Code in the form  | false | True or False  | yes |
| Icon  | The icon that will be associated with the card entity in the application user interfaces. This name will reference big icons (32\*32) and small icons (24\*24)  | If no name is defined, default icons will be used in the applications  | An icon name (exp: Form1.png). The icon should be at the png format. The big icons (32\*32) should be copied in the icons folder of the model project. The small icons (24\*24) should be copied in the icons/small folder of the model project. Big and small icons should have the same name for a given entity.  | no |
| Main Fields  | The fields that will be used as a display value for an entity instance. For all applications, it will be used to display the RelationField/Subform value. For the Android application, these are the fields that will be displayed in the list view that lists the entity instances that have been entered.  |  | A list of fields belonging to the card entity  | yes |
| Name  | The Card Entity Name  |  | Any string. Should be unique in the model. Should start with an upper case by convention. No white space, no accent, strange character allowed | yes |
| Secondary Fields  | Additional fields that will be used as a display value for an entity instance. It concerns the Android application. In an instance form, These fields will appear as a sub list under the mainfields  |  | A list of fields belonging to the card entity  | no |
| Short Name  | A Short Name identifying the Card Entity  |  | Any short string, in upper case by convention, should be unique in the model. There is a tool that enables to automatically generate short names, but a meaningful one should be put for Card Entities.  | yes |
| Sort Fields  | A list of fields used to sort the entity instances in the table listing the entity instances that have been entered in the IS.  | The entity instance modification date  | A list of fields belonging to the card entity. Only the first defined sort field is used until now  | no  |
| Top Level  | Indicates if a Card Entity is an entity on its own or if it is an entity that depends on another one.  | true  | True or false. If it is Top Level, the entity will appear in the application menus, otherwise, it will only be accessible from another entity form (only appear as referenced by this entity)  | yes  |


# Actor #
> _**Description**_
> An Actor is a specific type of Card Entity. It is a form that represents a type of user of the application. A specific type of Card Entity is necessary to model application users because these ones will have specific properties, privileges, etc. For instance, a user will be able to log in the applications so it will have user specific privileges fields. A user will be able to receive notifications, so it will have specific notification fields too.

> To manage Actors, their privileges and notifications, a dedicated Web application is available. It is the Administration application. It presents forms that enable to configure the Actor specific parameters.

> A Model must contain at least one Actor. Otherwise it will not be possible to enable users to access the applications.

> The Editor enables to add the following objects to an Actor:
    * A Description
    * A Field Group
    * Notification Info
    * Filter Field

> _**Properties**_
> An Actor is a specific type of Card Entity. Therefore it will have the same properties as the Card Entity ones.

> But it will have too a set of additional ones which are the following:
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Admin Fields  | The Actor standard Card Entity fields which should be present too in the User Administration application form.  |  | A list of fields (excluding RelatonFields) belonging to the actor.  | no  |
| Authorized Roles | The Roles (see the Role Object) that can be assigned to an Actor | If no role is referenced, all the roles that have been defined in the project will be taken into account, including the default 'web' (for application access) and 'administrator' (for accessing the administration module) roles | A list of Roles selected among the Roles of the project | no  |


# Description #
> _**Description**_
> The Description object enables to define user friendly texts for the Model objects (instead of object names which can not have spaces, accents, etc). These texts will be used in the application user interfaces to name the objects (form types, field groups, fields, etc). A description can be entered as a child for all objects of the Model.

> Specific alphabet characters should be written in Unicode, for instance '\u00E9' instead of '�' or '\u00F4' instead of '�'.

> Descriptions are not mandatory. If no description is defined for a Model object, its object name will be used as a display text in the application user interfaces.

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Display | The text describing the object |  | Any String. | yes  |
| Help | Additional text describing the object. As this text is not displayed directly i the interface, but is called explicitly by a a specific action on the user interface, this text can be more exhaustive to explain the model object. |  | Any String. | no  |
| Locale | Enables to define for which language of the model, this description is corresponding to. |  | The ISO code of one the languages that have been defined in the model | yes  |


# Role #
> _**Description**_
> A Role is a model object representing data access rules for the applications. These roles will be assigned to Actor instances (application users) to allow them or not to access Field Groups in read and/or write mode, to receive or not notifications.

> Roles are not mandatory. If no Role is defined in the Model and no authorization/permission defined in the Model, all users will have read/write accesses to all the application forms.

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Name | The Role name |  | Any string. No white space, no accent allowed | yes  |


# Language #
> _**Description**_
> The Language object enables to define the languages in which the data collection applications will be available.
> Languages are not mandatory. If no Language is defined in the Model, only one language will be available without being specifically mentioned.

> The user interface texts are stored in properties files (text files). Each application has as many properties files as Language objects defined in the model. These texts name the different object that are defined in the model. If no 'Description' is assigned to an object, the system will use the Object name as a display text. If 'Description' objects are assigned to the object, the system will use the 'Description' name as a display text.

> These properties files may have to be edited manually in order to customize the user interface texts, in case the generated texts are not completely right (gender issues for instance), or that not all translations were added to the model.

> Sometimes it is not possible to translate everything because the person who is doing the modelling work does not know all the languages. One solution is to send the properties files to someone who know the language so that he can make the translation himself, and then just copy the properties file in the application folder to make it available.

> To be inserted: image showing the properties files of the applications

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Date Format | The date format for the language | dd/MM/yyyy | A String representing a Java date format | yes  |
| Float Format | Not used until now |  |  | no  |
| Integer Format | Not used until now |  |  | no  |
| Iso Code | The official ISO code for the language |  | Any official ISO code | yes  |
| Name | The Language name |  | Any string. No white space, no accent allowed | yes  |
| Time Format | The time format for the language | HH:mm | A String representing a Java time format | yes  |


# Thema #
> _**Description**_
> The Thema object enables to group the Entities into logical groups. In the application menu, instead of having all form references listed one after the other, this object enables to group these references into comprehensive groups. A Thema will only show the CardEntities whose property 'TopLevel' is set to 'true'. At runtime, it will only show the CardEntities for which the current user has the a role among the ones entered as 'creators'.

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Entities | The entities that will be part of the thema |  | A list of CardEntities selected among the CardEntities of the project. | yes  |
| Name | The Thema name (a meaningful name that should summarize the thema of the entities) |  |  | yes |


# Card Entity UI Format #
> _**Description**_
> The Card Entity UI Format object has been created to group the modelling information that concern the application user interfaces.

> For the moment it concerns only the web application.

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Entity | The CardEntity whose User Interface has to be defined |  | A CardEntity selected among the CardEntities of the project. | yes  |
| With Tabulations | If set to true, the CardEntity group fields will be displayed as tabulations in the web application user interface | false | true/false | yes  |


# Field Group #
> _**Description**_
> Card Entities and Actors represent forms for data collection/consultation in the imogene IS applications. These forms have fields that can be grouped in field groups. A field group enables to group fields that deal with the same issue. The Field Group object enables to model these field groups.

> A Card Entity or an Actor must contain at least one Field Group.

> In the application user interfaces, Field Groups will enable to group fields in Tab Panels. The Tab title will be the name of the Field Groupn or the Description display name if a description has been defined for the Field Group.

> The Editor enables to add the following objects to a Field Group:
    * A Text Field
    * A Binary Field
    * A Main Relation Field Entity
    * A Reverse Relation Field Entity
    * An Integer Field
    * A Float Field
    * An Enum Field
    * An Email Field
    * A Video Field
    * A Date Field
    * A Date Time Field
    * A Time Field
    * A Phone Field
    * A Photo Field
    * A Sound Field
    * A Boolean Field
    * A TextArea Field
    * An Adress Field
    * A Geo Field
    * A Barcode Field
    * A Description

> _**Properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Icon | The icon that will be associated with the field group in the application user interfaces. This name will reference icons (32\*32) and small icons (24\*24) | If no name is defined, no icon will be used in the applications | An icon name (exp: Form1.png). The icon should be at the png format. It should be around 32\*32 and copied in the icons folder of the model project. | no  |
| Name | The Field Group Name |  | Any string. No white space, no accent allowed | yes  |
| Readers | A set of Roles which will have read access to the field group |  | A list of Roles selected among the Roles of the project. If no role is assigned to the readers, all actors will have read access to the field group | no  |
| Short Name | A Short Name identifying the Field Group |  | Any string, in upper case by convention, should be unique in the model | yes  |
| Writers | A set of Roles which will have write access to the field group |  | A list of Roles selected among the Roles of the project. If no role is assigned to the writers , write access is restricted to the roles defined as readers. | no  |


# Field common properties #
> _**Description**_
> A Field is a Form field. It enables to enter data for a specific item.
> Fields deal with different types of data (text, date, etc). The editor enables to add a specific the following field types:
    * A Text Field
    * A Binary Field
    * A Main Relation Field Entity
    * A Reverse Relation Field Entity
    * An Integer Field
    * A Float Field
    * An Enum Field
    * An Email Field
    * A Video Field
    * A Date Field
    * A Date Time Field
    * A Time Field
    * A Phone Field
    * A Photo Field
    * A Sound Field
    * A Boolean Field
    * A TextArea Field
    * An Adress Field
    * A Geo Field
    * A Barcode Field

> A Field Group must at least contain one Field.

> The Editor enables to add the following objects to a Field:
    * A Description
    * Field Dependent Visibility

> In the application interfaces, a field will be made of a label and a value.

> If a description is defined for a field, the field label will be the description display name. If no description is defined for the field, the field label will be the field name.

> Thanks to the 'Field Dependent Visibility' object, the modelling enables to define that the this given field is visible or not depending on the value of another field. Several 'Field Dependent Visibility' objects can be added to one field. In that case, the field will be visible if all the conditions defined are encountered.

> These field types will have common properties and specific properties.

> _**Field common properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Calculation Function Name | To define that the value of the field is calculated. For the moment, the generation will create an empty method in the generated application code so that the user can complete it with the calculation algorythm |  | A name string that will be used for the field calculation method.  | no  |
| Default Value | To set a default value to the field. (Not for Relation field and Binary fields) |  | Dates: 'now' will pre-fill the date field with the current date.Boolean: true or false. Enumeration with simple selection: The value of one of possible choices | no  |
| Hidden | To set if the field is hidden in the user interface or not | false | True: The field is hidden, it will not be displayed in the user interface. Or False | no  |
| Name | The Field name |  | Any string. No white space, no accent allowed | yes  |
| Read Only | To set if the field is read-only or not | false | True: The field is read-only. The user will not be able to edit it even when editing the form. Or False | no  |
| Required | Indicates if the field value is mandatory or not | false | True: the field value is mandatory. If a user does not enter the value, a message indicates that the form can not be saved without having entered this value -False: the field value is not mandatory. The user can save the form without having entered this value | yes  |
| Short Name | A Short Name identifying the Field |  | Any string, in upper case by convention, should be unique in the model. A tool enables to automatically generate the short names for all the model objects. | yes  |


# Text Field #
> A Text Field is a text field displayed on one row used to enter text values.


# TextArea Field #
> A TextArea Field is a text field displayed on several rows used to enter text values.


# Integer Field #
> _**Description**_
> An Integer Field is a numeric field used to enter integer values. If the entered value is not an integer value a message indicates that the entered value is not valid.

> _**Integer Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Max | The maximum value the user can enter |  | Any integer value | no  |
| Min | The minimum value the user can enter |  | Any integer value | no  |
| Unit | If the field corresponds to a measure with a unit, this property is used to display the unit in the user interface of the application. |  | Any abbreviation corresponding to a measure unit | no  |


# Float Field #
> _**Description**_
> A Float Field is a numeric field used to enter float values. If the entered value is not a float value a message indicates that the entered value is not valid.

> _**Float Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Max | The maximum value the user can enter |  | Any integer value | no  |
| Min | The minimum value the user can enter |  | Any integer value | no  |
| Unit | If the field corresponds to a measure with a unit, this property is used to display the unit in the user interface of the application. |  | Any abbreviation corresponding to a measure unit | no  |
| Decimal Number | The number of decimals that the user should use to enter the value and that the user interface should display for the value | -1 | -1 (no limitation). Any integer value. | no  |


# Boolean Field #
> A Boolean Field is a field used to enter a true or false value.


# Enum Field #
> _**Description**_
> An Enum Field is a field used to select values among a pre-defined set of values.
> The list of possible values is defined by adding Enum Value objects to the Enum Field Object.

> _**Enum Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Multiple Selection | Indicates if multiple values can be selected in the possible value list | false | True: The user can select several values among the list values. -False: The user can select only one value among the list values. | yes  |

> _**Enum Value properties**_

| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Name | The Enum Value name |  | Any string. No white space, no accent allowed | yes  |
| Value | The Enum Value value | 0 | Any integer. Must be unique among a given Enum Field Values | yes  |

> In the application user interfaces, the list will display the value name if no description has been defined for an Enum Value or the description display name if a description has been defined for an Enum Value.


# Email Field #
> An Email Field is a text field used to enter an email addresses.


# Phone Field #
> _**Description**_

> A Phone Field is a text field used to enter phone numbers.

> _**Phone Field specific properties**_

| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Phone Type | Type of phone number | PhoneNumber | Phone Number: an undefined phone number -Fax Number: a Fax phone number -Mobile Number: a mobile phone number - Fixe Number: a fixe phone number | yes  |


# Date Field #
> _**Description**_
> A Date Field is a field used to enter dates. The applications present a specific widget to enter dates in a user friendly way.

> _**Date Field specific properties**_
> When the 'Default Value' property is set to 'now', the field is pre-filled with the current date.

| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Max | The maximum date the user can enter |  | Any date value at the format dd/MM/yyyy | no  |
| Min | The minimum date the user can enter |  | Any date value at the format dd/MM/yyyy | no  |


# Time Field #
> _**Description**_
> A Time Field is a text field used to enter times.

> _**Time Field specific properties**_
> When the 'Default Value' property is set to 'now', the field is pre-filled with the current time.


# Date Time Field #
> _**Description**_
> A Date Time Field is a text field used to enter date + times.

> _**Time Field specific properties**_
> When the 'Default Value' property is set to 'now', the field is pre-filled with the current date and time.


# Binary Field #
> _**Description**_
> A Binary Field is a field used to store binary data.

> In edition mode, it presents a select file widget that enables to select a file in the file system and to upload it when the form is saved. In View mode, it presents a button that enables to download the stored binary file.

> _**Binary Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Category | Indicates the type of binary data |  | Any string. Uses in some cases to make the link with a specific driver for data acquisition | yes  |


# Video Field #
> _**Description**_
> A Video Field is a field used to store videos.

> _**Video Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Category | Indicates the type of binary data | Set to video | Can not be changed | yes  |


# Photo Field #
> _**Description**_
> A Photo Field is a field used to store photos.

> _**Photo Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Category | Indicates the type of binary data | Set to photo | Can not be changed | yes  |


# Sound Field #
> _**Description**_
> A Sound Field is a field used to store sounds.

> _**Sound Field specific properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Category | Indicates the type of binary data | Set to sound | Can not be changed | yes  |


# Address Field #
> A text field that is used to store addresses. In the web and Android applications, the field will be associated with a button that enables to visualize the address in a google map interface.


# Geo Field #
> A field that is used to store geographical coordinates.

> In the Android application, the coordinates are automatically acquired form the terminal GPS receiver. If the receiver does not get any signal, it can be also acquired from the communication network triangulation. A third possibility for the user it to indicate the location on the google map interface and the Android application will acquire the coordinates from the map.

> In the other applications, this field is displayed as a text field. In the web application, a button enables to to visualize the coordinates in a google map interface.


# Barcode Field #
> A field used to store barcode information. For the moment, it is a simple text field.


# Relation Field #
> _**Description**_
> A Relation Field enables to create relationships between 2 Card Entities of the project. A Relationship captures how two entities are related to one another.


> _**Relation Field properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Cardinality | Defines the number of occurrences of the related Card Entity that can be assigned to an occurrence of the current Card Entity. | -1 | -1: Indicates that the current Card Entity occurrence can be assigned more than one occurrences of the related Card Entity _1: Indicates that the current Card Entity occurrence can not be assigned more than one occurrence of the related Card Entity_ Any integer>1: Indicates that the current Card Entity occurrence can be assigned more than one occurrences of the related Card Entity | yes  |
| Entity | The referenced Card Entity (The Card Entity on the other side of the Relation) |  | A Card Entity selected among the Card Entities of the project | yes  |
| Type | Relation type | Association | Association: Means that the current Card Entity occurrence is related to the related Card Entity occurence(s). There is no dependence between the 2. _Composition: Means that the existence of the related Card Entity occurrence(s) depends on the existence of the current Card Entity occurrence. (When the current Card Entity occurrence is deleted, the related Card Entity occurence(s) are automatically deleted)_| yes  |
| Common Fields | Relation Fields (others than the ones which are part of the Relation) that are common to the 2 related Card Entities. Common Fields are used when the 2 related Card Entities share some common data. It is used to pre-fill the common data field when an occurrence of one side of the relation is created from an occurrence of the other side of the relation. |  | (X,Y) Couples of Relation Fields. X belongs to the current Card Entity, Y belongs to the related Card Entity. X and Y reference Card Entities of the same type | no  |
| Relation Hierarchical filter | Enables to define hierarchical lists (the values of one list depend on the value of another list, for instance, the values of a city list depend on the value of a province list). _See Hierarchical Lists (Relation Hierarchical Filter)_|  |  | no |

> _**Main and Reverse Relation Fields**_
> The Main and Reverse Relation Field objects enable to differentiate the 2 sides of a Relation.
> The Main Relation Field is the side of the Relation which is of Main interest. The Reverse Relation Field is its counterpart.
> A Relation can be bi-directional, that means that the user wants to know the status of the Relation on both sides of it. He declares the Relation on both sides of if, creating a Main Relation Field on one side and a Reverse Relation Field on the other.
> A Relation can be uni-directional, that means that the user wants to know the status of the Relation only on one side of it. He will then declare the Relation on one side of it only, creating a Main Relation Field on one side, but without creating a Reverse Relation Field on the other.


> _**Main Relation Fields properties**_

| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Opposite Relation Field | The related Relation Field (The Relation Field on the other side of the Relation) |  | The Relation Field of the related Card Entity referencing the current Card Entity Relation Field - In the case of a uni-directionnal Relation, there isn't any related Relation Field, the value is null. | See RelationFieldRules to follow |
| Inverse Cardinality | The Cardinality of the the related Card Entity |  |  | See 'rules to follow' section |

> _**Reverse Relation Fields properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Opposite Relation Field | The referenced Relation Field (The Relation Field on the other side of the Relation) |  | The Relation Field of the related Card Entity referencing the current Card Entity Relation Field | yes  |

> Check the RelationFieldRules to follow


# Field Dependent Visibility #
> _**Description**_
> This object can be added to any kind of field. It enables to define that the concerned field will be visible depending on the value of another field. Several 'Field Dependent Visibility' objects can be added to one field. Then, the visibility of the field will depend on the combination ('and' operator) of the different conditions.

> _**Field Dependent Visibility properties**_

| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Dependency Field | The field whose value will determine the visibility of the concerned field |  | Any other field selected among the card entity fields. Relation Fields and Binary Fields are not allowed | yes  |
| Dependency Field Value | The field value that the Dependency Field will have to match so that the concerned field is made visible |  | A regular expression that will be applied to the field value. If the field value matches it, the concerned field will be made visible. | yes  |

> The following table presents the different types of regular expressions that should be entered by type of field:

| **Field Type** | **Regular expression examples** |
|:---------------|:--------------------------------|
| Boolean Field  | 'true' or 'false' |
| Enumeration Field where the user can select only one value | One value among the Enumeration Enum Values, or a regular expression applying to the enumeration values. |

> Exp1: '1'' indicates that if the user selects in the list the item whose value is 1, the concerned field will be visible

> Exp2: '1|3' indicates that if the user selects in the list the item whose value is 1 or the item whose value is 3, the concerned field will be visible

| Enumeration Field where the user can select several values | A regular expression applying to the enumeration values. For this type of enumeration, the multiple values are separated by the character ';'. |
|:-----------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------|

> Exp1:' (.**\\D)?1(\\D.**)?' indicates that if, among the selected values, the user has selected the item whose value is 1, the concerned field will be visible.

> Exp2: '(.**\\D)?1(\\D.**)?|(.**\\D)?3(\\D.**)?' indicates the if, among the selected values, the user has selected the item whose value is 1 and/or the item whose value is 3, the concerned field will be visible

| String/Email/Phone/text/Address/Geo/Barcode Field | Any regular expression. For example: '[^\\s]'indicates that if the Dependency Field has a value (it is not empty), the concerned field is visible. 'e' indicates an empty string |
|:--------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

| Integer Field | Here are the combinations of values/operators that should be used: |
|:--------------|:-------------------------------------------------------------------|
| **Dependency Field Value** | **Meaning** |
| '2<=' | The value should be greater or equal than 2 |
| '2<' | The value should be greater than 2 |
| '2>=' | The value should be lower or equal than 2 |
| '2>' | The value should be greater than 2 |
| '2!='' | The value should not be equal to 2 |
| '2=' | The value should be equal to 2 |
| '2;5' | The value should be between 2 and 5 |


| Float Field | Here are the combinations of values/operators that should be used: |
|:------------|:-------------------------------------------------------------------|
| **Dependency Field Value** | **Meaning** |
| '2.5<='' | The value should be greater or equal than 2.5 |
| '2.5<' | The value should be greater than 2.5 |
| '2.5>=' | The value should be lower or equal than 2.5 |
| '2.5>'' | The value should be lower than 2.5 |
| '2.5!=' | The value should not be equal to 2.5 |
| '2.5=' | The value should be equal to 2.5 |
| '2.5;5.8' | The value should be between 2.5 and 5.8 |


| Date Field | Here are the combinations of values/operators that should be used: |
|:-----------|:-------------------------------------------------------------------|
| **Dependency Field Value** | **Meaning** |
| '03/10/2010<=' | The value should be after or equal than 03/10/2010 |
| '03/10/2010<' | The value should be after than 03/10/2010 |
| '03/10/2010>=' | The value should be before or equal than 03/10/2010 |
| '03/10/2010>' | The value should be before than 03/10/2010 |
| '03/10/2010!=' | The value should not be equal to 03/10/2010 |
| ''03/10/2010=' | The value should be equal to 03/10/2010 |
| '03/10/2010;03/11/2010' | The value should be between 03/10/2010 and 03/11/2010 |

> This link indicates how to build a regular expression:

> [http://www.regular-expressions.info/reference.html](http://www.regular-expressions.info/reference.html)


# Validation Rule #
> _**Description**_
> This object can be added to the following types of fields:
    * Text Field
    * Email Field
    * Phone Field
    * TextArea Field
    * Address Field
    * Geo Field
    * Barcode Field<br />

> They enable to define customized validation rules for concerned field in addition to other standard validation rules like if the field is required, date, integer, float formats.

> The user will not be allowed to save the form that contains this field until the field value does not match the specific validation rule that has been defined through this object.

> Several 'Validation Rule' objects can be added to one field. Then, the field validation will depend on the combination ('and' operator) of the different conditions.

> _**Validation Rule properties**_
| **Property name** | **Meaning** | **Default value** | **Possible values** | **Required** |
|:------------------|:------------|:------------------|:--------------------|:-------------|
| Validation Rule Regular Expression | The regular expression the field value will have to match so that the field can be validated and the form it belongs to can be saved |  | Any regular expression | yes  |

> Here are some regular expression examples:

> {2}: The entered value should have 2 digits. Exp: 18

> [0-9]{1,4}: The entered value should have between 1 and 4 digits. Exp: 18

> [A-Z].**: The entered value should start by an upper case.**


# Filter Fields #
> The modelling process enables to define filters that will filter the instances of given entities that a given user can view.
> For instance, a given interviewer will only be able to view the interviews that were done in a given geographical district. The administration application will enable to configure which district related data each interviewer has access to.

> The definition of filters is done in 2 steps.

> '''The first step''' is done on the Actor side, '''defining the type of entity an actor will be filtered upon'''.

> A Filter Field is added to the Interviewer Actor. A Filter Field is a type of Relation Field so it includes the properties of Relation Fields. The important properties here are the following:

> ''''Entity'''': The model Card Entity, the Actor type is going to be filtered upon. Here the Interviewer instances are going to be filtered by District instances.

> ''''Name'''': The name of the Filter Field.

> '''Cardinality''': If set to '1', only one instance of the Filtering Entity can be assigned to a given Actor (An Interviewer will only be allowed to access the data of one District). Otherwise, several instances of the Filtering Entity can be assigned to a given Actor (An Interviewer will be allowed to access the data of several Districts).

> '''The second step''' is done on the Card Entity side, defining the property 'Actor Filter Field' of the Card Entity, '''indicating what Relation Field will be used as a filter and for which type of user'''.

> The Card Entity type that has to be filtered must have a Relation Field relating to the Filtering Entity with a cardinality of 1.

> Here we want that the Interviewers can only view the Facilities of specific districts, so the Facility Card Entity must reference the District Entity.

> On the Card Entity side, the definition of the filter is done by filling out the 'Actor Filter Field' property of the Card Entity.

> This property enables to open a dialog window that presents on the lower left side the relation fields of the Card Entity with cardinality 1 and on the lower right side the Filter Fields that have been defined for the model Actors.

> The definition of the Filter consists in linking the Filtering Entity that have been defined on the Card Entity side and the one that have been defined on the Actor side.

> In that example, the windows enables to link the District Field of the Facility Entity and the District Filter Field of the Actor Interviewer. Thanks to this, when a given Interviewer will login to the applications, he will only be able to view the Facility instances that reference the Districts that he was allowed to see.


# Hierarchical Lists (Relation Hierarchical Filter) #
> The modelling process enables to define hierarchical lists, that means lists whose values depend on the value of another list. For instance, the values of a list 'city' will depend on the value that is selected in another list 'province'.

> To define hierarchical lists, 2 conditions must be filled.

> One on the side of the CardEntity that will contain the hierarchical lists. The hierarchical lists are made of 2 lists.
    * A 'parent' list which is the relationField that will serve as a filter. It must be of cardinality 1.
    * A 'child' list which is the relationField whose values are going to be filtered. It can be of cardinality 1 or n.

> And on the side of the CardEntity that is referenced by the child RelationField. This CardEntity must also have a RelationField (cardinality 1) referencing the CardEntity that is referenced by the parent RelationField

> It is on 'child' list that the hierarchical relation is defined through the property 'Relation Hierarchical Filter'. It opens a dialog window which enables to define a correspondence between the parent RelationField on the side of the current CardEntity and the parent RelationField on the side of the CardEntity that is referenced by the child RelationField.


