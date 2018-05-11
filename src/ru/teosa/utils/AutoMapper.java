package ru.teosa.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.core.RowMapper;

public class AutoMapper implements RowMapper{
    Class clazz;
    Object obj_;
    boolean withcorrect = false;


    protected AutoMapper(){}

    public AutoMapper(Class clz, Object obj){
        obj_ = obj;
        clazz = clz;
        this.withcorrect = true;
    }

    public Object mapRow(ResultSet rs, int rownum) throws SQLException{
        Object ret;
        try{
            if(obj_ == null)
                ret = clazz.getConstructors()[0].newInstance();
            else
                ret = obj_;
        }
        catch(Exception ex){
            Logger.getLogger("error").error(ExceptionUtils.getStackTrace(ex));
            ret = new Object();
        }
        BeanWrapper wret = new BeanWrapperImpl(ret);
        for(int x = 1; x < rs.getMetaData().getColumnCount() + 1; x++){
            if(wret.isWritableProperty(rs.getMetaData().getColumnName(x).toLowerCase())){
                if(rs.getObject(rs.getMetaData().getColumnName(x).toLowerCase()) == null){
                    wret.setPropertyValue(rs.getMetaData().getColumnName(x).toLowerCase(), null);
                }
                else{
                    if(rs.getMetaData().getColumnTypeName(x).toLowerCase().trim().indexOf("smallint") != -1 && withcorrect){
                        wret.setPropertyValue(rs.getMetaData().getColumnName(x).toLowerCase(), rs.getShort(rs.getMetaData().getColumnName(x).toLowerCase()));
                    }
                    else{
                        wret.setPropertyValue(rs.getMetaData().getColumnName(x).toLowerCase(), rs.getObject(rs.getMetaData().getColumnName(x).toLowerCase()));
                    }
                }
            }
        }
        return ret;
    }

}
