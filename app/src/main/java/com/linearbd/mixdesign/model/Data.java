package com.linearbd.mixdesign.model;

import java.io.Serializable;

public class Data implements Serializable {

    private String title;
    private String uid;
    private String id;
    private long date_time;
    private double sp_gr_ca;
    private double sp_gr_fa;
    private double fm_fa;
    private double bulk_density_ca;
    private int design_stn;
    private int maz_size_ca;
    private int slump_type;


    private int concrete_type;
    private int concrete_air_type;
    private int exposure;


    private int absorption_capacity_of_ca;
    private int surface_moisture_of_fa;


    public Data() {
    }

    public Data(String title) {
        this.title = title;
        this.date_time = System.currentTimeMillis();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate_time() {
        return date_time;
    }

    public void setDate_time(long date_time) {
        this.date_time = date_time;
    }

    public double getSp_gr_ca() {
        return sp_gr_ca;
    }

    public void setSp_gr_ca(double sp_gr_ca) {
        this.sp_gr_ca = sp_gr_ca;
    }

    public double getSp_gr_fa() {
        return sp_gr_fa;
    }

    public void setSp_gr_fa(double sp_gr_fa) {
        this.sp_gr_fa = sp_gr_fa;
    }

    public double getFm_fa() {
        return fm_fa;
    }

    public void setFm_fa(double fm_fa) {
        this.fm_fa = fm_fa;
    }

    public double getBulk_density_ca() {
        return bulk_density_ca;
    }

    public void setBulk_density_ca(double bulk_density_ca) {
        this.bulk_density_ca = bulk_density_ca;
    }

    public int getDesign_stn() {
        return design_stn;
    }

    public void setDesign_stn(int design_stn) {
        this.design_stn = design_stn;
    }

    public int getMaz_size_ca() {
        return maz_size_ca;
    }

    public void setMaz_size_ca(int maz_size_ca) {
        this.maz_size_ca = maz_size_ca;
    }

    public int getSlump_type() {
        return slump_type;
    }

    public void setSlump_type(int slump_type) {
        this.slump_type = slump_type;
    }

    public int getConcrete_type() {
        return concrete_type;
    }

    public void setConcrete_type(int concrete_type) {
        this.concrete_type = concrete_type;
    }

    public int getConcrete_air_type() {
        return concrete_air_type;
    }

    public void setConcrete_air_type(int concrete_air_type) {
        this.concrete_air_type = concrete_air_type;
    }

    public int getExposure() {
        return exposure;
    }

    public void setExposure(int exposure) {
        this.exposure = exposure;
    }

    public int getAbsorption_capacity_of_ca() {
        return absorption_capacity_of_ca;
    }

    public void setAbsorption_capacity_of_ca(int absorption_capacity_of_ca) {
        this.absorption_capacity_of_ca = absorption_capacity_of_ca;
    }

    public int getSurface_moisture_of_fa() {
        return surface_moisture_of_fa;
    }

    public void setSurface_moisture_of_fa(int surface_moisture_of_fa) {
        this.surface_moisture_of_fa = surface_moisture_of_fa;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
