package com.gaia.app.smartwarehouse.classes;

/**
 * Created by praveen_gadi on 6/19/2016.
 */
public class Dataclass {
    String email,name,orgn,address,date;
    public Dataclass(String email, String name, String orgn,String address,String date)
    {
     this.email=email;
        this.name=name;
        this.orgn=orgn;
        this.address=address;
        this.date=date;
    }
public String getEmail()
{
    return email;

}
    public String getName()
    {
        return name;

    }
    public String getOrgn()
    {
        return orgn;

    }
    public String getAddress()
    {
        return address;

    }
    public String getDate()
    {
        return date;

    }

}
