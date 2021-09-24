package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Request type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Requests")
public final class Request implements Model {
  public static final QueryField ID = field("Request", "id");
  public static final QueryField NAME = field("Request", "name");
  public static final QueryField DESCRIPTION = field("Request", "description");
  public static final QueryField PHONE = field("Request", "phone");
  public static final QueryField IS_TAKEN = field("Request", "isTaken");
  public static final QueryField SERVICES = field("Request", "requestServicesId");
  public static final QueryField USER = field("Request", "requestUserId");
  public static final QueryField LOCATION = field("Request", "requestLocationId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String phone;
  private final @ModelField(targetType="Boolean") Boolean isTaken;
  private final @ModelField(targetType="Services") @BelongsTo(targetName = "requestServicesId", type = Services.class) Services services;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "requestUserId", type = User.class) User user;
  private final @ModelField(targetType="Location") @BelongsTo(targetName = "requestLocationId", type = Location.class) Location location;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getPhone() {
      return phone;
  }
  
  public Boolean getIsTaken() {
      return isTaken;
  }
  
  public Services getServices() {
      return services;
  }
  
  public User getUser() {
      return user;
  }
  
  public Location getLocation() {
      return location;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Request(String id, String name, String description, String phone, Boolean isTaken, Services services, User user, Location location) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.phone = phone;
    this.isTaken = isTaken;
    this.services = services;
    this.user = user;
    this.location = location;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Request request = (Request) obj;
      return ObjectsCompat.equals(getId(), request.getId()) &&
              ObjectsCompat.equals(getName(), request.getName()) &&
              ObjectsCompat.equals(getDescription(), request.getDescription()) &&
              ObjectsCompat.equals(getPhone(), request.getPhone()) &&
              ObjectsCompat.equals(getIsTaken(), request.getIsTaken()) &&
              ObjectsCompat.equals(getServices(), request.getServices()) &&
              ObjectsCompat.equals(getUser(), request.getUser()) &&
              ObjectsCompat.equals(getLocation(), request.getLocation()) &&
              ObjectsCompat.equals(getCreatedAt(), request.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), request.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getPhone())
      .append(getIsTaken())
      .append(getServices())
      .append(getUser())
      .append(getLocation())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Request {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("phone=" + String.valueOf(getPhone()) + ", ")
      .append("isTaken=" + String.valueOf(getIsTaken()) + ", ")
      .append("services=" + String.valueOf(getServices()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Request justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Request(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      phone,
      isTaken,
      services,
      user,
      location);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Request build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep description(String description);
    BuildStep phone(String phone);
    BuildStep isTaken(Boolean isTaken);
    BuildStep services(Services services);
    BuildStep user(User user);
    BuildStep location(Location location);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private String phone;
    private Boolean isTaken;
    private Services services;
    private User user;
    private Location location;
    @Override
     public Request build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Request(
          id,
          name,
          description,
          phone,
          isTaken,
          services,
          user,
          location);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep phone(String phone) {
        this.phone = phone;
        return this;
    }
    
    @Override
     public BuildStep isTaken(Boolean isTaken) {
        this.isTaken = isTaken;
        return this;
    }
    
    @Override
     public BuildStep services(Services services) {
        this.services = services;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep location(Location location) {
        this.location = location;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String phone, Boolean isTaken, Services services, User user, Location location) {
      super.id(id);
      super.name(name)
        .description(description)
        .phone(phone)
        .isTaken(isTaken)
        .services(services)
        .user(user)
        .location(location);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder phone(String phone) {
      return (CopyOfBuilder) super.phone(phone);
    }
    
    @Override
     public CopyOfBuilder isTaken(Boolean isTaken) {
      return (CopyOfBuilder) super.isTaken(isTaken);
    }
    
    @Override
     public CopyOfBuilder services(Services services) {
      return (CopyOfBuilder) super.services(services);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder location(Location location) {
      return (CopyOfBuilder) super.location(location);
    }
  }
  
}
