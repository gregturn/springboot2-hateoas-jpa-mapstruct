package com.asimio.demo.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Inventory generated by hbm2java
 */
@Entity
@Table(name="inventory"
    ,schema="public"
)
public class Inventory  implements java.io.Serializable {


     private int inventoryId;
     private Film film;
     private short storeId;
     private Date lastUpdate;
     private Set<Rental> rentals = new HashSet<Rental>(0);

    public Inventory() {
    }

	
    public Inventory(int inventoryId, Film film, short storeId, Date lastUpdate) {
        this.inventoryId = inventoryId;
        this.film = film;
        this.storeId = storeId;
        this.lastUpdate = lastUpdate;
    }
    public Inventory(int inventoryId, Film film, short storeId, Date lastUpdate, Set<Rental> rentals) {
       this.inventoryId = inventoryId;
       this.film = film;
       this.storeId = storeId;
       this.lastUpdate = lastUpdate;
       this.rentals = rentals;
    }
   
     @Id 
    
    @Column(name="inventory_id", unique=true, nullable=false)
    public int getInventoryId() {
        return this.inventoryId;
    }
    
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="film_id", nullable=false)
    public Film getFilm() {
        return this.film;
    }
    
    public void setFilm(Film film) {
        this.film = film;
    }
    
    @Column(name="store_id", nullable=false)
    public short getStoreId() {
        return this.storeId;
    }
    
    public void setStoreId(short storeId) {
        this.storeId = storeId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_update", nullable=false, length=29)
    public Date getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="inventory")
    public Set<Rental> getRentals() {
        return this.rentals;
    }
    
    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }




}


