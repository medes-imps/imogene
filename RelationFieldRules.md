

# Principles #

> ## Uni-directional and Bi-directional Relations ##

> In the case of a bi-directional Relation ,a Main Relation Field and a Reverse Relation Field must be declared.
> In the Main Relation Field, the property 'Opposite Relation Field' must be filled and it must reference the Reverse Relation of the other side of the Relation. In the Reverse Relation Field, the property 'Opposite Relation Field' must be filled and it must reference the Main Relation of the other side of the Relation. In the Main Relation Field, the property 'Inverse Cardinality' is not used since the Inverse Cardinality is known through the Reverse Relation Field..


> In the case of a uni-directional Relation, only a Main Relation Field is declared.
> In the Main Relation Field, the property 'Opposite Relation Field' is empty (since no Reverse Relation Field is declared for a uni-directional Relation). In the Main Relation Field, the property 'Inverse Cardinality 'indicates the cardinality of the related Card Entity.


> ## For Compositions ##

> Compositions:
    * Composition can be declared on only one side of a Relation. If a Card Entity existence depends on another Card Entity existence, the other way round is impossible.
    * Composition can only be declared in a Main Relation Field. Since the other Card Entity existence depends on the Card Entity that declares the Composition, it is considered that the Card Entity that declares a Composition is the Main Relation Field of the Relation.


> When a Card Entity is referenced by a Composition, its side's Cardinality must be 1.

> It concerns 2 cases:
    * In the case of a bi-directional Relation, the Reverse Relation Field must have a Cardinality of 1.
    * In the case of a uni-directional Relation, the Inverse Cardinality of the Main Relation Field must be 1


> ## For uni-directional Relations ##

> For the moment, it is considered that a side of the Relation can be omitted only if its cardinality is different of 1 (-1 or >1). As a consequence, it is impossible to declare a Main Relation Field with an Inverse Cardinality of 1 if no Opposite Relation Field is declared (if the Relation is not bi-directional).


> ## For Common Fields ##

> Common Fields are used when the 2 Card Entities that are part of the Relation share some common data.
> For the moment, Common Fields must be declared in the Main Relation Field. A Dialog box enables to select the Relation Fields that are common to the Card Entities that are part of the Relation.


# An example #

> Let's take the example of a Patient/Doctor Relation and assume that a Patient has only one Doctor. In our model, we will have a Patient and a Doctor Card Entities.

> Our main interest is in knowing who is the doctor of a given Patient's occurrence. So we will declare a Main Relation Field in the Patient Card Entity.


> Now it has to be decided what kind of information we want to know about the Doctor. We have 2 possibilities:

> If on the Doctor's side, we are not interested in listing all the Patients (there might be many), we are going to create a one-direction Relation, that means that we will only declare the Relation on the Patient's side and not on the Doctor's side.

> On the Patient side, we will create a Main Relation Field which will have the following properties:
    * Cardinality = 1 (A Patient has only one Doctor)
    * Entity = Doctor (The related Card Entity)
    * Type = Association (The existence of one does not depend on the existence of the other)
    * Opposite Relation Field = empty (there isn't any related Relation Field since we do not declare the Relation on the Doctors side)
    * Inverse Cardinality = -1 (To define the Relation, we need to know the (virtual) cardinality of the other side of the relation, in this example, a given Doctor has many Patients so the inverse cardinality is -1)


> If on the Doctor's side, we are interested in listing all the Patients, we are going to create a bi-directional Relation, that means that we will declare the Relation on the Patient's side and on the Doctor's side.

> On the Patient side, we will create a Main Relation Field which will have the following properties:
    * Cardinality = 1 (A Patient has only one Doctor)
    * Entity = Doctor (The related Card Entity)
    * Type = Association (The existence of one does not depend on the existence of the other)
    * Opposite Relation Field = The Reverse Relation Field declared in the Doctor Card Entity
    * Inverse Cardinality = -1

> On the Doctor side, we will create a Reverse Relation Field which will have the following properties:
    * Cardinality = -1 (A Doctor can have more than one Patient)
    * Entity = Patient (The related Card Entity)
    * Type = Association (The existence of one does not depend on the existence of the other)
    * Opposite Relation Field = The Reverse Relation Field declared in the Patient Card Entity


> Let's assume that a Patient and its Doctor must belong to the same Health Organization. The model will have a Health Organization Card Entity.

> The Doctor Card Entity will have a Main Relation Field referencing this Health Organization Card Entity.

> The Patient Card Entity will have a Main Relation Field referencing this Health Organization Card Entity.

> Patients and Doctors share the same data, the Health Organization.


> In the Patient Card Entity, for the Main Relation Field declaring the Relation to the Doctor, we will fill the property Common Fields:

> In this property, we will put the Main Relation Field of the Patient Card Entity declaring the Relation with Health Organization and the Main Relation Field of the Doctor Card Entity declaring the Relation with Health Organization.

> Thanks to this property, when a Doctor will be created for a given Patient, the field 'Health Organization' of the Doctor's form will be pre-filled with the 'Health Organization' value of the previously filled Patient's form.
