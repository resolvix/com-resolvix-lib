package com.resolvix.lib.transform;

import com.resolvix.lib.transform.api.Transform;
import com.resolvix.lib.transform.api.TransformFactory1;

public abstract class BaseTransformFactory1Impl<T extends Transform<?, ?>, P>
    implements TransformFactory1<T, P>
{

    public abstract T get(P p);
}
