package model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {
    private int id;
    @JsonProperty("isActive")
    private boolean isActive;
    private String name;
    private String description;

    public Company() {
    }

    public Company(int id, boolean isActive, String name, String description) {
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && isActive == company.isActive && Objects.equals(name, company.name) && Objects.equals(description, company.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, name, description);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
