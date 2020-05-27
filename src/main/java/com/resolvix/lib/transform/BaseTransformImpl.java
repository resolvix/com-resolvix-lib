package com.resolvix.lib.transform;

import com.resolvix.lib.transform.api.Transform;

public abstract class BaseTransformImpl<T, U>
    implements Transform<T, U>
{

    public abstract U transform(T t);
}
