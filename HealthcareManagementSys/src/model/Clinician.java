package model;

/**
 * Clinician class representing doctors, nurses, and specialists.
 * Extends User class for common attributes.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public class Clinician extends User {

    // === CLINICIAN-SPECIFIC ATTRIBUTES ===
    private String firstName;
    private String lastName;
    private String title;              
    private String speciality;         
    private String gmcNumber;          
    private String workplaceId;        
    private String workplaceType;      
    private String employmentStatus;   // Full-time, Part-time
    private String startDate;          // YYYY-MM-DD format

    /**
     * Full constructor matching all 12 CSV columns.
     */
    public Clinician(String clinicianId, String firstName, String lastName,
                    String title, String speciality, String gmcNumber,
                    String phone, String email,
                    String workplaceId, String workplaceType,
                    String employmentStatus, String startDate) {
        // Call User constructor with combined name
        super(clinicianId, firstName + " " + lastName, email, phone);

        // Set Clinician-specific fields
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.speciality = speciality;
        this.gmcNumber = gmcNumber;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    @Override
    public void performRole() {
        System.out.println(title + " " + firstName + " " + lastName +
                " (" + speciality + ") is providing clinical care at " + workplaceType);
    }

    // === GETTERS ===
    // Inherited: getUserId(), getName(), getEmail(), getPhone()

    public String getClinicianId() { return getUserId(); }  // Alias for clarity
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTitle() { return title; }
    public String getSpeciality() { return speciality; }
    public String getGmcNumber() { return gmcNumber; }
    public String getWorkplaceId() { return workplaceId; }
    public String getWorkplaceType() { return workplaceType; }
    public String getEmploymentStatus() { return employmentStatus; }
    public String getStartDate() { return startDate; }

    // === SETTERS ===
    // Inherited: setName(), setEmail(), setPhone()

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName(firstName + " " + lastName);  // Update inherited name field
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName(firstName + " " + lastName);  // Update inherited name field
    }

    public void setTitle(String title) { this.title = title; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
    public void setPhone(String phone) {this.phone = phone;}
    public void setGmcNumber(String gmcNumber) { this.gmcNumber = gmcNumber; }
    public void setWorkplaceId(String workplaceId) { this.workplaceId = workplaceId; }
    public void setWorkplaceType(String workplaceType) { this.workplaceType = workplaceType; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
}