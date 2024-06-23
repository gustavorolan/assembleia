package com.sicredi.assembleia.core.factory;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class SetCpfFactory {
    public static Set<Long> create() {
        return new TreeSet<>(Comparator.naturalOrder());
    }

    public static Set<Long> create(Long cpf) {
        Set<Long> set = create();
        set.add(cpf);
        return set;
    }

    public static Set<Long> create(String cpf) {
        return create(Long.parseLong(cpf));
    }
}
