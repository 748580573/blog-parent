package com.heng.common.impl;

import com.heng.common.bean.ParamObject;

public abstract class Component<T> {

    public abstract T process(ParamObject paramObjects);
}
