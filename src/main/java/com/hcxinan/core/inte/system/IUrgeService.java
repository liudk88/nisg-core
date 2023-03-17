package com.hcxinan.core.inte.system;

import java.util.List;

public interface IUrgeService<T extends IUrge> {
    boolean savePushUrges(List<T> urges,String urge_type);
}
