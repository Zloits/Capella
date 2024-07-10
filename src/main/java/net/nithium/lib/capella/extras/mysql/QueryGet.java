package net.nithium.lib.capella.extras.mysql;

public interface QueryGet<T> {

    T get(SelectCall selectCall);
}
