package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface CrudInterface {
    Object create(BaseModel model);
    Object get(String id);

    Object update(String id, Object object);

    Object delete(String id);
}
