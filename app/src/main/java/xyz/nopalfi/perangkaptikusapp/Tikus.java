package xyz.nopalfi.perangkaptikusapp;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Tikus implements Serializable {
    private Long id;

    private String sensor;

    private ZonedDateTime createdAt;

    public Tikus(Long id, String sensor, ZonedDateTime createdAt) {
        this.id = id;
        this.sensor = sensor;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tikus{" +
                "id=" + id +
                ", sensor='" + sensor + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
