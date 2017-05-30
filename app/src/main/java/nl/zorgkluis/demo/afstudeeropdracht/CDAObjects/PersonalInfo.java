package nl.zorgkluis.demo.afstudeeropdracht.CDAObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.CommonElements.HL7Address;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.CommonElements.HL7PersonName;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Participant.HL7RecordTarget;
import nl.zorgkluis.hl7.ClinicalDocumentArchitecture.Role.HL7PatientRole;
import nl.zorgkluis.hl7.DataTypes.HL7PointInTime;

/**
 * Created by Zorgkluis (geert).
 */
public class PersonalInfo {

    private String name;
    private String birthday;
    private String gender;
    private String address;
    private String phoneNumber;

    public PersonalInfo(HL7RecordTarget recordTarget){
        HL7PatientRole patientRole = recordTarget.getPatientRole();

        setName(patientRole);
        setGender(patientRole);
        setBirthday(patientRole);
        setAddress(patientRole);
        setPhoneNumber(patientRole);

    }
    private void setName(HL7PatientRole patientRole) {
        HL7PersonName patientName = patientRole.getPatient().getName().get(0);
        name = patientName.getDescription();
    }

    private void setBirthday(HL7PatientRole patientRole) {
        HL7PointInTime birthTime = patientRole.getPatient().getBirthTime();
        Date birthDate = birthTime.getDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        birthday = dateFormat.format(birthDate);
    }

    private void setGender(HL7PatientRole patientRole){
        gender = patientRole.getPatient().getAdministrativeGenderCode().getDisplayName();
    }

    private void setAddress(HL7PatientRole patientRole) {
        HL7Address hl7Address = patientRole.getAddress().get(0);
        address = hl7Address.getStreetAddressLine() + "\n"
                + hl7Address.getCity() + " "
                + hl7Address.getPostalCode() + ", "
                + hl7Address.getCountry().getValue();
    }

    private void setPhoneNumber(HL7PatientRole patientRole){
        phoneNumber = patientRole.getTelecoms().get(0).getValue().replace("tel:", "");
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
