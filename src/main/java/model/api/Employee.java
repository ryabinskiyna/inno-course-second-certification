package model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private int companyId;
    private String email;

    //TODO: 2. Написать BUG-репорт, что при запросе Employee через API поле "url" меняется на "avatar_url"

    private String url;
    private String phone;

    //TODO: 3. Написать BUG-репорт на несоответствие формата поля "birthdate" в запросах GET по id сотрудника,
    // GET по id компании ("birthdate": "2023-08-12") и требованиях в Swagger ("birthdate": "2023-08-12T10:55:01.426Z")
    private String birthdate;
    private boolean isActive;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String middleName, int companyId, String email,
                    String url, String phone, String birthdate, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyId = companyId;
        this.email = email;
        this.url = url;
        this.phone = phone;
        this.birthdate = birthdate;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && companyId == employee.companyId && isActive == employee.isActive && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(middleName, employee.middleName) && Objects.equals(email, employee.email) && Objects.equals(url, employee.url) && Objects.equals(phone, employee.phone) && Objects.equals(birthdate, employee.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName, companyId, email, url, phone, birthdate, isActive);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", companyId=" + companyId +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
