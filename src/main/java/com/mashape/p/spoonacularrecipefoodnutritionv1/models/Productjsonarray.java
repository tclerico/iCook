package com.mashape.p.spoonacularrecipefoodnutritionv1.models;/*
 * SpoonacularAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Productjsonarray 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4660754850625556618L;
    private String pluCode;
    private String title;
    private String upc;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("plu_code")
    public String getPluCode ( ) { 
        return this.pluCode;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("plu_code")
    public void setPluCode (String value) { 
        this.pluCode = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("title")
    public String getTitle ( ) { 
        return this.title;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("title")
    public void setTitle (String value) { 
        this.title = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("upc")
    public String getUpc ( ) { 
        return this.upc;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("upc")
    public void setUpc (String value) { 
        this.upc = value;
    }
 
}
 