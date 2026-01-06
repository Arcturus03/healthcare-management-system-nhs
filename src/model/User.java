package model;

/**
 * Abstract base class for all users in the healthcare system.
 * All users (Patient, GP, Nurse, etc.) inherit from this class.
 *
 * @author Hrithik Chandra
 * @version 1.0
 */
public abstract class User {

    // === ATTRIBUTES (Protected so subclasses can access) ===
    protected String userId;
    protected String name;
    protected String email;
    protected String phone;

    // === CONSTRUCTOR ===
    /**
     * Constructor to initialize a User with basic information
     *
     * @param userId Unique identifier for the user
     * @param name Full name of the user
     * @param email Email address
     * @param phone Phone number
     */
    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // === COMMON METHODS (All subclasses inherit these) ===

    /**
     * User logs into the system
     */
    public void login() {
        System.out.println(name + " has logged in.");
    }

    /**
     * User logs out of the system
     */
    public void logout() {
        System.out.println(name + " has logged out.");
    }

    /**
     * Update user profile information
     *
     * @param email New email address
     * @param phone New phone number
     */
    public void updateProfile(String email, String phone) {
        this.email = email;
        this.phone = phone;
        System.out.println(name + "'s profile has been updated.");
    }

    // === ABSTRACT METHOD (Subclasses MUST implement) ===
    /**
     * Abstract method representing the main role of the user.
     * Each subclass will implement this differently.
     *
     * Example: Patient implements as "viewing medical record"
     *          GP implements as "creating prescription"
     */
    public abstract void performRole();

    // === GETTERS (Access attributes from outside) ===

    /**
     * Get the user's ID
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get the user's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the user's email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the user's phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    // === SETTERS (Modify attributes from outside) ===

    /**
     * Set the user's name
     * @param name New name
     */
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    /**
     * Set the user's email
     * @param email New email
     */
    public void setEmail(String email) {
        if (email != null && !email.isEmpty()) {
            this.email = email;
        }
    }
}
