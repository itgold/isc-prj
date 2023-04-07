package com.iscweb.persistence.model.jpa;

import com.google.common.collect.Sets;
import com.iscweb.common.model.entity.IUser;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.UserStatus;
import com.iscweb.persistence.model.BaseJpaTrackedEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.iscweb.common.model.metadata.UserStatus.ACTIVATED;

/**
 * A User that logs into the system. Users belong to {@link SchoolDistrictJpa} entities
 * and own certain roles.
 */
@Entity
@ToString
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(exclude = {"schoolDistrict"}, callSuper = true)
@SequenceGenerator(name = "seq_gen", sequenceName = "u_id_seq", allocationSize = 1)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "u_id")),
        @AttributeOverride(name = "created", column = @Column(name = "u_created", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "updated", column = @Column(name = "u_updated", columnDefinition = "timestamptz")),
        @AttributeOverride(name = "guid", column = @Column(name = "u_guid")),
})
public class UserJpa extends BaseJpaTrackedEntity implements IUser {

    private String email;
    private String password;

    private String firstName;
    private String lastName;

    private String imageUrl;

    private UserStatus status = UserStatus.REGISTERED;
    private ZonedDateTime statusDate;

    private SchoolDistrictJpa schoolDistrict;
    private Set<RoleJpa> roles = Sets.newHashSet();

    private ZonedDateTime lastLogin;
    private ZonedDateTime activationDate;

    @Transient
    @Override
    public String getName() {
        return getEmail();
    }

    @Transient
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Transient
    @Override
    public ConverterType getConverterType() {
        return ConverterType.USER;
    }

    @Transient
    public boolean isActive() {
        return getStatus().equals(ACTIVATED);
    }

    @Override
    @Column(name = "u_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    @Column(name = "u_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @Column(name = "u_first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    @Column(name = "u_last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    @Column(name = "u_image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    @Column(name = "u_status")
    @Enumerated(EnumType.STRING)
    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    @Column(name = "u_status_date", columnDefinition = "timestamptz")
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    public ZonedDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(ZonedDateTime statusDate) {
        this.statusDate = statusDate;
    }

    @ManyToOne
    @JoinColumn(name = "u_sd_id")
    public SchoolDistrictJpa getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(SchoolDistrictJpa company) {
        this.schoolDistrict = company;
    }

    @Override
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_joins",
            joinColumns = @JoinColumn(name = "urj_u_id", referencedColumnName = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "urj_ro_id", referencedColumnName = "ro_id"))
    public Set<RoleJpa> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleJpa> roles) {
        this.roles = roles;
    }

    @Override
    @Column(name = "u_last_login", columnDefinition = "timestamptz")
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    @Column(name = "u_activation_date", columnDefinition = "timestamptz")
    @Type(type = "org.hibernate.type.ZonedDateTimeType")
    public ZonedDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(ZonedDateTime activationDate) {
        this.activationDate = activationDate;
    }
}
