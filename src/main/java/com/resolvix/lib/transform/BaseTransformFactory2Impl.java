package com.resolvix.lib.transform;

import com.resolvix.lib.transform.api.Transform;
import com.resolvix.lib.transform.api.TransformFactory2;

public abstract class BaseTransformFactory2Impl<T extends Transform<?, ?>, P, Q>
    implements TransformFactory2<T, P, Q>
{

    public abstract T get(P p, Q q);
}
