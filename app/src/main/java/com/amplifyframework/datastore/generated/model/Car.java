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

/** This is an auto generated class representing the Car type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Cars")
public final class Car implements Model {
  public static final QueryField ID = field("Car", "id");
  public static final QueryField TYPE = field("Car", "type");
  public static final QueryField MODEL = field("Car", "model");
  public static final QueryField GASOLINE = field("Car", "gasoline");
  public static final QueryField USER = field("Car", "carUserId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String type;
  private final @ModelField(targetType="String") String model;
  private final @ModelField(targetType="String") String gasoline;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "carUserId", type = User.class) User user;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getType() {
      return type;
  }
  
  public String getModel() {
      return model;
  }
  
  public String getGasoline() {
      return gasoline;
  }
  
  public User getUser() {
      return user;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Car(String id, String type, String model, String gasoline, User user) {
    this.id = id;
    this.type = type;
    this.model = model;
    this.gasoline = gasoline;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Car car = (Car) obj;
      return ObjectsCompat.equals(getId(), car.getId()) &&
              ObjectsCompat.equals(getType(), car.getType()) &&
              ObjectsCompat.equals(getModel(), car.getModel()) &&
              ObjectsCompat.equals(getGasoline(), car.getGasoline()) &&
              ObjectsCompat.equals(getUser(), car.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), car.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), car.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getType())
      .append(getModel())
      .append(getGasoline())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Car {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("model=" + String.valueOf(getModel()) + ", ")
      .append("gasoline=" + String.valueOf(getGasoline()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TypeStep builder() {
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
  public static Car justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Car(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      type,
      model,
      gasoline,
      user);
  }

    public interface TypeStep {
    BuildStep type(String type);
  }


  public interface BuildStep {
    Car build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep model(String model);
    BuildStep gasoline(String gasoline);
    BuildStep user(User user);
  }
  

  public static class Builder implements TypeStep, BuildStep {
    private String id;
    private String type;
    private String model;
    private String gasoline;
    private User user;
    @Override
     public Car build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Car(
          id,
          type,
          model,
          gasoline,
          user);
    }
    
    @Override
     public BuildStep type(String type) {
        Objects.requireNonNull(type);
        this.type = type;
        return this;
    }
    
    @Override
     public BuildStep model(String model) {
        this.model = model;
        return this;
    }
    
    @Override
     public BuildStep gasoline(String gasoline) {
        this.gasoline = gasoline;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        this.user = user;
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
    private CopyOfBuilder(String id, String type, String model, String gasoline, User user) {
      super.id(id);
      super.type(type)
        .model(model)
        .gasoline(gasoline)
        .user(user);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
    
    @Override
     public CopyOfBuilder model(String model) {
      return (CopyOfBuilder) super.model(model);
    }
    
    @Override
     public CopyOfBuilder gasoline(String gasoline) {
      return (CopyOfBuilder) super.gasoline(gasoline);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  
}
