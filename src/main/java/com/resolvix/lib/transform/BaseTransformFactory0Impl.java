package com.resolvix.lib.transform;

import com.resolvix.lib.transform.api.Transform;
import com.resolvix.lib.transform.api.TransformFactory0;

public abstract class BaseTransformFactory0Impl<T extends Transform<?, ?>>
    implements TransformFactory0<T>
{

    public abstract T get();
}
